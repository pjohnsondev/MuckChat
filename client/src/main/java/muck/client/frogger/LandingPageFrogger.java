package muck.client.frogger;

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

public class LandingPageFrogger extends Node {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView titleView;
    private static final Image TITLE = new Image("/images/frogger/Frogger_Logo.png");

    final Button playButton = new Button("PLAY");
    final Button instructionsButton = new Button("INSTRUCTIONS");
    final Button exitButton = new Button("EXIT GAME");

    final GridPane grid = new GridPane();

    public LandingPageFrogger(BorderPane stage, Canvas canvas) {

        gc = canvas.getGraphicsContext2D();

        var root = new Pane();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> bground(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH/6);
            grid.getColumnConstraints().add(column);
        }

        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.setVgap(20);
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
        titleView.setFitWidth(720);
        titleView.setFitHeight(190);
        titleView.setImage(TITLE);
        grid.add(titleView, 0,5,2,5);

        // Play button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #15f51f");
        grid.add(playButton, 1, 11, 2, 3);

        // Instructions
        instructionsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        instructionsButton.setStyle("-fx-background-color: #15f51f");
        grid.add(instructionsButton, 1, 14, 2, 3);

        // Exit button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #15f51f");
        grid.add(exitButton, 1, 17, 2, 3);

        stage.getChildren().add(grid);

        // Create on-click events
        playButton.setOnAction(event -> {

            Label secondLabel = new Label("Welcome!");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            Scene secondScene = new Scene(secondaryLayout, WIDTH, HEIGHT);

            // Lets crack open a new window
            Stage newWindow = new Stage();
            newWindow.setTitle("Frogger");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);

            Frogger frg = new Frogger();
            try {
                frg.start(newWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Set position of second window, related to primary window.
            newWindow.show();
        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });
    }

    private void bground(GraphicsContext gc) {
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);
    }
}
