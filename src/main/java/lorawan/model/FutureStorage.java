package lorawan.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


public class FutureStorage {

    private Map<Integer, Future<String>> futureMap = new HashMap<>();
    private static FutureStorage instance;

    public static FutureStorage getInstance() {
        if(instance == null) {
            instance = new FutureStorage();
        }
        return instance;
    }

    private FutureStorage() {}


    public synchronized void push(int id, Future<String> future) {
        futureMap.put(id, future);
    }

    public Future<String> get(int id) {
        return futureMap.get(id);
    }


}
