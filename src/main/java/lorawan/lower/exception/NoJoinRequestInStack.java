package lorawan.lower.exception;

public class NoJoinRequestInStack extends Exception {

    @Override
    public String getMessage() {
        return "Non sono presenti richieste di Join ";
    }
}
