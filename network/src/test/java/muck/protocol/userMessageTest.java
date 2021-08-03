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
        testMessage.setMessage("This is a test");
        assertTrue("This is a test".equals(testMessage.getMessage()));
    }

    @Test
    public void getMessageTimestampTest() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = formatter.format(currentDate);

        userMessage testMessage = new userMessage();
        testMessage.setMessage("This is a test");

        assertTrue(formattedDate.equals(testMessage.getMessageTimestamp()));
    }
}