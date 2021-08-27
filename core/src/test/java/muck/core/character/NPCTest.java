package muck.core.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mockito.Mockito.*;

public class NPCTest {

    private static final Logger logger = LogManager.getLogger(NPCTest.class);

    // Test the NPC difficulty behaviour
    @Test
    public void testDifficulty() {
        NPC npc = new NPC();

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
    }

}
