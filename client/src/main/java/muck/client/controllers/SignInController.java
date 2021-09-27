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

    /**
     * signIn method passes input to validation methods and attempts to signin
     * player. If successfull, passes to next scene
     * @param event the mouse event that triggered sign in
     * @throws InterruptedException is sleep method causes exception
     */
    @FXML
    protected void signIn(MouseEvent event) throws InterruptedException {
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
                passToNextScene(event, uName);
            }
        }
    }


    /**
     * signUp method passes to the signUp scene if button is pressed
     * @throws IOException
     */
    public void signUp() throws IOException{
        App app = new App();
        app.changeScene("/fxml/SignUp.fxml");
    }

    /**
     * passToNextScene method passes to the next scene
     * @param event The mouse event that triggered sign in
     * @param username the provided username
     */
    public static void passToNextScene(MouseEvent event, String username) {
        AvatarController nextScene = new AvatarController();
        App.hideStage();
        nextScene.avatarCreation(event, username);
    }

    /**
     * isNotEmpty method checks that provided input field is not empty
     * @param username the username field text
     * @param password the password field text
     * @return true is neither password or username fields are empty
     */
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

    /**
     * sendData method sends the provided data to the server via MuckClient
     * @param userName the provided username
     * @param passwordText the provided password
     * @return true if no error occurs sending data to the server and throws a runtime exception if an error occurs
     */
    public boolean sendData(String userName, String passwordText) {
        try {
            MuckClient.getINSTANCE().login(userName, passwordText);
            setError("Data Sent");
            return true;
        } catch (Exception ex) {
            setError(String.format("Unable to create new user: %s.", userName));
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * success method checks that the signin has succeeded and the server has responded
      * @return true if server has responded with success or false if no success response
     */
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

