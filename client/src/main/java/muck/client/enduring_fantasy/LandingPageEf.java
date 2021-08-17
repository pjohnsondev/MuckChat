package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class LandingPageEf extends Node {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    final Button playButton = new Button("PLAY");
    final Button instructionsButton = new Button("How To Play");
    final Button exitButton = new Button("Exit Game");

    final GridPane grid = new GridPane();


    public LandingPageEf(BorderPane stage, Canvas canvas) {



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

        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #ff0000");
        grid.add(playButton, 10, 8, 10, 5);

        instructionsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        instructionsButton.setStyle("-fx-background-color: #ff0000");
        grid.add(instructionsButton, 10, 18, 20, 5);

        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #ff0000");
        grid.add(exitButton, 10, 28, 20, 5);

        stage.getChildren().add(grid);

    }


}
