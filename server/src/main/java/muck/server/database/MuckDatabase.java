package muck.server.database;

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
            System.out.println("Muck Database is up and running");
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
/*
    @Override
    public void closeConnection() {
        if (INSTANCE != null) {
            super.closeConnection();
            INSTANCE=null;
        }
    }
jdbc:derby:muckdb;create=true
jdbc:derby:testDB;create=true
 */
}
