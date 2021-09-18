package muck.server.models.models;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import muck.server.database.Database;
import muck.server.models.AbstractModel.Model;
import muck.core.structures.UserStructure;

/**
 * Creates and manages users on the database
 */
public class UserModel extends Model {
    public static final String ID_COL = "id";
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL = "password";
    public static final String SALT_COL = "salt";

    public UserModel() {
        this.table = "users";
    }

    public UserModel(Database db) {
        super(db);
        this.table = "users";
    }

    /**
     * Creates a table for the users, if it does not exist already
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void createTable() throws SQLException {
        // create a new table
        db.databaseIsConnected();
        db.createTableIfNotExists(
                "users",
                "CREATE TABLE users "
                        + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " username VARCHAR(80) UNIQUE, "
                        + " displayname VARCHAR(80) UNIQUE, "
                        + " password BLOB NOT NULL, "
                        + " salt BLOB NOT NULL)"
        );
        System.out.println("Table created");
    }


    /**
     * Creates a user within the database
     *
     * @throws SQLException              Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws InvalidParameterException Thrown when an invalid parameter is passed to a method. See: https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    public void insertNewUser(UserStructure user) throws SQLException, InvalidParameterException {
        //Insert the new user into the database table
        db.query("INSERT INTO users (username, displayname, password, salt) VALUES (?, ?, ?, ?)");
        db.bindString(1, user.username);
        db.bindString(2, user.displayName);
        db.bindBytes(3, user.hashedPassword);
        db.bindBytes(4, user.salt);
        db.executeInsert();
    }

    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param username The username to search for within the database
     * @return returns found user object or null
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */

    public ResultSet findUserByUsername(String username) throws SQLException {
        try{
            return this.select("username", username);
        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param id The user id to search for within the database
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public ResultSet findUserById(int id) throws SQLException {
        return this.select("id", id);
    }
}