package muck.server.models.models;

import muck.server.helpers.security.Hasher;
import muck.server.models.AbstractModel.Model;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates and manages users on the database
 */
public class UserModel extends Model {

    private static UserModel sInstance;

    /**
     * prevent instance of UserModel from being created
     * use {@link UserModel#getInstance()} method
     */
    private UserModel() {
        // prevent new instances of UserModel from being created
    }

    public static UserModel getInstance() {
        if (sInstance == null) {
            sInstance = new UserModel();
        }
        return sInstance;
    }
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
            + " salt BLOB NOT NULL, "
            + " points INTEGER)"
        );
    }

    /**
     * Creates a user within the database
     *
     * @param username Username for the new user which will be used as a handle and a login name
     * @param password Password for logging on to the system
     *
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

        //Insert the new user into the database table
        db.query("INSERT INTO users (username, password, salt, points) VALUES (?, ?, ?, ?)");
        db.bindString(1, username);
        db.bindBytes(2, hashedPassword);
        db.bindBytes(3, salt);
        db.bindInt(4, 0);
        db.executeInsert();
    }

    /**
     * Update a user within the database
     *
     * @param newUser updated user we want to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws InvalidParameterException Thrown when an invalid parameter is passed to a method. See: https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    public void updateUser(User newUser) throws SQLException, InvalidParameterException {
        if (newUser == null) {
            throw new InvalidParameterException("New user cannot be null");
        }

        //Insert the new user into the database table
        db.query("UPDATE users SET = username = ? , password = ?, salt = ?, points = ?)");
        db.bindString(1, newUser.getUserName());
        db.bindBytes(2, newUser.getHashedPassword());
        db.bindBytes(3, newUser.getSalt());
        db.bindInt(4, newUser.getPoints());
        db.executeUpdate();
    }

    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param username The username to search for within the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @return returns found user object or null
     */

    public User findUserByUsername(String username) throws SQLException {
        db.query("SELECT * FROM users WHERE username=?");
        db.bindString(1, username);
        ResultSet result = db.getResultSet();
        User user = null;
        if(result.next()) {
            user = new User(result.getInt("id"), result.getString("username"), result.getBytes("password"), result.getBytes("salt"), result.getInt("points"));
            result.close();
            return user;
        }
        result.close();
        return null;
    }
    /**
     * Retrieves user information from the database using the UserName is the criteria
     *
     * @param id The user id to search for within the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @return returns found user object or null
     */

    public User findUserById(int id) throws SQLException {
        db.query("SELECT * FROM users WHERE id=?");
        db.bindInt(1, id);
        ResultSet result = db.getResultSet();
        User user = null;
        if(result.next()) {
            user = new User(result.getInt("id"), result.getString("username"), result.getBytes("password"), result.getBytes("salt"), result.getInt("points"));
            result.close();
            return user;
        }
        result.close();
        return null;
    }

    /**
     * Retrieves list of users
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @return returns list of users or empty list
     */

    public List<User> getUsers() throws SQLException {
        db.query("SELECT * FROM users");
        ResultSet result = db.getResultSet();
        List<User> users = new ArrayList<>();
        if(result.next()) {
            User user = new User(result.getInt("id"), result.getString("username"), result.getBytes("password"), result.getBytes("salt"), result.getInt("points"));
            result.close();
            users.add(user);
        }
        result.close();
        return users;
    }
    /**
     * Retrieves ordered list of users by points
     * @param ascending or decending
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @return returns list of users or empty list
     */

    public List<User> getUsersOrderedByPoints(boolean ascending) throws SQLException {
        String sqlAsc = "ASC";
        if (ascending) {
            sqlAsc = "DESC";
        }
        db.query("SELECT * FROM users ORDER BY points "+sqlAsc);
        ResultSet result = db.getResultSet();
        List<User> users = new ArrayList<>();
        if(result.next()) {
            User user = new User(result.getInt("id"), result.getString("username"), result.getBytes("password"), result.getBytes("salt"), result.getInt("points"));
            result.close();
            users.add(user);
        }
        result.close();
        return users;
    }

    /**
     * User supplied information to authenticate/log the user into the system
     * @param username Username of the user
     * @param password Password of the user
     *
     * @return true if user is authenticated, false if the user is not authenticated
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public Boolean authenticateUser(String username, String password) throws SQLException {
        User user = findUserByUsername(username);
        Hasher hasher = new Hasher();
        return hasher.passwordMatches(password, user.getSalt(), user.getHashedPassword());
    }
}
