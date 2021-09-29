package muck.protocol;

import org.junit.jupiter.api.Test;
import muck.protocol.connection.Interaction;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a simple test class for the Interaction object.
 */
public class InteractionTest {

    /**
     * This test gets the command that was stored in the Interaction.
     */
    @Test
    public void getCommandTest() {
        Interaction interaction = new Interaction("/wave", 0);
        assertEquals("/wave", interaction.getCommand());
    }

}
