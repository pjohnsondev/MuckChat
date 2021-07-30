package muck.server;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import muck.server.models.User;
import muck.server.testHelpers.TestDatabase;

public class UserModelTest {

    @Test
    public void TableCreationTest() throws SQLException {
        // testDb for safety
        TestDatabase testDb = new TestDatabase();
        User user = new User();
        user.closeDbConnection();
        user.changeDb(testDb);

        testDb.dropTable("users");
        user.createTable();
       
        assertTrue(testDb.tableExists("users"));
        testDb.dropTable("users");
        assertFalse(testDb.tableExists("users"));
        user.closeDbConnection();

    }
    
    @Test
    public void UserRegistrationTest() throws SQLException, InvalidParameterException {
        // testDb for safety
        TestDatabase testDb = new TestDatabase();
        User user = new User();
        user.closeDbConnection();
        user.changeDb(testDb);
        // testDb for safety
        assertFalse(testDb.tableExists("users"));
        user.createTable();
        user.registerNewUser("newUser69", "myreallyGoodPassword");

        char[] badUsernameChars = new char[82];
        for (int i = 0; i <= 81; i++) {
            badUsernameChars[i] = '_';
        }
        String badUsername = String.valueOf(badUsernameChars);
        assertThrows(InvalidParameterException.class,
        () -> user.registerNewUser(badUsername,
        "myreallyGoodPassword"),
        "Username shouldn't have been accepted, and should have thrown instead");
        
        testDb.dropTable("users");
        assertFalse(testDb.tableExists("users"));
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
