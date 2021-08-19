package muck.client.space_invaders;

import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import muck.client.GameMap;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class LandingPage extends Node {

    List<StarBackground> stars;

    private ImageView titleView;
    private static final Image TITLE = new Image("/images/space-invaders-title.png");

    private static final Random RAND = new Random();
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    final Button playButton = new Button("PLAY");
    final Button gamePlayButton = new Button("GAMEPLAY INSTRUCTIONS");
    final Button exitButton = new Button("EXIT");

    final GridPane grid = new GridPane();

    public LandingPage (BorderPane stage, Canvas canvas) {

        gc = canvas.getGraphicsContext2D();

        var root = new Pane();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH/6);
            grid.getColumnConstraints().add(column);
        }

        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 50, 50, 95));


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

        stars = new ArrayList<>();

        // Add "Muck Space Invaders" Image as title
        ImageView titleView = new ImageView();
        titleView.setFitWidth(350);
        titleView.setFitHeight(130);
        titleView.setImage(TITLE);

        grid.add(titleView, 0,0,3,5);


        // Add Play Button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #00ff00");

        grid.add(playButton, 0, 8, 2, 5);

        // Add Gameplay Button
        gamePlayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gamePlayButton.setStyle("-fx-background-color: #00ff00");

        grid.add(gamePlayButton, 0, 13, 2, 5);

        // Add Exit Button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #00ff00");

        grid.add(exitButton, 0, 18, 2, 5);


        stage.getChildren().add(grid);


        // Create event when Play Button is clicked. For now it opens up a new window
        playButton.setOnAction(event -> {

            Label secondLabel = new Label("Welcome to Space Invaders!");

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);

            Scene secondScene = new Scene(secondaryLayout, WIDTH, HEIGHT);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Muck 2021 Space Invaders");
            newWindow.setScene(secondScene);

            newWindow.initModality(Modality.WINDOW_MODAL);

            //newWindow.initOwner(stage);

            // Set position of second window, related to primary window.

            newWindow.show();
        });

        gamePlayButton.setOnAction(event -> {
            // Add elements
            Label dummyText = new Label("Lorem ipsum dolor set amet");
            Button goBack = new Button("Return to landing page");
        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas);
            stage.getChildren().add(canvas);
        });

    }




    /*****************************************************************************
     *
     * Function name: run
     * Purpose: To run the graphics for the Star background
     * Arguments: GraphicContext gc
     * Return: void
     *
     *****************************************************************************/
    private void run(GraphicsContext gc) {
        this.gc.setFill(Color.grayRgb(20));
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);

        stars.forEach(StarBackground::draw);

        if (RAND.nextInt(10) > 2) {
            stars.add(new StarBackground());
        }
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).posY > HEIGHT)
                stars.remove(i);
        }
    }


    public class StarBackground {
        int posX, posY;
        private int h, w, r, g, b;
        private double opacity;

        public StarBackground() {
            posX = RAND.nextInt(WIDTH);
            posY = 0;
            w = RAND.nextInt(5) + 1;
            h = RAND.nextInt(5) + 1;
            r = 255;
            g = 255;
            b = 255;
            opacity = RAND.nextFloat();
            if (opacity < 0) opacity *= -1;
            if (opacity > 0.5) opacity = 0.5;
        }


        /*****************************************************************************
         *
         * Function name: draw
         * Purpose: To display the stars for the Star background
         * Arguments: nil
         * Return: void
         *
         *****************************************************************************/
        public void draw() {
            if (opacity > 0.8) opacity -= 0.01;
            if (opacity < 0.1) opacity += 0.01;
            gc.setFill(Color.rgb(r, g, b, opacity));
            gc.fillOval(posX, posY, w, h);
            posY += 20;
        }
    }


}


