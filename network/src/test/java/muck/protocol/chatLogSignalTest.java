package muck.protocol;
import muck.protocol.connection.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for Low Expectations' chatLog.java class.
 * Authored by Ryan Birch (rbirch4@myune.edu.au).
 * Revision 1.
 */
public class chatLogSignalTest {
    /**
     * chatLogPopulatedTest() test that a new chatLog object can be populated with messages and each of those messages
     * can be retrieved into a new List<String> correctly.
     */
    @Test
    public void chatLogPopulatedTest() {
        List<String> testChat = new ArrayList<String>();
        for(int i=0; i<10; i++) {
            testChat.add("Test Message: " + i + ".");
        }
        chatLog testLog = new chatLog();
        testLog.setChatLog(testChat);
        List<String> testReturnedLog = new ArrayList<String>();
        testReturnedLog = testLog.getChatLog();
        for(int testIndex = 0; testIndex<10; testIndex++) {
            assertEquals(testReturnedLog.get(testIndex), "Test Message: " + testIndex + ".");
        }

    }
}
