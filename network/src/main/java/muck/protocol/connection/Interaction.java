package muck.protocol.connection;


/**
 * A player interaction (like '/wave').
 * Arlo Taylor (ataylo90@myune.edu.au)
 */
public class Interaction {

    private String command;
    private int senderConnectionId;

    /**
     * Interaction constructor
     * @param commandArg
     * @param senderConnId
     */
    Interaction(String commandArg, int senderConnId) {
        this.command = commandArg;
        this.senderConnectionId = senderConnId;
    }

    /**
     * setCommand sets the interaction command.
     * @param newCommand
     */
    public void setCommand(String newCommand) {
        this.command = newCommand;
    }

    /**
     * setSenderConnectionId sets the sender's connection id.
     * @param SenderId
     */
    public void setSenderConnectionId(int SenderId) {
        this.senderConnectionId = SenderId;
    }

    /**
     * getCommand gets the interaction command.
     * @return
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * getSenderConnectionId gets the sender's connection id.
     * @return
     */
    public int getSenderConnectionId() {
        return this.senderConnectionId;
    }

}
