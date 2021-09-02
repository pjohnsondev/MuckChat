package muck.protocol.connection;

/**
 * This class acts as a signal to tell chatCreateTable to create a new chat log.
 * Holds the name of the chat log to create - this will be given to chatCreateTable.createNewChat() upon call.
 * Original Author: Ryan Birch (rbirch4@myune.edu.au) as a part of Low Expectations.
 */
public class newChatLog {
    private String newChatLogName;

    /**
     * Setter method to set a name for the new chat log.
     * @param nameToSet
     */
    public void setChatName(String nameToSet) {
        this.newChatLogName = nameToSet;
    }

    /**
     * Returns the name of the new chat log to create as a String so it can be implemented in a DB prepared statement.
     * @return
     */
    public String getChatName() {
        return this.newChatLogName;
    }

}
