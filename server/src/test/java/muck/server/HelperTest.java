package muck.server;

import muck.server.helpers.security.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HelperTest {

    /**
     * Test that password is generated
     */
    @Test
    public void generateNewPasswordTest() {
        Hasher hasher = new Hasher();
        String password = "password";
        hasher.setNewPasswordHash(password);

        assertTrue(hasher.getSalt() != null);
        assertTrue(hasher.getHashedPassword() != null);
    }

    /**
     * Test that passwordMatches function returns true when there is a match, and false when there is not
     */
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
