package muck.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esotericsoftware.kryonet.Client;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Handles network communication with the server. It's created as an enum to ensure there is only one instance.
 */
public enum MuckClient {

    INSTANCE;

    public static MuckClient getINSTANCE() {
        return INSTANCE;
    }

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(MuckClient.class);

    /** The KryoNet client */
    Client client;

    public synchronized void connect(KryoClientConfig config) throws IOException {
        if (client != null) {
            throw new IllegalStateException("Starting connection when already started");
        }

        client = new Client();
        client.start();

        // Register the protocol classes with Kryo
        Protocol.register(client.getKryo());

        // Connect to the server
        client.connect(config.getTimeOut(), config.getDestinationIp(), config.getTcpPort(), config.getUdpPort());

        // Add a Ping listener. This is useful for debugging in the early stages, where you just want to see if a
        // connection has been made
        client.addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) ->
                logger.info("Ping received from {}", conn.getID())
        ));
        client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, serverMessage) ->
                logger.info("Message from the server was: {}", serverMessage.getMessage())));

    }

    public synchronized void disconnect() throws IOException {
        if (client == null) {
            throw new IllegalStateException("Closing connection when not started");
        }

        client.sendTCP(new Disconnect());
        client.dispose();
        client = null;
    }

    public synchronized void send(Object message) {
        client.sendTCP(message);
    }


}
