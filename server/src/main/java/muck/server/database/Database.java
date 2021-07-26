package muck.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String connectionString;
    private Connection conn;
    public Database() {
        // Just in case some people are using windows.
        String seperator = java.io.File.separator;
        String pathToDB = System.getProperty("user.dir") + seperator + "muck.db";
        connectionString = "jdbc:sqlite:" + pathToDB;
        connect();
    }

    // This function was developed using this tutorial: https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
    private void connect() {
        try {
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connection to database established");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public Boolean databaseIsConnected() {
        return conn != null;
    }
}
