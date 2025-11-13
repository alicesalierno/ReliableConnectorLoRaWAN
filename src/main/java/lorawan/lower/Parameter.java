package lorawan.lower;

public interface Parameter {

    String ACTIVATION_OTAA = "1";
    String ACTIVATION_ABP = "0";
    int SEND_CONFIRMED_MODE = 1;
    int SEND_UNCONFIRMED_MODE = 0;
    int PORT = 2;
    String CLASS_A = "A";
    String CLASS_B = "B";
    String CLASS_C = "C";

    int SEND_TIMEOUT  = 60;
    int MAX_RXTIMEOUT = 16;
}
