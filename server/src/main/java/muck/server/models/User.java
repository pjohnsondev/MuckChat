package muck.server.models;

import java.sql.SQLException;

public class User extends Model{

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
    
            db.execute();    
        }
    }

    public void registerNewUser(String username, String password) throws SQLException {

    }

}
