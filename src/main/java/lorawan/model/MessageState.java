package lorawan.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class MessageState implements Serializable {

    private int messageId;
    private String message;

    public static final String SENT = "sent";
    public static final String QUEUED = "queued";

    public MessageState(int messageId, String message) {
        this.messageId = messageId;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MessageState that)) return false;
        return messageId == that.messageId && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, message);
    }

    @Override
    public String toString() {
        return "MessageHistory{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                '}';
    }

    public static String convertToJson(MessageState messageState) {
        return "{" +
                "messageId=" + messageState.getMessageId() +
                ",\n message='" + messageState.getMessage()  +
                '}';
    }

    public static MessageState convertFromJson(String messageJson) {
        Gson gson = new Gson();
        return gson.fromJson(messageJson, MessageState.class);
    }
}
