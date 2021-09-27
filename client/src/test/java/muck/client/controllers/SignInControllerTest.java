package muck.client.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SignInControllerTest extends ApplicationTest {
    private static final Logger logger = LogManager.getLogger(SignInController.class);

    private SignInController controllerMock = mock(SignInController.class);
    private String testUsername = "Test Username";
    private String testPassword = "Test Password";
    private String testEmptyField = "";

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
        logger.info("Testing that empty input fields halt signin");

        doNothing().when(controllerMock).setError(anyString());
        when(controllerMock.isNotEmpty(anyString(), anyString())).thenCallRealMethod();

        assertAll(
                () -> assertFalse(controllerMock.isNotEmpty(testUsername, testEmptyField)),
                () -> assertFalse(controllerMock.isNotEmpty(testEmptyField, testPassword)),
                () -> assertFalse(controllerMock.isNotEmpty(testEmptyField, testEmptyField)),
                () -> assertTrue(controllerMock.isNotEmpty(testUsername, testPassword))
        );
    }


}