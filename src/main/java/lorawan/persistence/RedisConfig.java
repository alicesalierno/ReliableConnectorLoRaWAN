package lorawan.persistence;

import lorawan.Configuration;

public interface RedisConfig {

    String address = Configuration.REDIS_ADDRESS;

}
