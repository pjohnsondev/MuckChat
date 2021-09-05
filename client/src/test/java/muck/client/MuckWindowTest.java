package muck.client;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MuckWindowTest extends ApplicationTest {

    Stage stage;
    int num = 4;
    private static final Logger logger = LogManager.getLogger(MuckWindowTest.class);

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Do this with a mock character???
        logger.info("Initializing window");
        MuckController.constructor("test", "peach");
        Parent root = FXMLLoader.load(Objects.requireNonNull(MuckWindowTest.class.getResource("/fxml/MuckWindow.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void anotherTest() {
        clickOn("#openFullChat");
        assertTrue(true);
    }

    // *********** START MUCK CONTROLLER TESTING *************

    // Wrapper thread updates this if
    // the JavaFX application runs without a problem.
    // Declared volatile to ensure that writes are visible to every thread.
    private volatile boolean success = false;

    /**
     * Test that a JavaFX application launches.
     * Copied from https://stackoverflow.com/questions/24851886/how-to-unit-test-that-a-javafx-application-launches
     */

    @Test
    @Order(9)
    public void testMuckWindows() {
        // Wrapper thread.
        Thread thread = new Thread(() -> {
            try {
                ApplicationTest.launch(App.class); // Run JavaFX application.
                success = true;
            } catch(Throwable t) {
                if(t.getCause() != null && t.getCause().getClass().equals(InterruptedException.class)) {
                    // We expect to get this exception since we interrupted
                    // the JavaFX application.
                    success = true;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(3000);  // Wait for 3 seconds before interrupting JavaFX application
        } catch(InterruptedException ex) {
        }
        thread.interrupt();
        try {
            thread.join(1); // Wait 1 second for our wrapper thread to finish.
        } catch(InterruptedException ex) {
        }
        assertTrue(success);
    }

    @Test
    @Order(10)
    public void stageLaunchesTest() throws Exception {
        App app = mock(App.class);
        stage = mock(Stage.class);
        app.start(stage);
    }

    @Test
    @Order(11)
    public void testChatController() {
        MuckController chatController = mock(MuckController.class);
        chatController.initialize(null, null);
    }

    // *********** END MUCK CONTROLLER TESTING ****************


    @AfterAll
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }

}