package muck.client.space_invaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class LandingPage extends Application {

    List<StarBackground> stars;

    private static final Random RAND = new Random();
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;


    public void start(final Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        var root = new Pane();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        final GridPane grid = new GridPane();
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH/3.3);
            grid.getColumnConstraints().add(column);
        }

        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 50, 50, 50));


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

        ImageView titleView = new ImageView
                ("file:client/src/main/java/muck/client/space_invaders/images/space_invaders_title.png");
        titleView.setFitWidth(WIDTH-100);
        titleView.setPreserveRatio(true);
        grid.add(titleView, 0,0,3,5);

        // Add Play Button
        final Button playButton = new Button("PLAY");
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #00ff00");
        grid.add(playButton, 1, 6, 1, 5);

        // Add Gameplay Button
        final Button gamePlayButton = new Button("GAMEPLAY INSTRUCTIONS");
        gamePlayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gamePlayButton.setStyle("-fx-background-color: #00ff00");
        grid.add(gamePlayButton, 1, 11, 1, 5);

        // Add Exit Button
        final Button exitButton = new Button("EXIT");
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #00ff00");
        grid.add(exitButton, 1, 16, 1, 5);


        root.getChildren().addAll(canvas, grid);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

        // Create event when Play Button is clicked. For now it opens up a new window
        // TODO: Open game up in same canvas.
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

            newWindow.initOwner(stage);

            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 500);
            newWindow.setY(stage.getY() - 100);

            newWindow.show();
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
            r = RAND.nextInt(100) + 150;
            g = RAND.nextInt(100) + 150;
            b = RAND.nextInt(100) + 150;
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


    public static void main(String[] args) {
        launch(args);
    }

}


