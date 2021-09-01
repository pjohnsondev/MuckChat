package muck.server.database;

/**
 * Connects (or creates) to the database called "muckdb", which is a derby database, through JDBC.
 * Extends the database class
 */
public class MuckDatabase extends Database {
    /** The single instance of the server that exists */
    private static MuckDatabase INSTANCE;

    /** Gets the MuckServer object */
    public static MuckDatabase getINSTANCE() {
        if (INSTANCE ==null){
            System.out.println("attempting to create Muck Database");
            INSTANCE = new MuckDatabase();
        }
        else{
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
}
