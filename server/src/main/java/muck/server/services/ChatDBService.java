package muck.server.services;
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
    ChatChannelModel channel = new ChatChannelModel();
    MessageModel message = new MessageModel();
    ChannelUsersModel channelUsers = new ChannelUsersModel();

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
                msg.setMessageId(result.getInt(1));
                msg.setChannelId(result.getInt(2));
                msg.setUserId(result.getString(3));
                msg.setMessage(result.getString(4));
                msg.setTimeStamp(result.getDate(5));
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
                msg.setMessageId(result.getInt(1));
                msg.setChannelId(result.getInt(2));
                msg.setUserId(result.getString(3));
                msg.setMessage(result.getString(4));
                msg.setTimeStamp(result.getDate(5));
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
        try{
            ResultSet result = message.retrieveMessages(channelID);
            while(result.next()){
                msg.setMessageId(result.getInt(1));
                msg.setChannelId(result.getInt(2));
                msg.setUserId(result.getString(3));
                msg.setMessage(result.getString(4));
                msg.setTimeStamp(result.getDate(5));
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
                users.add(resultUsers.getString(3));
            }
            ch.setUserList(users);
        } catch (SQLException e){

        }catch (InvalidParameterException e){

        }

        return ch;
    }

}
