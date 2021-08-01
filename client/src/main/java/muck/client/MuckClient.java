package muck.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esotericsoftware.kryonet.Client;

import muck.protocol.*;
import muck.core.Id;
import muck.protocol.connection.*;

import java.io.IOException;
import java.util.Calendar;
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
    Id<MuckClient> clientId = new Id<MuckClient>();

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

        // Create random dummy credentials
        login("TestUser_" + Calendar.getInstance().get(Calendar.SECOND) % 100, "TestPassword");

        // Add a Ping listener. Still being used for debugging.
        client.addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) ->
            logger.info("Ping received from {}", conn.getID())
        ));
        //Listener for the message sent back from the server.
        client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, serverMessage) ->
            logger.info("Message from the server was: {}", serverMessage.getMessage())
        ));
        client.addListener(ListenerBuilder.forClass(AddCharacter.class).onReceive((connection, addCharacter) -> {
            logger.info("Received new character from the server: {}", addCharacter.getCharacter().getIdentifier());

            logger.debug("Initial location of new character is: X:{}, Y:{}", addCharacter.getLocation().getX(), addCharacter.getLocation().getY());

            //TODO: Notify of the new character and its location so that it can be placed on the map.
        }));
    }

    /**
     * Login user to the game.
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        Login login = new Login(username, password);

        client.sendTCP(login);
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
