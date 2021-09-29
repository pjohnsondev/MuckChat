package muck.server.services;

import muck.server.Exceptions.ModelNotFoundException;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.helpers.security.Hasher;
import muck.server.models.models.UserModel;
import muck.core.structures.UserStructure;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private final UserModel userModel;

    public UserService() {
        this.userModel = new UserModel();
    }

    public UserService(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * Searches a database for a given username and returns the corresponding user's details
     *
     * @param userName - The username to search for
     * @return - Returns a UserStructure containing the user's details from the database
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public UserStructure findByUsername(String userName) throws SQLException {
        ResultSet result = userModel.findUserByUsername(userName);
        if (result == null) {
            return null;
        }
        UserStructure userStructure = new UserStructure();

        userStructure.id = result.getInt(UserModel.ID_COL);
        userStructure.username = userName;
        userStructure.displayName = result.getString(UserModel.DISPLAYNAME_COL);
        userStructure.hashedPassword = result.getBytes(UserModel.PASSWORD_COL);
        userStructure.salt = result.getBytes(UserModel.SALT_COL);
        userStructure.points = result.getInt(UserModel.POINTS_COL);
        return userStructure;
    }

    /**
     * Searches a database for a given display name and returns the corresponding user's details
     *
     * @param displayName - The display name to search for
     * @return - Returns a UserStructure containing the user's details from the database
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public UserStructure findByDisplayname(String displayName) throws SQLException {
        try{
            ResultSet result = userModel.findByDisplayname(displayName);
            if (result == null) {
                return null;
            }
            UserStructure userStructure = new UserStructure();

            userStructure.id = result.getInt(UserModel.ID_COL);
            userStructure.username = result.getString(UserModel.USERNAME_COL);
            userStructure.displayName = displayName;
            userStructure.hashedPassword = result.getBytes(UserModel.PASSWORD_COL);
            userStructure.salt = result.getBytes(UserModel.SALT_COL);
            userStructure.points = result.getInt(UserModel.POINTS_COL);
            return userStructure;
        } catch (Exception ex){
            System.out.println("sql error in userservice findbydisplayname");
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Searches a database for a given id and returns the corresponding user's details
     *
     * @param id - The id to search for
     * @return - Returns a UserStructure containing the user's details from the database
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public UserStructure findById(Integer id) throws SQLException {
        ResultSet result = this.userModel.findUserById(id);
        if (result.wasNull()) {
            return null;
        }
        UserStructure userStructure = new UserStructure();
        userStructure.id = id;
        userStructure.username = result.getString(UserModel.USERNAME_COL);
        userStructure.displayName = result.getString(UserModel.USERNAME_COL);
        userStructure.hashedPassword = result.getBytes(UserModel.PASSWORD_COL);
        userStructure.salt = result.getBytes(UserModel.SALT_COL);
        userStructure.points = result.getInt(UserModel.POINTS_COL);
        return userStructure;
    }

    /**
     * Registers a new user in a database
     *
     * @param userStructure - A UserStructure containing the username, password and displayName details to be registered
     * @return - Returns true if successful
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws UserNameAlreadyTakenException - A custom exception defined in muck.server.Exceptions
     */
    public boolean registerNewUser(UserStructure userStructure) throws SQLException, UserNameAlreadyTakenException {
        try {
            if (userStructure.username.length() > 80) {
                throw new InvalidParameterException("Username must be less than 80 characters long");
            }
            if (this.findByUsername(userStructure.username) != null) {
                throw new UserNameAlreadyTakenException("Username has already been taken");
            }

            if (this.findByDisplayname(userStructure.displayName) != null){
                throw new UserNameAlreadyTakenException("Display name has already been taken");
            }
            //set up hashed password
            Hasher hasher = new Hasher();
            hasher.setNewPasswordHash(userStructure.password);
            byte[] hashedPassword = hasher.getHashedPassword();
            byte[] salt = hasher.getSalt();

            userStructure.hashedPassword = hashedPassword;
            userStructure.salt = salt;

            this.userModel.insertNewUser(userStructure);
            userStructure.id = (findByUsername(userStructure.username)).id;
            return true;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Checks that a user's password matches its password recorded in the database
     *
     * @param user - A UserStructure containing a username and password to be checked against the corresponding database entry for that username
     * @return - Returns true if the password matches
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws ModelNotFoundException - A custom extension defined in muck.server.Exceptions
     */
    public Boolean authenticateUser(UserStructure user) throws SQLException, ModelNotFoundException {
        UserStructure userInDb = this.findByUsername(user.username);
        if(userInDb != null){
            Hasher hasher = new Hasher();
            return hasher.passwordMatches(user.password, userInDb.salt, userInDb.hashedPassword);
        }
        return false;
    }

    /**
     * Adds points received by a user to their corresponding record in the database
     *
     * @param user - A UserStructure containing the details of the user who has received points
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void updateUser(UserStructure user) throws  SQLException {
        this.userModel.insertPointsWhereId(user);
    }

}