package muck.server;

import muck.core.models.models.User;
import muck.core.structures.PlayerStructure;
import muck.core.structures.UserStructure;
import muck.server.Exceptions.MissingNecessaryPlayerInfoException;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.database.Database;
import muck.server.models.models.PlayerModel;
import muck.server.models.models.UserModel;
import muck.server.services.PlayerService;
import muck.server.services.UserService;
import muck.server.testHelpers.TestDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

public class PlayerServiceTest {

    private static final Logger logger = LogManager.getLogger(PlayerManagerTest.class);
    private Database testDb;
    private UserModel userModel;
    private UserService userService;
    private PlayerModel playerModel;
    private PlayerService playerService;

    private UserStructure userStructure;

    @BeforeEach
    public void beforeEach() throws SQLException{
        logger.info("This message prints BEFORE each test runs");
        // reset database using testDB
        testDb = TestDatabase.getINSTANCE();
        userModel = new UserModel(testDb);
        if (!testDb.tableExists("users")) {
            userModel.createTable();
        }
        if (!testDb.tableExists("players")) {
            playerModel.createTable();
        }
        userService = new UserService(userModel);
        playerModel = new PlayerModel(testDb);
        playerService = new PlayerService(playerModel);
        UserStructure userStructure = new UserStructure();
        userStructure.username = "timTheTester";
        userStructure.password = "timTheTester";
        try {
            userService.registerNewUser(userStructure);
        } catch (UserNameAlreadyTakenException e) {
            // forget about it we already have the user;
        }
        this.userStructure = userService.findByUsername("timTheTester");
    }

    @Test
    public void testInsertPlayerStatsWithCorrectStatsIsSuccessful() throws SQLException, MissingNecessaryPlayerInfoException {
        PlayerStructure playerStructure = new PlayerStructure();
        playerStructure.userId = this.userStructure.id;
        playerStructure.defense = 20;
        playerStructure.attack = 40;
        playerStructure.health = 50;
        playerService.addPlayerInformationToUser(playerStructure);

        PlayerStructure playerStructure1 = playerService.findByUserId(this.userStructure.id);
        assertEquals(playerStructure1.defense, playerStructure.defense);
        assertEquals(playerStructure1.attack, playerStructure.attack);
        assertEquals(playerStructure1.health, playerStructure.health);
    }
}
