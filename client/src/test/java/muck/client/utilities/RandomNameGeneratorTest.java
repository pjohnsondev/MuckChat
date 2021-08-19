package muck.client.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomNameGeneratorTest {

    private static final Logger logger = LogManager.getLogger(RandomNameGenerator.class);

    @BeforeAll
    public static void beforeAll(){
        logger.info("Running 'before all' setup");
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("Running 'before each' setup");
    }

    @Test
    public void testGeneratesName(){
        RandomNameGenerator rng = new RandomNameGenerator();
        assertDoesNotThrow(() -> rng.generateName());
    }

}
