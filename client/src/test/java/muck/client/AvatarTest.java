
package muck.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import javafx.scene.image.Image;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AvatarTest extends ApplicationTest {

    private static final Logger logger = LogManager.getLogger(AvatarTest.class);

    private String avatar;
    private Image avatarImage;
    private Image peach_full;
    private Image batman_full;
    private Image pikachu_full;
    private Image skeleton_full;
    private Image wonder_woman_full;
    private Image yoshi_full;

    // ********* START AVATAR CONTROLLER TESTING ***************

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
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
        AvatarController.avatarCreation("Username", "DisplayName", "error");
        FXMLLoader loader = new FXMLLoader(AvatarTest.class.getResource("/fxml/Avatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @Order(1)
    // Checks the display name passed into the interface is the name that displays
    public void testDisplayNameUpdates() {
        AvatarController.setMuck(0); //Changes Muck Points for next test (If this test fails the next test will be able to run successfully)

        String display = lookup("#username").queryAs(Text.class).getText();
        assertEquals("DisplayName", display);
    }


    @Test
    @Order(2)
    // Testing the selection method
    // Tests that the avatar id and image changes when you click on the appropriate unlocked avatars
    // Tests that the avatar id and image DOESN'T change when you click on a locked avatar
    public void testAvatarSelection() {

        logger.info("Checking unlocked avatarIDs");
        // Check unlocked avatars
        logger.info("Checking Peach");
        clickOn("#peach");
        avatar = AvatarController.getAvatarId();
        assertEquals("peach", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(peach_full, avatarImage));

        logger.info("Checking Batman");
        clickOn("#batman");
        avatar = AvatarController.getAvatarId();
        assertEquals("batman", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(batman_full, avatarImage));

        logger.info("Checking Pikachu");
        clickOn("#pikachu");
        avatar = AvatarController.getAvatarId();
        assertEquals("pikachu", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(pikachu_full, avatarImage));

        logger.info("Checking locked avatarIDs");
        // Check clicking on locked avatars does not update avatarID or image
        logger.info("Checking skeleton");
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("skeleton", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertFalse(checkImageEquality(skeleton_full, avatarImage));

        logger.info("Checking wonderWoman");
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("wonderWoman", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertFalse(checkImageEquality(wonder_woman_full, avatarImage));

        logger.info("Checking Yoshi");
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("yoshi", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertFalse(checkImageEquality(yoshi_full, avatarImage));

        AvatarController.setMuck(29); // This line has been added because the next two tests are disabled due to OutOfMemoryError
    }

    @Test
    @Disabled //Disabled due to out of memory error
    @Order(3)
    // Checks that the correct messages are displayed when a user hovers over a locked avatar
    public void testHoverMessage() {
        String message;
        AvatarController.setMuck(19); // Increases Muck Points for next test (If this test fails the next test will be able to run successfully)

        logger.info("Checking hover messages");
        moveTo("#skeleton");
        message = lookup("#skeletonAlert").queryAs(Text.class).getText();
        assertEquals("Earn 20 MuckPoints to unlock!", message);

        moveTo("#wonderWoman");
        message = lookup("#wonderWomanAlert").queryAs(Text.class).getText();
        assertEquals("Earn 30 MuckPoints to unlock!", message);

        moveTo("#yoshi");
        message = lookup("#yoshiAlert").queryAs(Text.class).getText();
        assertEquals("Earn 50 MuckPoints to unlock!", message);
    }

    @Test
    @Disabled //Disabled due to out of memory error
    @Order(4)
    //Tests that the locked skeleton avatar doesn't unlock early
    public void testLockedSkeleton() {
        AvatarController.setMuck(29); // Increases Muck Points for next test (If this test fails the next test will be able to run successfully)

        logger.info("Checking locked skeleton");
        //Making sure the skeleton image doesn't open up early
        clickOn("#peach"); // Puts up an avatar image in the imageview pane
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("skeleton", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertFalse(checkImageEquality(skeleton_full, avatarImage));
        }

    @Test
    @Order(5)
      //Tests that the avatar id changes when you click on the skeleton
      //Tests that the Wonder Woman avatar doesn't unlock early
    public void testSkeletonLockedWW() {
        AvatarController.setMuck(49); // Increases Muck Points for next test (If this test fails the next test will be able to run successfully)

        logger.info("Checking unlocked skeleton");
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertEquals("skeleton", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(skeleton_full, avatarImage));

        logger.info("Checking locked wonder woman");
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("wonderWoman", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertFalse(checkImageEquality(wonder_woman_full, avatarImage));

    }

    @Test
    @Order(6)
    //Tests that the avatar id changes when you click on Wonder Woman
    //Tests that the Yoshi avatar doesn't unlock early
    public void testWWLockedYoshi() {
        AvatarController.setMuck(50); // Increases Muck Points for next test (If this test fails the next test will be able to run successfully)

        logger.info("Checking unlocked wonderwoman");
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertEquals("wonderWoman", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(wonder_woman_full, avatarImage));

        logger.info("Checking locked Yoshi");
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("yoshi", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertThrows(IndexOutOfBoundsException.class, () -> checkImageEquality(yoshi_full, avatarImage));
    }

    @Test
    @Order(7)
    //Tests that the avatar id changes when you click on Yoshi
    public void testUnlockedYoshi() {
        logger.info("Checking unlocked Yoshi");
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertEquals("yoshi", avatar);
        avatarImage = lookup("#avatarFullBody").queryAs(ImageView.class).getImage();
        assertTrue(checkImageEquality(yoshi_full, avatarImage));
    }

    @Test
    @Order(8)
    // Tests the correct portrait image is returned with the get method
    public void testGetPortrait() {
        logger.info("Checking getPortrait() method");
        String[] avatarIDs = {"peach", "batman", "pikachu", "skeleton", "wonderWoman", "yoshi"};
        // IMAGE INITIALISATION
        Image PEACH_PORTRAIT = new Image("/images/peach-portrait.png");
        Image BATMAN_PORTRAIT = new Image("/images/batman-portrait.png");
        Image PIKACHU_PORTRAIT = new Image("/images/pikachu-portrait.png");
        Image SKELETON_PORTRAIT = new Image("/images/skeleton-portrait.png");
        Image WONDER_WOMAN_PORTRAIT = new Image("/images/wonderWoman-portrait.png");
        Image YOSHI_PORTRAIT = new Image("/images/yoshi-portrait.png");

        for (String id : avatarIDs) {
            Image portrait = AvatarController.getPortrait(id);

            switch (id) {
                case ("peach"):
                    assertTrue(checkImageEquality(PEACH_PORTRAIT, portrait));
                    break;
                case ("batman"):
                    assertTrue(checkImageEquality(BATMAN_PORTRAIT, portrait));
                    break;
                case ("pikachu"):
                    assertTrue(checkImageEquality(PIKACHU_PORTRAIT, portrait));
                    break;
                case ("skeleton"):
                    assertTrue(checkImageEquality(SKELETON_PORTRAIT, portrait));
                    break;
                case ("wonderWoman"):
                    assertTrue(checkImageEquality(WONDER_WOMAN_PORTRAIT, portrait));
                    break;
                case ("yoshi"):
                    assertTrue(checkImageEquality(YOSHI_PORTRAIT, portrait));
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    @Order(9)
    // Tests the correct full body image is returned with the get method
    public void testGetFull() {
        logger.info("Checking getFullAvatar() method");
        String[] avatarIDs = {"peach", "batman", "pikachu", "skeleton", "wonderWoman", "yoshi"};

        for (String id : avatarIDs) {
            Image full = AvatarController.getFullAvatar(id);

            switch (id) {
                case ("peach"):
                    assertTrue(checkImageEquality(peach_full, full));
                    break;
                case ("batman"):
                    assertTrue(checkImageEquality(batman_full, full));
                    break;
                case ("pikachu"):
                    assertTrue(checkImageEquality(pikachu_full, full));
                    break;
                case ("skeleton"):
                    assertTrue(checkImageEquality(skeleton_full, full));
                    break;
                case ("wonderWoman"):
                    assertTrue(checkImageEquality(wonder_woman_full, full));
                    break;
                case ("yoshi"):
                    assertTrue(checkImageEquality(yoshi_full, full));
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    @Order(10)
    // Tests the correct sprite image is returned with the get method
    public void testGetSprite() {
        logger.info("Checking getSprite() method");
        String[] avatarIDs = {"peach", "batman", "pikachu", "skeleton", "wonderWoman", "yoshi"};
        // IMAGE INITIALISATION
        Image PEACH_SPRITE = new Image("/images/peachSprite.png");
        Image BATMAN_SPRITE = new Image("/images/batmanSprite.png");
        Image PIKACHU_SPRITE = new Image("/images/pikachuSprite.png");
        Image SKELETON_SPRITE = new Image("/images/skeletonSprite.png");
        Image WONDER_WOMAN_SPRITE = new Image("/images/wonderWomanSprite.png");
        Image YOSHI_SPRITE = new Image("/images/yoshiSprite.png");

        for (String id : avatarIDs) {
            Image sprite = AvatarController.getSprite(id);

            switch (id) {
                case ("peach"):
                    assertTrue(checkImageEquality(PEACH_SPRITE, sprite));
                    break;
                case ("batman"):
                    assertTrue(checkImageEquality(BATMAN_SPRITE, sprite));
                    break;
                case ("pikachu"):
                    assertTrue(checkImageEquality(PIKACHU_SPRITE, sprite));
                    break;
                case ("skeleton"):
                    assertTrue(checkImageEquality(SKELETON_SPRITE, sprite));
                    break;
                case ("wonderWoman"):
                    assertTrue(checkImageEquality(WONDER_WOMAN_SPRITE, sprite));
                    break;
                case ("yoshi"):
                    assertTrue(checkImageEquality(YOSHI_SPRITE, sprite));
                    break;
                default:
                    break;
            }
        }
    }

    //TODO: Test uname/avID/MuckPoints update with mock character. Full image shows. Correct avatars unlocked

    // *********** END AVATAR CONTROLLER TESTING *************

    @AfterAll
    @Disabled //Disabled due to out of memory error
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }

    public static boolean checkImageEquality(Image official, Image test) {

        for(int x = 0; x < test.getWidth(); x++) {
            for(int y = 0; y < test.getHeight(); y++) {
                if (!test.getPixelReader().getColor(x, y).equals(official.getPixelReader().getColor(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }

}
