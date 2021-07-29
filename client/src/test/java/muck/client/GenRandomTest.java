package muck.client;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**Wasn't working on PC, wanting to test using Turing.
 * Execution failed for client:test
 *
 */

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
        int min = 1;
        int max = 10;
        GenRandom testerGenerator = new GenRandom();
        int random = testerGenerator.GetRandomDice(min,max);
        assertAll("random should be greater or equal min and less than or equal max",
                () -> assertTrue(random <= max),
                () -> assertTrue(random >= min));
    }

    //I think we can delete this test?
    @Test
    public boolean randomIntGenerated() {
        logger.info("Testing generated random int is between 1 and 10");

        int min = 1;
        int max = 10;

        Random random = new Random();

        int r = random.nextInt(max + min) + min;
        if (r >= min && r <= max) {

            return true;
        }

        return false;

    }
}


