package muck.server.services;

import muck.server.Exceptions.ModelNotFoundException;
import muck.server.models.models.UserModel;
import muck.server.structures.UserStructure;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private UserModel userModel = new UserModel();

    public UserStructure findByUsername(String userName) throws SQLException, ModelNotFoundException {
        ResultSet result = userModel.findUserByUsername(userName);
        if (result.wasNull()) {
            throw new ModelNotFoundException();
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
}
