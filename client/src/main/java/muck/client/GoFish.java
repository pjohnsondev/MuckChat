package muck.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GoFish extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(GoFish.class.getResource("/fxml/cards_game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1130, 800);
            scene.setRoot(root);
            Stage stage = new Stage();
            stage.setTitle("Go Fish");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}

