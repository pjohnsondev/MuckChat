package muck.client.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpriteDistanceTest {

    private static final Logger logger = LogManager.getLogger(SpriteDistance.class);

    @BeforeAll
    public static void beforeAll(){
        logger.info("Running 'before all' setup");
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("Running 'before each' setup");
    }

    @Test
    public void producesNoErrors(){
        assertTrue(true);
    }

    @Test
    public void testCalc1(){
        assertTrue(true);
    }

    @Test
    public void testCalc2(){
        assertTrue(true);
    }

    @Test
    public void testCalc3(){
        assertTrue(true);
    }

}
