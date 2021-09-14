package muck.server.structures;

import java.util.ArrayList;

public class ChatChannelStructure {
    private int channelId;



    private int cuID;
    private String channelName;
    private ArrayList<String> userList;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ArrayList<String> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }

    public int getCuID() {return cuID;}

    public void setCuID(int cuID) {this.cuID = cuID;}

}
