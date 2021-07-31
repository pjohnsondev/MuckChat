package muck.server.models.AbstractModel;

import java.sql.SQLException;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;

interface IModel {
    public void createTable() throws SQLException;
}

abstract public class Model implements IModel {
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
