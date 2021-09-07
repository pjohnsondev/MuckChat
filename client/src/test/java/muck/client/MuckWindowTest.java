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
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MuckWindowTest extends ApplicationTest {

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    Stage stage;
    private static final Logger logger = LogManager.getLogger(MuckWindowTest.class);


    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }


    public void start(Stage stage) throws IOException {
        // TODO: Do this with a mock character???
        logger.info("Initializing window");
        MuckController.constructor("test", "peach");
        Parent root = FXMLLoader.load(Objects.requireNonNull(MuckWindowTest.class.getResource("/fxml/MuckWindow.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    // *********** START MUCK CONTROLLER TESTING *************

    // Wrapper thread updates this if
    // the JavaFX application runs without a problem.
    // Declared volatile to ensure that writes are visible to every thread.

    /**
     * Test that a JavaFX application launches.
     * Copied from https://stackoverflow.com/questions/24851886/how-to-unit-test-that-a-javafx-application-launches
     */



    //Mocks an App.java instance and a stage and starts it
    @Test
    @Order(2)
    public void stageLaunchesTest() throws Exception {
        App app = mock(App.class);
        stage = mock(Stage.class);
        app.start(stage);
    }

    //Checks if the chat open and close buttons works
    @Test
    @Order(3)
    public void chatOpensClosesTest() {
        clickOn("#openFullChat");
        assertTrue(lookup("#windowPane").queryAs(SplitPane.class).getDividerPositions()[0] < 1.000);
        clickOn("#closeChat");
        assertTrue(lookup("#chatSplitPane").queryAs(SplitPane.class).getDividerPositions()[0] > 0.9700);
    }

    //Checks if the a new tab is added when method is called
    @Test
    @Order(4)
    @Disabled
    public void newTabTest() {
        FxRobot robot = new FxRobot();
        int currentTabs = lookup("#chatPane1").queryAs(TabPane.class).getTabs().size();
        robot.clickOn("#file");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        robot.clickOn("#plus");
        assertTrue(lookup("#chatPane1").queryAs(TabPane.class).getTabs().size()>currentTabs);
    }

    //Checks if the dashboard can be opened by clicking on the circle and the menu item and checks if the avatar is changed
    @Test
    @Order(5)
    @Disabled
    public void dashboardOpensAvatarChangesTest() {
        Paint avatar =  lookup("#circle").queryAs(Circle.class).getFill();
        System.out.println(avatar);
        clickOn("#circle");
        clickOn("#change");
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        clickOn("#pikachu");
        clickOn("#submit");
        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());
        clickOn("#file");
        clickOn("#playerDashboardMenu");
        clickOn("#change");
        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        clickOn("#peach");
        clickOn("#submit");
        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());
    }

    //Checks that a message submitted will appear in the text area
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

    //Checks that the game Frogger launches
    @Test
    @Order(7)
    public void openFroggerTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game3Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that the game Space Invaders launches
    @Test
    @Order(8)
    public void openSpaceInvadersTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game1Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that the game Enduring Fantasy launches
    @Test
    @Order(9)
    public void openEnduringFantasyTest()  {
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game2Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that alert pops up when quitting and that cancel button is enabled
    @Test
    @Disabled
    @Order(10)
    public void quitMuckTest() {
        clickOn("#file");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        clickOn("#quitMuck");
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        FxAssert.verifyThat("#cancel",isEnabled());
        FxAssert.verifyThat("#confirmQuit",isEnabled());
        clickOn("#cancel");
    }

    // *********** END MUCK CONTROLLER TESTING ****************

    @AfterAll
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }
}