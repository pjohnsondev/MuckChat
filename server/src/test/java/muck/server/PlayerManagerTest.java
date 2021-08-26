package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.core.Login;
import muck.core.user.SignUpInfo;
import muck.server.models.models.User;
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
    private User user = new User();
    private TestDatabase testDb = new TestDatabase();

    @BeforeEach
    public void beforeEach() throws SQLException {
        logger.info("This message gets printed before each test runs");
        resetTable(user, testDb);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        logger.info("This message gets printed after each test runs");
        dropAndClose(user, testDb);
    }

    // A little test helper
    private void resetTable(User user, TestDatabase testDb) throws SQLException {
        user.closeDbConnection();
        user.changeDb(testDb);
        testDb.dropTable("users");
        user.createTable();
    }

    private void dropAndClose(User user, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        user.closeDbConnection();
    }

    @Test
    public void loginIsSuccessfulWithValidCredentials() throws SQLException, CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        String username = "test_username+1357";
        String password = "password";
        user.registerNewUser(username, password);
        Login login = new Login(username, password);
        PlayerManager playerManager = new PlayerManager(user);

        Player player = playerManager.loginPlayer(login);
        assertNotNull(player);
        assertEquals(username, player.getUsername());
    }

    @Test
    public void loginIsFailedWitInvalidPassword() throws SQLException {
        String username = "test_username";
        String password = "password";
        user.registerNewUser(username, password);

        Login login = new Login(username, "WrongPassword");
        PlayerManager playerManager = new PlayerManager(user);

        assertThrows(AuthenticationFailedException.class, () -> playerManager.loginPlayer(login));
    }

    @Test
    public void CharacterGetsCreatedOnLoginWithSuppliedUsername() throws CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException, SQLException {
        String username = "Test_Username";
        String password = "Test_Password";
        user.registerNewUser(username, password);

        Login login = new Login(username, password);
        PlayerManager playerManager = new PlayerManager(user);

        Player player = playerManager.loginPlayer(login);

        assertNotNull(player);
        assertEquals(username, player.getUsername());
    }

    @Test
    public void playerIsCreatedOnSignup() throws BadRequestException, SQLException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        SignUpInfo signUpInfo = new SignUpInfo(username, password, displayName);
        PlayerManager playerManager = new PlayerManager(user);

        Player player = playerManager.signupPlayer(signUpInfo);

        assertNotNull(player, "Player is null on signup");
        assertEquals(username, player.getUsername(), "Player username is not the same as the one supplied");
        assertTrue(player.getIdentifier() != null && player.getIdentifier().length() > 0, "Player username is not null and is not an empty string");
        assertTrue(user.findUserByUsername(username), "User with the specified username has been created in the database.");
    }

    @Test
    public void ExceptionIsThrownWhenThereIsADuplicateLogin() throws SQLException, CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        String username = "test_username";
        String password = "test_password";
        user.registerNewUser(username, password);

        Login login = new Login(username, "test_password");
        PlayerManager playerManager = new PlayerManager(user);

        playerManager.loginPlayer(login);

        assertThrows(DuplicateLoginException.class, ()-> playerManager.loginPlayer(login));
    }

    @Test
    public void ExceptionIsThrowWhenInvalidDataIsSupplied() throws BadRequestException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        SignUpInfo signUpInfo = new SignUpInfo(username, password, displayName);
        PlayerManager playerManager = new PlayerManager(user);

        // Username tests
        signUpInfo.setUsername("");
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));

        signUpInfo.setUsername(null);
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));

        // Password tests
        signUpInfo.setUsername(username);
        signUpInfo.setPassword("");
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));

        signUpInfo.setPassword(null);
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));

        // Display name tests
        signUpInfo.setPassword(password);
        signUpInfo.setDisplayName("");
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));

        signUpInfo.setDisplayName(null);
        assertThrows(BadRequestException.class, ()-> playerManager.signupPlayer(signUpInfo));
    }

    @Test
    public void TestUniqueUsernameConstraint() throws RuntimeException, SQLException {
        String username = "Test_Username";
        String password = "Test_Password";
        String displayName = "Test Display";

        SignUpInfo signUpInfo = new SignUpInfo(username, password, displayName);
        PlayerManager playerManager = new PlayerManager(user);

        user.registerNewUser(username, password);

        assertThrows(RuntimeException.class, ()-> playerManager.signupPlayer(signUpInfo));
    }
}
