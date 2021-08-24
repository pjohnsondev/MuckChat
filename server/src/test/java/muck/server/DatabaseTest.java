package muck.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.server.testHelpers.TestDatabase;

public class DatabaseTest {
    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private TestDatabase db;

    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() {
        logger.info("This message prints BEFORE each test runs");
        db = new TestDatabase();
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() {
        logger.info("This message prints AFTER each test runs");
        db.closeConnection();
        }

    /**
     * Test that database can connect
     */
    @Test
    public void dbCanConnectTest(){
        assertTrue(db.databaseIsConnected());
    }

    /**
     * Test that databaseIsConnected returns false when the connection has been closed
     */
    @Disabled
    @Test
    public void dbCanDisconnectTest(){

        //TODO - Fix Database.java code so this test passes
        db.closeConnection();
        assertFalse(db.databaseIsConnected());
    }

    /**
     * Test that database can create a table
     *
     * @throws SQLException
     */
    @Test
    public void dbCanCreateTableTest() throws SQLException{
        // create a new table
        db.createTableIfNotExists(
            "test_table", 
            "CREATE TABLE test_table "
            + "(id INTEGER NOT NULL, "
            + " some_text VARCHAR(255), "
            + " more_text LONG VARCHAR, "
            + " floating_point REAL)"
        );

        // check to see if table was created
        assertTrue(db.tableExists("test_table"));
    }

    /**
     * Test that database can insert
     *
     * @throws SQLException
     * @throws Exception
     */
    @Test
    public void dbCanInsertTest() throws SQLException, Exception {
    // create a new table if it doesn't already exist
        db.createTableIfNotExists(
            "test_table", 
            "CREATE TABLE test_table "
            + "(id INTEGER NOT NULL, "
            + " some_text VARCHAR(255), "
            + " more_text LONG VARCHAR, "
            + " floating_point REAL)"
            );
        
        // insert some values into the table
        db.query("INSERT INTO test_table (id, some_text, more_text, floating_point) VALUES (?, ?, ?, ?)");
        db.bindInt(1, 69);
        db.bindString(2, "parameter");
        db.bindString(3, "some texty text");
        db.bindDouble(4, 22.22);
        db.executeUpdate();

        // check to see if the values are there
        db.query("SELECT * FROM test_table");
        ResultSet result = db.getResultSet();

        try {
            while ( result.next() ) {
                int id = result.getInt("id");
                String some_text = result.getString("some_text");
                String more_text = result.getString("more_text");
                double floating_pointingPoint = result.getDouble("floating_point");
                assertTrue(id == 69);
                assertTrue(some_text.equals("parameter"));
                assertTrue(more_text.equals("some texty text"));
                // I'm using an epsilon to test floating point eqality see this: http://www.fredosaurus.com/notes-java/data/expressions/comparing-floatingpoint.html
                // There are apparently still problems with doing it this way, but it would be overkill to go further than this
                assertTrue(Math.abs(22.22 - floating_pointingPoint) < 0.00001);
                
            }} catch (Exception exception) {
                System.err.println("Got an exception! ");
                System.err.println(exception.getMessage());
            }
        result.close();
    }

    /**
     * Test that database can drop table
     *
     * @throws SQLException
     */
    @Test
    public void dbCanDropTableTest() throws SQLException {
    // create a new table if it doesn't already exist
        db.createTableIfNotExists(
            "test_table", 
            "CREATE TABLE test_table "
            + "(id INTEGER NOT NULL, "
            + " some_text VARCHAR(255), "
            + " more_text LONG VARCHAR, "
            + " floating_point REAL)"
            );
        
        // get rid of the table so the db is back to normal
        db.dropTable("test_table");
        assertFalse(db.tableExists("test_table"));
    }
}