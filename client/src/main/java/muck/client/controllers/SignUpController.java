package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import muck.client.App;
import muck.core.models.models.UserModel;
import org.mindrot.jbcrypt.BCrypt;

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
    protected void signUp(ActionEvent event) throws SQLException, IOException {
        String passWordText = password.getText();
        String userName = username.getText();
        String displayName = displayname.getText();
        String passwordTwo = passwordtwo.getText();

        // Validate the sign up
        boolean validated = validateSignUp(displayName, userName, passWordText, passwordTwo);
        if (validated) {
            try {
                UserModel.getInstance().registerNewUser(userName, passWordText);
                error.setText("New muck user created"+ userName);
            } catch (Exception ex) {
                error.setText("Unable to create new user: {}"+ userName);

                throw new RuntimeException(String.format("Unable to create new user: %s.", userName));
            }
        }
    }

    // TODO: Sign Up validation method - implement functionality
    public boolean validateSignUp(String displayname, String username, String password, String passwordtwo) throws IOException {
        boolean validated = false;
        if (validUserNameLength(username) && validPasswordLength(password) &&
                userNameAvailable(username) && passwordsMatch(password, passwordtwo)) {

            // Hash password for security
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            // Add user to database
            // addUser(uName, displayName, hashed);

            // retrieve user id from database and pass to the next scene

            // Pass to next scene
            passToAvatar();
            validated = true;

        } else if (!passwordsMatch(password, passwordtwo)) {
            error.setText("Passwords don't match");
        } else if (!validUserNameLength(username)) {
            error.setText("Username must be less than " + maxUsernameLength + " characters");
        } else if (!validPasswordLength(password)) {
            error.setText("Password must be less than " + maxPasswordLength + " characters");
        } else {
            error.setText("Sorry that user name is taken");
        }
        return validated;
    }

    // TODO: User name Available method - implement functionality
    public boolean userNameAvailable(String username) {
        // Check that username is available
        return true;
    }

    // TODO: Username Length Validation method
    public boolean validUserNameLength(String username) {
        if (username.length() < maxUsernameLength) {
            return false;
        } else {
            return true;
        }
    }

    // TODO: Password Length Validation method
    public boolean validPasswordLength(String password) {
        if (password.length() > maxPasswordLength) {
            return false;
        } else {
            return true;
        }
    }

    // TODO: Passwords match Validation method
    public boolean passwordsMatch(String password, String passwordtwo) {
        if (password != passwordtwo) {
            return false;
        } else {
            return true;
        }
    }


    //TODO: Add User to Database - Add functionality
    public void addUser(String uName, String password) throws SQLException {
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

    // TODO: Add Front End Logic
    public static void signUpForm() {
        try {
            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("/fxml/SignUp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.setRoot(root);
            scene.getStylesheets().add(SignUpController.class.getResource("/css/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Join Muck");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Add Pass to Avatar Selection Display
    public void passToAvatar() throws IOException {
        App a = new App();
        a.changeScene("/fxml/Avatar.fxml");
    }

}