package muck.client.controllers;
/*Sign Up - Issue 31 */



import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import muck.client.AvatarController;
import org.mindrot.jbcrypt.*;

import java.io.IOException;
import java.sql.SQLException;


public class SignUpController {
    private int maxPasswordLength = 10;
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

    // Todo add logic to
    @FXML
    protected void signUp(MouseEvent event) throws SQLException, IOException {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();

        if(isNotEmpty(passWordText, userName, displayName)){
            // Validate the sign up
            validateSignUp(event, displayName, userName, passWordText, passwordTwo);
        }
    }

    // TODO: Sign Up validation method - implement functionality
    public void validateSignUp(MouseEvent event, String displayname, String username, String password, String passwordtwo ) throws IOException {
        if (validUserNameLength(username) && validPasswordLength(password) &&
                userNameAvailable(username) && passwordsMatch(password, passwordtwo)) {

            // Hash password for security
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            // Add user to database
            // addUser(uName, displayName, hashed);

            // retrieve user id from database and pass to the next scene

            // Pass to next scene
            passToAvatar(event, username);

            //TODO: Fix error display as it currently gets frozen at one error
        } else if (!passwordsMatch(password, passwordtwo)) {
            error.setText("Passwords don't match");
        } else if (!validUserNameLength(username)) {
            error.setText("Username must be less than " + maxUsernameLength + " characters");
        } else if (!validPasswordLength(password)){
            error.setText("Password must be less than " + maxPasswordLength + " characters");
        } else {
            error.setText("Sorry that user name is taken");
        }
    }

    // TODO: User name Available method - implement functionality
    public boolean userNameAvailable(String username){
        // Check that username is available
        return true;
    }

    // TODO: Username Length Validation method
    public boolean validUserNameLength(String username){
        if (username.length() > maxUsernameLength){
            return false;
        } else {
            return true;
        }
    }

    // TODO: Password Length Validation method
    public boolean validPasswordLength(String password){
        if (password.length() > maxPasswordLength){
            return false;
        } else {
            return true;
        }
    }

    // TODO: Passwords match Validation method
    public boolean passwordsMatch(String password, String passwordtwo){
        if(password.equals(passwordtwo)){
            return true;
        }
        return false;
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

    public boolean isNotEmpty(String password, String username, String displayname){
        if(username.length() < 1){
            error.setText("You must enter a user name");
            return false;
        } else if (password.length() < 1){
            error.setText("You must enter your password");
            return false;
        } else if (displayname.length() < 1){
            error.setText("You must a display name");
            return false;
        } else {
            return true;
        }
    }


    // TODO: Add Pass to Avatar Selection Display
    public void passToAvatar(MouseEvent event, String username) throws IOException{
        AvatarController nextScene = new AvatarController();
        nextScene.avatarCreation(event, username);
    }
}