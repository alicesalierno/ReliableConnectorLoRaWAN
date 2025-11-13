package lorawan.lower;

import java.io.IOException;

public interface LoRaWANModuleAPI {

    void AT() throws IOException;
    void setDeui(String deui) throws IOException;
    void setADR(String adr);
    void setDR(String dr);
    void setClass(String type);

    void getDeui();
    void getADR();
    void getDR();
    void getATClass();

    void getAT();
    void reset();
    void getVerboseLevel();
    void setVerboseLevel(String verboseLevel);
    void getLocalTime();
    void resetFS();
    void storeContext();
    void setAppEui(String appEui);
    void getAppEui();
    void getNwkKey();
    void setNwkKey(String nwkKey);
    void getAppKey();
    void setAppKey(String appKey);
    void getAppSKey();
    void setAppSKey(String appSKey);
    void getNwkSKey();
    void setNwkSKey(String nwkSKey);
    void getDeviceAddress();
    void setDeviceAddress(String deviceAddress);
    void getNwkId();
    void setNwkId(String nwkId);
    void linkc();
    void getVersion();
    void getBand();
    void setBand(String band);
    void getDutyCycle();
    void setDutyCycle(String dutyCycle);
    void getJoinDelay1();
    void setJoinDelay1(String joinDelay1);
    void getJoinDelay2();
    void setJoinDelay2(String joinDelay2);
    void getRx1Delay();
    void setRx1Delay(String rx1Delay);
    void getRx2Delay();
    void setRx2Delay(String rx2Delay);
    void getRx2DR();
    void setRx2DR(String rx2DR);
    void getRx2Frquency();
    void setRx2Frquency(String rx2Frquency);
    void getTxPower();
    void setTxPower(String txPower);
    void getPingSlot();
    void setPingSlot(String pingSlot);

    void startTTone();
    void startTRssi();
    void setTConf(int frequency, int txpower, int bandwidth, int sf, int codeRate, int lowNoiseAmplifier, int paBoost, int modulation, int paylen,
                  int freqdev, int lowdropt, int bt);

    void setTTx(int nb_packet_sent);
    void setTRx(int nb_packet_received);
    void startTTh(int FStrat, int fstop, int fdelta, int packetNb);
    void RFTestOff();
    void setCertification(int mode);
    void getBatteryLevel();






    void joinRequest(String activation);



    void send(String message, int port, int type);
}
