package muck.protocol;

import org.junit.jupiter.api.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import muck.protocol.connection.userMessage;


import static org.junit.jupiter.api.Assertions.*;

public class userMessageTest {

    @Test
    public void getMessageTest() {
        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test", "testUser");
        assertTrue("This is a test".equals(testMessage.getMessage()));
    }

    @Test
    public void getMessageTimestampTest() {
        Date currentDate = new Date();

        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test", "testUser");

        assertTrue(currentDate.equals(testMessage.getMessageTimestamp()));
    }

    @Test
    public void getAssociatedUsernameTest() {
        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test message", "testUser");
        assertTrue("testUser".equals(testMessage.getUserName()));
    }
}