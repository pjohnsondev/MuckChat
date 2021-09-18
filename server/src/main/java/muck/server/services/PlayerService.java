package muck.server.services;

import muck.core.structures.PlayerStructure;
import muck.server.Exceptions.MissingNecessaryPlayerInfoException;
import muck.server.models.models.PlayerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerService {

    private final PlayerModel playerModel;

    public PlayerService() {
        this.playerModel = new PlayerModel();
    }

    public PlayerService(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerStructure findByUserId(int userId) throws SQLException {
        ResultSet result = playerModel.findByUserId(userId);
        return returnPlayerStructure(result);
    }
    public PlayerStructure findById(int id) throws SQLException {
        ResultSet result = playerModel.findById(id);
        return returnPlayerStructure(result);
    }

    public void addPlayerInformationToUser(PlayerStructure playerStructure) throws SQLException, MissingNecessaryPlayerInfoException {
        if (
                playerStructure.userId == null
                || playerStructure.health == null
                || playerStructure.defense == null
                || playerStructure.attack == null
        ) {
            throw new MissingNecessaryPlayerInfoException();
        }
        playerModel.insertNewPlayer(playerStructure);
    }

    /**
     * Only use this method if you don't have access to the player id. It's slightly less performant than updatePlayerStats
     * This is because we get the player id from the database and then updatePlayerStats, so we're making two trips to the db.
     *
     * @throws SQLException
     * @throws MissingNecessaryPlayerInfoException
     */
    public PlayerStructure updatePlayerStatsFromUserId(PlayerStructure playerStructure) throws SQLException, MissingNecessaryPlayerInfoException {
        PlayerStructure playerInDb = this.findByUserId(playerStructure.userId);
        playerStructure.identifier = playerInDb.identifier;
        return updatePlayerStats(playerStructure);
    }

    /**
     *  Takes in the user structure. The identifier cannot be null otherwise it will throw an exception
     *  if any other fields are null they will default to the already existing stats.
     *
     * @param playerStructure parameter needs to have an identifier in order to work
     * @throws SQLException
     * @throws MissingNecessaryPlayerInfoException
     */
    public PlayerStructure updatePlayerStats(PlayerStructure playerStructure) throws SQLException, MissingNecessaryPlayerInfoException {
        if (playerStructure.identifier == null) {
            throw new MissingNecessaryPlayerInfoException();
        }
        PlayerStructure playerInDb = findById(playerStructure.identifier);
        if (playerInDb == null) {
            return null;
        }
        playerStructure.attack = playerStructure.attack != null ? playerStructure.attack : playerInDb.attack;
        playerStructure.defense = playerStructure.defense != null ? playerStructure.defense : playerInDb.defense;
        playerStructure.health = playerStructure.health != null ? playerStructure.health : playerInDb.health;

        playerModel.insertWhereId(playerStructure);
        return playerStructure;
    }

    private PlayerStructure returnPlayerStructure(ResultSet result) throws SQLException {
        if (result == null) {
            return null;
        }
        PlayerStructure playerStructure = new PlayerStructure();
        playerStructure.identifier = result.getInt(PlayerModel.ID_COL);
        playerStructure.userId = result.getInt(PlayerModel.USER_ID_COL);
        playerStructure.health = result.getInt(PlayerModel.HEALTH_COL);
        playerStructure.attack = result.getInt(PlayerModel.ATTACK_COL);
        playerStructure.defense = result.getInt(PlayerModel.DEFENCE_COL);
        return playerStructure;
    }
}
