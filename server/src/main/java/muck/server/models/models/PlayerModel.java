package muck.server.models.models;

import muck.server.models.AbstractModel.Model;

import java.sql.SQLException;

public class PlayerModel extends Model {
    public void createTable() throws SQLException {
        // create a new table
        db.databaseIsConnected();
        db.createTableIfNotExists(
                "players",
                "CREATE TABLE players "
                        + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " user_id INTEGER REFERENCES users(id), "
                        + " health INTEGER NOT NULL, "
                        + " attack INTEGER NOT NULL,"
                        + " defense INTEGER NOT NULL,"
                        + " PRIMARY KEY (id, user_id))"
        );
    }

}
