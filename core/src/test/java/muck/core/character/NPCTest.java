package muck.core.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mockito.Mockito.*;

public class NPCTest {

    private static final Logger logger = LogManager.getLogger(NPCTest.class);

    // Test the NPC difficulty behaviour
    @Test
    public void testDifficulty() {
        NPC npc = new NPC();

        logger.info("Testing that negative difficulty level values should be one");
        npc.setDifficulty(-5);
        assertEquals(1,
                npc.getDifficulty(),
                "Negative difficulty should be changed to 1");

        logger.info("Testing that the difficulty level can be changed to 25");
        npc.setDifficulty(25);
        assertEquals(25,
                npc.getDifficulty(),
                "Difficulty should have been change to 25");

        logger.info("Testing that the difficulty level can increase 5 levels to 30");
        assertEquals(30,
                npc.increaseDifficulty(5),
                "Difficulty should be changed to 30");
    }

    // Test the NPC stat behaviour
    @Test
    public void testStats() {
        NPC npc = new NPC();

        logger.info("Testing NPC all stats can be set to 10");
        npc.setNPCStats(10, 10, 10, 10);
        assertAll(
                () -> assertEquals(10, npc.getHealth()),
                () -> assertEquals(10, npc.getAttack()),
                () -> assertEquals(10, npc.getDefence()),
                () -> assertEquals(10, npc.getDifficulty())
        );

        logger.info("Testing that negative stats are converted to base values");
        npc.setNPCStats(-5, -5, -5, -5);
        assertAll(
                () -> assertEquals(0, npc.getHealth()),
                () -> assertEquals(0, npc.getAttack()),
                () -> assertEquals(0, npc.getDefence()),
                () -> assertEquals(1, npc.getDifficulty())
        );
    }

    // Test NPC direction behaviour
    @Test
    public void testDirectionFacing() {
        NPC npc = new NPC();

        assertAll("Testing setting a valid direction, then an invalid one for a Player object",
                () -> assertTrue(npc.setDirection("down")),
                () -> assertFalse(npc.setDirection("notValid")));
    }

    // Test health increment function
    @Test
    public void testHealthIncrease() {
        int initialHealth = 100;
        NPC npc = new NPC();
        npc.setHealth(initialHealth);

        logger.info("Testing NPC health can increase by 30");
        int healthIncrement = 30;
        npc.increaseHealth(healthIncrement);
        assertEquals(
                npc.getHealth(),
                initialHealth + healthIncrement,
                "NPC health should now be 130");
    }

    // Test health decrement function
    @Test
    public void testHealthDecrease() {
        int initialHealth = 100;
        NPC npc = new NPC();
        npc.setHealth(initialHealth);

        // Reduce player health, but still alive
        logger.info("Testing the health decrease of a NPC, that is still alive");
        int healthDecrement1 = 40;
        boolean status = npc.decreaseHealth(healthDecrement1);
        assertEquals(
                npc.getHealth(),
                60,
                "Health should be 60");
        assertFalse(
                status,
                "NPC is still alive");

        npc.setHealth(initialHealth);

        // Reduce player health, but now dead
        logger.info("Testing the health decrease of a NPC, that now has died");
        int healthDecrement2 = 101;
        npc.decreaseHealth(healthDecrement2);
        status = npc.decreaseHealth(healthDecrement2);
        assertEquals(
                npc.getHealth(),
                0,
                "Health should be 0");
        assertTrue(
                status,
                "NPC is now dead");
    }
}
