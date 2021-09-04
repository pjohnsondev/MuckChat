package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import muck.client.AvatarController;
import muck.core.models.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.*;
import muck.client.App;

import java.io.IOException;
import java.sql.SQLException;


public class SignUpController {
    private int maxPasswordLength = 10;
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

    // Todo add logic to
    @FXML
    protected void signUp(MouseEvent event) throws SQLException, IOException {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();

        // Validate the sign up
        boolean validated = validateSignUp(event, displayName, userName, passWordText, passwordTwo);
        if (validated) {
            try {
                UserModel.getInstance().registerNewUser(userName, passWordText);
                error.setText("New muck user created" + userName);
            } catch (Exception ex) {
                error.setText("Unable to create new user: {}" + userName);

                throw new RuntimeException(String.format("Unable to create new user: %s.", userName));
            }
        }
    }

    // TODO: Sign Up validation method - implement functionality
    public boolean validateSignUp(MouseEvent event, String displayname, String username, String password, String passwordtwo) throws IOException {
        if (validUserNameLength(username) && validPasswordLength(password) && validDisplayNameLength(displayname) &&
                userNameAvailable(username) && passwordsMatch(password, passwordtwo) && passwordIsNotEmpty(password) && passwordIsNotEmpty(passwordtwo) &&
                userNameIsNotEmpty(username) && displayNameIsNotEmpty(displayname) ) {

            // Hash password for security
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            // Add user to database

            // addUser(uName, displayName, hashed);

            // retrieve user id from database and pass to the next scene


            // Pass display name to the next scene
            // Pass to next scene
            passToAvatar(event, username, displayname);
            return true;

        } else {
            // Catches and displays the corresponding error from the display method.
            displayErrors();
            return  false;

        }
    }

    // TODO: User name Available method - implement functionality
    public boolean userNameAvailable(String username) {
        // Check that username is available
        return true;
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
//            error.setText("Passwords do not match");
            return true;
        } else {
            return false;
        }
    }

    //TODO: Add User to Database - Add functionality
    public void addUser(String uName, String password) throws SQLException {
//        This will need to be replaced as we wont directly communicate with the server
//        User user = new User();
//        try{
//            user.registerNewUser(uName, password);
//        } catch (SQLException se){
//            System.out.println(se);
//        }
    }

    //TODO: Retrieve user from Database
    public void returnUser(String uName) throws SQLException {
//        User user = new User();
//        try{
//            return user.findUserByUsername(uName);
//        } catch (SQLException se){
//            System.out.println(se);
//            return false;
//        }
    }
    // Password not empty validation
    public boolean passwordIsNotEmpty(String password) {
        if (password.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    // Username not empty validation
    public boolean userNameIsNotEmpty(String username) {
        if (username.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    // Display name not empty validation
    public boolean displayNameIsNotEmpty(String displayname) {
        if (displayname.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }



    // TODO: Add Pass to Avatar Selection Display
    public void passToAvatar(MouseEvent event, String username, String displayName) throws IOException{
        AvatarController nextScene = new AvatarController();
        nextScene.avatarCreation(event, username, displayName);
    }
    // display errors method for signup screen validations
    public void displayErrors() {
        // Pass and stores the signup form's inputs into corresponding variables
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();
        if (!validUserNameLength(userName)) {
            error.setText("Username must be less than " + maxUsernameLength + " characters");
        } else if (!validPasswordLength(passWordText)) {
            error.setText("Password must be less than " + maxPasswordLength + " characters");
        } else if (!validDisplayNameLength(displayName)) {
            error.setText("Display name must be less than " + maxDisplayNameLength + " characters");
        } else if (!userNameIsNotEmpty(userName)) {
            error.setText("You must enter a user name");
        } else if (!displayNameIsNotEmpty(displayName)) {
            error.setText("You must enter a display name");
        } else if (!passwordIsNotEmpty(passWordText)) {
            error.setText("You must enter a password");
        } else if (!passwordsMatch(passWordText, passwordTwo)) {
            error.setText("Passwords do not match");
        }
    }
}