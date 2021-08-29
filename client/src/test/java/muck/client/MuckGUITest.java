
package muck.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import javafx.scene.image.Image;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MuckGUITest extends ApplicationTest {



    private static final Logger logger = LogManager.getLogger(MuckGUITest.class);

    String avatar;
    Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Do this with a mock character???
        logger.info("Initializing window");
        AvatarController.avatarCreation("Test", "error");
        FXMLLoader loader = new FXMLLoader(AvatarController.class.getResource("/fxml/Avatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @Order(1)
    // Tests that the avatar id changes when you click on the appropriate unlocked avatars
    // Tests that the avatar id DOESN'T change when you click on a locked avatar
    public void testAvatarID() {

        logger.info("Checking unlocked avatarIDs");
        // Check unlocked avatars
        clickOn("#peach");
        avatar = AvatarController.getAvatarId();
        assertEquals("peach", avatar);
        clickOn("#batman");
        avatar = AvatarController.getAvatarId();
        assertEquals("batman", avatar);
        clickOn("#pikachu");
        avatar = AvatarController.getAvatarId();
        assertEquals("pikachu", avatar);

        logger.info("Checking locked avatarIDs");
        // Check clicking on locked avatars does not update avatarID
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("skeleton", avatar);
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("wonderWoman", avatar);
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("yoshi", avatar);

        AvatarController.setMuck(19); // Increases Muck Points for next test

    }

    @Test
    @Order(2)
    //Tests that the locked skeleton avatar doesn't unlock early
    public void testLockedSkeleton() {
        logger.info("Checking Skeleton");
        //Making sure the skeleton image doesn't open up early
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("skeleton", avatar);

        AvatarController.setMuck(29);
    }

    @Test
    @Order(3)
      //Tests that the avatar id changes when you click on the skeleton
      //Tests that the Wonder Woman avatar doesn't unlock early
    public void testSkeletonLockedWW() {
        logger.info("Checking unlocked skeleton");
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertEquals("skeleton", avatar);

        logger.info("Checking locked wonder woman");
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("wonderWoman", avatar);

        AvatarController.setMuck(49);
    }

    @Test
    @Order(4)
    //Tests that the avatar id changes when you click on Wonder Woman
    //Tests that the Yoshi avatar doesn't unlock early
    public void testWWLockedYoshi() {
        logger.info("Checking unlocked wonderwoman");
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertEquals("wonderWoman", avatar);

        logger.info("Checking locked Yoshi");
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertNotEquals("yoshi", avatar);

        AvatarController.setMuck(50);
    }

    @Test
    @Order(5)
    //Tests that the avatar id changes when you click on Yoshi
    public void testUnlockedYoshi() {
        logger.info("Checking unlocked Yoshi");
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertEquals("yoshi", avatar);
    }

    @Test
    @Order(6)
    public void testGetPortrait() {
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
    @Order(7)
    public void testGetFull() {
        String[] avatarIDs = {"peach", "batman", "pikachu", "skeleton", "wonderWoman", "yoshi"};
        // IMAGE INITIALISATION
        Image PEACH_FULL = new Image("/images/peach.png");
        Image BATMAN_FULL = new Image("/images/batman.png");
        Image PIKACHU_FULL = new Image("/images/pikachu.png");
        Image SKELETON_FULL = new Image("/images/skeleton.png");
        Image WONDER_WOMAN_FULL = new Image("/images/wonderWoman.png");
        Image YOSHI_FULL = new Image("/images/yoshi.png");

        for (String id : avatarIDs) {
            Image full = AvatarController.getFullAvatar(id);

            switch (id) {
                case ("peach"):
                    assertTrue(checkImageEquality(PEACH_FULL, full));
                    break;
                case ("batman"):
                    assertTrue(checkImageEquality(BATMAN_FULL, full));
                    break;
                case ("pikachu"):
                    assertTrue(checkImageEquality(PIKACHU_FULL, full));
                    break;
                case ("skeleton"):
                    assertTrue(checkImageEquality(SKELETON_FULL, full));
                    break;
                case ("wonderWoman"):
                    assertTrue(checkImageEquality(WONDER_WOMAN_FULL, full));
                    break;
                case ("yoshi"):
                    assertTrue(checkImageEquality(YOSHI_FULL, full));
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    @Order(8)
    public void testGetSprite() {
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

    private boolean checkImageEquality(Image official, Image test) {

        for(int x = 0; x < test.getWidth(); x++) {
            for(int y = 0; y < test.getHeight(); y++) {
                if (!test.getPixelReader().getColor(x, y).equals(official.getPixelReader().getColor(x, y))) {
                    return false;
                }
            }
        }
        return true;
    }


    //TODO: Test uname/avID/MuckPoints update with mock character. Full image shows. Correct avatars unlocked
    //TODO: Test details are sent to server on submit
    //TODO: Test username Text field updates

    @AfterAll
    public static void testWindowClose() {
        logger.info("Closing window");
        //avatarStage.close();
        Platform.exit();
    }

    @Test
    @Order(2)
    public void stageLaunchesTest() throws Exception {
        ChatJFX app = mock(ChatJFX.class);
        stage = mock(Stage.class);
        app.start(stage);
    }

    @Test
    @Order(3)
    public void testChatController() {
        MuckController chatController = mock(MuckController.class);
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
    @Order(1)
    public void testChatJFX() {
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

}
