package muck.client.space_invaders;

import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.*;

import static org.mockito.Mockito.mock;


public class SpaceInvadersTest {

    private static final Logger logger = LogManager.getLogger(SpaceInvaders.class);


    @Test
    public void launchSpaceInvadersTest() throws Exception {
        logger.info("Testing that the stage can be launched");
        SpaceInvaders spaceInvaders = mock(SpaceInvaders.class);
        Stage stage = mock(Stage.class);
        spaceInvaders.start(stage);

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



}
