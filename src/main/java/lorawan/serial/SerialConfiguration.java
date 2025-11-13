package lorawan.serial;

import lorawan.Configuration;

public interface SerialConfiguration {

    String SERIAL_PORT = Configuration.SERIAL_PORT;
    int BAUDRATE = 9600;
    int DATA_BITS = 8;
    int STOP_BITS = 1;
    int PARITY_NONE = 0;
}
