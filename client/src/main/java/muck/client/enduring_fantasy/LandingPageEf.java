package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
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
import javafx.util.Duration;
import muck.client.GameMap;


public class LandingPageEf extends Node {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView titleView;
    private static final Image TITLE = new Image("/images/Enduring_Fantasy_Logo.png");

    final Button playButton = new Button("PLAY");
    final Button instructionsButton = new Button("How To Play");
    final Button exitButton = new Button("Exit Game");

    final GridPane grid = new GridPane();

    public LandingPageEf(BorderPane stage, Canvas canvas) {

        gc = canvas.getGraphicsContext2D();

        var root = new Pane();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> bground(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH/6);
            grid.getColumnConstraints().add(column);
        }

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

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
        titleView.setFitWidth(480);
        titleView.setFitHeight(130);
        titleView.setImage(TITLE);
        grid.add(titleView, 1,50,3,5);

        // Play button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #ff0000");
        grid.add(playButton, 10, 8, 10, 5);

        // Instructions
        instructionsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        instructionsButton.setStyle("-fx-background-color: #ff0000");
        grid.add(instructionsButton, 10, 18, 20, 5);

        // Exit button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #ff0000");
        grid.add(exitButton, 10, 28, 20, 5);

        stage.getChildren().add(grid);

        // Create on-click events
        playButton.setOnAction(event -> {

            Label secondLabel = new Label("Welcome!");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            Scene secondScene = new Scene(secondaryLayout, WIDTH, HEIGHT);

            // Lets crack open a new window
            Stage newWindow = new Stage();
            newWindow.setTitle("Enduring Fantasy");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Set position of second window, related to primary window.
            newWindow.show();
        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas);

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