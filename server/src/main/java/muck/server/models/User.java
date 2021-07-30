package muck.server.models;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import muck.server.helpers.security.Hasher;

public class User extends Model{
    private int id;
    private String username;
    private byte[] hashedPassword;
    private byte[] salt;

    public void createTable() throws SQLException {
        // create a new table
        // this conditional is used to emulate the "CREATE IF NOT EXISTS" syntax from other SQL implementations
        // this "IF NOT EXISTS" doesn't exist in Apache Derby.
        if (!db.tableExists("users")) {
            db.query(
                "CREATE TABLE users "
                + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + " username VARCHAR(80) UNIQUE, "
                + " password BLOB NOT NULL, "
                + " salt BLOB NOT NULL)"
                );
    
            db.executeUpdate();
        }
    }

    public void registerNewUser(String username, String password) throws SQLException, InvalidParameterException {
        if (username.length() > 80) {
            throw new InvalidParameterException("Username must be less than 80 characters long");
        }
        Hasher hasher = new Hasher();
        hasher.setNewPasswordHash(password);
        byte[] hashedPassword = hasher.getHashedPassword();
        byte[] salt = hasher.getSalt();

        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;

        db.query("INSERT INTO users (username, password, salt) VALUES (?, ?, ?)");
        db.bindString(1, username);
        db.bindBytes(2, hashedPassword);
        db.bindBytes(3, salt);
        db.executeInsert();
    }
    

}
