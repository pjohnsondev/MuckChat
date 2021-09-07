package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import muck.client.GameMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

//TODO the is the class to run the actual game
public class enduringFantasyGame extends Node implements Initializable {

    //Buttons, TextField etc. from fxml file.

    @FXML // fx:id="atk_button" The player attack button
    private Button atk_button;

    @FXML // fx:id="mag_button" The player magic button
    private Button mag_button;

    @FXML // fx:id="itm_button" The player item button
    private Button itm_button;

    @FXML // fx:id="def_button" The player defend button
    private Button def_button;

    @FXML // fx:id="player_char_1_name" Player character 1 Name
    private TextField player_char_1_name;

    @FXML // fx:id="player_char_2_name" Player character 2 Name
    private TextField player_char_2_name;

    @FXML // fx:id="player_char_3_name" Player character 3 Name
    private TextField player_char_3_name;

    @FXML // fx:id="player_char_4_name" Player character 4 Name
    private TextField player_char_4_name;

    @FXML // fx:id="player_char_1_HP" Player character 1 HP
    private TextField player_char_1_HP;

    @FXML // fx:id="player_char_2_HP" Player character 2 HP
    private TextField player_char_2_HP;

    @FXML // fx:id="player_char_3_HP" Player character 3 HP
    private TextField player_char_3_HP;

    @FXML // fx:id="player_char_4_HP" Player character 4 HP
    private TextField player_char_4_HP;

    @FXML // fx:id="player_char_1_MP" Player character 1 MP
    private TextField player_char_1_MP;

    @FXML // fx:id="player_char_2_MP" Player character 2 MP
    private TextField player_char_2_MP;

    @FXML // fx:id="player_char_3_MP" Player character 3 MP
    private TextField player_char_3_MP;

    @FXML // fx:id="player_char_4_MP" Player character 4 MP
    private TextField player_char_4_MP;

    @FXML // fx:id="e_list_1" The enemy #1
    private TextField e_list_1;

    @FXML // fx:id="e_list_2" The enemy #2
    private TextField e_list_2;

    @FXML // fx:id="e_list_3" The enemy #3
    private TextField e_list_3;

    @FXML // fx:id="e_list_4" The enemy #4
    private TextField e_list_4;

    @Override
    public void initialize (URL location, ResourceBundle resources) {

        // I Think we launch the game from here. I've just been going through the
        // muck controller code and working backwards.

    }

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView titleView;


    final Button exitButton = new Button("BACK");

    final GridPane grid = new GridPane();


    public enduringFantasyGame(BorderPane stage, Canvas canvas) {

        ImageView titleView = new ImageView();
        titleView.setFitWidth(480);
        titleView.setFitHeight(130);


        //set battle background image
        Image image = new Image("resources/images/EnduringFantasy/grass_battle_bg.png");
        BackgroundImage backgroundimage = new BackgroundImage(image,
                                                BackgroundRepeat.NO_REPEAT,
                                                BackgroundRepeat.NO_REPEAT,
                                                BackgroundPosition.DEFAULT,
                                                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundimage);



        grid.add(titleView, 1, 50, 3, 5);


        gc = canvas.getGraphicsContext2D();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #000dff");
        grid.add(exitButton, 0, 0, 20, 5);

        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);


        stage.getChildren().add(grid);

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
        });
    }

}