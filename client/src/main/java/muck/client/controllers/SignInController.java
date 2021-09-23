package muck.client.controllers;
/*Sign in - Issue 11 */



import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import muck.client.App;
import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.core.LoginResponse;
import muck.core.ResponseCodes;
import muck.core.SignupResponse;
import muck.core.observer.ObservableSubject;
import muck.core.observer.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.*;
import muck.client.AvatarController;

import java.io.IOException;


public class SignInController implements Observer<LoginResponse> {

    private static final Logger logger = LogManager.getLogger(App.class);

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
    public static void passToNextScene(MouseEvent event, String username) throws IOException{
        // Currently needs to go to muck via avatar controller until users can be brought from database

        // This will likely need to be updated to pass in the display name from the database to be consistent
        // with the sign up class otherwise only username will be passed in both.
        AvatarController nextScene = new AvatarController();
        App.hideStage();
        nextScene.avatarCreation(event, username);
    }

    public boolean sendData(String userName, String passwordText){
        try {
            // subscribe this class to notifications regarding registration results
            MuckClient.getINSTANCE().loginResponseNotifier.register(this);
            MuckClient.getINSTANCE().login(userName, passwordText);
            error.setText("Data Sent");
            return true;
        } catch (Exception ex) {
            error.setText("error");
            throw new RuntimeException(String.format("Unable to create new user: %s.", userName));
        }
    }

    public boolean isNotEmpty(String password, String username){
        if(username.isEmpty()){
            error.setText("You must enter a user name");
            return false;
        } else if(password.isEmpty()){
            error.setText("You must enter your password");
            return false;
        } else {
            return true;
        }
    }

    public boolean success(){
        if(ActiveUser.getInstance().getServerMessage().equals("Login Successful")){
            error.setText("Login successful");
            return true;
        } else {
            error.setText(ActiveUser.getInstance().getServerMessage());
            return false;
        }
    }

    /**
     * This method gets the result of the signin request to the server.
     * Once the result is received, this method unsubscribes to the notifications.
     * @param loginResponse
     */
    @Override
    public void update(LoginResponse loginResponse) {
        if(loginResponse.getResultCode() == ResponseCodes.OK) {
            logger.info("Login was successful");
            // TODO: handle login success
        } else if(loginResponse.getResultCode() == ResponseCodes.UNAUTHORISED) {
            logger.info("Invalid username and/or password");
            // TODO: handle login failure
        } else if(loginResponse.getResultCode() == ResponseCodes.INVALID_INPUT) {
            logger.info("Invalid input provided to login");
            // TODO: handle invalid input supplied to login
        } else if(loginResponse.getResultCode() == ResponseCodes.DUPLICATE_LOGIN) {
            logger.info("You are already logged in");
            // TODO: handle existing user;
        } else if(loginResponse.getResultCode() == ResponseCodes.SERVER_ERROR) {
            logger.info("Server error occurred while logging in up");
            // TODO: handle server error
        }
        MuckClient.getINSTANCE().signupResponseNotifier.unRegister(this);
    }
}

