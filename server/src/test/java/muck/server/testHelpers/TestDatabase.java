package muck.server.testHelpers;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;

public class TestDatabase extends Database {

    protected static TestDatabase INSTANCE;

    /** Gets the MuckServer object */
    public static TestDatabase getINSTANCE() {
        if (INSTANCE == null) {
            System.out.println("TestDatabase-getInstance: attempting to create Test Database");
            INSTANCE = new TestDatabase();
        }
        else {
            System.out.println("TestDatabase-getInstance: Test Database is up and running. Refreshing connection");
            INSTANCE.closeConnection();
            INSTANCE.connect();

        }
        System.out.println("TestDatabase-getInstance: "+ INSTANCE.connectionString);
        return INSTANCE;
    }

    private TestDatabase () {
        dbName = "testDB";
        connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        System.out.println(connectionString);
        connect();
    }
}
