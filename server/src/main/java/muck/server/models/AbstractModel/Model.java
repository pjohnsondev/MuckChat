package muck.server.models.AbstractModel;

import java.sql.Array;
import java.sql.ResultSet;
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
    public static final String table = null;

    /**
     * Constructor: creates a MuckDatabase instance
     */
    public Model() {
        db = new MuckDatabase();
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


    public ResultSet selectOne(String column, String condition) throws SQLException {
        this.db.query("SELECT * FROM ? WHERE ? = ?");
        db.bindString(1, this.table);
        db.bindString(2, column);
        db.bindString(3, condition);
        ResultSet result = db.getResultSet();
        if (!result.next()) {
            return null;
        }
        result.first();
        return result;
    }

    public ResultSet selectOne(String column, int condition) throws SQLException {
        this.db.query("SELECT * FROM ? WHERE ? = ?");
        db.bindString(1, this.table);
        db.bindString(2, column);
        db.bindInt(3, condition);
        ResultSet result = db.getResultSet();
        if (!result.next())
        result.first();
        db.closeConnection();
        return result;
    }

    public void insert(String[] columns, Object[] values) {
        db.query("INSERT INTO users (username, password, salt) VALUES (?, ?, ?)");
        StringBuilder query = new StringBuilder("INSERT INTO ? (");
        query.append(columns.toString());
        query.append(')');



    }
}
