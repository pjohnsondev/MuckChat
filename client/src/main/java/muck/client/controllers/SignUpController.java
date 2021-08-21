package muck.client.controllers;
/*Sign Up - Issue 31 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.*;
// import org.mindrot.jbcrypt.*;



public class SignUpController {
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

        if (validateSignUp()) {
            actiontarget.setText("Congratulations!\n\r" +
                    "you have now joined muck");
        }

    }

    // TODO: Sign Up validation method - implement functionality
    // Limit username and password length on frontend
    public boolean validateSignUp(){ return true;}

    // TODO: User name Available method - implement functionality
    public boolean userNameAvailable(){ return true;}

    // TODO:


}