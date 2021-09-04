package muck.server;

import muck.server.models.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import muck.server.testHelpers.TestDatabase;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase testDb;
    private UserModel userModel;


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
    @Disabled
    @Test
    public void TableCreationTest() throws SQLException {

        assertTrue(testDb.tableExists("users"));
        testDb.dropTable("users");
        assertFalse(testDb.tableExists("users"));
        userModel.closeDbConnection();
    }

}
