package muck.server;

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

        // Add a Ping listener. This is useful for debugging in the early stages, where you just want to see if a
        // connection has been made
        addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) ->
                logger.info("Ping received from {}", conn.getID())
        ));
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
