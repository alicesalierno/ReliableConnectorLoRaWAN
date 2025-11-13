package lorawan.upper;

import java.util.concurrent.Future;

public interface ReliableLoRaWANConnectorAPI {

    /**
     * Richiesta di join,
     * ritorna il messaggio ricevuto dal gateway
     *
     * @param activationMode OTAA o ABP
     */
    String subscribe(String activationMode);


    //void publish(int quality, String message);


    /**
     * Pubblica i messaggi
     * ritorna una Future che viene popolata quando l'ack viene ricevuto
     * @param quality indica la qualità del servizio
     *                0 basta che il messaggio venga inviato
     *                1 il messaggio deve arrivare almeno una volta
     *                2 messaggio unconfirmed
     * @param message messaggio da inviare
     *
     */
    Future<String> publish(int quality, String message);

}
