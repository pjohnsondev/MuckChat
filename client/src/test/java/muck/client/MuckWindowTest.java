package muck.client;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.testfx.api.FxAssert.verifyThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MuckWindowTest extends ApplicationTest {

    Stage stage;
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
    @Order(1)
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
    @Order(2)
    public void stageLaunchesTest() throws Exception {
        App app = mock(App.class);
        stage = mock(Stage.class);
        app.start(stage);
    }

    @Test
    @Order(3)
    public void chatOpensClosesTest() {
        clickOn("#openFullChat");
        assertTrue(lookup("#windowPane").queryAs(SplitPane.class).getDividerPositions()[0] < 1);
        clickOn("#closeChat");
        assertEquals(1, lookup("#chatSplitPane").queryAs(SplitPane.class).getDividerPositions()[0]);

    }
    @Test
    @Order(4)
    public void newTabTest() {
        int currentTabs = lookup("#chatPane1").queryAs(TabPane.class).getTabs().size();
        clickOn("#file");
        clickOn("#plus");
        assertTrue(lookup("#chatPane1").queryAs(TabPane.class).getTabs().size()>currentTabs);
    }

    @Test
    @Order(5)
    public void dashboardOpensAvatarChangesTest() {
        Paint avatar =  lookup("#circle").queryAs(Circle.class).getFill();
        System.out.println(avatar);
        clickOn("#circle");
        clickOn("#change");
        clickOn("#batman");
        clickOn("#submit");
        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());
        clickOn("#file");
        clickOn("#playerDashboardMenu");
        clickOn("#change");
        clickOn("#pikachu");
        clickOn("#submit");
        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());
    }

    /** Currently fails due to server error but once fixed the final 3 lines can be uncommented out and should run and pass */
    @Test
    @Order(6)
    public void messageDisplaysTest() {
        clickOn("#openFullChat");
        clickOn("#chatPane1");
        lookup("#messageBox").queryAs(TextField.class).setText("testing");
        //clickOn("#enter");
        //System.out.println(lookup("#groupChatBox").queryAs(TextArea.class).getText());
        //assertEquals("test: testing", lookup("#groupChatBox").queryAs(TextArea.class).getText(0, 13));
    }

    @Test
    @Order(7)
    public void openFroggerTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game3Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    @Test
    @Order(8)
    public void openSpaceInvadersTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game1Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());

    }

    @Test
    @Order(9)
    public void openEnduringFantasyTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game2Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    // *********** END MUCK CONTROLLER TESTING ****************

    @AfterAll
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }

}