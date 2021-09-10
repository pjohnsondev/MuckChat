package muck.core.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private static final Logger logger = LogManager.getLogger(PlayerTest.class);

    // Test Player username
    @Test
    public void testUsername() {
        String username = "user";
        Player player = new Player(username);

        logger.info("Testing a a Players username");
        assertTrue(
                username.equals(player.getUsername()),
                "Player username should be user");
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
        player.setPlayerStats( 50, 50, 50);
        assertAll(
                "Player stats should be 50",
                () -> assertEquals(50, player.getHealth()),
                () -> assertEquals(50, player.getAttack()),
                () -> assertEquals(50, player.getDefence())
        );
    }

    //TODO: ===Currently commented out until database is up====

    // Test player interaction with achievements
    @Test
    public void testAchievementInteraction() {
        Player player = mock(Player.class);
        String achievement = "Wannabe";
        String description = "I want an achievement";

        logger.info("Testing that the player should not have any achievements");
        assertTrue(
                player.getAchievements().isEmpty(),
                "Player should have no achievements");

        logger.info("Testing that the player can receive and get achievements");
        player.addAchievement(achievement, description);
        /*
        assertTrue(
                () -> {
                    if(player.getAchievements().get(0)[0].equals(achievement) &&
                            player.getAchievements().get(0)[1].equals(description)) {
                        return true;
                    }
                    return false;
                },
                "Player should have the achievement"
        );
         */
    }

    // Test players interaction with collectables and inventory
    @Test
    public void testCollectableInteraction() {
        Player player = mock(Player.class);
        Player otherPlayer = mock(Player.class);
        String collectable = "Special Item";

        logger.info("Testing that a player should have no items in inventory");
        assertTrue(
                player.getInventory().isEmpty(),
                "Player should have no items in inventory");

        // Add item to player inventory
        logger.info("Testing adding an item to player inventory");
        player.addItemToInventory(collectable);
        /*
        assertTrue(
                () -> {
                    for(int i = 0; i < player.getInventory().size(); i++) {
                        if(player.getInventory().get(i).equals(collectable)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Player should have item");
        */

        // Trade with other player
        logger.info("Testing that the player cannot trade with a nonexistent player");
        assertFalse(player.tradeCollectable(collectable, "Nonexistent player"));

        logger.info("Testing that the player can trade an item with another player");
        /*
        assertAll(
                "Player should no longer have item and other player should have item" ,
                () -> assertTrue(player.tradeCollectable(collectable, otherPlayer.getIdentifier())),
                () -> assertFalse(
                        () -> {
                            for(int i = 0; i < player.getInventory().size(); i++) {
                                if(player.getInventory().get(i).equals(collectable)) {
                                    return true;
                                }
                            }
                            return false;
                        }),
                () -> assertTrue(
                        () -> {
                            for(int i = 0; i < otherPlayer.getInventory().size(); i++) {
                                if(otherPlayer.getInventory().get(i).equals(collectable)) {
                                    return true;
                                }
                            }
                            return false;
                        })
                );
         */
    }
}
