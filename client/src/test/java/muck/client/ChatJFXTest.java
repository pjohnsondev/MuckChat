package muck.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;


public class ChatJFXTest {

    private static final Logger logger = LogManager.getLogger(ChatJFXTest.class);

    @Test
    public void stageLaunchesTest() throws Exception {
        ChatJFX app = mock(ChatJFX.class);
        Stage stage = mock(Stage.class);
        app.start(stage);
    }

    @Test
    public void testChatController() {
        ChatControl chatController = mock(ChatControl.class);
        chatController.initialize(null, null);
    }


        // Wrapper thread updates this if
        // the JavaFX application runs without a problem.
        // Declared volatile to ensure that writes are visible to every thread.
        private volatile boolean success = false;

        /**
         * Test that a JavaFX application launches.
         * Copied from https://stackoverflow.com/questions/24851886/how-to-unit-test-that-a-javafx-application-launches
         */

        @Test
        public void testChatJFX() {
            // Wrapper thread.
            Thread thread = new Thread(() -> {
                try {
                    Application.launch(ChatJFX.class); // Run JavaFX application.
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
                // We don't care if we wake up early.
            }
            thread.interrupt();
            try {
                thread.join(1); // Wait 1 second for our wrapper thread to finish.
            } catch(InterruptedException ex) {
                // We don't care if we wake up early.
            }
            assertTrue(success);
        }

}

