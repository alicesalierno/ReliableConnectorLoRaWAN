package lorawan.lower.exception;

public class MessageNotFound extends Exception {

    @Override
    public String getMessage() {
        return "Message not found";
    }
}
