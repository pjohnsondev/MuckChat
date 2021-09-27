package muck.server.models.models;

import muck.server.models.AbstractModel.Model;

import java.sql.SQLException;

public class AchievementModel extends Model {
    @Override
    public void createTable() throws SQLException {
        // create a new table
        db.databaseIsConnected();
        db.createTableIfNotExists(
        "players",
        "CREATE TABLE players "
                + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + " user_id INTEGER NOT NULL REFERENCES users(id), "
                + " achievement VARCHAR(255)"
                + " PRIMARY KEY (id, user_id))"
        );
    }
}
