package lorawan.lower;

import lorawan.serial.SendHandler;
import lorawan.serial.SerialManager;
import lorawan.serial.SerialReader;
import lorawan.upper.ReliableLoRaWANConnector;

import java.io.IOException;

public class TestAtCommand {
    public static void main(String[] args) throws IOException, InterruptedException {
        LoRaWANModule loRaWANModule = LoRaWANModule.getInstance();

        SerialManager serialManager = SerialManager.getInstance();
        SerialReader reader = SerialReader.getInstance();
        SendHandler writer = SendHandler.getInstance();


        loRaWANModule.AT();
        Thread.sleep(5000);

        loRaWANModule.setDeui("30:36:36:32:77:39:7c:07");
        Thread.sleep(5000);
        loRaWANModule.setADR("0");
        Thread.sleep(5000);
        loRaWANModule.setDR("5");
        Thread.sleep(5000);
        while(!ReliableLoRaWANConnector.getJoined()){
            loRaWANModule.joinRequest(Parameter.ACTIVATION_OTAA);
            Thread.sleep(100);
            while (!ReliableLoRaWANConnector.getJoined() && !ReliableLoRaWANConnector.getJoinFailed()){
                Thread.sleep(100);
            }
        }
        System.out.println("joined");

        loRaWANModule.send("ciao", 2, 0);
        System.out.println("send");
        while(!ReliableLoRaWANConnector.getAck()){
            Thread.sleep(100);
        }
        System.out.println("ack received");



    }
}
