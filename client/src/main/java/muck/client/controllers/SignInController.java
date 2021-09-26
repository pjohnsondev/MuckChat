package muck.client.controllers;
/*Sign in - Issue 11 */



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import muck.client.*;
import muck.client.components.ActiveUser;

import java.io.IOException;
import java.util.Objects;


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

    private static Stage stage = new Stage();

    // Use this method from external classes to open the gameplay window. Added by CA 14 Aug
    public static void constructor() {
        try {
            stage.setResizable(true);
            Parent root = FXMLLoader.load(Objects.requireNonNull(SignInController.class.getResource("/fxml/SignIn.fxml")));
            stage.setTitle("MUCK 2021");
            stage.setScene(new Scene(root));
            stage.show();
            stage.toFront();
        } catch (IOException e) {
            System.out.println("Can't find primary stage FXML file");
        }
    }

    @FXML
    protected void signIn(MouseEvent event) throws Exception {
        String passwordText = password.getText();
        String uName = username.getText();


        if(isNotEmpty(username.getText(), password.getText())){
            boolean dataSent = sendData(uName, passwordText);
            boolean success = false;
            Thread.sleep(500);
            if(dataSent){
                success = success();
            }
            if(success){
                // forward on to next scene
                passToNextScene(event, uName);
            }
        }
    }


    // TODO: Add Pass to SignUp Display
    public void signUp() throws IOException{
        App a = new App();
        a.changeScene("/fxml/SignUp.fxml");
    }

    // TODO: Add Pass to Avatar Selection Display - work out how to pass data between scenes
    public static void passToNextScene(MouseEvent event, String username) {
        AvatarController nextScene = new AvatarController();
        hide();
        nextScene.avatarCreation(event, username);
    }

    public boolean sendData(String userName, String passwordText){
        try {
            sendToMuck(userName, passwordText);
            setError("Data sent");
            return true;
        } catch (Exception ex) {
            setError(String.format("Unable to create new user: %s.", userName));
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean isNotEmpty(String username, String password){
        if(username.isEmpty()){
            error.setText("You must enter a user name");
            return false;
        } else if(password.isEmpty()){
            setError("You must enter your password");
            return false;
        } else {
            return true;
        }
    }

    public boolean success(){
        try{
            if(ActiveUser.getInstance().getServerMessage().equals("Login Successful")){
                return true;
            } else {
                setError(ActiveUser.getInstance().getServerMessage());
                return false;
            }
        } catch (NullPointerException ex){
            setError("There was no response from the server. Please try again");
            return false;
        }
    }

    public void setError(String notification){
        error.setText(notification);
    }

    public void sendToMuck(String username, String password){
        MuckClient.getINSTANCE().login(username, password);
    };

    public static void hide(){
        stage.hide();
    }

}

