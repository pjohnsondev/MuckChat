package muck.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import muck.client.AvatarController;

/*
This class executes the Avatar FXML application.
 */

public class Avatar extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Avatar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setRoot(root);
            /* The below doesn't seem tp work. But why?
            //Event listener for enter press for Avatar screen
            scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if(event.getCode().equals(KeyCode.ENTER)) {
                  AvatarController.submit();
              };
            });*/
            Stage stage = new Stage();
            stage.setTitle("Muck 2021");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
