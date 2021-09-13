package muck.client.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TicTacToe extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(TicTacToe.class.getResource("/fxml/TicTacToe.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            scene.setRoot(root);
            Stage stage = new Stage();
            stage.setTitle("Muck Tic Tac Toe");
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