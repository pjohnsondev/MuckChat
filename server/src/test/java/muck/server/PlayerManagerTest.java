package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.server.models.models.UserModel;
import muck.server.services.UserService;
import muck.server.structures.UserStructure;
import muck.server.testHelpers.TestDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerTest {

    private static final Logger logger = LogManager.getLogger(PlayerManagerTest.class);
    private UserModel userModel = new UserModel();
    private TestDatabase testDb = new TestDatabase();

    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() throws SQLException{
        logger.info("This message prints BEFORE each test runs");
        // reset database using testDB
        testDb = new TestDatabase();
        userModel = new UserModel();
        resetTable(userModel, testDb);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        logger.info("This message gets printed after each test runs");
        dropAndClose(userModel, testDb);
    }

    // A little test helper
    private void resetTable(UserModel userModel, TestDatabase testDb) throws SQLException {

        userModel.closeDbConnection();
        userModel.changeDb(testDb);
        testDb.dropTable("users");
        userModel.createTable();
    }

    private void dropAndClose(UserModel userModel, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        userModel.closeDbConnection();
    }

    @Test
    public void loginIsSuccessfulWithValidCredentials() throws SQLException, CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        String username = "test_username+1357";
        String password = "password";

        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;

        UserService userService = new UserService();
        if (userService.findByUsername(username) == null) {
            userService.registerNewUser(userStructure);
        }

        PlayerManager playerManager = new PlayerManager(userService);

        Player player = playerManager.loginPlayer(userStructure);
        assertNotNull(player);
        assertEquals(username, player.getUsername());
    }

    @Test
    public void loginIsFailedWitInvalidPassword() throws SQLException {
        String username = "test_username";
        String password = "password";
        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;

        UserService userService = new UserService();
        if (userService.findByUsername(username) == null) {
            userService.registerNewUser(userStructure);
        }

        userStructure.password = "WrongPassword";
        PlayerManager playerManager = new PlayerManager(userService);

        assertThrows(AuthenticationFailedException.class, () -> playerManager.loginPlayer(userStructure));
    }

    @Test
    public void CharacterGetsCreatedOnLoginWithSuppliedUsername() throws CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException, SQLException {
        String username = "test_username";
        String password = "password";
        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;

        UserService userService = new UserService();
        PlayerManager playerManager = new PlayerManager(userService);

        Player player = playerManager.loginPlayer(userStructure);

        assertNotNull(player);
        assertEquals(username, player.getUsername());
    }

    @Test
    public void playerIsCreatedOnSignup() throws BadRequestException, SQLException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;
        userStructure.displayName = displayName;

        UserService userService = new UserService();
        PlayerManager playerManager = new PlayerManager(userService);

        if (userService.findByUsername(username) == null) {
            Player player = playerManager.signupPlayer(userStructure);
            assertNotNull(player, "Player is null on signup");
            assertEquals(username, player.getUsername(), "Player username is not the same as the one supplied");
            assertTrue(player.getIdentifier() != null && player.getIdentifier().length() > 0, "Player username is not null and is not an empty string");
            assertNotNull(userService.findByUsername(username), "User with the specified username has been created in the database.");

        }
    }

    @Test
    public void ExceptionIsThrownWhenThereIsADuplicateLogin() throws SQLException, CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        String username = "test_user";
        String password = "test_pass";
        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;

        UserService userService = new UserService();


        if (userService.findByUsername(username) == null) {
            userService.registerNewUser(userStructure);
        }

        PlayerManager playerManager = new PlayerManager(userService);

        playerManager.loginPlayer(userStructure);

        assertThrows(DuplicateLoginException.class, ()-> playerManager.loginPlayer(userStructure));
    }

    @Test
    public void ExceptionIsThrowWhenInvalidDataIsSupplied() throws BadRequestException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;
        userStructure.displayName = displayName;

        UserService userService = new UserService();
        PlayerManager playerManager = new PlayerManager(userService);

        // Username tests
        userStructure.username = "";
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));

        userStructure.username = null;
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));

        // Password tests
        userStructure.username = username;
        userStructure.password = "";
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));

        userStructure.password = null;
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));

        // Display name tests
        userStructure.password = password;
        userStructure.displayName = "";
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));

        userStructure.displayName = null;
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(userStructure));
    }

    @Test
    public void TestUniqueUsernameConstraint() throws RuntimeException, SQLException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        UserStructure userStructure = new UserStructure();
        userStructure.username = username;
        userStructure.password = password;
        userStructure.displayName = displayName;

        UserService userService = new UserService();
        PlayerManager playerManager = new PlayerManager(userService);

        if (userService.findByUsername(username) == null) {
            userService.registerNewUser(userStructure);
        }

        assertThrows(RuntimeException.class, ()-> playerManager.signupPlayer(userStructure));
    }
}
