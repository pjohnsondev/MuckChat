package muck.client.space_invaders;


import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.*;

import java.io.IOException;


public class SpaceInvadersTest extends ApplicationTest {

    private static final Logger logger = LogManager.getLogger(SpaceInvaders.class);

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;


    @Override
    public void start(Stage stage) throws IOException {

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Scene scene = new Scene(new StackPane(canvas));

        stage.setScene(scene);
        stage.setTitle("Muck 2021 Space Invaders Tests");

    }

    @Test
    public void collisionTest() {
        logger.info("Testing getBounds method used for collision detection");
        Rectangle r1 = new Rectangle(1,1,1,1);
        Rectangle r2 = new Rectangle(1,1,1,1);
        r1.getBounds();
        r2.getBounds();
        assertTrue(r1.intersects(r2));

    }

    @Test
    public void moveUpTest(){
        logger.info("Testing setX(), setY(), getY() and moveUp() methods");
        SpriteAnimation spriteTest = new SpriteAnimation();

        spriteTest.setX(400);
        spriteTest.setY(400);
        spriteTest.moveUp();
        assertEquals(spriteTest.getY(),394.0);
    }

    @Test
    public void moveDownTest(){
        logger.info("Testing setX(), setY(), getY() and moveDown() methods");
        SpriteAnimation spriteTest = new SpriteAnimation();

        spriteTest.setX(400);
        spriteTest.setY(400);
        spriteTest.moveDown();
        assertEquals(spriteTest.getY(),406.0);
    }

    @Test
    public void moveRightTest(){
        logger.info("Testing setX(), setY(), getX() and moveRight() methods");
        SpriteAnimation spriteTest = new SpriteAnimation();

        spriteTest.setX(400);
        spriteTest.setY(400);
        spriteTest.moveRight();
        assertEquals(spriteTest.getX(),406.0);
    }

    @Test
    public void moveLeftTest(){
        logger.info("Testing setX(), setY(), getX() and moveLeft() methods");
        SpriteAnimation spriteTest = new SpriteAnimation();

        spriteTest.setX(400);
        spriteTest.setY(400);
        spriteTest.moveLeft();
        assertEquals(spriteTest.getX(),394.0);
    }

    @Test
    public void moveStarTest(){
        logger.info("Testing the moveStar() method");
        Star star = new Star();

        double startingPosition = star.getCenterY();
        star.moveStar();
        assertEquals(star.getCenterY(),startingPosition + 5);
    }

}
