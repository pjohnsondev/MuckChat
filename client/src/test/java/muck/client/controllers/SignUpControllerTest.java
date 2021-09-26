package muck.client.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SignUpControllerTest {
    private static final Logger logger = LogManager.getLogger();

    private String password = "password";
    private String username = "username";
    private String passwordTwo = "password";
    private String displayName = "display name";

    SignUpController controllerMock = mock(SignUpController.class);
    SignUpController controllerSpy = spy(SignUpController.class);

    @Test
    public void testFieldsAreNotEmpty(){
        logger.info("Test fields display an error if empty");

        String emptyPasswordField = "";
        doNothing().when(controllerSpy).setError(anyString());

        assertAll(
                () -> assertTrue(controllerSpy.IsNotEmpty(username)),
                () -> assertTrue(controllerSpy.IsNotEmpty(displayName)),
                () -> assertTrue(controllerSpy.IsNotEmpty(password)),
                () -> assertTrue(controllerSpy.IsNotEmpty(displayName)),
                () -> assertTrue(controllerSpy.IsNotEmpty(password)),
                () -> assertFalse(controllerSpy.passwordsMatch(password, emptyPasswordField))
        );
    }

    @Test
    public void testValidPassword(){
        logger.info("Test password is valid");
        String passwordTooLong = "thispasswordistoolongtopassvalidation";
        String usernameTooLong = "thisusernameistoolongtopassvalidation";
        String wrongPasswordTwo = "passwordtwo";

        doNothing().when(controllerSpy).setError(anyString());

        assertAll(
                () -> assertFalse(controllerSpy.validPasswordLength(passwordTooLong)),
                () -> assertFalse(controllerSpy.validUserNameLength(usernameTooLong)),
                () -> assertFalse(controllerSpy.passwordsMatch(password, wrongPasswordTwo)),
                () -> assertTrue(controllerSpy.passwordsMatch(password, passwordTwo))
        );
    }

}