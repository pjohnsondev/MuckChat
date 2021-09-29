package muck.client;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import muck.client.frogger.Frogger;
import muck.client.space_invaders.SpaceInvaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class FroggerTest extends ApplicationTest {

    private static final Logger logger = LogManager.getLogger(Frogger.class);

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double FROG_WIDTH = 38;
    private static final double FROG_HEIGHT = 38;
    private static final double CAR_WIDTH = 40;
    private static final double CAR_HEIGHT = 40;
    private static final double MOVEMENT_X = 40;
    private static final double MOVEMENT_Y = 40;

    @Override
    public void start(Stage stage) throws IOException {

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Scene scene = new Scene(new StackPane(canvas));

        stage.setScene(scene);
        stage.setTitle("Muck 2021 Frogger Tests");

    }

    @Test
    void collisionTest() {
        logger.info("Testing logic used for frog and car collision detection.");
        // Create a Frog
        Rectangle frog = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        frog.setTranslateY(HEIGHT - (FROG_HEIGHT + 1));
        frog.setTranslateX(WIDTH/2);

        // Create a Car
        Rectangle car = new Rectangle(CAR_WIDTH, CAR_HEIGHT, Color.RED);
        car.setTranslateY(CAR_HEIGHT);
        car.setTranslateX(WIDTH/2);

        assertTrue(frog.intersects(car.getBoundsInLocal()));
    }

    @Test
    void moveFrogUpTest() {
        logger.info("Testing logic used for frog movement (UP)");
        // Create a Frog
        Rectangle frog = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        frog.setTranslateY(HEIGHT - (FROG_HEIGHT + 1));
        frog.setTranslateX(WIDTH/2);

        // Move frog UP
        frog.setTranslateY(frog.getTranslateY() - MOVEMENT_Y);

        assertTrue(frog.getTranslateY() == 521.0);
    }

    @Test
    void moveFrogDownTest() {
        logger.info("Testing logic used for frog movement (DOWN)");
        // Create a Frog
        Rectangle frog = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        frog.setTranslateY((HEIGHT - MOVEMENT_Y) - (FROG_HEIGHT + 1));
        frog.setTranslateX(WIDTH/2);

        // Move Frog DOWN
        frog.setTranslateY(frog.getTranslateY() + MOVEMENT_Y);

        assertTrue(frog.getTranslateY() == 561.0);
    }

    @Test
    void moveFrogLeftTest() {
        logger.info("Testing logic used for frog movement (LEFT)");
        // Create a Frog
        Rectangle frog = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        frog.setTranslateY(HEIGHT - (FROG_HEIGHT + 1));
        frog.setTranslateX(WIDTH/2);

        // Move Frog LEFT
        frog.setTranslateX(frog.getTranslateX() - MOVEMENT_X);

        assertTrue(frog.getTranslateX() == 360.0);
    }

    @Test
    void moveFrogRightTest() {
        logger.info("Testing logic used for frog movement (RIGHT)");
        // Create a Frog
        Rectangle frog = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        frog.setTranslateY(HEIGHT - (FROG_HEIGHT + 1));
        frog.setTranslateX(WIDTH/2);

        // Move Frog RIGHT
        frog.setTranslateX(frog.getTranslateX() + MOVEMENT_X);

        assertTrue(frog.getTranslateX() == 440.0);
    }

}
