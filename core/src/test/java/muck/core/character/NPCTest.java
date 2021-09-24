package muck.core.character;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NPCTest {

    private static final Logger logger = LogManager.getLogger(NPCTest.class);
    private static NPC npc;

    @BeforeAll
    public static void init() {
        npc = new NPC();
    }

    // Test the NPC difficulty behaviour
    @Test
    public void testDifficulty() {
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
    
    @Test
    // Test the NPCMediator is working correctly, including its global tracking instance
    public void testNPCMediator() throws CharacterDoesNotExistException {
        ConcreteNPCMediator concreteNPCMediator = GlobalTracker.concreteNPCMediator;
        
        // We will not mock these objects, as we must ensure they actually work interdependently (technically integration)
        NPC npc1 = new NPC("dummy1");
        NPC npc2 = new NPC("dummy2");

        // Subscribe NPCs to mediator
        concreteNPCMediator.addNPCToMediator(npc1);
        concreteNPCMediator.addNPCToMediator(npc2);

        // Send message from npc1 to npc2
        concreteNPCMediator.messageToOtherNPC(npc1, npc2.getIdentifier(), Action.Follow);
        
        // Ensure the message successfully set the state of the npc2 object, and the source is recognized as
        // being from the npc1 object
        assertEquals(npc2.getOtherNPCAction(), Action.Follow);
        assertEquals(npc2.getOtherNPCIdentifier(), npc1.getIdentifier());
    }
}
