package muck.server.database;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to (or creates) the database called "muckdb", which is a derby database, through JDBC.
 * Extends the database class
 */
public class MuckDatabase extends Database {
    private static MuckDatabase INSTANCE;
    /** Gets the MuckServer object */
    public static MuckDatabase getINSTANCE() {
        if (INSTANCE == null) {
            System.out.println("Attempting to create and connect to Muck Database");
            INSTANCE = new MuckDatabase();
        }
        else {
            System.out.println("Muck Database is up and running. Refreshing connection");
            INSTANCE.closeConnection();
            INSTANCE.connect();
        }
        return INSTANCE;
    }

    /**
     * Constructor for the MuckDatabase class. Connects to the database and will create the database if it does not exist.
     */
    private MuckDatabase () {
        dbName = "muckdb";
        connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        System.out.println(connectionString);
        connect();
    }

    public static void shutdown() {
        try {
            if (INSTANCE !=null) {
                INSTANCE.closeConnection();
                DriverManager.getConnection("jdbc:derby: " + INSTANCE.dbName + ";shutdown=true");
                INSTANCE = null;
            }
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                // could not shut down the database
                // handle appropriately
                System.out.println("Derby shutdown was not achieved");
            }
        }
    }

}
