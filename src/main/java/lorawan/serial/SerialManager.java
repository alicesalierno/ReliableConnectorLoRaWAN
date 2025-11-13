package lorawan.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialManager {

    private InputStream inputStream;
    private OutputStream outputStream;

    private SerialManager(){
        SerialPort serialPort = SerialPort.getCommPort(SerialConfiguration.SERIAL_PORT);

        serialPort.setComPortParameters(SerialConfiguration.BAUDRATE, SerialConfiguration.DATA_BITS,
                SerialConfiguration.STOP_BITS, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);


        if (!serialPort.isOpen()) {
            serialPort.openPort();
        }

        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
    }

    private static class Holder{
        private static final SerialManager INSTANCE = new SerialManager();
    }

    public static SerialManager getInstance(){
        return Holder.INSTANCE;
    }


    public InputStream getInputStream() {
        return inputStream;
    }
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
