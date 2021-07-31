package muck.client.controllers;
/*Sign in - Issue 11 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.*;



public class SignInController {
    @FXML
    private Text actiontarget;

    @FXML
    PasswordField passwordField;

    @FXML
    TextField userName;

    // Todo add logic to
    @FXML
    protected void signIn(ActionEvent event) {
        String hashed = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
        String uName = userName.getText();

        if(validateSignIn()){
            actiontarget.setText("Congratulations!\n\r" +
                    "User Name and Password Match");
        }

    }

    // TODO: Sign in validation method - implement functionality

    public boolean validateSignIn(){
        // Check that user exists in database
        if(!userExists() || !passwordMatches()) {
            // Handle NoUserExists
            actiontarget.setText("User Name or Password are Incorrect");
            return false;
        } else {
            return true;
        }
    }

    //TODO: User validation method - implement functionality
    public boolean userExists(){
        return true;
    }

    //TODO: Password validation method - implement functionality
    public boolean passwordMatches(){
        return true;
    }




}