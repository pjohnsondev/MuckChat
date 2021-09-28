package muck.protocol;

import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import muck.protocol.connection.userMessage;


import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the test class for Low Expectations' userMessage.java class.
 * Joint authored by Ryan Birch (rbirch4@myune.edu.au) and Lara Butlin (lbutlin2@myune.edu.au).
 * Revision 3.
 */
public class userMessageTest {

    /**
     * getMessageTest() tests that a userMessage can be set correctly with an associated username
     * and that the accessor method returns the correct message for the passed object.
     */
    @Test
    public void getMessageTest() {
        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test", "testUser");
        assertTrue("This is a test".equals(testMessage.getMessage()));
    }

    /**
     * getMessageTimestampTest() tests that a userMessage can be set with the correct timestamp.
     */
    @Test
    public void getMessageTimestampTest() {
        Date currentDate = new Date();

        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test", "testUser");

        assertTrue(currentDate.equals(testMessage.getMessageTimestamp()));
    }

    /**
     * getAssociatedUserNameTest() tests that an associated username is correctly tied to a userMessage and can be retrieved correctly.
     */
    @Test
    public void getAssociatedUsernameTest() {
        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test message", "testUser");
        assertTrue("testUser".equals(testMessage.getUserName()));
    }
}