package muck.server;


import muck.core.character.Character;
import muck.core.character.CharacterDoesNotExistException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;

import java.util.ArrayList;

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

    CharacterLocationTracker<String> tracker = new CharacterLocationTracker<String>();

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

        // The arraylist is only a temporary datastructure and is subject to change.
        ArrayList<String> players = new ArrayList<String>();
        // Adds a listener to listen for new client connections, then adds the clients id to the players arraylist.
        addListener(ListenerBuilder.forClass(Connected.class).onReceive((conn, connected) -> {
            players.add(Integer.toString(conn.getID()));
            logger.info("Player connection id's are: {}", players);

        }));

        // Adds a listener to listen for clients disconnecting from the server, then removes them from the players arraylist.
        addListener(ListenerBuilder.forClass(Disconnect.class).onReceive((conn, disconnect) -> {
            players.remove(Integer.toString(conn.getID())); // This will obtain the index of the player
            logger.info("Player connection id's are: {} disconnected: {}", players, disconnect);
        }));

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
            logger.info(clientMessage);
            kryoServer.sendToAllTCP(clientMessage); //Send to all clients connected. Can be switched to send only to one client.
        }));

        addListener(ListenerBuilder.forClass(CharacterLocation.class).onReceive((conn, location) -> {
                logger.info("Received a character location.");
                logger.info("TODO: Process character location: {}", location.toString());
        }));

        addListener(ListenerBuilder.forClass(Login.class).onReceive((connection, login) -> {
            loginCharacter(login, (MuckConnection)connection);
        }));
    }

    public void loginCharacter(Login login, MuckConnection muckConnection) {
        logger.info("Attempting to log in");
        logger.debug("{} is trying to log in", login.getUsername());

        CharacterManager characterManager = new CharacterManager();

        Character character = null;

        try {
            character = characterManager.loginCharacter(login);
        } catch (DuplicateLoginException ex) {
            userMessage testMessage = new userMessage(); //Create new message to send back.
            testMessage.setMessage("Duplicate login");
            kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client
        } catch (CharacterDoesNotExistException ex) {
            userMessage testMessage = new userMessage(); //Create new message to send back.
            testMessage.setMessage("Character does not exist. Please register.");
            kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client
        }

        muckConnection.setCharacter(character);

        logger.info("Login successful for {}", login.getUsername());

        AddCharacter addCharacter = addCharacter(character);

        kryoServer.sendToAllTCP(addCharacter);
    }

    public AddCharacter addCharacter(Character character) {
        Location location = new Location(0,0);

        AddCharacter addCharacter = new AddCharacter(character, location);

        tracker.addClient(new muck.core.Id<String>(character.getIdentifier()), character, new muck.core.Location(location.getX(), location.getY()));

        logger.info("Character added successfully {}", character.getIdentifier());

        return addCharacter;
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
