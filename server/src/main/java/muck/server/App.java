package muck.server;

import muck.protocol.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The Main class called by the server:run task. 
 */
public class App {

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        try {
            MuckServer.INSTANCE.startKryo(new KryoServerConfig());
        } catch (IOException ex) {
            logger.error("Start up failed", ex);
        }


    }

}
