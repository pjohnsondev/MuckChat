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
            actiontarget.setText("User name: " + uName +
                    "\r\n Your Hashed Password:\r\n"+ hashed);
        }

    }

    // TODO: Sign in validation method - implement functionality

    public boolean validateSignIn(){
        return true;
    }

    //TODO:



}