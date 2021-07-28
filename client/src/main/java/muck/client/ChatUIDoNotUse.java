// Obselete Class if we are not using SceneBuilder and fxml
//DO NOT USE THIS! IGNORE

package muck.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ChatUIDoNotUse extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set up the window and grid
        primaryStage.setTitle("Muck");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 600, 400);
        grid.setVgap(5);
        grid.setHgap(5);
        Text scenetitle = new Text("Welcome To Muck");
        scenetitle.setFont(Font.font("Comic Sans", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 2, 1, 1);
        grid.setPadding(new Insets(10));


        //Fields
        TextField userTextField = new TextField();
        grid.add(userTextField, 0, 4, 4, 1);
        userTextField.setPromptText("Type your message here...");
        TextArea chatArea = new TextArea();
        chatArea.setDisable(true);
        grid.add(chatArea, 0,3, 6, 1);
        Button button = new Button("Enter");
        grid.add(button, 4, 4, 2, 1);
        button.setAlignment(Pos.CENTER);
        //Actually run the damn thing
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
