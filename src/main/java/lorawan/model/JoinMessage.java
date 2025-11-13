package lorawan.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class JoinMessage implements Serializable {

    private String activationMode;

    public JoinMessage(String activationMode) {
        this.activationMode = activationMode;
    }

    public JoinMessage() {
    }

    public String getActivationMode() {
        return activationMode;
    }

    public void setActivationMode(String activationMode) {
        this.activationMode = activationMode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JoinMessage that)) return false;
        return activationMode == that.activationMode;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(activationMode);
    }

    @Override
    public String toString() {
        return "JoinMessage{" +
                "activationMode=" + activationMode +
                '}';
    }

    public static String toJson(JoinMessage joinMessage) {
        return "{\"activationMode\":\"" + joinMessage.getActivationMode() + "\"}";
    }

    public static JoinMessage fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JoinMessage.class);
    }
}
