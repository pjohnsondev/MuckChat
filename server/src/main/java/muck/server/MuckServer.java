package muck.server;

import aw.character.Character;
import aw.character.CharacterDoesNotExistException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;

/**
 * The central body of the server. This should avoid getting too large, but should have references to
 * any of the necessary components. It is a singleton.
 */
public enum MuckServer {

    /** The single instance of the server that exists */
    INSTANCE;

    /** Gets the MuckServer object */
    public static MuckServer getINSTANCE() {
        return INSTANCE;
    }

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(MuckServer.class);

    // Create a new KryoNet server
    Server kryoServer;

    // Tries to make handling background tasks easier
    WorkerManager workerManager = new WorkerManager();

    CharacterLocationTracker tracker = new CharacterLocationTracker();

    /** Sets up the KryoNet server that will handle communication */
    synchronized void startKryo(KryoServerConfig config) throws IOException {

        if (kryoServer != null) {
            throw new IllegalStateException("Attempted to start KryoServer when it was already started");
        }

        // Create a new KryoNet server
        kryoServer = new Server(){
            protected Connection newConnection() {
                return new MuckConnection();
            }
        };
        kryoServer.start();

        // Register the protocol classes with Kryo
        Protocol.register(kryoServer.getKryo());

        // Bind the server to the configured ports
        kryoServer.bind(config.getTcpPort(), config.getUdpPort());

        // Add a Ping listener. Still being used for debugging.
        addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) -> {

            logger.info("Ping received from {}", conn.getID());

            // Let's just demonstrate how to send messages to worker actors, by sending this message to one.
            workerManager.schedule(ping, reply -> {
                logger.info("I sent my ping to a background worker, and all I got in return was this lousy {}", reply);
            });

        }));
        /*
        This listener listens for a message from client. Prints to logger when received.
         */
        addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, clientMessage) -> {
            logger.info("Recieved a message!");
            logger.info("Message received from {}", connID.getID());
            logger.info("Message is: {}", clientMessage.getMessage());
            userMessage testMessage = new userMessage(); //Create new message to send back.
            testMessage.setMessage("TEST MESSAGE FROM SERVER!");
            kryoServer.sendToAllTCP(testMessage); //Send to all clients connected. Can be switched to send only to one client.
        }));

        addListener(ListenerBuilder.forClass(CharacterLocation.class).onReceive((conn, location) -> {
                logger.info("Received a character location.");
                logger.info("TODO: Process character location: {}", location.toString());
        }));

        loginCharacter();
    }

    public void loginCharacter() {
        addListener(ListenerBuilder.forClass(Login.class).onReceive((connection, login) -> {
            logger.info("Attempting to log in");
            logger.debug("{} is trying to log in", login.getUsername());
            MuckConnection muckConnection = (MuckConnection)connection;

            CharacterManager characterManager = new CharacterManager();

            Character character = null;

            try {
                character = characterManager.loginCharacter(login);
            } catch (DuplicateLoginException ex) {
                userMessage testMessage = new userMessage(); //Create new message to send back.
                testMessage.setMessage("Duplicate login");
                kryoServer.sendToTCP(connection.getID(), testMessage); // send message back to client
            } catch (CharacterDoesNotExistException ex) {
                userMessage testMessage = new userMessage(); //Create new message to send back.
                testMessage.setMessage("Character does not exist. Please register.");
                kryoServer.sendToTCP(connection.getID(), testMessage); // send message back to client
            }

            muckConnection.setCharacter(character);
            Location location = new Location(0,0);

            AddCharacter addCharacter = new AddCharacter(character, location);
            kryoServer.sendToAllTCP(addCharacter);

            tracker.addClient(login.getUsername(), character, new muck.core.Location(location.getX(), location.getY()));

            logger.info("Login successful for {}", login.getUsername());
        }));
    }

    /** Stops the KryoNet server. */
    synchronized void stopKryo() {
        kryoServer.stop();
        kryoServer = null;
    }

    /**
     * Registers a listener with the KryoNet server.
     * @param l
     */
    public void addListener(Listener l) {
        if (kryoServer == null) {
            throw new IllegalStateException("Attempted to register listener when stopped.");
        }

        kryoServer.addListener(l);
    }


}
