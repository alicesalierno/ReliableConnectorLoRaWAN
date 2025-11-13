package lorawan.upper;

import lorawan.lower.Parameter;
import lorawan.lower.Response;
import lorawan.model.FutureStorage;
import lorawan.model.JoinMessage;
import lorawan.model.Message;
import lorawan.model.MessageState;
import lorawan.persistence.RedisManager;
import lorawan.serial.SendHandler;
import lorawan.serial.SerialReader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ReliableLoRaWANConnector implements ReliableLoRaWANConnectorAPI {
    static RedisManager redisManager = RedisManager.getInstance();

    FutureStorage futureStorage = FutureStorage.getInstance();
    ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private ReliableLoRaWANConnector() {
        int n = counter.incrementAndGet();
        System.out.println("Costruttore ReliableLoRaWANConnector chiamato! Conteggio: " + n);
        if (n > 1) {
            throw new IllegalStateException("Costruttore invocato più di una volta!");
        }
        initialize();
    }

    private static class Holder {
        private static final ReliableLoRaWANConnector INSTANCE = new ReliableLoRaWANConnector();
    }

    public static ReliableLoRaWANConnector getInstance() {
        return Holder.INSTANCE;
    }

    private void initialize() {
        System.out.println("Initializing ReliableLoRaWANConnector");

        ReliableLoRaWANConnector.setAck(false);
        ReliableLoRaWANConnector.setJoined(false);
        ReliableLoRaWANConnector.setJoinFailed(false);
        ReliableLoRaWANConnector.setRxTimeout(0);
        ReliableLoRaWANConnector.setBusyError(false);
        ReliableLoRaWANConnector.setDutyCycle(false);
        //ReliableLoRaWANConnector.delJoinRequestList();

        SerialReader.getInstance().start();
        SendHandler.getInstance().start();

    }

    @Override
    public String subscribe(String activationMode)  {
        String mode = Parameter.ACTIVATION_OTAA; //default mode
        if (activationMode.equalsIgnoreCase("OTAA") || activationMode.equalsIgnoreCase("1")) {
            mode = Parameter.ACTIVATION_OTAA;
        }else if (activationMode.equalsIgnoreCase("ABP" )|| activationMode.equalsIgnoreCase("0")) {
            mode = Parameter.ACTIVATION_ABP;
        }

        // richiesta di join
        try {
            System.out.println("richiesta");
            redisManager.insertJoinRequest(new JoinMessage(mode));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // attendo finchè non ricevo JOINED oppure JOIN_FAILED
        while (!getJoined() && !getJoinFailed()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        if (getJoined()){
            return Response.JOINED;
        }else{
            return Response.JOIN_FAILED;
        }
    }
/*
    @Override
    public void publish(int quality, String message) {

        int port = 2;

        Message messageObj = new Message(message, quality, port);
        //messageStorage.push(messageObj);
        redisManager.insertMessage(messageObj);
        System.out.println("message published");

    }*/

    @Override
    public Future<String> publish(int quality, String message) {
        int port = 2;

        Message messageObj = new Message(message, quality, port);

        int messageID = redisManager.insertMessage(messageObj);
        System.out.println("message published");

        Callable<String> t = ()-> checkMessageState(messageID);
        Future<String> future = executor.submit(t);

        futureStorage.push(messageID, future); // non servirebbe

        return future;

    }


    //chiama checkMessage ricorsivamente finchè non trova il messaggio nella lista
    private String checkMessageState(int messageID){

        // deve verificare se il messaggio messageID è stato consegnato

        String state = redisManager.getMessageState(messageID);

        while(!state.equals(MessageState.SENT)) {
            state = redisManager.getMessageState(messageID);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return MessageState.SENT;


    }


    public static void setAck(boolean ack) {
        redisManager.set("ack", ack+"");
    }

    public static void setJoined(boolean joined) {
        redisManager.set("joined", joined+"");
    }

    public static void setJoinFailed(boolean joinFailed) {
        redisManager.set("join_failed", joinFailed+"");
    }

    public static boolean getAck() {
        return redisManager.getBooleanValue("ack");
    }

    public static boolean getJoined() {
        return redisManager.getBooleanValue("joined");
    }

    public static boolean getJoinFailed() {
        return redisManager.getBooleanValue("join_failed");
    }

    public static void setRxTimeout(int rxTimeout) {
        redisManager.set("rxtimeout", rxTimeout+"");
    }

    public static int getRxTimeout() {
        return redisManager.getIntValue("rxtimeout");
    }

    public static boolean getDutyCycle(){
        return redisManager.getBooleanValue("dutycycle");
    }

    public static void setDutyCycle(boolean dutyCycle) {
        redisManager.set("dutycycle", dutyCycle+"");
    }

    public static void setBusyError(boolean busyError) {
        redisManager.set("busyerror", busyError+"");
    }
    public static boolean getBusyError() {
        return redisManager.getBooleanValue("busyerror");
    }

    public static void delJoinRequestList() {
        redisManager.delJoinRequestList();
    }
}
