package lorawan.persistence;

import lorawan.lower.exception.MessageNotFound;
import lorawan.model.JoinMessage;
import lorawan.model.Message;
import lorawan.model.MessageState;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

    JedisPool jedisPool;

    String messagesState = "messagesState";
    String joinRequest = "joinRequest";

    private RedisManager() {

        System.out.println(RedisConfig.address);
        this.jedisPool = new JedisPool(new JedisPoolConfig(), RedisConfig.address);

        try (Jedis jedis = jedisPool.getResource()) {
            String sent = jedis.get("sent"); // indice dei messaggi inviati
            if (sent == null) {
                jedis.set("sent", "0");
            }
            String queued = jedis.get("queued"); // indice dei messaggi accodati
            if (queued == null) {
                jedis.set("queued", "0");
            }
        }


    }

    private static class Holder{
        private static final RedisManager INSTANCE = new RedisManager();
    }

    public static RedisManager getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized String set(String key, String value){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    public synchronized String get(String key){
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    public synchronized boolean getBooleanValue(String key){
        try (Jedis jedis = jedisPool.getResource()) {
            String res = jedis.get(key);
            return "true".equals(res);
        }
    }

    public synchronized int getIntValue(String key){
        try (Jedis jedis = jedisPool.getResource()) {
            String res = jedis.get(key);
            return Integer.parseInt(res);
        }
    }

    public synchronized int insertMessage(Message message){
        String messageJson = Message.messageToJson(message);

        try (Jedis jedis = jedisPool.getResource()) {
            int index = Integer.parseInt(jedis.get("queued"));
            jedis.set("message:" + index, messageJson);

            MessageState messageState = new MessageState(index, MessageState.QUEUED);
            setMessageState(messageState);

            index++;
            jedis.set("queued", String.valueOf(index));
            return index - 1;
        }
    }

    public synchronized Message getMessageOldest(){
        try (Jedis jedis = jedisPool.getResource()) {
            int index = Integer.parseInt(jedis.get("sent"));
            String res = jedis.get("message:" + index);
            if (res == null) {
                return null;
            }
            Message message = Message.messageFromJson(res);
            System.out.println(message);
            return message;
        }
    }

    public synchronized void removeMessage(){
        try (Jedis jedis = jedisPool.getResource()) {
            int index = Integer.parseInt(jedis.get("sent"));
            jedis.del("message:" + index);

            MessageState messageState = new MessageState(index, MessageState.SENT);
            setMessageState(messageState);

            index++;
            jedis.set("sent", String.valueOf(index));
        }
    }

    public synchronized int getSent(){
        try (Jedis jedis = jedisPool.getResource()) {
            return Integer.parseInt(jedis.get("sent"));
        }
    }

    public synchronized int getQueued(){
        try (Jedis jedis = jedisPool.getResource()) {
            return Integer.parseInt(jedis.get("queued"));
        }
    }

    public synchronized void setMessageState(MessageState messageState) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (jedis.lindex(messagesState, messageState.getMessageId()) == null) {
                jedis.rpush(messagesState, messageState.getMessage());
            } else {
                jedis.lset(messagesState, messageState.getMessageId(), messageState.getMessage());
            }
        }
    }

    public String getMessageState(int messageID){
        try (Jedis jedis = jedisPool.getResource()) {
            try {
                if (jedis.lindex(messagesState, messageID ) == null) {
                    throw new MessageNotFound();
                }
            } catch (MessageNotFound e) {
                return e.getMessage();
            }
            return jedis.lindex(messagesState, messageID );
        }
    }

    public void insertJoinRequest(JoinMessage message) throws InterruptedException {
        System.out.println("insertJoinRequest");
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.rpush(joinRequest, message.getActivationMode());
        }

    }

    public synchronized String getJoinRequest() throws InterruptedException {
        String activationMode;
        while (true) {
            try (Jedis jedis = jedisPool.getResource()) {
                activationMode = jedis.rpop(joinRequest);
            }

            if (activationMode != null) {
                System.out.println(activationMode);
                return activationMode;
            }

            Thread.sleep(1000);
        }
    }

    public synchronized long delJoinRequestList(){
        long result = 0;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.del(joinRequest);
        }

        return result;
    }

    public void shutdown() {
        if (!jedisPool.isClosed()) {
            jedisPool.close();
        }
    }
}
