package muck.server;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import muck.server.models.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import muck.server.testHelpers.TestDatabase;

public class UserModelModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb;
    private UserModel userModel;

    /**
     * A little test helper
     *
     * @param userModel
     * @param testDb
     * @throws SQLException
     */
    private void resetTable(UserModel userModel, TestDatabase testDb) throws SQLException {
        userModel.closeDbConnection();
        userModel.changeDb(testDb);
        testDb.dropTable("users");
        userModel.createTable();
    }

    /**
     * Another test helper
     *
     * @param userModel
     * @param testDb
     * @throws SQLException
     */
    private void dropAndClose(UserModel userModel, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        userModel.closeDbConnection();
    }

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
        userModel.closeDbConnection();
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

        userModel.registerNewUser("newUser69", "myreallyGoodPassword");

        char[] badUsernameChars = new char[82];
        for (int i = 0; i <= 81; i++) {
            badUsernameChars[i] = '_';
        }
        String badUsername = String.valueOf(badUsernameChars);
        assertThrows(InvalidParameterException.class,
        () -> userModel.registerNewUser(badUsername,
        "myreallyGoodPassword"),
        "Username shouldn't have been accepted, and should have thrown instead");
        
        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByNameTest() throws SQLException {

        userModel.registerNewUser("newUser69", "myreallyGoodPassword");
        userModel.findUserByUsername("newUser69");
       
        assertTrue(userModel.getId() != 0, "id not set");
        assertTrue(userModel.getUserName() == "newUser69", "username wrong");
        assertTrue(userModel.getHashedPassword() != null, "password not set");
        assertTrue(userModel.getSalt() != null, "salt not set");

        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByIdTest() throws SQLException {

        userModel.registerNewUser("newUser69", "myreallyGoodPassword");

        userModel.findUserById(1);

        assertTrue(userModel.getId() != 0, "id not set");
        assertTrue(userModel.getUserName().equals("newUser69"), "username wrong");
        assertTrue(userModel.getHashedPassword() != null, "password not set");
        assertTrue(userModel.getSalt() != null, "salt not set");

        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     * @throws SQLException
     */
    @Test
    public void authenticateUserTest() throws SQLException {
        userModel.registerNewUser("newUser69", "myreallyGoodPassword");

        assertTrue(userModel.authenticateUser("newUser69", "myreallyGoodPassword"));
        assertFalse(userModel.authenticateUser("newUser69", "myreallyBadPassword"));

        dropAndClose(userModel, testDb);
    }
}