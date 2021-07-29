package muck.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import muck.server.testHelpers.TestDatabase;


public class DatabaseTest {

    @Test
    public void dbCanConnectTest(){
        TestDatabase db = new TestDatabase();
        assertTrue(db.databaseIsConnected());
        db.closeConnection();
    }

    @Test
    public void dbCanCreateTableTest() throws SQLException{
        TestDatabase db = new TestDatabase();

        // create a new table
        if (!db.tableExists("test_table")) {
            db.query(
                "CREATE TABLE test_table "
                + "(id INTEGER NOT NULL, "
                + " some_text VARCHAR(255), "
                + " more_text LONG VARCHAR, "
                + " floating_point REAL)"
                );
            db.execute();    
        }
        // check to see if table was created
        assertTrue(db.tableExists("test_table"));

        // insert some values into the table
        db.query("INSERT INTO test_table (id, some_text, more_text, floating_point) VALUES (?, ?, ?, ?)");
        db.bindInt(1, 69);
        db.bindString(2, "parameter");
        db.bindString(3, "some texty text");
        db.bindDouble(4, 22.22);
        db.execute();

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
        // get rid of the table so the db is back to normal
        db.query("DROP TABLE test_table");
        db.execute();
        db.closeConnection();
    }
}
