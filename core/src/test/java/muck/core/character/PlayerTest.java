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

    // Test Player stat change behaviour
    @Test
    public void testPlayerStats() {
        Player player = new Player();

        logger.info("Testing that negative stats values should be zero");
        player.setAttack(-5);
        player.setDefence(-5);
        assertAll(
                "Player stats should be zero",
                () -> assertEquals(0, player.getAttack()),
                () -> assertEquals(0, player.getDefence())
        );

        logger.info("Testing that stat changes occur");
        player.setAttack(50);
        player.setDefence(50);
        assertAll(
                "Player stats should be 50",
                () -> assertEquals(50, player.getAttack()),
                () -> assertEquals(50, player.getDefence())
        );
    }
    
    // Test health increment function
    @Test
    public void testHealthIncrease() {
        int initialHealth = 100;
        Player player = new Player();
        player.setHealth(initialHealth);
        
        int healthIncrement = 30;
        player.increaseHealth(healthIncrement);
        assertEquals(player.getHealth(), initialHealth + healthIncrement);
    }
    
    // Test health decrement function
    @Test
    public void testHealthDecrease() {
        int initialHealth = 100;
        Player player = new Player();
        player.setHealth(initialHealth);

        // Reduce player health, but still alive
        int healthDecrement1 = 40;
        boolean status = player.decreaseHealth(healthDecrement1);
        assertEquals(player.getHealth(), 60);
        assertFalse(status);

        player.setHealth(initialHealth);

        // Reduce player health, but now dead
        int healthDecrement2 = 101;
        player.decreaseHealth(healthDecrement2);
        status = player.decreaseHealth(healthDecrement2);
        assertEquals(player.getHealth(), 0);
        assertTrue(status);
    }

    /* TODO: ===Currently commented out until database is up====
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
        String description = "I want an achievement";

        logger.info("Testing that the player should not have any achievements");
        assertNull(player.getAchievements(), "Player should have no achievements");

        logger.info("Testing that the player can receive and get achievements");
        player.addAchievement(achievement, description);
        assertTrue(
                () -> {
                        if(player.getAchievements()[0][0].equals(achievement) &&
                                player.getAchievements()[0][1].equals(description)) {
                            return true;
                        }
                    return false;
                },
                "Player should have the achievement"
        );
    }
    */
}
