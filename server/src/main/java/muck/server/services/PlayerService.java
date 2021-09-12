package muck.server.services;

import muck.core.structures.PlayerStructure;
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

    public PlayerStructure findByUserId(int userId)  throws SQLException {
        ResultSet result = playerModel.findByUserId(userId);
        return returnPlayerStructure(result);
    }
    public PlayerStructure findById(int id) throws SQLException {
        ResultSet result = playerModel.findById(id);
        return returnPlayerStructure(result);
    }

    public void addPlayerInformationToUser(PlayerStructure playerStructure) throws SQLException {
        playerModel.insertNewPlayer(playerStructure);
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
