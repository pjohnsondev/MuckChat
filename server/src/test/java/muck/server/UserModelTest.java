package muck.server;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import muck.server.models.User;
import muck.server.testHelpers.TestDatabase;

public class UserModelTest {

    @Test
    public void testDbCreation() throws SQLException {
        TestDatabase testDb = new TestDatabase();
        User user = new User();
        user.changeDb(testDb);

        user.createTable();
       
        assertTrue(testDb.tableExists("users"));
        user.closeDbConnection();
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
