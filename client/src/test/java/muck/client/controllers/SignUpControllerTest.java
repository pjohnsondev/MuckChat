package muck.client.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SignUpControllerTest extends ApplicationTest {
    private static final Logger logger = LogManager.getLogger();

    private String passwordText = "password";
    private String userNameText= "username";
    private String displayNameText = "displayname";
    private String passwordTwoText = "password";

    @Mock
    private PasswordField password;
    @Mock
    private Label error;
    @Mock
    private PasswordField passwordtwo;
    @Mock
    private TextField username;
    @Mock
    private TextField displayname;
    @Mock
    private Button signUp;
    private int maxPasswordLength = 26;
    private int maxDisplayNameLength = 26;
    private int maxUsernameLength = maxPasswordLength;
    private AutoCloseable closeable;

    @InjectMocks
    SignUpController controllerMock;


//    SignUpController controllerMock = mock(SignUpController.class);
//    SignUpController controllerSpy = spy(SignUpController.class);

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
        controllerMock.password = new PasswordField();
        controllerMock.passwordtwo = new PasswordField();
        controllerMock.username = new TextField();
        controllerMock.displayname = new TextField();
        controllerMock.signUp = new Button();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
//    @Disabled
    public void testFieldsAreNotEmpty(){
        logger.info("Test fields return false if empty");

        String emptyPasswordField = "";

        assertAll(
                () -> assertTrue(controllerMock.IsNotEmpty(userNameText)),
                () -> assertTrue(controllerMock.IsNotEmpty(displayNameText)),
                () -> assertTrue(controllerMock.IsNotEmpty(passwordText)),
                () -> assertTrue(controllerMock.IsNotEmpty(displayNameText)),
                () -> assertTrue(controllerMock.IsNotEmpty(passwordText)),
                () -> assertFalse(controllerMock.IsNotEmpty(emptyPasswordField)),
                () -> assertFalse(controllerMock.passwordsMatch(passwordText, emptyPasswordField))
        );
    }

    @Test
    public void testValidPassword(){
        logger.info("Test password is valid");
        String passwordTooLong = "thispasswordistoolongtopassvalidation";
        String usernameTooLong = "thisusernameistoolongtopassvalidation";
        String wrongPasswordTwo = "passwordtwo";


        assertAll(
                () -> assertFalse(controllerMock.validPasswordLength(passwordTooLong)),
                () -> assertFalse(controllerMock.validUserNameLength(usernameTooLong)),
                () -> assertFalse(controllerMock.passwordsMatch(passwordText, wrongPasswordTwo)),
                () -> assertTrue(controllerMock.passwordsMatch(passwordText, passwordTwoText))
        );
    }

    @Test
    public void testErrorsDisplay(){
        logger.info("Testing that error Label is set for each field input error");

        String passwordTooLong = "thispasswordistoolongtopassvalidation";
        String usernameTooLong = "thisusernameistoolongtopassvalidation";
        String wrongPasswordTwo = "passwordtwo";
        String displayNameTooLong = "thisdisplaynameistoolongtopassvalidation";

        controllerMock.displayname.setText("");
        controllerMock.password.setText("");
        controllerMock.username.setText("");
        controllerMock.passwordtwo.setText("");


       // test that empty username displays error
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals("You must enter a user name"));
        controllerMock.error.getText().equals("");
        // test that empty display name displays error
        controllerMock.username.setText(userNameText);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals("You must enter a display name"));
        // test that empty password displays error
        controllerMock.displayname.setText(displayNameText);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals("You must enter a password"));
        // test that empty second password and password mismatch displays error
        controllerMock.password.setText(passwordText);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals("Passwords do not match"));
        // test that username displays error if too long
        controllerMock.passwordtwo.setText(passwordText);
        controllerMock.username.setText(usernameTooLong);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals(
                "Username must be less than " + maxUsernameLength + " characters"
        ));
        // test that password displays error if too long
        controllerMock.username.setText(userNameText);
        controllerMock.password.setText(passwordTooLong);
        controllerMock.passwordtwo.setText(passwordTooLong);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals(
                "Password must be less than " + maxPasswordLength + " characters"
        ));
        // test that display name displays error if too long
        controllerMock.password.setText(passwordText);
        controllerMock.passwordtwo.setText(passwordText);
        controllerMock.displayname.setText(displayNameTooLong);
        controllerMock.displayErrors();
        assertTrue(controllerMock.error.getText().equals(
                "Display name must be less than " + maxDisplayNameLength + " characters"
        ));
        // test that no error is displayed if all fields are set correctly
        controllerMock.error.setText("");
        controllerMock.displayname.setText(displayNameText);
        assertTrue(controllerMock.error.getText().equals(""));

    }




}