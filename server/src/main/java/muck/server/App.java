package muck.server;

import muck.protocol.*;
import muck.server.models.ModelRegister;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The Main class called by the server:run task. 
 */
public class App {

    static KryoServerConfig config = new KryoServerConfig();

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(App.class);

    /** The main method that is launched on gradle server:run */
    public static void main(String[] args) {

        // If a command-line argument is passed in, it is assumed to be a port number to use
        // Kryo will then be started to use that port number for TCP and the next 1 up for UDP.
        if (args.length > 0) {
            try {
                config.setTcpPort(Integer.parseInt(args[0]));
                config.setUdpPort(config.getTcpPort() + 1);
            } catch (NumberFormatException x) {
                logger.warn("Port argument could not be parsed {}", args[0], x);
            }
        }

        try {
            // Start listening for messages from the client
            MuckServer.INSTANCE.startKryo(config);
        } catch (IOException ex) {
            logger.error("Start up failed", ex);
            System.exit(0);
        }

        new ModelRegister().makemigrations();

    }

}
