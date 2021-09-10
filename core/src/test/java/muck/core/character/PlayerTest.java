package muck.core.character;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private static final Logger logger = LogManager.getLogger(PlayerTest.class);
    private static Player player;
    private static Player mockPlayer;
    private static Player otherPlayer;
    private static final String USERNAME = "Test_User";

    @BeforeAll
    public static void init() {
        player = new Player(USERNAME, true);
        mockPlayer = mock(Player.class, withSettings().useConstructor(USERNAME, true));
        otherPlayer = mock(Player.class, withSettings().useConstructor("otherPlayer", true));
    }

    // Test Player username
    @Test
    public void testUsername() {
        logger.info("Testing a a Players username");
        assertTrue(
                USERNAME.equals(player.getUsername()),
                "Player username should be user");
    }

    // Test Player stat change behaviour
    @Test
    public void testPlayerStats() {
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
        String achievement = "Wannabe";
        String description = "I want an achievement";
        ArrayList<String[]> achievements = new ArrayList<>();
        achievements.add(new String[]{achievement, description});

        logger.info("Testing that the player should not have any achievements");
        assertTrue(
                player.getAchievements().isEmpty(),
                "Player should have no achievements");

        logger.info("Testing that the player can receive and get achievements");
        player.addAchievement(achievement, description);

        // mock retrieval
        when(mockPlayer.getAchievements()).thenReturn(achievements);

        assertTrue(
                () -> {
                    if(mockPlayer.getAchievements().get(0)[0].equals(achievement) &&
                            mockPlayer.getAchievements().get(0)[1].equals(description)) {
                        return true;
                    }
                    return false;
                },
                "Player should have the achievement"
        );
    }

    // Test players interaction with collectables and inventory
    @Test
    public void testCollectableInteraction() {
        String collectable = "Special Item";
        ArrayList<String> items = new ArrayList<String>();
        items.add(collectable);

        logger.info("Testing that a player should have no items in inventory");
        assertTrue(
                player.getInventory().isEmpty(),
                "Player should have no items in inventory");

        // Add item to player inventory
        logger.info("Testing adding an item to player inventory");
        mockPlayer.addItemToInventory(collectable);

        // mock retrieval
        when(mockPlayer.getInventory()).thenReturn(items);

        assertTrue(
                () -> {
                    for(int i = 0; i < mockPlayer.getInventory().size(); i++) {
                        if(mockPlayer.getInventory().get(i).equals(collectable)) {
                            return true;
                        }
                    }
                    return false;
                },
                "Player should have item");

        // Trade with nonexistent player
        logger.info("Testing that the player cannot trade with a nonexistent player");
        assertFalse(player.tradeCollectable(collectable, "Nonexistent player"));

        // Trade with other player with nonexistent collectable
        logger.info("Testing that the player cannot trade with a nonexistent player");
        assertFalse(player.tradeCollectable("Nonexistent collectable", otherPlayer.getUsername()));

        logger.info("Testing that the player can trade an item with another player");
        /*
        assertAll(
                "Player should no longer have item and other player should have item" ,
                () -> assertTrue(player.tradeCollectable(collectable, otherPlayer.getUsername())),
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
