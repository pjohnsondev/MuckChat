package muck.core.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.mockito.Mockito.*;

public class PlayerTest {

    private static final Logger logger = LogManager.getLogger(PlayerTest.class);

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

    // Test players interaction with collectables and inventory
    @Test
    public void testCollectableInteraction() {
        Player player = mock(Player.class);
        Player otherPlayer = mock(Player.class);
        String collectable = "Special Item";

        // Add item to player inventory
        logger.info("Testing adding an item to player inventory");
        player.addItemToInventory(collectable);
        assertEquals(collectable, player.getInventory(), "Player should have item");

        // Trade with other player
        logger.info("Testing that the player can trade an item with another player");
        assertAll(
                "Player should no longer have item and other player should have item" ,
                () -> assertTrue(player.tradeCollectable(collectable, otherPlayer)),
                () -> assertEquals(null, player.getInventory()),
                () -> assertEquals(collectable, otherPlayer.getInventory())
        );


    }
}
