package muck.server;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import muck.server.models.models.UserModel;
import muck.server.services.UserService;
import muck.server.structures.UserStructure;
import muck.server.Exceptions.ModelNotFoundException;

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
    private UserService userService;
    private UserStructure userStructure;

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
        userService = new UserService();
        userStructure = new UserStructure();
        resetTable(userModel, testDb);
        userStructure.username="newUser69";
        userStructure.password="myreallyGoodPassword";
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

        userService.registerNewUser(userStructure);

        char[] badUsernameChars = new char[82];
        for (int i = 0; i <= 81; i++) {
            badUsernameChars[i] = '_';
        }
        userStructure.username = String.valueOf(badUsernameChars);
        assertThrows(InvalidParameterException.class,
        () -> userService.registerNewUser(userStructure)//badUsername,"myreallyGoodPassword")
                 ,"Username shouldn't have been accepted, and should have thrown instead");
        
        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByNameTest() throws SQLException, ModelNotFoundException {

        userService.registerNewUser(userStructure);//"newUser69", "myreallyGoodPassword");
        userService.findByUsername("newUser69");

        assertTrue(userStructure.id != 0, "id not set");
        assertTrue(userStructure.username == "newUser69", "username wrong");
        assertTrue(userStructure.hashedPassword != null, "password not set");
        assertTrue(userStructure.salt != null, "salt not set");

        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     *
     * @throws SQLException
     */
    @Test
    public void findUserByIdTest() throws SQLException, ModelNotFoundException {

        userService.registerNewUser(userStructure);//"newUser69", "myreallyGoodPassword");

        userService.findById(1);

        assertTrue(userStructure.id != 0, "id not set");
        assertTrue(userStructure.username.equals("newUser69"), "username wrong");
        assertTrue(userStructure.hashedPassword != null, "password not set");
        assertTrue(userStructure.salt != null, "salt not set");

        dropAndClose(userModel, testDb);
    }

    /**
     * Test that ...
     * @throws SQLException
     */
    @Test
    public void authenticateUserTest() throws SQLException, ModelNotFoundException {
        userService.registerNewUser(userStructure);//"newUser69", "myreallyGoodPassword");

        assertTrue(userService.authenticateUser(userStructure));//"newUser69", "myreallyGoodPassword"));
        assertFalse(userService.authenticateUser(userStructure));//"newUser69", "myreallyBadPassword"));

        dropAndClose(userModel, testDb);
    }
}