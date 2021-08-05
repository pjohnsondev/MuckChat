package muck.client;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class SpotTest {

    private static final Logger logger = LogManager.getLogger(SpotTest.class);

    @Test
    public void testTestSuiteRuns() {
        logger.info("Dummy test to show the test suite runs");
        assertTrue(true);
    }
    @Test
    public void testSpot(){
        logger.info("Testing generated result is TRUE");
        int sides = 10;
        int numOfRolls = 9;
        Dice dice = new Dice(sides);
        int oneDice = dice.Roll(numOfRolls);
        assertAll("oneDice should be greater or equal 1 and less than or equal number of sides",
                () -> assertTrue(oneDice <= sides*numOfRolls),
                () -> assertTrue(oneDice >= 1));
    }

}


