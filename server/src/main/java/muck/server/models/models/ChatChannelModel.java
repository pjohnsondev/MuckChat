package muck.server.models.models;

import muck.server.database.Database;
import muck.server.models.AbstractModel.Model;
import muck.server.structures.ChatChannelStructure;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatChannelModel extends Model {
    public static String CHANNEL_ID_COL = "channel_id";
    public static String CHANNEL_NAME_COL = "channel_name";

    public ChatChannelModel() {
        commonCreatorFunctions();
    }

    public ChatChannelModel(Database db) {
        super(db);
        commonCreatorFunctions();
    }

    private void commonCreatorFunctions(){
        this.table = "channels";
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
                "channels",
                "CREATE TABLE channels "
                        + "(channel_id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " channel_name VARCHAR(255) NOT NULL) "
        );
    }

    public void insertChannel(ChatChannelStructure channel) throws SQLException, InvalidParameterException {
        //Insert the new user into the database table
        db.query("INSERT INTO channels (channel_id, channel_name) VALUES (?, ?)");
        System.out.println(channel);
        db.bindInt(1, channel.getChannelId());
        db.bindString(2, channel.getChannelName());
        db.executeInsert();
    }

    public ResultSet retrieveChannel(ChatChannelStructure channel) throws SQLException {
        return this.select("channel_id", channel.getChannelId());
    }

    public ResultSet retrieveChannel(int channelID) throws SQLException {
        return this.select("channel_id", channelID);
    }

}
