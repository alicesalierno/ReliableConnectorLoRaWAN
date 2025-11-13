package lorawan.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public class Message implements Serializable {
    String body;
    int quality;
    int port;

    private static class MessageWrapper{
        JsonObject body;
        int quality;
        int port;

        private MessageWrapper(){}
    }

    public Message() {}

    public Message(String body, int quality, int port) {
        this.body = body;
        this.quality = quality;
        this.port = port;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body=" + body +
                ", quality=" + quality +
                ", port=" + port +
                '}';
    }

    public static String messageToJson(Message message) {
        return "{" +
                "body: " + message.body +
                ",\n quality: " + message.quality +
                ",\n port: " + message.port +
                '}';
    }

    public static Message messageFromJson(String messageJson){
        System.out.println(messageJson);
        Gson gson = new Gson();
        //MessageWrapper wrapper = gson.fromJson(messageJson, MessageWrapper.class);
        //return new Message(wrapper.body.toString(), wrapper.quality, wrapper.port);
        return  gson.fromJson(messageJson, Message.class);
    }



}
