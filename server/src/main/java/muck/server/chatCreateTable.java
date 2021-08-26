package muck.server;



import muck.server.database.Database;
import muck.protocol.connection.*;

import java.sql.SQLException;

/**
 * Class of methods to create DB tables. Uses the Database Interface.
 * Original Author: Ryan Birch (rbirch4@myune.edu.au) as a part of Low Expectations.
 */
public class chatCreateTable {

    /* private String groupChatStmt = "CREATE TABLE groupChatLog (" +
            "chat_id varchar(20)," +
            "chat_message varchar(200)," +
            "sender varchar(20)," +
            "time_sent varchar(15)," +
            "PRIMARY KEY (chat_id)" +
            ")";
            
     */

    /* private String chatLogStmt = "CREATE TABLE " + chatLogName +" (" +
            "chat_id varchar(20)," +
            "chat_message varchar(200)," +
            "sender varchar(20)," +
            "time_sent varchar(15)," +
            "PRIMARY KEY (chat_id)" +
            ")";
     */
    //private String dbName = "muckdb";
    //private String connectionString = String.format("jdbc:derby:%s;create=true", dbName);

    protected static void createGroupChat() throws SQLException {
        //Call to DB to create the group chat table.
        Database muckDB = new Database() {
            @Override
            protected void connect() {
                super.connect();
            }
        };
        muckDB.createTableIfNotExists("groupChatLog", "CREATE TABLE groupChatLog (" +
                "chat_id varchar(20)," +
                "chat_message varchar(200)," +
                "sender varchar(20)," +
                "time_sent varchar(15)," +
                "PRIMARY KEY (chat_id)" +
                ")");
    }


    public static void createNewChat(newChatLog chatName) throws SQLException {
        //Call to DB to create the chat with generated ID.
        Database muckDB = new Database() {
            @Override
            protected void connect() {
                super.connect();
            }
        };
        String newLogName = chatName.getChatName();
        muckDB.createTableIfNotExists("groupChatLog", "CREATE TABLE " + newLogName + " (" +
                "chat_id varchar(20)," +
                "chat_message varchar(200)," +
                "sender varchar(20)," +
                "time_sent varchar(15)," +
                "PRIMARY KEY (chat_id)" +
                ")");
    }



}
