package lorawan.persistence;

import lorawan.model.Message;
import redis.clients.jedis.Jedis;

public class TestRedis{

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis(RedisConfig.address);


        RedisManager redisManager = RedisManager.getInstance();


        for (int i = 0; i<5; i++){
            Message message = new Message("ciao"+i, 1, 2);
            redisManager.insertMessage(message);
            Thread.sleep(1000);
        }

        Message message1 = redisManager.getMessageOldest();
        System.out.println(message1.getBody());
        Thread.sleep(5000);
        redisManager.removeMessage();

        Message message2 = redisManager.getMessageOldest();
        System.out.println(message2.getBody());





    }
}
