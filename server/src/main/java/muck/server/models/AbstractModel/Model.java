package muck.server.models.AbstractModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import muck.server.database.Database;
import muck.server.database.MuckDatabase;


interface IModel {
    void createTable() throws SQLException;
}

/**
 * Enables classes to connect to the database, change database and close the connection
 */
abstract public class Model implements IModel {
    protected Database db;
    protected String table;

    /**
     * Constructor: creates a MuckDatabase instance
     */
    public Model() {
        db = MuckDatabase.getINSTANCE();
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


    public ResultSet select(String column, String condition) throws SQLException {
        this.db.query("SELECT * FROM " + this.table + " WHERE " + column + " = ?");
        db.bindString(1, condition);
        ResultSet result = db.getResultSet();
        if (!result.next()) {
            return null;
        }
        return result;
    }

    public ResultSet select(String column, int condition) throws SQLException {
        this.db.query("SELECT * FROM " + this.table + " WHERE " + column + " = ?");
        db.bindInt(1, condition);
        ResultSet result = db.getResultSet();
        if (!result.next())
            db.closeConnection();
        return result;
    }
}
