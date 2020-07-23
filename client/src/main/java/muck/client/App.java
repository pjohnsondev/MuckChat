package muck.client;

import muck.protocol.*;
import muck.protocol.connection.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The class that is run by the client:run task
 */
public class App extends Application {

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(App.class);

    /** The port configuration for the client */
    static KryoClientConfig config = new KryoClientConfig();

    @Override
    public void start(Stage stage) throws Exception {
        // start the network connection
        startConnection();

        // Create and show the UI
        Label l = new Label("Hello world");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    void startConnection() {
        logger.info("Starting connection to server");
        try {
            MuckClient.INSTANCE.connect(config);
            MuckClient.INSTANCE.send(new Connected());

            // Just demonstrates that the worker manager hands the ping off -- likely to be removed when the project
            // progresses
            MuckClient.INSTANCE.send(new Ping());
        } catch (IOException ex) {
            logger.error("Start up failed");
        }
    }


    void shutdown() {
        // Exit the program
        System.exit(0);
    }

    public static void main(String[] args) {

        // Read in any port arguments that were passed into the program
        if (args.length > 0) {
            try {
                config.setTcpPort(Integer.parseInt(args[0]));
                config.setUdpPort(config.getTcpPort() + 1);
            } catch (NumberFormatException x) {
                logger.warn("Port argument could not be parsed {}", args[0], x);
            }
        }

        launch();
    }

}
