package lorawan.serial;

import com.fazecast.jSerialComm.SerialPortIOException;
import lorawan.lower.Response;
import lorawan.upper.ReliableLoRaWANConnector;

import java.io.IOException;
import java.io.InputStream;


/**
 * Gestisce il flusso di input dalla seriale
 */
public class SerialReader implements Runnable {

    SerialManager serialManager = SerialManager.getInstance();
    InputStream inputStream;


    private SerialReader() {}

    private static class Holder {
        private static final SerialReader INSTANCE = new SerialReader();
    }

    public static SerialReader getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void start() {
        if (inputStream == null) {
            inputStream = serialManager.getInputStream();
            new Thread(this, "SerialReader").start();
        }
    }

    @Override
    public void run() {
        readBySerial();


    }

    private void readBySerial(){

        try {
            StringBuilder builder = new StringBuilder();
            int c = 0;
            while (true) {

                try {
                    c = inputStream.read();
                }
                catch (SerialPortIOException e) {}

                if (c == -1) {
                    break;
                }

                if (c == '\n') { // terminatore trovato
                    String data = builder.toString().trim();
                    System.out.println("Ricevuto: " + data);

                    new Thread(() -> checkResponse(data)).start();

                    builder.setLength(0); // resetta il buffer per il prossimo messaggio
                } else {
                    builder.append((char) c);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



    private void checkResponse(String data){

        switch (data){
            case Response.SEND_CONFIRMED:
                ReliableLoRaWANConnector.setAck(true);
                break;
            case Response.JOINED:
                ReliableLoRaWANConnector.setJoined(true);
                ReliableLoRaWANConnector.setJoinFailed(false);
                break;
            case Response.JOIN_FAILED:
                ReliableLoRaWANConnector.setJoinFailed(true);
                ReliableLoRaWANConnector.setJoined(false);
                break;
            case Response.AT_NO_NETWORK_JOINED:
                ReliableLoRaWANConnector.setJoined(false);
            case Response.AT_BUSY_ERROR:
                ReliableLoRaWANConnector.setBusyError(true);
            case Response.DUTY_CLYCLE_RESTRICTED:
                ReliableLoRaWANConnector.setDutyCycle(true);
            default:
                break;

        }

        if (data.contains(Response.RXTIMEOUT)){
            int rx = ReliableLoRaWANConnector.getRxTimeout();
            ReliableLoRaWANConnector.setRxTimeout(rx+1);
        }
    }
}
