package muck.client.controllers;
/*Sign in - Issue 11 */



import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import muck.client.App;
import org.mindrot.jbcrypt.*;
import muck.client.AvatarController;

import java.io.IOException;


public class SignInController{
    @FXML
    Label error;

    @FXML
    PasswordField password;

    @FXML
    TextField username;

    @FXML
    Button signUp;

    @FXML
    Button signIn;

    // Todo add logic to
    @FXML
    protected void signIn(MouseEvent event) throws IOException {
        String hashed = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
        String uName = username.getText();

        if(validateSignIn(uName, hashed)){
            // forward on to next scene
            passToNextScene(event, uName);
        }

    }

    // TODO: Sign in validation method - implement functionality

    public boolean validateSignIn(String username, String password){
        // Check that user exists in database
        if(!userExists(username) || !passwordMatches(username, password)) {
            // Handle NoUserExists
            error.setText("User Name or Password are Incorrect");
            return false;
        } else {
            return true;
        }
    }

    //TODO: User validation method - implement functionality
    public boolean userExists(String username){
        // check database for user
        return true;
    }

    //TODO: Password validation method - implement functionality
    public boolean passwordMatches(String username, String password){
        //match password to user from database
        return true;
    }

    // TODO: Add Pass to SignUp Display
    public void signUp() throws IOException{
        App a = new App();
        a.changeScene("/fxml/SignUp.fxml");
    }

    // TODO: Add Pass to Avatar Selection Display - work out how to pass data between scenes
    public static void passToNextScene(MouseEvent event, String username) throws IOException{
        // Currently needs to go to muck via avatar controller until users can be brought from database
        AvatarController nextScene = new AvatarController();
        nextScene.avatarCreation(event, username);
    }

}

