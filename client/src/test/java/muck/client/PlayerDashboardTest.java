
package muck.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerDashboardTest extends ApplicationTest {

    private static final Logger logger = LogManager.getLogger(PlayerDashboardTest.class);
    private Stage stage;
    private ArrayList<String[]> achievements; //TODO: Remove once we have the ability to call the server
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

        //TODO: Remove the below once we have the ability to call the server
        achievements = new ArrayList<>();
        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});
        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});
        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});

        // ************************************************************************

    }

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Initializing window");
        //TODO: Need to do this with a mock player incl Muck and Health
        PlayerDashboardController.playerDashboard("Username", "DisplayName", "peach");

        FXMLLoader loader = new FXMLLoader(PlayerDashboardTest.class.getResource("/fxml/PlayerDashboard.fxml"));
        stage.initStyle(StageStyle.DECORATED);
        this.stage = stage;
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    @Order(1)
    // Checks the player statistics update as expected
    public void testNameMuckHealthUpdates() {
        logger.info("Checking player details updating on window");
        String display = lookup("#username").queryAs(Text.class).getText();
        assertEquals("DisplayName", display);

        //TODO: Update the below with appropriate muck and health with test person
        String muck = lookup("#muckPoints").queryAs(Text.class).getText();
        assertEquals(muck, "100");
        String health = lookup("#health").queryAs(Text.class).getText();
        assertEquals(health, "80");
    }

    @Test
    @Disabled //This test has been merged with dashboardOpensAvatarChangesTest() to prevent out of memory error
    @Order(2)
    // Checks the full body avatar image displays correctly
    public void testAvatarImageUpdates() {
        logger.info("Checking the avatar image updates on avatar change");
        Image avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(peach_full, avatarImage));

        AvatarController.setMuck(50);

        clickOn("#change");
        clickOn("#batman");
        clickOn("#submit");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(batman_full, avatarImage));

        clickOn("#change");
        clickOn("#pikachu");
        clickOn("#submit");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(pikachu_full, avatarImage));

        clickOn("#change");
        clickOn("#skeleton");
        clickOn("#submit");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(skeleton_full, avatarImage));

        clickOn("#change");
        clickOn("#wonderWoman");
        clickOn("#submit");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(wonder_woman_full, avatarImage));

        clickOn("#change");
        clickOn("#yoshi");
        clickOn("#submit");
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(AvatarTest.checkImageEquality(yoshi_full, avatarImage));
    }

    @Test
    @Order(3)
    // Checks that the achievements logged on the screen are the same as the achievements stored for a player
    public void testAchievementsUpdate() {
        logger.info("Checking achievements update");
        clickOn("#change");
        clickOn("#submit");
        //TODO: Call achievements for character (or might need to add achievements for mock character
        //ArrayList<String[]> achievements = new ArrayList<>();

        logger.info("Turning player achievements into a String arrayList");
        ArrayList<String> achieved = new ArrayList<>();
        for (String[] achievement: achievements) {
            achieved.add(achievement[0] + ": " + achievement[1]);
        }
        ArrayList<String> text = new ArrayList<>(Arrays.asList(lookup("#achievementWindow").queryAs(TextArea.class).getText().split("\n\n")));
        logger.info(achieved);
        logger.info(text);

        logger.info("Checking the ArrayLists are the same size");
        assertEquals(achieved.size(), text.size());

        logger.info("Checking each achievement is the same");
        for (int i = 0; i<achievements.size(); i++) {
            assertEquals(achieved.get(i), text.get(i));
        }
    }

    @Test
    @Disabled //Disabled due to out of memory error
    @Order(4)
    // Makes sure the window closes when the returnToGame button is pressed.
    // Checks the correct details are sent to Muck on return
    public void testReturnToGame() {
        logger.info("Testing the window closes on game return");
        clickOn("#gameReturn");
        assertFalse(stage.isShowing());
 }

    @AfterAll
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }

}