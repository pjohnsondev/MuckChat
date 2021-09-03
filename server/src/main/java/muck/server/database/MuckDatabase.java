package muck.server.database;

/**
 * Connects (or creates) to the database called "muckdb", which is a derby database, through JDBC.
 * Extends the database class
 */
public class MuckDatabase extends Database {
    /**
     * Constructor for the MuckDatabase class. Connects to the database and will create the database if it does not exist.
     */
    public MuckDatabase () {
        dbName = "muckdb";
        connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        connect();
    }
}
