//package muck.client.controllers;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.testfx.framework.junit5.ApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class SignInControllerTest extends ApplicationTest {
//    private static final Logger logger = LogManager.getLogger();
//
//    private String passwordText = "password";
//    private String userNameText= "username";
//    private String emptyText = "";
//
//    @Mock
//    private PasswordField password;
//    @Mock
//    private Label error;
//    @Mock
//    private PasswordField passwordtwo;
//    @Mock
//    private TextField username;
//    @Mock
//    private Button signUp;
//    @Mock
//    private Button signIn;
//
//    private AutoCloseable closeable;
//
//    @InjectMocks
//    SignInController controllerMock;
//
//
//    @BeforeEach
//    void initService() {
//        closeable = MockitoAnnotations.openMocks(this);
//        controllerMock.password = new PasswordField();
//        controllerMock.username = new TextField();
//        controllerMock.signUp = new Button();
//        controllerMock.signIn = new Button();
//    }
//
//    @AfterEach
//    void closeService() throws Exception {
//        closeable.close();
//    }
//
//    @Test
//    public void testIsNotEmpty() {
//        logger.info("Testing that empty input fields halt signin");
//
//        assertAll(
//                () -> assertFalse(controllerMock.isNotEmpty(userNameText, emptyText)),
//                () -> assertFalse(controllerMock.isNotEmpty(emptyText, passwordText)),
//                () -> assertFalse(controllerMock.isNotEmpty(emptyText, emptyText)),
//                () -> assertTrue(controllerMock.isNotEmpty(userNameText, passwordText))
//        );
//    }
//
//    @Test
//    public void testErrorsDisplay(){
//        logger.info("Testing that empty input fields cause an error to display");
//
//        controllerMock.isNotEmpty(emptyText, emptyText);
//        assertTrue(controllerMock.error.getText().equals("You must enter a user name"));
//        controllerMock.isNotEmpty(emptyText, passwordText);
//        assertTrue(controllerMock.error.getText().equals("You must enter a user name"));
//        controllerMock.isNotEmpty(userNameText, emptyText);
//        assertTrue(controllerMock.error.getText().equals("You must enter your password"));
//
//    }
//}