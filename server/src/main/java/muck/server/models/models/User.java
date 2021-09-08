package muck.server.models.models;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import muck.server.helpers.security.Hasher;
import muck.server.models.AbstractModel.Model;

/**
 * Creates and manages users on the database
 */
public class User extends Model {
    private int id;
    private String username;
    private byte[] hashedPassword;
    private byte[] salt;
    private int points;

    public User() {

    }

    public User(int id, String username, byte[] hashedPassword, byte[] salt, int points) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.points = points;
    }

    /**
     * Creates a table for the users, if it does not exist already
     * @deprecated Please use {@link UserModel} methods
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
            + " salt BLOB NOT NULL, "
            + " points INTEGER)"
        );
    }

    /**
     * Creates a user within the database
     *
     * @param username Username for the new user which will be used as a handle and a login name
     * @param password Password for logging on to the system
     * @deprecated Please use {@link UserModel} methods
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws InvalidParameterException Thrown when an invalid parameter is passed to a method. See: https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    public void registerNewUser(String username, String password) throws SQLException, InvalidParameterException {
        if (username.length() > 80) {
            throw new InvalidParameterException("Username must be less than 80 characters long");
        }
        //set up hashed password
        Hasher hasher = new Hasher();
        hasher.setNewPasswordHash(password);
        byte[] hashedPassword = hasher.getHashedPassword();
        byte[] salt = hasher.getSalt();

        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;

        //Insert the new user into the database table
        db.query("INSERT INTO users (username, password, salt, points) VALUES (?, ?, ?, ?)");
        db.bindString(1, username);
        db.bindBytes(2, hashedPassword);
        db.bindBytes(3, salt);
        db.bindInt(4, 0);
        db.executeInsert();
    }


    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param username The username to search for within the database
     * @deprecated Please use {@link UserModel} methods
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */

    public boolean findUserByUsername(String username) throws SQLException {
        db.query("SELECT * FROM users WHERE username=?");
        db.bindString(1, username);
        ResultSet result = db.getResultSet();
        if(result.next()) {
            this.id = result.getInt("id");
            this.username = username;
            this.hashedPassword = result.getBytes("password");
            this.salt = result.getBytes("salt");
            this.points = result.getInt("points");
            result.close();
            return true;
        }
        result.close();
        return false;
    }
    /**
     * Retrieves user information from the database using the user id is the criteria
     *
     * @param id The user id to search for within the database
     * @deprecated Please use {@link UserModel} methods
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void findUserById(int id) throws SQLException {
        db.query("SELECT * FROM users WHERE id=?");
        db.bindInt(1, id);
        ResultSet result = db.getResultSet();
        result.next();
        this.id = id;
        this.username = result.getString("username");
        this.hashedPassword = result.getBytes("password");
        this.salt = result.getBytes("salt");
        this.points = result.getInt("points");
        result.close();
    }

    /**
     * User supplied information to authenticate/log the user into the system
     * @param username Username of the user
     * @param password Password of the user
     * @deprecated Please use {@link UserModel} methods
     * @return true if user is authenticated, false if the user is not authenticated
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public Boolean authenticateUser(String username, String password) throws SQLException {
        findUserByUsername(username);
        Hasher hasher = new Hasher();
        return hasher.passwordMatches(password, salt, hashedPassword);
    }

    /**
     * Retrieves the user id
     *
     * @return the user id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the username
     *
     * @return the username
     */
    public String getUserName() {
        return this.username;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Add points to user's current points
     * @param points points to be added
     * @author Jason Aboh
     */
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * Retrieves the hashed password of the user
     *
     * @return Hashed password of the user
     */
    public byte[] getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * Retrieves the salt (random generated) of the user
     *
     * @return salt of the user to generate the hashed password
     */
    public byte[] getSalt() {
        return this.salt;
    }

}
