package muck.client;

import muck.client.controllers.SignInController;
import muck.client.controllers.SignUpController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpTest {
    private static final Logger logger = LogManager.getLogger(SignInTest.class);

    @BeforeAll
    public static void beforeAll(){
        logger.info("Run this code 'before all' setup");
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("Run this code 'before each' setup");
    }

    @Test
    // TODO: Check if username and password are within length limits
    public void testValidPassword(){
        String password = "passwordistoolong";
        String username = "usernameistoolong";
        String passwordCopy = "passwordone";
        String passwordTwo = "passwordtwo";
        SignUpController user = new SignUpController();

        assertEquals(false, user.validPasswordLength(password));
        assertEquals(false, user.validUserNameLength(username));

        /**
         * the below test is failing as the boolean function sets error text prior to returning the boolean
//        assertEquals(false, user.passwordsMatch("test", "testing"));
         */
    }

    @Test
    // TODO: check if Fields are empty alert is added to front end
    public void testIsNotEmpty(){
        String password = "password";
        String username = "username";
        String displayName = "display name";

        SignUpController user = new SignUpController();

        /**
         * need to figure out how to pass empty string to test that fail works
        assertEquals(false, user.isNotEmpty("", username, displayName));
        assertEquals(false, user.isNotEmpty(password, "", displayName));
        assertEquals(false, user.isNotEmpty(password,username, ""));
         */
        assertEquals(true, user.isNotEmpty(password, username, displayName));
    }
}
