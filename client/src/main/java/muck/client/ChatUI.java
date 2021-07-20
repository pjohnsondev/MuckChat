package muck.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ChatUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set up the window and grid
        primaryStage.setTitle("Muck");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 500);
        grid.setVgap(5);
        grid.setHgap(5);
        Text scenetitle = new Text("Welcome To Muck");
        scenetitle.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));
        grid.add(scenetitle, 1, 0, 2, 10);

        //Fields
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 20, 1, 10);
        userTextField.setText("Type your message here...");
        TextArea chatArea = new TextArea();
        chatArea.setDisable(true);
        grid.add(chatArea, 1, 10, 1, 10);

        //Actually run the damn thing
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}