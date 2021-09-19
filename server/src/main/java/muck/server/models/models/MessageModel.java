package muck.server.models.models;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import muck.server.database.Database;
import muck.server.models.AbstractModel.Model;
import muck.server.structures.ChatChannelStructure;
import muck.server.structures.ChatMessageStructure;

public class MessageModel extends Model {
    public static String MESSAGE_ID_COL = "message_id";
    public static String CHANNEL_ID_COL = "channel_id";
    public static String USER_ID_COL = "user_name";
    public static String MESSAGE_COL = "message";
    public static String TIMESTAMP_COL = "timestamp";

    public MessageModel() {
        this.table = "messages";
    }

    public MessageModel(Database db) {
        super(db);
        this.table = "messages";
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
                "messages",
                "CREATE TABLE messages "
                        + "(message_id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " channel_id INTEGER NOT NULL, "
                        + " user_name VARCHAR(255) NOT NULL, "
                        + " message VARCHAR(255) NOT NULL, "
                        + " timestamp TIMESTAMP NOT NULL)"
        );
    }

    public void insertMessage(ChatMessageStructure msg) throws SQLException, InvalidParameterException {
        //Insert the new user into the database table
        db.query("INSERT INTO messages (channel_id, user_name, message, timestamp) VALUES (?, ?, ?,?)");
        System.out.println(msg);
        db.bindInt(1, msg.getChannelId());
        db.bindString(2, msg.getUserName());
        db.bindString(3, msg.getMessage());
        db.bindDate(4, msg.getTimeStamp());
        db.executeInsert();
    }

    public ResultSet retrieveMessages () throws SQLException{
        return this.selectAll();
    }

    public ResultSet retrieveMessages (ChatMessageStructure msg) throws SQLException{
        return this.select("channel_id", msg.getChannelId());
    }

    public ResultSet retrieveMessages (int channelID) throws SQLException{
        return this.select("channel_id", channelID);
    }
}
