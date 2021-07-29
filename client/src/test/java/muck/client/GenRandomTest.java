package muck.client;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**Wasn't working on PC, wanting to test using Turing.
 * Execution failed for client:test
 *
 */

public class GenRandomTest {

    private static final Logger logger = LogManager.getLogger(GenRandomTest.class);

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


