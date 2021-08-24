package muck.client;

import muck.client.controllers.SignInController;
import muck.client.controllers.SignUpController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        SignUpController user = new SignUpController();
        String password = "passwordistoolong";
        assertEquals(false, user.validPasswordLength(password));
        String username = "usernameistoolong";
        assertEquals(false, user.validUserNameLength(username));
        String passwordOne = "passwordone";
        String passwordTwo = "passwordtwo";
        assertEquals(false, user.passwordsMatch(passwordOne, passwordTwo));
    }

    @Test
    // TODO: check if Fields are empty alert is added to front end
    public void testFieldsAreNotEmpty(){
        String password = "password";
        String passwordTwo = "passwordtwo";
        String username = "username";
        String displayName = "display name";

        SignUpController user = new SignUpController();

        assertAll(
                () -> assertTrue(user.userNameIsNotEmpty(username)),
                () -> assertTrue(user.displayNameIsNotEmpty(displayName)),
                () -> assertTrue(user.passwordIsNotEmpty(password)),
                () -> assertTrue(user.displayNameIsNotEmpty(displayName)),
                () -> assertTrue(user.passwordIsNotEmpty(password)),
                () -> assertFalse(user.passwordsMatch(password, passwordTwo))
        );
    }
}
