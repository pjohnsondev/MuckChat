package muck.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignIn extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignIn.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 350);
            scene.setRoot(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Sign In");
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


