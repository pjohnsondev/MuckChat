package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import muck.client.GameMap;

import java.io.IOException;


public class LandingPageEf extends Node {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView titleView;
    private static final Image TITLE = new Image("/images/EnduringFantasy/Enduring_Fantasy_Logo.png");

    final Button playButton = new Button("PLAY");
    final Button instructionsButton = new Button("How To Play");
    final Button exitButton = new Button("Exit Game");

    final GridPane grid = new GridPane();
    private BorderPane gamePane;

    public LandingPageEf(BorderPane stage, Canvas canvas) {
        gamePane = stage;
        gc = canvas.getGraphicsContext2D();

        var root = new Pane();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> bground(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH/7);
            grid.getColumnConstraints().add(column);
        }

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(20);

        ColumnConstraints cc;
        cc = new ColumnConstraints();
        cc.setMinWidth(GridPane.USE_PREF_SIZE);
        cc.setHgrow(Priority.ALWAYS);
        cc.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(cc);

        RowConstraints rc;
        rc = new RowConstraints();
        rc.setMinHeight(GridPane.USE_PREF_SIZE);
        rc.setVgrow(Priority.ALWAYS);
        rc.setValignment(VPos.CENTER);
        grid.getRowConstraints().add(rc);

        // Title
        ImageView titleView = new ImageView();
        titleView.setFitWidth(780);
        titleView.setFitHeight(190);
        titleView.setImage(TITLE);
        grid.add(titleView, 0,15,3,5);

        // Play button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color:  #85303B");
        grid.add(playButton, 1, 25, 3, 5);

        // Instructions
        instructionsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        instructionsButton.setStyle("-fx-background-color: #cebfbf");
        grid.add(instructionsButton, 1, 30, 3, 5);

        // Exit button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #cebfbf");
        grid.add(exitButton, 1, 35, 3, 5);

        stage.getChildren().add(grid);

        // Create on-click events
        playButton.setOnAction(event -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EnduringFantasyGame.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.initModality(Modality.WINDOW_MODAL);

                stage1.setTitle("Enduring Fantasy");
                stage1.setScene(new Scene(root1));
                stage1.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas, gamePane);
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(559); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(200);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });
    }


    /***
     * Function name: bground
     * Purpose: To set our background
     * Arguments: GraphicContext gc
     * Return: void
     ***/
    private void bground(GraphicsContext gc) {
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);
    }
}
