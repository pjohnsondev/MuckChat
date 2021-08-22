package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import muck.client.AvatarController;
import org.mindrot.jbcrypt.*;
import muck.server.models.models.*;
import muck.client.AvatarController;

import java.security.InvalidParameterException;
import java.sql.SQLException;


public class SignUpController {
    private int maxPasswordLength = 10;

    @FXML
    private Text actiontarget;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField userName;

    // Todo add logic to
    @FXML
    protected void signUp(ActionEvent event) throws SQLException{
        String hashed = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
        String uName = userName.getText();

        // TODO call validate username and password length

        // TODO: Validate the sign up
        if (validateSignUp()) {
            addUser(uName, hashed);
            if (returnUser(uName)){
                actiontarget.setText("Added to Database");
            }
        }

    }

    // TODO: Sign Up validation method - implement functionality
    public boolean validateSignUp(){ return true;}

    // TODO: User name Available method - implement functionality
    public boolean userNameAvailable(){ return true;}

    // TODO: Password Length validation method
    public boolean validPassword(){
        return true;
    }

    // TODO: Username Length Validation method
    public boolean validUserNameLength(String password){

        if (password.length() > maxPasswordLength){
            return false;
        } else {
            return true;
        }
    }

    //TODO: Add User to Database
    public void addUser(String uName, String password) throws SQLException {
        User user = new User();
        try{
            user.registerNewUser(uName, password);
        } catch (SQLException se){
            System.out.println(se);
        }
    }
    //TODO: Add User to Database
    public boolean returnUser(String uName) throws SQLException {
        User user = new User();
        try{
            return user.findUserByUsername(uName);
        } catch (SQLException se){
            System.out.println(se);
            return false;
        }
    }

    // TODO: Add Front End Logic
    public static void signUpForm(){
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
    public void passToAvatar(String username){
        AvatarController.avatarCreation(username);
    }


}