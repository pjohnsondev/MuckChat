package muck.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




/**
 * Tests concerning anything to do with the client-side map i.e checking players can't move out of bounds or into objects
 */
public class MapTest {

    private static final Logger logger = LogManager.getLogger(MapTest.class);

    @BeforeEach
    public void beforeEachTest(){
        TileMapReader tm = new TileMapReader("/Test.tmx");
        //TileMapReader tmerror = new TileMapReader("/T.tm");
    }

    @Test
    public void testSuiteRuns() {
        logger.info("This test to shows the test suite runs");
        assertTrue(true);
    }


    @Test void testOutOfBounds() {
        // Test that an attempt to move off the game map fails (throws some exception)
    }
    @Test void testCollisionDetection() {
        // Test that an attempt to move into an object fails (throws some exception)
    }
    @Test void testTileMapReader() {
        //assertThrows();
    }
}