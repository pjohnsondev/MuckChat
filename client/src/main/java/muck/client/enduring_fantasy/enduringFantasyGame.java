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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import muck.client.GameMap;

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