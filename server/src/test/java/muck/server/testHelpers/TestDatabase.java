package muck.server.testHelpers;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;

public class TestDatabase extends Database {

    /** Gets the MuckServer object */
    public static Database getINSTANCE() {
        if (INSTANCE == null) {
            System.out.println("attempting to create Muck Database");
            INSTANCE = new TestDatabase();
        }
        else {
            System.out.println("Muck Database is up and running");
        }
        return INSTANCE;
    }

    private TestDatabase () {
        super();
        this.dbName = "testDB";
        this.connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        connect();
    }

}
