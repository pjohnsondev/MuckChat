package muck.client.controllers;

import javafx.scene.control.Label;
import muck.client.components.ActiveUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignInControllerTest2 extends ApplicationTest {
    private static final Logger logger = LogManager.getLogger();

    @Mock
    private static AutoCloseable autoCloseable;
    private SignInController signin;



    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        signin = spy(SignInController.class);
        signin.error = new Label();
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    @Disabled
    void constructor() {
    }

    @Test
    @Disabled
    void testSignIn() {

    }

    @Test
    @Disabled

    void testSignUp() {

    }

    @Test
//    @Disabled
    void testPassToNextScene() {

    }

    @Test
    void testSendData() {
        logger.info("Testing that senData returns true if provided a username and password");
        // Given
        String password = "password";
        String userName = "username";
        // When
        doNothing().when(signin).sendToMuck(anyString(), anyString());
        // Then
        assertTrue(signin.sendData(userName, password));
        assertEquals("Data sent", signin.error.getText());

    }

    @Test
//    @Disabled
    void testIsNotEmpty() {
        logger.info("Testing that empty input field check works");
        String testUsername = "Test Username";
        String testPassword = "Test Password";
        String testEmptyField = "";
        sleep(500);
        assertAll(
                () -> assertFalse(signin.isNotEmpty(testUsername, testEmptyField)),
                () -> assertFalse(signin.isNotEmpty(testEmptyField, testPassword)),
                () -> assertFalse(signin.isNotEmpty(testEmptyField, testEmptyField)),
                () -> assertTrue(signin.isNotEmpty(testUsername, testPassword))
        );
    }

    @Test
//    @Disabled
    void testSuccess() {
        ActiveUser activeUser = mock(ActiveUser.class);

        logger.info("Testing that success method returns false if ActiveUser is not set");
        assertFalse(signin.success());

        logger.info("Testing that success method returns false and sets error if signIn fails on server");
        activeUser.getInstance().setServerMessage("Server error message");
        signin.success();
        assertFalse(signin.success());
        assertEquals("Server error message", signin.error.getText());

        logger.info("Testing that success method returns true if signIn passes on server");
        activeUser.getInstance().setServerMessage("Login Successful");
        assertTrue(signin.success());
    }

    @Test
//    @Disabled
    void setError() {
        logger.info("Testing that setError inserts text into error Label");
        String errorTest = "test";
        signin.setError(errorTest);
        assertEquals("test", signin.error.getText());
    }
}