package muck.protocol.connection;
         
         
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * userMessage creates a new instance of a message to be sent in chat.
 * Initial Author: Ryan Birch (rbirch4) - Low Expectations.
 * Revision: 1.
 */

public class userMessage {

    private String message;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date messageTimestamp;
    private int senderConnectionId;


    /**
     * setMessage sets the message to be sent.
     * @param messageIn
     */
    public void setMessage(String messageIn) {
        this.message = messageIn;
        this.messageTimestamp = new Date();

    }

    /**
     * getTimestamp - get the timestamp of the message
     * @return
     */
     public String getMessageTimestamp() {
         return formatter.format(this.messageTimestamp);
     }


    /**
     * getMessage can be called to return the string of the message.
     * @return
     */
    public String getMessage() {
        return this.message;
    }

}
