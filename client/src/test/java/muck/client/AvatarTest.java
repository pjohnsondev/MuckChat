package muck.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.testfx.robot.Motion;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class AvatarTest extends ApplicationTest{

    private static final Logger logger = LogManager.getLogger(AvatarTest.class);

    private static ModuleLayer.Controller controller;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Do this with a mock character???
        AvatarController.avatarCreation("Test");
        FXMLLoader loader = new FXMLLoader(AvatarController.class.getResource("/fxml/Avatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAvatarID() {
        String avatar;

        // Check unlocked avatars
        clickOn("#peach");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("peach"));
        clickOn("#batman");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("batman"));
        clickOn("#pikachu");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("pikachu"));

        /* This bit doesn't work. Does not unlock avatars once window opens by changing muck points
        // Increase muckPoints to unlock remaining avatars
        AvatarController.setMuck(100);

        // Check locked avatars
        clickOn("#skeleton");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("skeleton"));
        clickOn("#wonderWoman");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("wonderWoman"));
        clickOn("#yoshi");
        avatar = AvatarController.getAvatarId();
        assertTrue(avatar.equals("yoshi"));

        // Return muckPoints to 0 for remaining tests
        AvatarController.setMuck(0);*/

    }

    /*@Test
    public void testLockedAvatars() {

    }*/
}