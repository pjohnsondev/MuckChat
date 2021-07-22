package muck.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.EventHandler;


public class ChatJFX extends Application {

    @FXML
    TextArea groupChatBox;

    @FXML
    TextField messageBox;

    @FXML
    Button enter;

    String message;


    @Override
        public void start(Stage primaryStage) throws Exception {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MuckChat.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setRoot(root);
                Stage stage = new Stage();
                stage.setTitle("Muck 2021");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();

            }
    }

            public static void main (String[]args){
                launch(args);
            }
        }