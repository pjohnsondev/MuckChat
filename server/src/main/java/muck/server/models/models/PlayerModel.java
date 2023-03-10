package muck.server.models.models;

import muck.core.structures.PlayerStructure;
import muck.server.database.Database;
import muck.server.models.AbstractModel.Model;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerModel extends Model {

    public static final String ID_COL = "id";
    public static final String USER_ID_COL = "user_id";
    public static final String HEALTH_COL = "health";
    public static final String ATTACK_COL = "attack";
    public static final String DEFENCE_COL = "defense";

    public PlayerModel() {
        this.table = "players";
    }

    public PlayerModel(Database db) {
        super(db);
        this.table = "players";
    }


    public void createTable() throws SQLException {
        // create a new table
        db.databaseIsConnected();
        db.createTableIfNotExists(
                "players",
                "CREATE TABLE players "
                        + "(id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + " user_id INTEGER NOT NULL REFERENCES users(id), "
                        + " health INTEGER NOT NULL, "
                        + " attack INTEGER NOT NULL,"
                        + " defense INTEGER NOT NULL,"
                        + " PRIMARY KEY (id, user_id))"
        );
    }

    /**
     * Creates adds new player stats
     *
     * @throws SQLException              Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws InvalidParameterException Thrown when an invalid parameter is passed to a method. See: https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    public void insertNewPlayer(PlayerStructure player) throws SQLException, InvalidParameterException {
        db.query("INSERT INTO players (user_id, attack, defense, health) VALUES (?, ?, ?, ?)");
        db.bindInt(1, player.userId);
        db.bindInt(2, player.attack);
        db.bindInt(3, player.defense);
        db.bindInt(4, player.health);
        db.executeInsert();
    }

    /**
     * Used to update already existing characters
     *
     * @param player
     * @throws SQLException
     */
    public void insertWhereId(PlayerStructure player) throws SQLException {
        db.query("INSERT INTO players (attack, defense, health) VALUES (?, ?, ?) WHERE id = ?");
        db.bindInt(1, player.attack);
        db.bindInt(2, player.defense);
        db.bindInt(3, player.health);
        db.bindInt(4, player.identifier);
        db.executeInsert();
    }

    public ResultSet findByUserId(int userId) throws SQLException {
        return this.select(USER_ID_COL, userId);
    }
    public ResultSet findById(int id) throws SQLException {
        return this.select(ID_COL, id);
    }

}
