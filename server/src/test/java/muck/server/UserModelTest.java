package muck.server;

import muck.core.structures.UserStructure;
import muck.server.models.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import muck.server.testHelpers.TestDatabase;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb = new TestDatabase();
    private UserModel userModel = new UserModel(testDb);


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
    public void InsertNewUserTest() throws SQLException, InvalidParameterException {
        //TODO: Complete this test
        //UserStructure testUser = new UserStructure();
        //userModel.insertNewUser(testUser);
        //assertTrue();
        //userModel.closeDbConnection();
    }

    @Test
    public void FindUserByUsernameTest() throws SQLException {

    }

    @Test
    public void FindUserByIdTest() throws SQLException {

    }


}
