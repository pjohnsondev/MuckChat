package muck.server;

import static org.mockito.Mockito.*;

import muck.server.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Source for developing Mockito testing of database connection:
 * https://examples.javacodegeeks.com/core-java/mockito/mockito-mock-database-connection-example/
 */
public class DatabaseTestMock {
    private static final Logger logger = LogManager.getLogger(DatabaseTestMock.class);

    @InjectMocks
    private Database mockDb = mock(Database.class, CALLS_REAL_METHODS);

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;


    /**
     * Establish a new database connection before each test
     */
    @BeforeEach
    public void beforeEach() {
        logger.info("This message prints BEFORE each test runs");
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() {
        logger.info("This message prints AFTER each test runs");
        mockDb.closeConnection();
    }

    /**
     * Test that database can connect
     */
    @Disabled
    @Test
    public void dbCanConnectTest(){
        System.out.println("dbCanConnect runs");
        assertTrue(mockDb.databaseIsConnected());
    }

    /**
     * Alternate test that database can connect
     */
    @Disabled
    @Test
    public void dbCanConnectTestTakeTwo() throws Exception {
        System.out.println("dbCanConnect2 runs");
        when(mockConnection.createStatement()).thenReturn(mockPreparedStatement);
        //when(mockConnection.createStatement().excuteUpdate(any())).thenReturn(1);
        //int value = mockDb.executeUpdate("");
        //assertEquals(value, 1);
        verify(mockConnection.createStatement(), times(1));
    }

    /**
     * Test that databaseIsConnected returns false when the connection has been closed
     */
    @Disabled
    @Test
    public void dbCanDisconnectTest(){

        //TODO - Fix Database.java code so this test passes
        mockDb.closeConnection();
        assertFalse(mockDb.databaseIsConnected());
    }

    /**
     * Test that database can create a table
     *
     * @throws SQLException
     */
    @Disabled
    @Test
    public void dbCanCreateTableTest() throws SQLException{
        // create a new table
        mockDb.createTableIfNotExists(
                "test_table",
                "CREATE TABLE test_table "
                        + "(id INTEGER NOT NULL, "
                        + " some_text VARCHAR(255), "
                        + " more_text LONG VARCHAR, "
                        + " floating_point REAL)"
        );

        // check to see if table was created
        assertTrue(mockDb.tableExists("test_table"));
    }

    /**
     * Test that database can insert
     *
     * @throws SQLException
     * @throws Exception
     */
    @Disabled
    @Test
    public void dbCanInsertTest() throws SQLException, Exception {
        // create a new table if it doesn't already exist
        mockDb.createTableIfNotExists(
                "test_table",
                "CREATE TABLE test_table "
                        + "(id INTEGER NOT NULL, "
                        + " some_text VARCHAR(255), "
                        + " more_text LONG VARCHAR, "
                        + " floating_point REAL)"
        );

        // insert some values into the table
        mockDb.query("INSERT INTO test_table (id, some_text, more_text, floating_point) VALUES (?, ?, ?, ?)");
        mockDb.bindInt(1, 69);
        mockDb.bindString(2, "parameter");
        mockDb.bindString(3, "some texty text");
        mockDb.bindDouble(4, 22.22);
        mockDb.executeUpdate();

        // check to see if the values are there
        mockDb.query("SELECT * FROM test_table");
        ResultSet result = mockDb.getResultSet();

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
    @Disabled
    @Test
    public void dbCanDropTableTest() throws SQLException {
        // create a new table if it doesn't already exist
        mockDb.createTableIfNotExists(
                "test_table",
                "CREATE TABLE test_table "
                        + "(id INTEGER NOT NULL, "
                        + " some_text VARCHAR(255), "
                        + " more_text LONG VARCHAR, "
                        + " floating_point REAL)"
        );

        // get rid of the table so the db is back to normal
        mockDb.dropTable("test_table");
        assertFalse(mockDb.tableExists("test_table"));
    }
}
