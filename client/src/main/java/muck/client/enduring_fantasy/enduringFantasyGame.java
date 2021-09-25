package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import muck.client.GameMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

//TODO the is the class to run the actual game
public class enduringFantasyGame extends Node {



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