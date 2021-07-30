package muck.client;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class GenRandomTest {

    private static final Logger logger = LogManager.getLogger(GenRandomTest.class);

    @Test
    public void testTestSuiteRuns() {
        logger.info("Dummy test to show the test suite runs");
        assertTrue(true);
    }
    @Test
    public void testRandomDice(){
        logger.info("Testing generated random int is between 1 and 10");
        int sides = 10;
        Dice dice = new Dice(sides);
        int oneDice = dice.Roll(1);
        assertAll("oneDice should be greater or equal 1 and less than or equal number of sides",
                () -> assertTrue(oneDice <= sides),
                () -> assertTrue(oneDice >= 1));
    }

}


