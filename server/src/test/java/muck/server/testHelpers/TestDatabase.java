package muck.server.testHelpers;

import muck.server.database.Database;

public class TestDatabase extends Database {
    public TestDatabase () {
        super();
        this.dbName = "testDB";
        this.connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        connect();
    }
}
