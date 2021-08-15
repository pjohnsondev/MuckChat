package muck.server.database;

public class DatabaseTools {
    /*
    "users", "CREATE TABLE users (id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + " username VARCHAR(80) UNIQUE, password BLOB NOT NULL, salt BLOB NOT NULL)"
     */
    public boolean createTableSQL(String tName){


        //This needs to be update with success of the creation
        return true;
    }
}
