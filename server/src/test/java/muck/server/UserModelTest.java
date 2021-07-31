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

    // A little test helper
    public void resetTable(User user, TestDatabase testDb) throws SQLException {
        user.closeDbConnection();
        user.changeDb(testDb);
        testDb.dropTable("users");
        user.createTable();
    }


    @Test
    public void TableCreationTest() throws SQLException {
        // reset database using testDB
        TestDatabase testDb = new TestDatabase();
        User user = new User();
        resetTable(user, testDb);

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
        resetTable(user, testDb);
        assertTrue(testDb.tableExists("users"));

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
    public void findUserByNameTest() throws SQLException {
        TestDatabase testDb = new TestDatabase();
        User user = new User();
        resetTable(user, testDb);
        user.registerNewUser("newUser69", "myreallyGoodPassword");
        user.findUserByUsername("newUser69");
       
        assertTrue(user.getId() != 0);
        assertTrue(user.getUserName() != null);
        assertTrue(user.getHashedPassword() != null);
        assertTrue(user.getSalt() != null);

        testDb.dropTable("users");
        assertFalse(testDb.tableExists("users"));
        user.closeDbConnection();
    }
}
