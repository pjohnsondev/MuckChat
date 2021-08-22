package muck.client.controllers;
/*Sign in - Issue 11 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import muck.client.App;
import muck.client.AvatarController;
import org.mindrot.jbcrypt.*;
import muck.client.controllers.SignUpController;

import java.io.IOException;


public class SignInController {
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
    protected void signIn(ActionEvent event) throws IOException {
        String hashed = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
        String uName = username.getText();


        if(validateSignIn()){
            error.setText("Congratulations!\n\r" +
                    "User Name and Password Match");
            App a = new App();
            a.changeScene("/fxml/Avatar.fxml");
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

    // TODO: Add Front End Logic
    public static void signInForm(){
        try {
            FXMLLoader loader = new FXMLLoader(SignUpController.class.getResource("/fxml/SignIn.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 350);
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

    // TODO: Add Pass to SignUp Display
    public void signUp() throws IOException{
        App a = new App();
        a.changeScene("/fxml/SignUp.fxml");
    }



}

