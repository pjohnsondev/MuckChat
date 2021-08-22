package muck.server;


import muck.server.database.Database;

import java.sql.SQLException;

/**
 * Class of methods to create DB tables. Inherits Muck database methods.
 * Original Author: Ryan Birch (rbirch4@myune.edu.au) as a part of Low Expectations.
 */
public class chatCreateTable extends Database {

    private String groupChatStmt = "CREATE TABLE groupChatLog (\n" +
            "    chat_id varchar(20),\n" +
            "    chat_message varchar(200),\n" +
            "    sender varchar(20),\n" +
            "    time_sent varchar(15),\n" +
            "    PRIMARY KEY (chat_id)\n" +
            ")";
    private String chatLogName;
    private String chatLogStmt = "CREATE TABLE " + chatLogName +" (\n" +
            "    chat_id varchar(20),\n" +
            "    chat_message varchar(200),\n" +
            "    sender varchar(20),\n" +
            "    time_sent varchar(15),\n" +
            "    PRIMARY KEY (chat_id)\n" +
            ")";

    private String dbName = "muckdb";
    private String connectionString = String.format("jdbc:derby:%s;create=true", dbName);

    protected void createGroupChat() throws SQLException {
        //Call to DB to create the group chat table.
        connect();
        createTableIfNotExists("groupChatLog", groupChatStmt);


    }

    public void prepareStmt(String chatLogName) {
        this.chatLogName = chatLogName;
    }
    public void createNewChat(String chatLogName) throws SQLException {
        //Call to DB to create the chat with generated ID.
        connect();
        createTableIfNotExists(chatLogName, groupChatStmt);
    }



}
