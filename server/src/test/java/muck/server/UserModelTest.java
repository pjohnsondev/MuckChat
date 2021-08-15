package muck.server;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import muck.server.models.models.User;
import muck.server.testHelpers.TestDatabase;

public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb;
    private User user;

    /**
     * A little test helper
     *
     * @param user
     * @param testDb
     * @throws SQLException
     */
    private void resetTable(User user, TestDatabase testDb) throws SQLException {
        user.closeDbConnection();
        user.changeDb(testDb);
        testDb.dropTable("users");
        user.createTable();
    }

    /**
     * Another test helper
     *
     * @param user
     * @param testDb
     * @throws SQLException
     */
    private void dropAndClose(User user, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        user.closeDbConnection();
    }

    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() throws SQLException{
        logger.info("This message prints BEFORE each test runs");
        // reset database using testDB
        testDb = new TestDatabase();
        user = new User();
        resetTable(user, testDb);
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() {
        logger.info("This message prints AFTER each test runs");
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void TableCreationTest() throws SQLException {

        assertTrue(testDb.tableExists("users"));
        testDb.dropTable("users");
        assertFalse(testDb.tableExists("users"));
        user.closeDbConnection();
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Test
    public void UserRegistrationTest() throws SQLException, InvalidParameterException {

        assertTrue(testDb.tableExists("users"));

        user.registerNewUser("newUser69", "myreallyGoodPassword");

        char[] badUsernameChars = new char[82];
        for (int i = 0; i <= 81; i++) {
            badUsernameChars[i] = '_';
        }
        String badUsername = String.valueOf(badUsernameChars);
        assertThrows(InvalidParameterException.class,
        () -> user.registerNewUser(badUsername,
        "myreallyGoodPassword"),
        "Username shouldn't have been accepted, and should have thrown instead");
        
        dropAndClose(user, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByNameTest() throws SQLException {

        user.registerNewUser("newUser69", "myreallyGoodPassword");
        user.findUserByUsername("newUser69");
       
        assertTrue(user.getId() != 0, "id not set");
        assertTrue(user.getUserName() == "newUser69", "username wrong");
        assertTrue(user.getHashedPassword() != null, "password not set");
        assertTrue(user.getSalt() != null, "salt not set");

        dropAndClose(user, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByIdTest() throws SQLException {

        user.registerNewUser("newUser69", "myreallyGoodPassword");

        user.findUserById(1);

        assertTrue(user.getId() != 0, "id not set");
        assertTrue(user.getUserName().equals("newUser69"), "username wrong");
        assertTrue(user.getHashedPassword() != null, "password not set");
        assertTrue(user.getSalt() != null, "salt not set");

        dropAndClose(user, testDb);
    }

    /**
     * Test that ...
     * @throws SQLException
     */
    @Test
    public void authenticateUserTest() throws SQLException {
        user.registerNewUser("newUser69", "myreallyGoodPassword");

        assertTrue(user.authenticateUser("newUser69", "myreallyGoodPassword"));
        assertFalse(user.authenticateUser("newUser69", "myreallyBadPassword"));

        dropAndClose(user, testDb);
    }
}