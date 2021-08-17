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

        logger.info("Testing that a player should have no items in inventory");
        assertNull(player.getInventory(), "Player should have no items in inventory");

        // Add item to player inventory
        logger.info("Testing adding an item to player inventory");
        player.addItemToInventory(collectable);
        assertTrue(
                () -> {
                    for(int i = 0; i < player.getInventory().length; i++) {
                        if(player.getInventory()[i].equals(collectable)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Player should have item");

        // Trade with other player
        logger.info("Testing that the player can trade an item with another player");
        assertAll(
                "Player should no longer have item and other player should have item" ,
                () -> assertTrue(player.tradeCollectable(collectable, otherPlayer.getIdentifier())),
                () -> assertFalse(
                        () -> {
                            for(int i = 0; i < player.getInventory().length; i++) {
                                if(player.getInventory()[i].equals(collectable)) {
                                    return true;
                                }
                            }
                            return false;
                        }),
                () -> assertTrue(
                        () -> {
                            for(int i = 0; i < otherPlayer.getInventory().length; i++) {
                                if(otherPlayer.getInventory()[i].equals(collectable)) {
                                    return true;
                                }
                            }
                            return false;
                        })
                );
    }

    // Test player interaction with achievements
    @Test
    public void testAchievementInteraction() {
        Player player = mock(Player.class);
        String achievement = "Wannabe";

        logger.info("Testing that the player should not have any achievements");
        assertNull(player.getAchievements(), "Player should have no achievements");

        logger.info("Testing that the player can receive and get achievements");
        player.addAchievement(achievement);
        assertTrue(
                () -> {
                    for(int i = 0; i < player.getAchievements().length; i++) {
                        if(player.getAchievements()[i].equals(achievement)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Player should have the achievement"
        );
    }
}
