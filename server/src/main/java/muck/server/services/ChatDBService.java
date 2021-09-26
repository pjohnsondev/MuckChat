package muck.server.services;
import muck.server.database.Database;
import muck.server.models.models.ChannelUsersModel;
import muck.server.models.models.ChatChannelModel;
import muck.server.models.models.MessageModel;
import muck.server.structures.ChatChannelStructure;
import muck.server.structures.ChatMessageStructure;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class ChatDBService {
    ChatChannelModel channel;
    MessageModel message;
    ChannelUsersModel channelUsers;

    public ChatDBService(){
        this.channel = new ChatChannelModel();
        this.message = new MessageModel();
        this.channelUsers = new ChannelUsersModel();
    }

    public ChatDBService(Database db){
        this.channel = new ChatChannelModel(db);
        this.message = new MessageModel(db);
        this.channelUsers = new ChannelUsersModel(db);
    }

    public boolean storeMessage(ChatMessageStructure msg){
        try{
            message.insertMessage(msg);
            return true;
        } catch (SQLException e){
            return false;
        }catch (InvalidParameterException e){
            return false;
        }
    }

    public boolean storeChannel(ChatChannelStructure ch){
        try{
            channel.insertChannel(ch);
            return true;
        } catch (SQLException e){
            return false;
        }catch (InvalidParameterException e){
            return false;
        }
    }

    public ArrayList<ChatMessageStructure> getMessages(){
        ArrayList<ChatMessageStructure> msgs = new ArrayList();
        ChatMessageStructure msg = new ChatMessageStructure();
        try{
            ResultSet result = message.retrieveMessages();
            while(result.next()){
                msg.setMessageId(result.getInt(MessageModel.MESSAGE_ID_COL)); //1));
                msg.setChannelId(result.getInt(MessageModel.CHANNEL_ID_COL));
                msg.setUserName(result.getString(MessageModel.USER_ID_COL));
                msg.setMessage(result.getString(MessageModel.MESSAGE_COL));
                msg.setTimeStamp(result.getDate(MessageModel.TIMESTAMP_COL));
                msgs.add(msg);
            }
        } catch (SQLException e){

        }catch (InvalidParameterException e){
        }
        return msgs;
    }

    public ArrayList<ChatMessageStructure> getMessages(int channelID){
        ArrayList<ChatMessageStructure> msgs = new ArrayList();
        ChatMessageStructure msg = new ChatMessageStructure();
        try{
            ResultSet result = message.retrieveMessages(channelID);
            while(result.next()){
                msg.setMessageId(result.getInt(MessageModel.MESSAGE_ID_COL)); //1));
                msg.setChannelId(result.getInt(MessageModel.CHANNEL_ID_COL));
                msg.setUserName(result.getString(MessageModel.USER_ID_COL));
                msg.setMessage(result.getString(MessageModel.MESSAGE_COL));
                msg.setTimeStamp(result.getDate(MessageModel.TIMESTAMP_COL));
                msgs.add(msg);
            }
        } catch (SQLException e){

        }catch (InvalidParameterException e){
        }
        return msgs;
    }

    //THis needs more work!!!
    public ArrayList<ChatMessageStructure> getMessages(int channelID, Date date){
        ArrayList<ChatMessageStructure> msgs = new ArrayList();
        ChatMessageStructure msg = new ChatMessageStructure();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try{
            ResultSet result = message.retrieveMessages(channelID, sqlDate);
            while(result.next()){
                msg.setMessageId(result.getInt(MessageModel.MESSAGE_ID_COL)); //1));
                msg.setChannelId(result.getInt(MessageModel.CHANNEL_ID_COL));
                msg.setUserName(result.getString(MessageModel.USER_ID_COL));
                msg.setMessage(result.getString(MessageModel.MESSAGE_COL));
                msg.setTimeStamp(result.getDate(MessageModel.TIMESTAMP_COL));
                msgs.add(msg);
            }
        } catch (SQLException e){

        }catch (InvalidParameterException e){

        }
        return msgs;
    }
    public ChatChannelStructure getChannelInfo(int chatChannel){
        ChatChannelStructure ch = new ChatChannelStructure();

        try{
            ResultSet resultCh = channel.retrieveChannel(chatChannel);
            ResultSet resultUsers = channelUsers.retrieveChannelUser(chatChannel);
            ArrayList<String> users = new ArrayList();
            while(resultUsers.next()){
                users.add(resultUsers.getString(ChannelUsersModel.USER_NAME_COL));
            }
            ch.setUserList(users);
            ch.setChannelId(resultCh.getInt(ChatChannelModel.CHANNEL_ID_COL));
            ch.setChannelName(resultCh.getString(ChatChannelModel.CHANNEL_NAME_COL));
        } catch (SQLException e){

        }catch (InvalidParameterException e){

        }
        return ch;
    }

    public void closeConnections(){
        channel.closeDbConnection();
        message.closeDbConnection();
        channelUsers.closeDbConnection();
    }
}
