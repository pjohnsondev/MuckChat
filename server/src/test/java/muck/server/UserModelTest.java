package muck.server;

import muck.core.structures.UserStructure;
import muck.server.Exceptions.ModelNotFoundException;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.database.Database;
import muck.server.models.models.UserModel;
import muck.server.services.UserService;
import muck.server.testHelpers.TestDatabase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserModelTest tests methods from the UserModel and UserService classes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private Database testDb;
    private UserModel userModel;
    private UserService userService;
    private UserStructure testUser1;
    private UserStructure testUser2;

    /**
     * A test helper method - not currently required
     *
     * @param userModel - An instance of the UserModel which enables creation and management of users on the database
     * @param testDb - A connection to the test database
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    private void dropAndClose(UserModel userModel, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        userModel.closeDbConnection();
    }

    /**
     * Another test helper method - not currently required
     *
     * @param userModel - An instance of the UserModel which enables creation and management of users on the database
     * @param testDb - A connection to the test database
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    private void resetTable(UserModel userModel, TestDatabase testDb) throws SQLException {
        userModel.closeDbConnection();
        userModel.changeDb(testDb);
        testDb.dropTable("users");
        userModel.createTable();
    }

    /**
     * Create test user to be used in the tests
     */
    @BeforeAll
    public void beforeAll() {
        testUser1 = new UserStructure();
        testUser1.username = "Bob19";
        testUser1.password = "Password01";
        testUser1.displayName = "Bob Ross";
    }

    /**
     * Establish a new database connection before each test
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws UserNameAlreadyTakenException - A custom exception defined in muck.server.Exceptions
     */
    @BeforeEach
    public void beforeEach() throws SQLException, UserNameAlreadyTakenException {
        logger.info("This message prints BEFORE each test runs");
        // Reset database using testDb
        testDb = TestDatabase.getINSTANCE();
        userModel = new UserModel(testDb);
        userService = new UserService(userModel);
        if (!testDb.tableExists("users")) {
            userModel.createTable();
        }
        // Reset testUser2 each time
        testUser2 = new UserStructure();
        testUser2.username = "testUser2";
        testUser2.password = "Password02";
        testUser2.displayName = "Test User 2";

        // Register new user with username Bob19 and displayName "Bob Ross"
        userService.registerNewUser(testUser1);
    }

    /**
     * Remove registered test user and close database connection after each test
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @AfterEach
    public void afterEach() throws SQLException {
        logger.info("This message prints AFTER each test runs");
        // Remove user Bob19 from table to reset for the next test
        testDb.query("DELETE FROM users WHERE username = 'Bob19'");
        testDb.executeUpdate();
        // Close database connection
        userModel.closeDbConnection();
    }

    /**
     * TableCreationTest tests that the users table has been created in the test database
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void TableCreationTest() throws SQLException {
        assertTrue(testDb.tableExists("users"), "The users table does not exist");
    }

    /**
     * DropTableTest tests that the table has been dropped successfully
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void DropTableTest() throws SQLException {
        //TODO: Fix this test
        //assertTrue(testDb.tableExists("users"), "The users table does not exist before running dropTable");
        //testDb.dropTable("users");
        //assertFalse(testDb.tableExists("users"), "The users table still exists after running dropTable");
        //userModel.closeDbConnection();
    }

    /**
     * RegisterNewUserTest tests the registerNewUser method from UserService successfully adds
     * a user to the users table in the test database; and that it returns a boolean value of true when
     * executed successfully
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void RegisterNewUserTest() throws SQLException, UserNameAlreadyTakenException {
        testDb.query("SELECT * FROM users WHERE username = 'Bob19'");
        ResultSet rs = testDb.getResultSet();
        assertTrue(rs.next(), "No database entry where username is Bob19");

        testDb.query("SELECT * FROM users WHERE username = 'Bob20'");
        ResultSet rs2 = testDb.getResultSet();
        assertFalse(rs2.next(),"Database entry found for user that has not been registered - suggests logic error in test code");

        assertTrue(userService.registerNewUser(testUser2),"registerNewUser did not return a boolean value of true");
        // Remove testUser2 from table to reset for the next test
        testDb.query("DELETE FROM users WHERE username = 'testUser2'");
        testDb.executeUpdate();
    }

    /**
     * The UniqueUserNameTest tests that the registerNewUser method throws an exception when
     * a username is already taken
     */
    @Test
    public void UniqueUserNameTest() {
        testUser2.username = "Bob19";
        assertThrows(UserNameAlreadyTakenException.class, () ->
                userService.registerNewUser(testUser2), "Registered a second user called Bob19");
    }

    /**
     * The UniqueDisplayNameTest tests that the registerNewUser method throws an exception when
     * a displayName is already taken
     */
    @Test
    public void UniqueDisplayNameTest() {
        testUser2.displayName = "Bob Ross";
        assertThrows(UserNameAlreadyTakenException.class, () ->
                userService.registerNewUser(testUser2), "Registered a second user with same displayName");
    }

    /**
     * The UsernameLimitTest tests that the registerNewUser method throws an exception when
     * username exceeds 80 characters
     *
     * @throws InvalidParameterException - Provides information on the error arising from the argument passed. For more information see https://docs.oracle.com/javase/7/docs/api/java/security/InvalidParameterException.html
     */
    @Test
    public void UsernameLimitTest() throws InvalidParameterException {
        char[] badUsernameChars = new char[82];
        for (int i = 0; i < 81; i++) {
            badUsernameChars[i] = '_';
        }
        testUser2.username = String.valueOf(badUsernameChars);

        assertThrows(InvalidParameterException.class, () ->
                userService.registerNewUser(testUser2), "Registered user with username greater than 80 characters");
    }

    /**
     * The UserStructureFieldsGeneratedTest tests that registerNewUser (as called in beforeEach() method)
     * generates an id, hashedPassword, and salt values for the UserStructure
     */
    @Test
    public void UserStructureFieldsGeneratedTest() {
        assertNotNull(testUser1.id, "id not set");
        assertNotNull(testUser1.hashedPassword, "Hashed password not set");
        assertNotNull(testUser1.salt, "Salt not set");
    }

    /**
     * The AuthenticateUserTest tests that the AuthenticateUser method will return true
     * where the user's details match those stored in the database, and false where they
     * do not match
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     * @throws ModelNotFoundException - A custom exception defined in muck.server.Exceptions
     */
    @Test
    public void AuthenticateUserTest() throws SQLException, ModelNotFoundException {
        assertTrue(userService.authenticateUser(testUser1), "Authentication failed for testUser1");
        testUser1.password = "Password02";
        assertFalse(userService.authenticateUser(testUser1), "Authentication succeeded for testUser1 with the incorrect password");

        // Reset testUser1's password in order to not upset future tests
        testUser1.password = "Password01";
    }

    /**
     * The FindUserByUsernameTest tests that the findByUsername method, when called on a username,
     * returns a UserStructure containing the user details associated with that username
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void FindUserByUsernameTest() throws SQLException {
        assertEquals(userService.findByUsername("Bob19").username, testUser1.username, "Username of the UserStructure found does not match testUser1's username");
        assertEquals(userService.findByUsername("Bob19").displayName, testUser1.displayName, "displayName of the UserStructure found does not match testUser1's displayName");
        }

    /**
     * The FindUserByDisplaynameTest tests that the findByDisplayname method, when called on a displayName,
     * returns a UserStructure containing the user details associated with that displayName
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void FindUserByDisplaynameTest() throws SQLException {
        assertEquals(userService.findByDisplayname("Bob Ross").username, testUser1.username,"Username of the UserStructure found does not match testUser1's username");
        assertEquals(userService.findByDisplayname("Bob Ross").displayName, testUser1.displayName, "displayName of the UserStructure found does not match testUser1's displayName");
    }

    /**
     * The FindByIdTest tests that the findById method, when called on a userID,
     * returns a UserStructure containing the user details associated with that userID
     *
     * @throws SQLException - Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @Test
    public void FindByIdTest() throws SQLException {
        assertEquals((userService.findById(testUser1.id)).username, testUser1.username,"Username of the UserStructure found does not match testUser1's username");
    }

}