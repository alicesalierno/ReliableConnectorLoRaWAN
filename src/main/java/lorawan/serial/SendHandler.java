package lorawan.serial;

import lorawan.lower.LoRaWANModule;
import lorawan.lower.Parameter;
import lorawan.model.Message;
import lorawan.persistence.RedisManager;
import lorawan.upper.ReliableLoRaWANConnector;

/**
 * Gestisce le richieste di send
 */
public class SendHandler implements Runnable{

    LoRaWANModule loRaWANModule = LoRaWANModule.getInstance();
    private volatile boolean started;
    RedisManager redisManager = RedisManager.getInstance();
    private SendHandler() {}

    private static class Holder {
        private static final SendHandler INSTANCE = new SendHandler();
    }

    public static SendHandler getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void start() {
        if (!started) {
            started = true;
            new Thread(this, "SendHandler").start();
        }
    }

    @Override
    public void run() {
        System.out.println("STARTED");


        while(true){
            if (!ReliableLoRaWANConnector.getJoined()){
                ReliableLoRaWANConnector.setJoinFailed(false);
                try {
                    String activationMode = redisManager.getJoinRequest();
                    System.out.println(activationMode);
                    loRaWANModule.joinRequest(activationMode);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            if (ReliableLoRaWANConnector.getJoined()){

                ReliableLoRaWANConnector.setAck(false);
                ReliableLoRaWANConnector.setRxTimeout(0);

                Message messageObj = null;

                while(messageObj == null){

                    messageObj = redisManager.getMessageOldest();
                }

                System.out.println(messageObj.getBody());
                int quality = messageObj.getQuality();

                if (quality == 0 || quality == 1){ //se confirmed QoS0
                    loRaWANModule.send(messageObj.getBody(), messageObj.getPort(), 1);

                    while (!ReliableLoRaWANConnector.getAck() && ReliableLoRaWANConnector.getRxTimeout() < Parameter.MAX_RXTIMEOUT && !ReliableLoRaWANConnector.getBusyError() && !ReliableLoRaWANConnector.getDutyCycle()){

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    if (ReliableLoRaWANConnector.getAck() || (ReliableLoRaWANConnector.getRxTimeout() > Parameter.MAX_RXTIMEOUT && quality == 0)){
                        redisManager.removeMessage();

                    }else if (ReliableLoRaWANConnector.getBusyError()){
                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        ReliableLoRaWANConnector.setBusyError(false);


                    }else if (ReliableLoRaWANConnector.getDutyCycle()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        ReliableLoRaWANConnector.setDutyCycle(false);
                    }


                } else{ // se unconfirmed
                    loRaWANModule.send(messageObj.getBody(), messageObj.getPort(), 0);
                    redisManager.removeMessage();


                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }

            }else { // se non è join aspetto che potrebbe diventarlo
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }



            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }



    }
}
