package lorawan;

import static lorawan.PropertiesReader.read;

public interface Configuration {

    String REDIS_ADDRESS = read("redis");
    String SERIAL_PORT= read("serialPort");
    int REQUIRED_QUALITY = Integer.parseInt(read("requiredQuality"));
    int INTERVAL_TIME = Integer.parseInt(read("intervalTime"));
    String DEVUI=read("devui");
}
