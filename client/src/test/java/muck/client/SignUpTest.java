package muck.client;

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
        SignUpController user = new SignUpController();
        String password = "passwordistoolong";
        assertEquals(false, user.validUserNameLength(password));
    }
}
