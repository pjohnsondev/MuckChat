package muck.server.structures;

import java.sql.Date;

public class ChatMessageStructure {
    private Date timeStamp;
    private int messageId;
    private int channelId;
    private String userName;
    private String message;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
