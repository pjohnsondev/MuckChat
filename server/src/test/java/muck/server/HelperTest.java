package muck.server;

import muck.server.helpers.security.*;
import muck.server.testHelpers.TestDatabase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HelperTest {
    private static final Logger logger = LogManager.getLogger(HelperTest.class);

    private Hasher hasher;
    private String password;

    /**
     * Set up environment before each test
     */
    @BeforeEach
    public void beforeEach() {
        logger.info("This message prints BEFORE each test runs");
        hasher = new Hasher();
        password = "password";
        hasher.setNewPasswordHash(password);
    }

    /**
     * Test that password is generated
     */
    @Test
    public void generateNewPasswordTest() {

        assertTrue(hasher.getSalt() != null);
        assertTrue(hasher.getHashedPassword() != null);
    }

    /**
     * Test that passwordMatches function returns true when there is a match, and false when there is not
     */
    @Test
    public void passwordMatchesTest() {
        byte[] salt = hasher.getSalt();
        byte[] hashedPassword = hasher.getHashedPassword();

        assertTrue(hasher.passwordMatches(password, salt, hashedPassword));
        assertFalse(hasher.passwordMatches("the wrong password", salt, hashedPassword));
    }
        
}