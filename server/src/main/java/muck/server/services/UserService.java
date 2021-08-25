package muck.server.services;

import muck.server.Exceptions.ModelNotFoundException;
import muck.server.helpers.security.Hasher;
import muck.server.models.models.UserModel;
import muck.server.structures.UserStructure;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private UserModel userModel = new UserModel();

    public UserStructure findByUsername(String userName) throws SQLException {
        ResultSet result = userModel.findUserByUsername(userName);
        if (result == null) {
            return null;
        }
        UserStructure userStructure = new UserStructure();

        userStructure.id = result.getInt(UserModel.ID_COL);
        userStructure.username = userName;
        userStructure.displayName = userName;
        userStructure.hashedPassword = result.getBytes(UserModel.PASSWORD_COL);
        userStructure.salt = result.getBytes(UserModel.SALT_COL);
        return userStructure;
    }

    public UserStructure findById(Integer id) throws SQLException, ModelNotFoundException {
        ResultSet result = this.userModel.findUserById(id);
        if (result.wasNull()) {
            throw new ModelNotFoundException();
        }
        UserStructure userStructure = new UserStructure();
        userStructure.id = id;
        userStructure.username = result.getString(UserModel.USERNAME_COL);
        userStructure.displayName = result.getString(UserModel.USERNAME_COL);
        userStructure.hashedPassword = result.getBytes(UserModel.PASSWORD_COL);
        userStructure.salt = result.getBytes(UserModel.SALT_COL);
        return userStructure;
    }

    /**
     *
     * @param userStructure
     * @return true if successful
     */
    public boolean registerNewUser(UserStructure userStructure) throws SQLException {
        if (userStructure.username.length() > 80) {
            throw new InvalidParameterException("Username must be less than 80 characters long");
        }
        //set up hashed password
        Hasher hasher = new Hasher();
        hasher.setNewPasswordHash(userStructure.password);
        byte[] hashedPassword = hasher.getHashedPassword();
        byte[] salt = hasher.getSalt();

        userStructure.hashedPassword = hashedPassword;
        userStructure.salt = salt;

        this.userModel.insertNewUser(userStructure);
        return true;
    }

    public Boolean authenticateUser(UserStructure user) throws SQLException, ModelNotFoundException {
        UserStructure userInDb = this.findByUsername(user.username);
        Hasher hasher = new Hasher();
        return hasher.passwordMatches(user.password, userInDb.salt, userInDb.hashedPassword);
    }
}
