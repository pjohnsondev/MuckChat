package muck.server.models.models;

import muck.server.database.Database;
import muck.server.models.AbstractModel.Model;
import muck.server.structures.ChatChannelStructure;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChannelUsersModel extends Model {
    public static String CHANNEL_USER_ID_COL = "cu_id";
    public static String USER_NAME_COL = "user_name";
    public static String CHANNEL_NAME_COL = "channel_id";

    public ChannelUsersModel() {commonCreatorFunctions();}

    public ChannelUsersModel(Database db) {
        super(db);
        commonCreatorFunctions();
    }

    private void commonCreatorFunctions(){
        this.table = "channel_user_list";
        try {
            this.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table for the chat messages, if it does not exist already
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void createTable() throws SQLException {
        // create a new table
        db.databaseIsConnected();
        db.createTableIfNotExists(
                "channel_user_list",
                "CREATE TABLE channel_user_list "
                        + "(cu_id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " channel_id INTEGER NOT NULL, "
                        + " user_name VARCHAR(255) NOT NULL) "
        );
    }

    public void insertChannelUser(ChatChannelStructure channel) throws SQLException, InvalidParameterException{
        for (String name : channel.getUserList()){
            db.query("INSERT INTO channel_user_list (cu_id, channel_id, user_name) VALUES (?, ?, ?)");
            System.out.println(channel);
            db.bindInt(1, channel.getCuID());
            db.bindInt(2, channel.getChannelId());
            db.bindString(3, name);
            db.executeInsert();
        }
    }

    public ResultSet retrieveChannelUser (ChatChannelStructure channel) throws SQLException{
        return this.select("channel_id", channel.getChannelId());
    }

    public ResultSet retrieveChannelUser (int channelID) throws SQLException{
        return this.select("channel_id", channelID);
    }
}
