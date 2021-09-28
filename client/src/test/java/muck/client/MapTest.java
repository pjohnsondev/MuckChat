package muck.client;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;

/**
 * Tests concerning anything to do with the client-side map i.e checking players can't move out of bounds or into objects
 */
public class MapTest {

    private static final Logger logger = LogManager.getLogger(MapTest.class);

    @BeforeEach
    public void beforeEachTest(){
        //
    }

    @Test
    public void testSuiteRuns() {
        logger.info("This test to shows the test suite runs");
        assertTrue(true);
    }

    @Disabled //Due to out of memory error
    @Test void testOutOfBounds() {
        final Canvas canvas = new Canvas(800,800);
        GameMap gm = new GameMap(canvas);
        logger.info("Test getTileIndex");
        assertEquals(gm.getTileIndex(20,30,1),0);



        //logger.info(gm.getTileIndex(350,350,2));



        // Test that an attempt to move off the game map fails (throws some exception)
    }
    @Test void testCollisionDetection() {
        // Test that an attempt to move into an object fails (throws some exception)
        // Part of this test happens in the Sprite Test
    }
    @Test void testTileMapReader() {
        TileMapReader tm = new TileMapReader("/Test.tmx"); // This is Test/Resources
        //Make sure public methods in TileMapReader don't get changed.
        assertEquals(tm.getWidth() ,80);
        assertEquals(tm.getHeight() , 64);
        assertEquals(tm.getTileWidth() ,32);
        assertEquals(tm.getTileHeight() , 32);
        assertEquals(tm.getTileColumns() , 32);
        assertEquals(tm.getTileCount() , 1024);
        assertEquals(tm.getLayerId(0,1,1) , 307);
        //logger.info(tm.getLayerId(0,1,1));//Have been using this get actual value to data
    }
}