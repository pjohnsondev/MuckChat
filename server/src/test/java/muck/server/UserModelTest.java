package muck.server;

import muck.core.structures.UserStructure;
import muck.server.Exceptions.ModelNotFoundException;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.models.models.UserModel;
import muck.server.services.UserService;
import muck.server.testHelpers.TestDatabase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb;
    private UserModel userModel;
    private UserService userService;
    private UserStructure testUser1;
    private UserStructure testUser2;

    /**
     * A test helper method
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
     * Another test helper method
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
     * Create test users to be used in the tests
     */
    @BeforeAll
    public void beforeAll() {
        testUser1 = new UserStructure();
        testUser1.username = "Bob19";
        testUser1.password = "Password01";
        testUser1.displayName = "Bob Ross";

        testUser2 = new UserStructure();
        testUser2.username = "Bob19";
        testUser2.password = "Password02";
        testUser2.displayName = "Bob Smith";

    }


    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() throws SQLException, UserNameAlreadyTakenException {
        logger.info("This message prints BEFORE each test runs");
        // Reset database using testDb
        testDb = new TestDatabase();
        userModel = new UserModel(testDb);
        userService = new UserService(userModel);
        if (!testDb.tableExists("users")) {
            userModel.createTable();
        }
        // Register Bob19 as a new user
        userService.registerNewUser(testUser1);
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() throws SQLException {
        logger.info("This message prints AFTER each test runs");
        // Remove user from table to reset for the next test
        testDb.query("DELETE FROM users WHERE username = 'Bob19'");
        testDb.executeUpdate();
        // Close database connection
        userModel.closeDbConnection();
    }

    /**
     * TableCreationTest tests that the users table has been created in the test database
     *
     * @throws SQLException
     */
    @Test
    public void TableCreationTest() throws SQLException {
        assertTrue(testDb.tableExists("users"));
    }

    /**
     * DropTableTest tests that the table has been dropped successfully
     *
     * @throws SQLException
     */
    @Test
    public void DropTableTest() throws SQLException {
        //TODO: Fix this test
        //assertTrue(testDb.tableExists("users"));
        //testDb.dropTable("users");
        //assertFalse(testDb.tableExists("users"));
        //userModel.closeDbConnection();
    }

    /**
     * RegisterNewUserTest tests the registerNewUser method from UserService successfully adds
     * a user to the users table in the test database
     *
     * @throws SQLException
     * @throws UserNameAlreadyTakenException
     */
    @Test
    public void RegisterNewUserTest() throws SQLException, UserNameAlreadyTakenException {
        testDb.query("SELECT * FROM users WHERE username = 'Bob19'");
        ResultSet rs = testDb.getResultSet();
        assertTrue(rs.next());

        testDb.query("SELECT * FROM users WHERE username = 'Bob20'");
        ResultSet rs2 = testDb.getResultSet();
        assertFalse(rs2.next());
    }

    /**
     * The UniqueUserNameTest tests that the registerNewUser method throws an exception when
     * a user name is already taken
     * @throws SQLException
     * @throws UserNameAlreadyTakenException
     */
    @Test
    public void UniqueUserNameTest() throws SQLException, UserNameAlreadyTakenException {
        assertThrows(UserNameAlreadyTakenException.class, () ->
                userService.registerNewUser(testUser2), "Registered a second user called Bob19");
    }

    @Test
    public void AuthenticateUserTest() throws SQLException, UserNameAlreadyTakenException, ModelNotFoundException {
        assertTrue(userService.authenticateUser(testUser1));
        testUser1.password = "Password02";
        assertFalse(userService.authenticateUser(testUser1));

        // Reset testUser1's password so as to not upset future tests
        testUser1.password = "Password01";
    }


    @Test
    public void FindUserByUsernameTest() throws SQLException, UserNameAlreadyTakenException {
        assertEquals(userService.findByUsername("Bob19").username, testUser1.username);

        // Need to update UserService.java to recognise display name before below assertion will pass
        //assertEquals(userService.findByUsername("Bob19").displayName, testUser.displayName);

        }

    @Test
    public void FindByIdTest() throws SQLException, UserNameAlreadyTakenException {
        //assertEquals(userService.findById(1).username, testUser1.username);
    }

}