package muck.protocol.connection;

import java.util.*;

/**
 * This class acts as a signal to tell chatCreateTable to create a new chat log.
 * Holds the name of the chat log to create - this will be given to chatCreateTable.createNewChat() upon call.
 * Original Author: Ryan Birch (rbirch4@myune.edu.au) as a part of Low Expectations.
 */
public class chatLog {
    private List<String> chatLog;

    public List<String> getChatLog() {
        return this.chatLog;
    }

    public void setChatLog(List<String> newLog) {
      chatLog = newLog;
    }
}
