package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import muck.client.AvatarController;
import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.core.models.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.*;
import muck.client.App;
import muck.client.utilities.RandomNameGenerator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.EventListener;


public class SignUpController {
    private Integer maxPasswordLength = 26;
    private int maxDisplayNameLength = 26;
    private int maxUsernameLength = maxPasswordLength;
    private static final Logger logger = LogManager.getLogger(App.class);

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

    // Todo add logic to
    @FXML
    protected void signUp(MouseEvent event) throws SQLException, IOException, InterruptedException {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();
        boolean success = false;

        // Validate the sign up
        boolean validated = validateSignUp(event, displayName, userName, passWordText, passwordTwo);
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

    // TODO: Sign Up validation method - implement functionality
    public boolean validateSignUp(MouseEvent event, String displayname, String username, String password, String passwordtwo) {
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

    // Username Length Validation method
    public boolean validUserNameLength(String username) {
        if (username.length() > maxUsernameLength) {
            return false;
        } else {
            return true;
        }
    }

    // Display name Length Validation method
    public boolean validDisplayNameLength(String displayname) {
        if (displayname.length() > maxDisplayNameLength) {
            return false;
        } else {
            return true;
        }
    }

    // Password Length Validation method
    public boolean validPasswordLength(String password) {
        if (password.length() > maxPasswordLength) {
            return false;
        } else {
            return true;
        }
    }


    // Passwords match Validation method
    public boolean passwordsMatch(String passWordText, String passwordTwo) {
        if (passWordText.equals(passwordTwo)) {
            return true;
        } else {
            setError("Passwords do not match");
            return false;
        }
    }

    // Field not empty validation
    public boolean IsNotEmpty(String field) {
        if (field.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean createUser(boolean validated, String userName, String passwordText, String displayName){
        if (validated) {
            try {
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


    // TODO: Add Pass to Avatar Selection Display
    public void passToAvatar(MouseEvent event, String username, String displayName) throws IOException{
        AvatarController nextScene = new AvatarController();
        App.hideStage();
        nextScene.avatarCreation(event, username, displayName);
    }

    public boolean success(){
        if(ActiveUser.getInstance().getServerMessage() != null && ActiveUser.getInstance().getServerMessage().equals("Signup successful")){
            setError("Signup successful");
            return true;
        } else {
            setError(ActiveUser.getInstance().getServerMessage());
            return false;
        }
    }


    // display errors method for signup screen validations
    public void displayErrors() {
        // Pass and stores the signup form's inputs into corresponding variables
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();
        if (!validUserNameLength(userName)) {
            setError("Username must be less than " + maxUsernameLength + " characters");
        } else if (!validPasswordLength(passWordText)) {
            setError("Password must be less than " + maxPasswordLength + " characters");
        } else if (!validDisplayNameLength(displayName)) {
            setError("Display name must be less than " + maxDisplayNameLength + " characters");
        } else if (!IsNotEmpty(userName)) {
            setError("You must enter a user name");
        } else if (!IsNotEmpty(displayName)) {
            setError("You must enter a display name");
        } else if (!IsNotEmpty(passWordText)) {
            setError("You must enter a password");
        } else if (!passwordsMatch(passWordText, passwordTwo)) {
            setError("Passwords do not match");
        }
    }

    public void setError(String notification){
        error.setText(notification);
    }
}
