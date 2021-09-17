package muck.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpriteTest extends ApplicationTest {
    private static final Logger logger = LogManager.getLogger(SpriteTest.class);
    private String avatar;
    private String previous = "playerDashboard";

    @Override
    public void start(Stage stage) throws IOException {
        AvatarController.avatarCreation("Username", "DisplayName", "error");
        FXMLLoader loader = new FXMLLoader(AvatarTest.class.getResource("/fxml/Avatar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @Order(1)
    public void SpriteMethodTest() {
        logger.info("Checking Sprite Methods");
        // Check unlocked avatars
        logger.info("Initialise Peach");
        clickOn("#peach");
        avatar = AvatarController.getAvatarId();
        clickOn("#submit");
        Sprite hero = new Sprite(300,300);
        TileMapReader tm = new TileMapReader("/maps/homeTown.tmx");
        hero.setDx(6);
        hero.setDy(6);
        hero.move(tm, hero);
        assertEquals(hero.getPosX(),306.0);
        assertEquals(hero.getPosY(),306.0);
        hero.setDx(-6);
        hero.setDy(-6);
        hero.move(tm, hero);
        assertEquals(hero.getPosX(),300.0);
        assertEquals(hero.getPosY(),300.0);
        logger.info(hero.getPosX());

    }
}
