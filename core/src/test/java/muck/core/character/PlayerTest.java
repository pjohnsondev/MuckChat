package muck.core.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerTest {

    private static final Logger logger = LogManager.getLogger(PlayerTest.class);
    
    // Test Dummy - TO BE REMOVED
    @Test
    public void testTestSuiteRuns() {
        logger.info("Dummy test");
        assertTrue(true);
    }
    
    // Test failure for an invalid direction, and success for a valid direction
    @Test
    public void testDirectionFacing() {
        Player player = new Player();

        assertAll("Testing setting a valid direction, then an invalid one for a Player object",
                () -> assertTrue(player.setDirection("down")),
                () -> assertFalse(player.setDirection("notValid")));
    }
    
    // Test player health changes behave as expected
    @Test
    public void testHealthModification() {
        Player player = new Player();
        
        player.setHealth(-5);
        assertEquals(0, player.getHealth());
        
        player.setHealth(30);
        assertEquals(30, player.getHealth());
    }
}
