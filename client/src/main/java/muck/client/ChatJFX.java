package muck.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This UI was built in SceneBuilder and incorporates a FXML file. This class is the launcher of the chat window */

public class ChatJFX extends Application {

    @Override
        public void start(Stage primaryStage) throws Exception {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MuckWindow.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setRoot(root);
                Stage stage = new Stage();
                stage.setMaxWidth(800);
                stage.setMaxHeight(680);
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