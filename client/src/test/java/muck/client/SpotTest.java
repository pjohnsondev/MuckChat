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
        logger.info("Output of dice roll is " + oneDice);
        assertAll("oneDice should be greater or equal 1 and less than or equal number of sides",
                () -> assertTrue(oneDice <= sides*numOfRolls),
                () -> assertTrue(oneDice >= 1));

    }
    @Test
    public void testCheck(){
        logger.info("Testing Spot.Check functionality");

        Spot spot = new Spot();

        assertAll("oneDice should be greater or equal 1 and less than or equal number of sides",
                () -> assertTrue(spot.Check(10,1)), //this should always be true
                () -> assertFalse(spot.Check(10,11))); //this should always be false

    }

    @Test
    public void testDefend(){
        logger.info("Testing defend values between min and max expected");
        Spot spot = new Spot();
        double oneDefend = spot.Defend();
        logger.info("Output of defence() is " + oneDefend);
        assertAll("spotDefend should not be less than 0.5 or more than 1.5",
                () -> assertTrue(oneDefend <= 1.5),
                () -> assertTrue(oneDefend >= 0.5));

    }
    @Test
    public void testAttack(){
        logger.info("Testing attack values between min and max expected");
        Spot newSpot = new Spot();
        double oneAttack = newSpot.Attack();
        logger.info("Output of attack() is " + oneAttack );
        assertAll("oneAttack should not be less than 0.5 or more than 1.5",
                () -> assertTrue(oneAttack <= 1.5),
                () -> assertTrue(oneAttack >= 0.5));

    }
    @Test
    public void testDamageOutput(){
        logger.info("testing damage output values calculated and returned");
        Spot spot = new Spot();
        double oneDamageOutput = spot.DamageOutput(1.5,0.5,10,10);
        logger.info("Using static values, our damage calculator should return 10 - value is " + oneDamageOutput);
        assertEquals(10,oneDamageOutput);

    }

}


