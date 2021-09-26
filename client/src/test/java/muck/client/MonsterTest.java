package muck.client;

import muck.client.enduring_fantasy.Monster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonsterTest {

    private static final Logger logger = LogManager.getLogger(EnduringFantasyTest.class);

    @Test
    public void testTestSuiteRuns() {
        logger.info("Dummy test to show the test suite runs");
        assertTrue(true);
    }

    @Test
    public void testPlayerSpawn() {
        logger.info("Testing monster is spawned with correct stats. ");
        Monster m = new Monster();
        m.genMonster(0);
        String name = m.getName();
        if (name.equals("Rat")){
            assertEquals(1,m.getHealth());
        } else if (name.equals("Wolf")){
            assertEquals(50,m.getHealth());
        } else {
            assertEquals(200,m.getHealth());
        }
    }



}
