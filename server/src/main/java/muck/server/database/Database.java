package muck.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private String connectionString;
    private Connection conn;
    private PreparedStatement statement;

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

    public void query(String sql) {
        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // PREPARED STATEMENT BINDING
    public void bindInt(int pIndex, int parameter) {
        try {
            statement.setInt(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindDouble(int pIndex, double parameter) {
        try {
            statement.setDouble(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindString(int pIndex, String parameter) {
        try {
            statement.setString(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindNull(int pIndex) {
        try {
            statement.setString(pIndex, null);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void bindObj(int pIndex, Object parameter) {
        try {
            statement.setObject(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    //END PREPARED STATMENT BINDING
}
