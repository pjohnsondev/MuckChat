package muck.protocol.connection;

/**
 * userMessage creates a new instance of a message to be sent in chat.
 * Initial Author: Ryan Birch (rbirch4) - Low Expectations.
 * Revision: 1.
 */

public class userMessage {
    private String message;

    /**
     * setMessage sets the message to be sent.
     * @param messageIn
     */
    public void setMessage(String messageIn) {
        this.message = messageIn;
    }

    /**
     * getMessage can be called to return the string of the message.
     * @return
     */
    public String getMessage() {
        return this.message;
    }

}
