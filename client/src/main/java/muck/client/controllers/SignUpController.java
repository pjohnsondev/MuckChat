package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.*;
// import org.mindrot.jbcrypt.*;



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
    protected void signUp(ActionEvent event) {
        // String hashed = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
        // String uName = userName.getText();

        // TODO call validate username and password length
        
        // if username or password don't match regex 
        // alert error
        // actiontarget.setText("Username and Password can only be a maximum of x characters");

        // Validate the sign up
        if (validateSignUp()) {
            // 
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
    public void addUser(){
        
    }

}