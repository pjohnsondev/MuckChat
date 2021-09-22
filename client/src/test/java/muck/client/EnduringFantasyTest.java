package muck.client;

import muck.client.enduring_fantasy.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnduringFantasyTest {

    private static final Logger logger = LogManager.getLogger(EnduringFantasyTest.class);

    @Test
    public void testTestSuiteRuns() {
        logger.info("Dummy test to show the test suite runs");
        assertTrue(true);
    }
    @Test
    public void testPlayerSpawn(){
        logger.info("Testing player is spawned with correct stats. ");
        Player p = new Player("testName");
        p.setType("Soldier");
        p.setTypeStats();
        int hp = p.getHealth();
        int dmg = p.getDamage();
        int mgc = p.getMagicStr();
        int magicPoints= p.getMP();

        assertAll("player should be spawned with correct stats for a soldier",
                () -> assertEquals(200, hp),
                () -> assertEquals(50, dmg),
                () -> assertEquals(5, mgc),
                () -> assertEquals(10, magicPoints));
    }
    @Test
    public void testLvlUp(){
        logger.info("Testing level up functionality works ");
        Player p = new Player("testName");
        p.setType("Soldier");
        p.setTypeStats();
        p.setNextLvl(0);
        p.incPcLvl();
        int hp = p.getHealth();
        int dmg = p.getDamage();
        int mgc = p.getMagicStr();
        int magicPoints= p.getMP();

        assertAll("player should be spawned with correct stats for a soldier",
                () -> assertEquals(2, p.getPlayerLvl()),
                () -> assertEquals(300, hp),
                () -> assertEquals(80, dmg),
                () -> assertEquals(15, mgc),
                () -> assertEquals(20, magicPoints));

    }

}


