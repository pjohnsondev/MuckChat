package muck.server.models.AbstractModel;

import java.sql.SQLException;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;

/**
 * Used to ensure a class creates a table
 */
interface IModel {
    void createTable() throws SQLException;
}

/**
 * Enables classes to connect to the database, change database and close the connection
 */
abstract public class Model implements IModel {
    protected Database db;

    /**
     * Constructor: creates a MuckDatabase instance
     */
    public Model() {
        this.db = new MuckDatabase();
    }
    public Model(Database db) {
        this.db = db;
    }

    /**
     * Changes databases
     *
     * @param db Database to change to
     */
    public void changeDb(Database db) {
        closeDbConnection();
        this.db = db;
    }

    /**
     * Closes the database connection
     */
    public void closeDbConnection() {
        db.closeConnection();
    }
}
