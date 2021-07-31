package muck.client.controllers;
/*Sign in - Issue 11 */

//public class SignInController {
//
////TODO: Sign in validation method - implement functionality
//
//
//
//// Create a constructor to allow access to class.
//public SignInController(){
//
//        }
//
//        public String validateSignIn(){
//            return "True";
//        }
//    }


        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.text.Text;
        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.animation.ScaleTransition;
        import javafx.scene.control.*;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.paint.ImagePattern;
        import javafx.scene.shape.Circle;
        import javafx.scene.text.Text;
        import javafx.util.Duration;
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

    public boolean validateSignIn(){
        return true;
    }



}