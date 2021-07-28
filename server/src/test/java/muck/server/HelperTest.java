package muck.server;

import muck.server.helpers.security.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.derby.iapi.sql.dictionary.PasswordHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HelperTest {
    private static final Logger logger = LogManager.getLogger(DataBaseUserTest.class);

    @Test
    public void generateNewPasswordTest() {
        Hasher hasher = new Hasher();
        String password = "password";
        hasher.setNewPasswordHash(password);

        assertTrue(hasher.getSalt() != null);
        assertTrue(hasher.getHashedPassword() != null);
    }

    @Test
    public void passwordMatchesTest() {
        Hasher hasher = new Hasher();
        String password = "password";
        hasher.setNewPasswordHash(password);
        byte[] salt = hasher.getSalt();
        byte[] hashedPassword = hasher.getHashedPassword();

        assertTrue(hasher.passwordMatches(password, salt, hashedPassword));
        assertFalse(hasher.passwordMatches("the wrong password", salt, hashedPassword));
        
    }
        
}
