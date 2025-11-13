# ReliableConnectorLoRaWAN

**ReliableConnectorLoRaWAN** is a Java library designed to maintain a **persistent communication channel** over a **LoRaWAN network**.  
Message publishing is **decoupled from transmission**, meaning messages are first stored in a database and then sent asynchronously.

---

## 🛰️ Quality of Service Levels

- **QoS-1**: *Unconfirmed messages*, semantics **at most once**  
- **QoS0**: *Confirmed messages*, semantics **at most once**  
- **QoS1**: *Confirmed messages*, semantics **at least once** (all published messages will be successfully transmitted and received by the destination)

---

## 🧩 API

- `subscribe(activationMode)` — performs the join request to the LoRaWAN network  
- `publish(qos, message)` — publishes a message to the network  

---

## ⚙️ Usage

1. Clone the repository  
2. Install a Redis instance using Docker  
3. Edit the `config.properties` file and update the `"redis"` and `"serialPort"` fields accordingly  

---

## 💡 Example Usage

```java
public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
    LoRaWANModule loRaWANModule = LoRaWANModule.getInstance();
    ReliableLoRaWANConnector reliableLoRaWANConnector = ReliableLoRaWANConnector.getInstance();
    String joinResp = "";

    /* Configuration */
    loRaWANModule.AT();
    Thread.sleep(5000);
    loRaWANModule.setDeui(Configuration.DEVUI);
    Thread.sleep(5000);
    loRaWANModule.setADR("0");
    Thread.sleep(5000);
    loRaWANModule.setDR("5");
    Thread.sleep(5000);

    while (!joinResp.contains("JOINED")) {
        joinResp = reliableLoRaWANConnector.subscribe("1");
        ReliableLoRaWANConnector.setJoinFailed(false);
        System.out.println(joinResp);
        Thread.sleep(1000);
    }

    loRaWANModule.setClass("C");

    Future<String> response = publish(Configuration.QUALITY, "hello");
    // Other operations
    System.out.println(response.get());
}
