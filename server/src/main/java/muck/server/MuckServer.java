package muck.server;

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

    /** Sets up the KryoNet server that will handle communication */
    synchronized void startKryo(KryoServerConfig config) throws IOException {

        if (kryoServer != null) {
            throw new IllegalStateException("Attempted to start KryoServer when it was already started");
        }

        // Create a new KryoNet server
        kryoServer = new Server();
        kryoServer.start();

        // Register the protocol classes with Kryo
        Protocol.register(kryoServer.getKryo());

        // Bind the server to the configured ports
        kryoServer.bind(config.getTcpPort(), config.getUdpPort());

        ArrayList<Integer> players = new ArrayList<Integer>();

        // Add a Ping listener. Still being used for debugging.
        addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) -> {

            logger.info("Ping received from {}", conn.getID());

            // Let's just demonstrate how to send messages to worker actors, by sending this message to one.
            workerManager.schedule(ping, reply -> {
                logger.info("I sent my ping to a background worker, and all I got in return was this lousy {}", reply);
            });
            players.add(conn.getID());

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
