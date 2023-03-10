package muck.client;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
import muck.protocol.*;
import muck.protocol.connection.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.awt.geom.Rectangle2D;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

//Chat JFX imports. This allows the group working on Chat UI to be used in the main application.
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * The class that is run by the client:run task
 */
public class App extends Application {

    /**
     * A logger for logging output
     */
    private static final Logger logger = LogManager.getLogger(App.class);

    /**
     * The port configuration for the client
     */
    static KryoClientConfig config = new KryoClientConfig();

    /**
     * Set stage
     */
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // start the network connection
        startConnection();

        // Create and show the UI
        //**NOTE**: This was commented out in order to import the ChatJFX's Ui instead.
        //          I will leave it here for reference.
        /*
        Label l = new Label("Hello world");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);

        */

        //Creating a test userMessage to send to the server.
        /* userMessage testMessage = new userMessage();
        testMessage.setMessage("Hello World! From client");
        MuckClient.INSTANCE.send(testMessage); */

        /* Last edited: 27/07/2021 by Harrison Liddell with assistance from W.Billingsley
          Imported work from the ChatUI group written in ChatJFX to work with the
          exsisting stand alone application/ gradle build.
        */
	/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MuckWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setRoot(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        //Stage stage = new Stage();
        stage.setMaxWidth(1200);
        stage.setMaxHeight(1100);
        stage.setResizable(false);
        stage.setTitle("Muck 2021");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> shutdown()); // lambda function to ensure shutdown method is called on close

        stage.show();*/

        /* End of Imported work */
        try {
            stage = primaryStage;
            primaryStage.setResizable(true);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignIn.fxml")));
            primaryStage.setTitle("MUCK 2021");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.toFront();
            primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        } catch (IOException e) {
            logger.error("Can't find primary stage FXML file");
        }
    }

    public void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Quit Muck?");
        alert.setContentText("Are you sure you want to quit Muck?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent()) {
            if (res.get().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                shutdown();
            }
    }
}


    public static void hideStage(){
        stage.hide();
    }


    public void changeScene(String fxml) throws IOException {
        try {
            Parent pane = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml)));
            hideStage();
            stage.setScene(new Scene(pane));
            stage.show();
            stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        } catch (IOException e){
            logger.error("Could not find file " + fxml);
        }
    }

    public void changeScene(String fxml, String data) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.setScene(new Scene(pane));
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
        try {
            // Disconnects the client from the server before closing the application
            MuckClient.INSTANCE.disconnect();
            System.exit(0);
            logger.info("Client disconnected successfully");
        } catch (IOException ex) {
            System.exit(1);
            logger.error("Client exited without disconnecting");
        }
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
