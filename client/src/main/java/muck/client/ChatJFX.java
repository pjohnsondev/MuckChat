package muck.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ChatJFX extends Application {

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