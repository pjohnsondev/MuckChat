package muck.client;

import muck.client.controllers.SignInController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SignInTest extends ApplicationTest {
    private static final Logger logger = LogManager.getLogger(SignInTest.class);

    @BeforeAll
    public static void beforeAll(){
        logger.info("Run this code 'before all' tests");
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("Run this code 'before each' test");
    }

    @Test
    public void testIsNotEmpty() {
        logger.info("Testing that empty input field check works");
        SignInController user = spy(SignInController.class);
        String testUsername = "Test Username";
        String testPassword = "Test Password";
        String testEmptyField = "";
        doNothing().when(user).setError(anyString());
        sleep(500);
        assertAll(
                () -> assertFalse(user.isNotEmpty(testUsername, testEmptyField)),
                () -> assertFalse(user.isNotEmpty(testEmptyField, testPassword)),
                () -> assertFalse(user.isNotEmpty(testEmptyField, testEmptyField)),
                () -> assertTrue(user.isNotEmpty(testUsername, testPassword))
        );
    }

}

