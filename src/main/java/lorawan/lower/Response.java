package lorawan.lower;

public interface Response {

    String OK = "OK";
    String AT_ERROR = "AT_ERROR";
    String AT_PARAM_ERROR = "AT_PARAM_ERROR";
    String AT_BUSY_ERROR = "AT_BUSY_ERROR";
    String AT_TEST_PARAM_OVERFLOW = "AT_TEST_PARAM_OVERFLOW";
    String AT_NO_NETWORK_JOINED = "AT_NO_NETWORK_JOINED";

    String SEND_CONFIRMED = "+EVT:SEND_CONFIRMED";
    String JOINED = "+EVT:JOINED";
    String JOIN_FAILED = "+EVT:JOIN FAILED";
    String RXTIMEOUT = "rxTimeOut";
    String DUTY_CLYCLE_RESTRICTED = "AT_DUTYCYCLE_RESTRICTED";


}
