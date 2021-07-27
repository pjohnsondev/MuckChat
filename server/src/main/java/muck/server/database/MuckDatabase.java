package muck.server.database;

public class MuckDatabase extends Database {
    public MuckDatabase () {
        dbName = "muckdb";
        connectionString = String.format("jdbc:derby:%s;create=true", dbName);
        connect();
    }
}
