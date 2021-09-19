package muck.server;

import muck.core.structures.UserStructure;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.models.models.UserModel;
import muck.server.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import muck.server.testHelpers.TestDatabase;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb = new TestDatabase();
    private UserModel userModel = new UserModel(testDb);
    private UserService userService = new UserService(userModel);

    private void dropAndClose(UserModel userModel, TestDatabase testDb) throws SQLException {
        testDb.dropTable("users");
        userModel.closeDbConnection();
    }
    private void resetTable(UserModel userModel, TestDatabase testDb) throws SQLException {
        userModel.closeDbConnection();
        userModel.changeDb(testDb);
        testDb.dropTable("users");
        userModel.createTable();
    }
    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() throws SQLException{
        logger.info("This message prints BEFORE each test runs");
//         reset database using testDB
        testDb = new TestDatabase();
        userModel = new UserModel(testDb);
        if (!testDb.tableExists("users")) {
            userModel.createTable();
        }
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() {
        logger.info("This message prints AFTER each test runs");
    }

    @Test
    public void TableCreationTest() throws SQLException {
        assertTrue(testDb.tableExists("users"));
        userModel.closeDbConnection();
    }

    @Test
    public void DropTableTest() throws SQLException {
        //TODO: Fix this test
        //assertTrue(testDb.tableExists("users"));
        //testDb.dropTable("users");
        //assertFalse(testDb.tableExists("users"));
        //userModel.closeDbConnection();
    }

    @Test
    public void RegisterNewUserTest() throws SQLException, UserNameAlreadyTakenException {
        UserStructure testUser = new UserStructure();
        testUser.username = "Bob19";
        testUser.password = "Password01";
        testUser.displayName = "Bob Ross";
        userService.registerNewUser(testUser);
        testDb.query("SELECT * FROM users WHERE username = 'Bob19'");
        ResultSet rs = testDb.getResultSet();
        assertTrue(rs.next());
        // Remove user so the test will still pass in future (i.e. won't throw UserNameAlreadyTakenException)
        testDb.query("DELETE FROM users WHERE username = 'Bob19'");
        testDb.executeUpdate();

        userModel.closeDbConnection();
    }


    @Test
    public void FindUserByUsernameTest() throws SQLException {
        //
        }

    @Test
    public void FindUserByIdTest() throws SQLException {

    }


}
