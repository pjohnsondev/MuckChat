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


    public static void constructor() {
        try {
            Stage stage = new Stage();
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
        boolean fieldsAreNotEmpty = isNotEmpty(username.getText(), password.getText());

        if(fieldsAreNotEmpty){
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
        App.hideStage();
        nextScene.avatarCreation(event, username);
    }

    public boolean isNotEmpty(String username, String password){
        if(username.isEmpty()){
            setError("You must enter a user name");
            return false;
        } else if(password.isEmpty()){
            setError("You must enter your password");
            return false;
        } else {
            return true;
        }
    }

    public boolean sendData(String userName, String passwordText){
        try {
            MuckClient.getINSTANCE().login(userName, passwordText);
            setError("Data Sent");
            return true;
        } catch (Exception ex) {
            setError(String.format("Unable to create new user: %s.", userName));
            throw new RuntimeException(ex.getMessage());
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

}

