package lorawan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {


    private static final Properties properties = new Properties();

    // Metodo statico per leggere un singolo valore
    public static String read( String key) {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la lettura del file di configurazione", e);
        }
    }
}
