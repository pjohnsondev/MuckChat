package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import muck.client.AvatarController;
import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.client.App;
import muck.client.utilities.RandomNameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class SignUpController {
    private int maxPasswordLength = 26;
    private int maxDisplayNameLength = 26;
    private int maxUsernameLength = maxPasswordLength;

    @FXML
    Label error;

    @FXML
    PasswordField password;

    @FXML
    PasswordField passwordtwo;

    @FXML
    TextField username;

    @FXML
    TextField displayname;

    @FXML
    Button signUp;

    @FXML
    Button genName;


    @FXML
    void initialize() {
        genName.setOnAction(this::randomDisplayName);
    }

    public static void constructor() {
        try {
            Stage stage = new Stage();
            stage.setResizable(true);
            Parent root = FXMLLoader.load(Objects.requireNonNull(SignInController.class.getResource("/fxml/SignUp.fxml")));
            stage.setTitle("MUCK 2021");
            stage.setScene(new Scene(root));
            stage.show();
            stage.toFront();
        } catch (IOException e) {
            System.out.println("Can't find primary stage FXML file");
        }
    }

    /**
     * SignUp method calls validation methods and attempts to sign up new player
     * If signup is successful it passes to the next scene
     * @param event The Mouse event that triggered sign up
     * @throws InterruptedException if sleep method causes exception
     */
    @FXML
    protected void signUp(MouseEvent event) throws InterruptedException {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();
        boolean success = false;

        // Validate the sign up
        boolean validated = validateSignUp(displayName, userName, passWordText, passwordTwo);
        boolean user = createUser(validated, userName, passWordText, displayName);
        if(user){
            Thread.sleep(500);
            success = success();
        }
        if(success) {
            passToAvatar(event, userName, displayName);
        }

    }

    public void randomDisplayName(ActionEvent event) {
        RandomNameGenerator rng = new RandomNameGenerator();
        displayname.setText(rng.generateName());
    }

    /**
     * validateSignUp method calls all the required signup validation methods
     * @param displayname The provided display name
     * @param username the provided username
     * @param password the provided password
     * @param passwordtwo the provided password from the second password field
     * @return true if params pass all validation methods
     */
    public boolean validateSignUp(String displayname, String username, String password, String passwordtwo) {
        if (
            validUserNameLength(username) &&
            validPasswordLength(password) &&
            validDisplayNameLength(displayname) &&
            passwordsMatch(password, passwordtwo) &&

            IsNotEmpty(password) &&
            IsNotEmpty(passwordtwo) &&
            IsNotEmpty(username) &&
            IsNotEmpty(displayname)
        ) {
            return true;
        } else {
            // Catches and displays the corresponding error from the display method.
            displayErrors();
            return  false;

        }

    }

    /**
     * validUserNameLength method checks that username is not too long
     * @param username the provided username
     * @return true if username is within length limit and false if too long
     */
    public boolean validUserNameLength(String username) {
        if (username.length() > maxUsernameLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * validDisplayNameLength method checks that display name is not too long
     * @param displayname the provided display name
     * @return true if display name is within length limit and false if too long
     */
    public boolean validDisplayNameLength(String displayname) {
        if (displayname.length() > maxDisplayNameLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * validPasswordLength method checks that Passwords are not too long
     * @param password the provided password
     * @return true if password is within length limit and false if too long
     */
    public boolean validPasswordLength(String password) {
        if (password.length() > maxPasswordLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * passwordsMatch method checks that provided Passwords match
     * @param passWordText The text provided in the password field
     * @param passwordTwo The text provided in the second password field
     * @return True if passwords match and false if passwords don't match
     */
    public boolean passwordsMatch(String passWordText, String passwordTwo) {
        if (passWordText.equals(passwordTwo)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * IsNotEmpty method confirms that an input field is not empty
     * @param field The input field to test
     * @return True if field is not empty and False if field is empty
     */
    public boolean IsNotEmpty(String field) {
        if (field.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * CreateUser method sends user to the database via MuckClient
     * @param validated true if the input has been validated
     * @param userName The provided username
     * @param passwordText The provided password
     * @param displayName The provided display name
     * @return True if no error is returned when passing the information to the server via MuckClient
     */
    public boolean createUser(boolean validated, String userName, String passwordText, String displayName){
        if (validated) {
            try {
                // subscribe this class to notifications regarding registration results
                MuckClient.getINSTANCE().signUp(userName, passwordText, displayName);
                setError("New muck user created" + userName);
                return true;
            } catch (Exception ex) {
                setError("Unable to create new user: {}" + userName);

                throw new RuntimeException(String.format("Unable to create new user: %s.", userName));

            }
        }
        return false;
    }


    /**
     * Pass to avatar controller scene
     * @param event The mouse event that initiated signup
     * @param username The provided username
     * @param displayName The provided display name
     */
    public void passToAvatar(MouseEvent event, String username, String displayName){
        AvatarController nextScene = new AvatarController();
        App.hideStage();
        nextScene.avatarCreation(event, username, displayName);
    }

    /**
     * Confirm that the server has responded to signup and active user has been set
     * @return true if ActiveUser has been set
     */
    public boolean success(){
        if(ActiveUser.getInstance().getServerMessage() != null && ActiveUser.getInstance().getServerMessage().equals("Signup successful")){
            setError("Signup successful");
            return true;
        } else {
            setError(ActiveUser.getInstance().getServerMessage());
            return false;
        }
    }


    /**
     * display errors method for signup screen validations
     */
    public void displayErrors() {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();

        if (!IsNotEmpty(userName)) {
            setError("You must enter a user name");
        } else if (!IsNotEmpty(displayName)) {
            setError("You must enter a display name");
        } else if (!IsNotEmpty(passWordText)) {
            setError("You must enter a password");
        } else if (!passwordsMatch(passWordText, passwordTwo)) {
            setError("Passwords do not match");
        } else if (!validUserNameLength(userName)) {
            setError("Username must be less than " + maxUsernameLength + " characters");
        } else if (!validPasswordLength(passWordText)) {
            setError("Password must be less than " + maxPasswordLength + " characters");
        } else if (!validDisplayNameLength(displayName)) {
            setError("Display name must be less than " + maxDisplayNameLength + " characters");
        }
    }

    public void setError(String notification){
        error.setText(notification);
    }

}
