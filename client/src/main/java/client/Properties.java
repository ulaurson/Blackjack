package client;

import java.io.IOException;
import java.io.InputStream;

public class Properties {

    private final String serverAddress = loadProperties();

    public Properties () throws IOException {}

    private String loadProperties() throws IOException {
        java.util.Properties properties = new java.util.Properties();
        InputStream in = Properties.class.getResourceAsStream("/config.properties");
        properties.load(in);
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String server = properties.getProperty("server");

        return "http://" + host + ":" + port + server;
    }

    public String getServerAddress() {
        return serverAddress;
    }
}
