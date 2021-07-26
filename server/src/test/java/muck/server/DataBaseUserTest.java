package muck.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.server.database.Database;




public class DataBaseUserTest {

    private static final Logger logger = LogManager.getLogger(DataBaseUserTest.class);

    @Test
    public void dbCanConnectTest(){
        Database db = new Database();
        assertTrue(db.databaseIsConnected());
        db.closeConnection();
    }

    @Test
    public void dbCanCreateTableTest () {
        Database db = new Database();
        // create a new table
        db.query(
            "CREATE TABLE IF NOT EXISTS test_table "
            + "(id INTEGER NOT NULL, "
            + " some_text VARCHAR(255), "
            + " more_text TEXT, "
            + " float REAL);"
            );
        db.execute();

        // insert some values into the table
        db.query("INSERT INTO test_table (id, some_text, more_text, float) VALUES (?, ?, ?, ?);");
        db.bindInt(1, 69);
        db.bindString(2, "parameter");
        db.bindString(3, "some texty text");
        db.bindDouble(4, 22.22);
        db.execute();

        // check to see if the values are there
        db.query("SELECT * FROM test_table;");
        ResultSet result = db.getResultSet();

        try {
            while ( result.next() ) {
                int id = result.getInt("id");
                String some_text = result.getString("some_text");
                String more_text = result.getString("more_text");
                double floatingPoint = result.getDouble("float");
                logger.info(id);
                logger.info(some_text);
                logger.info(more_text.length());
                logger.info(floatingPoint);
                assertTrue(id == 69);
                assertTrue(some_text.equals("parameter"));
                assertTrue(more_text.equals("some texty text"));
                assertTrue(floatingPoint == 22.22);
                
            }} catch (Exception exception) {
                System.err.println("Got an exception! ");
                System.err.println(exception.getMessage());
            }
    
        // get rid of the table so the db is back to normal
        db.query("DROP TABLE IF EXISTS test_table;");
        db.execute();
        db.closeConnection();
    }

    @Test
    public void dbUserAccessTest(){
        /*This test unit will ensure the connection to the database is working*/
    }

    @Test
    public void requestUserInformationTest(){
        /*This test unit will test incoming user information testing.*/
    }
}
