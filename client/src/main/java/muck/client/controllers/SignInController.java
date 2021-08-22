package muck.client.controllers;
/*Sign in - Issue 11 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.*;
import muck.client.controllers.SignUpController;



public class SignInController {
    @FXML
    Label error;

    @FXML
    PasswordField password;

    @FXML
    TextField username;

    // Todo add logic to
    @FXML
    protected void signIn(ActionEvent event) {
        String hashed = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
        String uName = username.getText();

        if(validateSignIn()){
            error.setText("Congratulations!\n\r" +
                    "User Name and Password Match");
        }

    }

    // TODO: Sign in validation method - implement functionality

    public boolean validateSignIn(){
        // Check that user exists in database
        if(!userExists() || !passwordMatches()) {
            // Handle NoUserExists
            error.setText("User Name or Password are Incorrect");
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

    public void signUp(){
    }



}

