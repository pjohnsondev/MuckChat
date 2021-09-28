package muck.client;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for the getCurrentMessage() method in MuckClient by Low Expectations.
 * Authored by Ryan Birch (rbirch4@myune.edu.au).
 * Revision 1.
 */

public class messageCheckerTest {
    /**
     * messageCheckerEmptyTest() tests that the correct size List<String> is returned when the message buffer in the client is empty.
     */
    @Test
    public void messageCheckerEmptyTest() {
        List<String> fetchMessages= new ArrayList<String>();
        fetchMessages = MuckClient.INSTANCE.getCurrentMessage();
        assertEquals(fetchMessages.size(), 0);

    }

    /**
     * messageCheckerPopulatedTest() tests that the List<String> returned by getCurrentMesssage contains only one item -
     * the oldest message.
     * This test also checks that the oldest element from the client's buffer was removed - i.e. the List<String> after getCurrentMessage() was called
     * is 1 less than originally.
     */
    @Test
    public void messageCheckerPopulatedTest() {
        for(int i=0; i<10; i++) {
            MuckClient.INSTANCE.inMessages.add("Test Message Number: " + i + ".");
        }
        List<String> fetchMessages= new ArrayList<String>();
        fetchMessages = MuckClient.INSTANCE.getCurrentMessage();


        assertEquals(fetchMessages.get(0), "Test Message Number: " + 0 + ".");
        assertEquals(MuckClient.INSTANCE.inMessages.size(), 9);


    }
}
