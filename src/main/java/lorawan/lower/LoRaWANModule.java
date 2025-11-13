package lorawan.lower;

import lorawan.serial.SerialManager;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoRaWANModule implements LoRaWANModuleAPI {

    SerialManager serialManager = SerialManager.getInstance();
    String terminator = "\r";
    OutputStream outputStream;

    private LoRaWANModule() {
        outputStream = serialManager.getOutputStream();
    }

    private static class Holder {
        private static final LoRaWANModule INSTANCE = new LoRaWANModule();
    }

    public static LoRaWANModule getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void AT() throws IOException {
        String command = "AT";
        writeToSerial(command);
    }

    private synchronized void writeToSerial(String command)  {
        String data = addTerminator(command);
        byte[] buffer = data.getBytes();
        try {
            outputStream.write(buffer, 0, buffer.length);
            System.out.println("Scritto: " + data);
        }catch (NullPointerException | IndexOutOfBoundsException | IOException e){
           System.err.println(e);
        }
    }


    @Override
    public void setDeui(String deui) throws IOException {
        String command = "AT+DEUI="+deui;
        writeToSerial(command);
    }

    @Override
    public void setADR(String adr) {
        String command = "AT+ADR="+adr;
        writeToSerial(command);
    }

    @Override
    public void setDR(String dr) {
        String command = "AT+DR="+dr;
        writeToSerial(command);
    }

    @Override
    public void setClass(String type) {
        String command = "AT+CLASS="+type.toUpperCase();
        writeToSerial(command);
    }

    @Override
    public void getDeui() {
        String command = "AT+DEUI=?";
        writeToSerial(command);
    }

    @Override
    public void getADR() {
        String command = "AT+ADR=?";
        writeToSerial(command);
    }

    @Override
    public void getDR() {
        String command = "AT+DR=?";
        writeToSerial(command);

    }

    @Override
    public void getATClass() {
        String command = "AT+CLASS=?";
        writeToSerial(command);
    }

    @Override
    public void getAT() {
        String command = "AT?";
        writeToSerial(command);
    }

    @Override
    public void reset() {
        String command = "ATZ";
        writeToSerial(command);
    }

    @Override
    public void getVerboseLevel() {
        String command = "AT+VL=?";
        writeToSerial(command);
    }

    @Override
    public void setVerboseLevel(String verboseLevel) {
        String command = "AT+VL="+verboseLevel;
        writeToSerial(command);
    }

    @Override
    public void getLocalTime() {
        String command = "AT+LTIME=?";
        writeToSerial(command);
    }

    @Override
    public void resetFS() {
        String command = "AT+RFS";
        writeToSerial(command);
    }

    @Override
    public void storeContext() {
        String command = "AT+CS";
        writeToSerial(command);
    }

    @Override
    public void setAppEui(String appEui) {
        String command = "AT+APPEUI="+appEui;
        writeToSerial(command);
    }

    @Override
    public void getAppEui() {
        String command = "AT+APPEUI=?";
        writeToSerial(command);
    }

    @Override
    public void getNwkKey() {
        String command = "AT+NWKKEY=?";
        writeToSerial(command);
    }

    @Override
    public void setNwkKey(String nwkKey) {
        String command = "AT+NWKKEY="+nwkKey;
        writeToSerial(command);
    }

    @Override
    public void getAppKey() {
        String command = "AT+APPKEY=?";
        writeToSerial(command);
    }

    @Override
    public void setAppKey(String appKey) {
        String command = "AT+APPKEY="+appKey;
        writeToSerial(command);
    }

    @Override
    public void getAppSKey() {
        String command = "AT+APPSKEY=?";
        writeToSerial(command);
    }

    @Override
    public void setAppSKey(String appSKey) {
        String command = "AT+APPSKEY="+appSKey;
        writeToSerial(command);
    }

    @Override
    public void getNwkSKey() {
        String command = "AT+NWKSKEY=?";
        writeToSerial(command);
    }

    @Override
    public void setNwkSKey(String nwkSKey) {
        String command = "AT+NWKSKEY="+nwkSKey;
        writeToSerial(command);
    }

    @Override
    public void getDeviceAddress() {
        String command = "AT+DADDR=?";
        writeToSerial(command);
    }

    @Override
    public void setDeviceAddress(String deviceAddress) {
        String command = "AT+DADDR="+deviceAddress;
        writeToSerial(command);
    }

    @Override
    public void getNwkId() {
        String command = "AT+NWKID=?";
        writeToSerial(command);
    }

    @Override
    public void setNwkId(String nwkId) {
        String command = "AT+NWKID="+nwkId;
        writeToSerial(command);
    }

    @Override
    public void linkc() {
        String command = "AT+LINKC";
        writeToSerial(command);
    }

    @Override
    public void getVersion() {
        String command = "AT+VER=?";
        writeToSerial(command);
    }

    @Override
    public void getBand() {
        String command = "AT+BAND=?";
        writeToSerial(command);
    }

    @Override
    public void setBand(String band) {
        String command = "AT+BAND="+band;
        writeToSerial(command);
    }

    @Override
    public void getDutyCycle() {
        String command = "AT+DCS=?";
        writeToSerial(command);
    }

    @Override
    public void setDutyCycle(String dutyCycle) {
        String command = "AT+DCS="+dutyCycle;
        writeToSerial(command);
    }

    @Override
    public void getJoinDelay1() {
        String command = "AT+JN1DL=?";
        writeToSerial(command);
    }

    @Override
    public void setJoinDelay1(String joinDelay1) {
        String command = "AT+JN1DL="+joinDelay1;
        writeToSerial(command);
    }

    @Override
    public void getJoinDelay2() {
        String command = "AT+JN2DL=?";
        writeToSerial(command);
    }

    @Override
    public void setJoinDelay2(String joinDelay2) {
        String command = "AT+JN2DL="+joinDelay2;
        writeToSerial(command);
    }

    @Override
    public void getRx1Delay() {
        String command = "AT+RX1DL=?";
        writeToSerial(command);
    }

    @Override
    public void setRx1Delay(String rx1Delay) {
        String command = "AT+RX1DL="+rx1Delay;
        writeToSerial(command);
    }

    @Override
    public void getRx2Delay() {
        String command = "AT+RX2DL=?";
        writeToSerial(command);
    }

    @Override
    public void setRx2Delay(String rx2Delay) {
        String command = "AT+RX2DL="+rx2Delay;
        writeToSerial(command);
    }

    @Override
    public void getRx2DR() {
        String command = "AT+RX2DR=?";
        writeToSerial(command);
    }

    @Override
    public void setRx2DR(String rx2DR) {
        String command = "AT+RX2DR="+rx2DR;
        writeToSerial(command);
    }

    @Override
    public void getRx2Frquency() {
        String command = "AT+RX2FQ=?";
        writeToSerial(command);
    }

    @Override
    public void setRx2Frquency(String rx2Frquency) {
        String command = "AT+RX2FQ="+rx2Frquency;
        writeToSerial(command);
    }

    @Override
    public void getTxPower() {
        String command = "AT+TXP=?";
        writeToSerial(command);
    }

    @Override
    public void setTxPower(String txPower) {
        String command = "AT+TXP="+txPower;
        writeToSerial(command);
    }

    @Override
    public void getPingSlot() {
        String command = "AT+PGSLOT=?";
        writeToSerial(command);
    }

    @Override
    public void setPingSlot(String pingSlot) {
        String command = "AT+PGSLOT="+pingSlot;
        writeToSerial(command);
    }

    @Override
    public void startTTone() {
        String command = "AT+TTONE";
        writeToSerial(command);
    }

    @Override
    public void startTRssi() {
        String command = "AT+TRSSI";
        writeToSerial(command);
    }

    @Override
    public void setTConf(int frequency, int txpower, int bandwidth, int sf,
                         int codeRate, int lowNoiseAmplifier, int paBoost,
                         int modulation, int paylen, int freqdev, int lowdropt, int bt) {
        String command = "AT+TCONF="+frequency+":"+txpower+":"+bandwidth+":"+codeRate+":"+lowNoiseAmplifier+":"+paBoost+
                ":"+modulation+":"+paylen+":"+freqdev+":"+lowdropt+":"+bt;
        writeToSerial(command);
    }

    @Override
    public void setTTx(int nb_packet_sent) {
        String command = "AT+TTX="+nb_packet_sent;
        writeToSerial(command);
    }

    @Override
    public void setTRx(int nb_packet_received) {
        String command = "AT+TRX="+nb_packet_received;
        writeToSerial(command);
    }

    @Override
    public void startTTh(int FStrat, int fstop, int fdelta, int packetNb) {
        String command = "AT+TTH="+FStrat+":"+fstop+":"+fdelta+":"+packetNb;
        writeToSerial(command);
    }

    @Override
    public void RFTestOff() {
        String command = "AT+TOFF";
        writeToSerial(command);
    }

    @Override
    public void setCertification(int mode) {
        String command = "AT+CERTIF="+mode;
        writeToSerial(command);
    }

    @Override
    public void getBatteryLevel() {
        String command = "AT+BAT=?";
        writeToSerial(command);
    }

    private String addTerminator(String command) {
        return command + terminator;
    }

    @Override
    public void joinRequest(String activation) {
        String command = "AT+JOIN="+activation;
        writeToSerial(command);
    }

    @Override
    public void send(String message, int port, int type) {
        String command = "AT+SEND="+port+":"+type+":"+stringToHex(message);
        writeToSerial(command);
    }

    private String stringToHex(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        String hex = Hex.encodeHexString(bytes).toUpperCase();
        return hex;
    }

}
