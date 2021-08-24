package muck.server.models.models;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import muck.server.models.AbstractModel.Model;
import muck.server.structures.UserStructure;

/**
 * Creates and manages users on the database
 */
public class UserModel extends Model{
    protected String TABLE = "users";
    public static final String ID_COL = "id";
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL  = "password";
    public static final String SALT_COL = "salt";


    /**
     * Creates a table for the users, if it does not exist already
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void createTable() throws SQLException {
        // create a new table
        db.createTableIfNotExists(
            "users",
            "CREATE TABLE users "
            + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + " username VARCHAR(80) UNIQUE, "
            + " password BLOB NOT NULL, "
            + " salt BLOB NOT NULL)"
        );
    }

    /**
     * Creates a user within the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws InvalidParameterException Thrown when an invalid parameter is passed to a method. See: https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    public void insertNewUser(UserStructure user) throws SQLException, InvalidParameterException {
        //Insert the new user into the database table
        db.query("INSERT INTO users (username, password, salt) VALUES (?, ?, ?)");
        db.bindString(1, user.username);
        db.bindBytes(2, user.hashedPassword);
        db.bindBytes(3, user.salt);
        db.executeInsert();
    }

    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param username The username to search for within the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public ResultSet findUserByUsername(String username) throws SQLException {
        return this.selectOne("username", username);
    }

    /**
     * Retrieves user information from the database using the user id is the criteria
     *
     * @param id The user id to search for within the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public ResultSet findUserById(int id) throws SQLException {
        return this.selectOne("id", id);
    }

}
