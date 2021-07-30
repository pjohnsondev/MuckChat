package muck.server.models;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;

public class Model {
    protected Database db;
    public Model() {
        db = new MuckDatabase();
    }

    public void changeDb(Database db) {
        closeDbConnection();
        this.db = db;
    }

    public void closeDbConnection() {
        db.closeConnection();
    }

}
