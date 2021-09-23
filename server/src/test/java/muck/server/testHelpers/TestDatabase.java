package muck.server.testHelpers;

import muck.server.database.Database;

public class TestDatabase extends Database {
    /** The single instance of the server that exists */
    private static TestDatabase INSTANCE;

    /** Gets the MuckServer object */
    public static TestDatabase getINSTANCE() {
        if (INSTANCE == null) {
            System.out.println("Attempting to create Test Database");
            INSTANCE = new TestDatabase();
        }
        else {
            System.out.println("Test Database is up and running");
        }
        return INSTANCE;
    }

    public static boolean activeConnection(){
        if (INSTANCE==null){
            return false;
        } else{
            return true;
        }
    }

    public static void setInstanceNull(){
        INSTANCE = null;
    }

    public TestDatabase () {
        super();
        this.dbName = "testDB";
        this.connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        connect();
    }
}
