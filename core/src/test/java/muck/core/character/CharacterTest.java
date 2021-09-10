package muck.core.character;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    private static final Logger logger = LogManager.getLogger(CharacterTest.class);
    private static Character player;

    @BeforeAll
    public static void init() {
        player = mock(Player.class, CALLS_REAL_METHODS);
    }

    // Test avatar
    @Test
    public void testAvatar() {
        String avatar = "player";

        logger.info("Testing a character can set and get an avatar");

        player.setAvatar(avatar);
        assertEquals(avatar, player.getAvatar());
    }

    // Test X and Y position
    @Test
    public void testXYPosition() {
        int x = 200;
        int y = 300;

        logger.info("Testing a character can set and get X & Y positions");

        player.setXPos(x);
        player.setYPos(y);

        assertAll(
                () -> assertEquals(x, player.getXPos()),
                () -> assertEquals(y, player.getYPos())
        );
    }

    // Test failure for an invalid direction, and success for a valid direction
    @Test
    public void testDirectionFacing() {
        logger.info("Testing directing facing of a character");

        assertAll("Testing setting a valid direction, then an invalid one",
                () -> assertTrue(player.setDirection("down")),
                () -> assertFalse(player.setDirection("notValid")));
    }

    // Test player health changes behave as expected
    @Test
    public void testHealthModification() {
        logger.info("Testing health cannot go below 0");

        player.setHealth(-5);
        assertEquals(0, player.getHealth());

        logger.info("Testing health cannot go above 100");

        player.setHealth(200);
        assertEquals(100, player.getHealth());

        logger.info("Testing normal health behaviour");

        player.setHealth(30);
        assertEquals(30, player.getHealth());
    }

    // Test health increment function
    @Test
    public void testHealthIncrease() {
        int initialHealth = 70;
        player.setHealth(initialHealth);

        logger.info("Testing health can increase");

        int healthIncrement = 30;
        player.increaseHealth(healthIncrement);
        assertEquals(initialHealth + healthIncrement, player.getHealth());
    }

    // Test health decrement function
    @Test
    public void testHealthDecrease() {
        int initialHealth = 100;
        player.setHealth(initialHealth);

        logger.info("Testing health decrease, that is still alive");

        // Reduce player health, but still alive
        int healthDecrement1 = 40;
        assertAll(
                () -> assertFalse(
                        player.decreaseHealth(healthDecrement1)),
                () -> assertEquals(
                        60,
                        player.getHealth())
        );

        player.setHealth(initialHealth);

        logger.info("Testing health decrease, that now has died");

        // Reduce player health, but now dead
        int healthDecrement2 = 101;

        assertAll(
                () -> assertTrue(
                        player.decreaseHealth(healthDecrement2)),
                () -> assertEquals(
                        0,
                        player.getHealth())
        );
    }
}
