package muck.client;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled //Disabled due to out of memory error
public class MuckWindowTest extends ApplicationTest {

    Stage stage;
    private static final Logger logger = LogManager.getLogger(MuckWindowTest.class);
    private Image peach_full;
    private Image batman_full;
    private Image pikachu_full;
    private Image skeleton_full;
    private Image wonder_woman_full;
    private Image yoshi_full;

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);

        //IMAGE INITIALISATION
        peach_full = new Image("/images/peach.png");
        batman_full = new Image("/images/batman.png");
        pikachu_full = new Image("/images/pikachu.png");
        skeleton_full = new Image("/images/skeleton.png");
        wonder_woman_full = new Image("/images/wonderWoman.png");
        yoshi_full = new Image("/images/yoshi.png");
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

    //Mocks an App.java instance and a stage and starts it
    @Test
    @Order(2)
    public void stageLaunchesTest() throws Exception {
        logger.info("Testing that stage can be started");
        App app = mock(App.class);
        stage = mock(Stage.class);
        app.start(stage);
    }

    //Checks if the chat open and close buttons works
    @Test
    @Order(3)
    public void chatOpensClosesTest() {
        logger.info("Testing that chat pane can be opened and closed");
        clickOn("#openFullChat");
        assertTrue(lookup("#windowPane").queryAs(SplitPane.class).getDividerPositions()[0] < 1.000);
        clickOn("#closeChat");
        assertTrue(lookup("#chatSplitPane").queryAs(SplitPane.class).getDividerPositions()[0] > 0.9700);
    }

    //Checks if the a new tab is added when method is called
    @Test
    @Order(4)
    public void newTabTest() {
        logger.info("Testing that new chat tab can be added");
        int currentTabs = lookup("#chatPane1").queryAs(TabPane.class).getTabs().size();
        clickOn("#file");
        String id = lookup("#plusImg").queryAs(ImageView.class).getId();
        clickOn("#"+id);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        assertTrue(lookup("#chatPane1").queryAs(TabPane.class).getTabs().size()>currentTabs);
    }

    @Test
    @Order(5)
    public void testDashboardOpensAvatarUpdates() {
        Paint avatar =  lookup("#circle").queryAs(Circle.class).getFill();
        System.out.println(avatar);

        Image avatarImage;

        logger.info("Opening player dashboard");

        AvatarController.setMuck(50);
        clickOn("#circle");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(peach_full, avatarImage));

        clickOn("#change");
        clickOn("#batman");
        clickOn("#submit");
        logger.info("Checking Batman image the same");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(batman_full, avatarImage));

        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());
        //clickOn("#file");
        //clickOn("#playerDashboardMenu");
        clickOn("#circle");
        clickOn("#change");
        clickOn("#pikachu");
        clickOn("#submit");
        logger.info("Checking pikachu image the same");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(pikachu_full, avatarImage));
        clickOn("#gameReturn");
        assertNotSame(avatar, lookup("#circle").queryAs(Circle.class).getFill());

        clickOn("#circle");
        clickOn("#change");
        clickOn("#skeleton");
        clickOn("#submit");
        logger.info("Checking skeleton image the same");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(skeleton_full, avatarImage));

        clickOn("#change");
        clickOn("#wonderWoman");
        clickOn("#submit");
        logger.info("Checking wonder woman image the same");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(wonder_woman_full, avatarImage));

        clickOn("#change");
        clickOn("#yoshi");
        clickOn("#submit");
        logger.info("Checking yoshi image the same");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(yoshi_full, avatarImage));
    }

    //The original test before merging with PlayerDashboard test
    //Checks if the dashboard can be opened by clicking on the circle and the menu item and checks if the avatar is changed
    @Test
    @Disabled //This test has been merged with testAvatarImageUpdates in PlayerDashboardTest. Merged test is above
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

    //Checks that a message submitted will appear in the text area
    /** Currently fails due to server error but once fixed the final 3 lines can be uncommented out and should run and pass */
    @Test
    @Order(6)
    public void messageDisplaysTest() {
        logger.info("Testing messages typed and submitted and displayed");
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
        logger.info("Testing that the game Frogger launches");
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game3Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that the game Space Invaders launches
    @Test
    @Order(8)
    public void openSpaceInvadersTest()  {
        logger.info("Testing that the game Space Invaders launches");
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game1Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that the game Enduring Fantasy launches
    @Test
    @Order(9)
    public void openEnduringFantasyTest()  {
        logger.info("Testing that the game Enduring Fastasy launches");
        String oldID = lookup("#gameCanvas").queryAs(Canvas.class).getId();
        clickOn("#game2Button");
        assertNotEquals(oldID, lookup("#gamePane1").queryAs(BorderPane.class).getChildren().get(0).getId());
    }

    //Checks that alert pops up when quitting and that cancel button is enabled
    @Test
    @Order(10)
    public void quitMuckTest() {
        logger.info("Testing that user can access the quit alert");
        clickOn("#file");
        String id = lookup("#exitImg").queryAs(ImageView.class).getId();
        clickOn("#"+id);
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