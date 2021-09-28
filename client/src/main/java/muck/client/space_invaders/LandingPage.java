package muck.client.space_invaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import muck.client.GameMap;

public class LandingPage extends Node {

    private static final Image TITLE = new Image("/images/space-invaders-title.png");
    private static final Image INSTRUCTIONS = new Image("/images/spaceinvaders/instructions.png");
    private final GraphicsContext gc;

    final Button playButton = new Button("PLAY");
    final Button gamePlayButton = new Button("GAMEPLAY INSTRUCTIONS");
    final Button exitButton = new Button("EXIT");

    final GridPane grid = new GridPane();
    private final BorderPane gamePane;

    List<Star> stars;

    private static final String DIRECTIONS =
            """
                    *   WASD to move

                    *   SPACE to shoot

                    *   TAB for God mode

                    *   You win if you destroy all the enemies or if you
                         are still alive after level 4

                    *   You lose if you have no lives left or run out of ammunition

                    *   Big enemies have 3 lives, Medium enemies have 2 lives,
                        Small enemies have 1 life
                    """;

    public LandingPage (BorderPane stage, Canvas canvas) {
        gamePane = stage;

        gc = canvas.getGraphicsContext2D();

        stars = new ArrayList<>();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(SpaceInvadersUtility.WIDTH/6);
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
        ImageView titleView = new ImageView();
        titleView.setFitWidth(350);
        titleView.setFitHeight(130);
        titleView.setImage(TITLE);

        grid.add(titleView, 1,0,2,5);


        // Add Play Button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #00ff00");

        grid.add(playButton, 1, 8, 2, 5);

        // Add Gameplay Button
        gamePlayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gamePlayButton.setStyle("-fx-background-color: #00ff00");

        grid.add(gamePlayButton, 1, 13, 2, 5);

        // Add Exit Button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #00ff00");

        grid.add(exitButton, 1, 18, 2, 5);


        stage.getChildren().add(grid);


        // Add event listener to playButton and open new game window.
        playButton.setOnAction(event -> {

            Stage newWindow = new Stage();
            newWindow.setTitle("Muck 2021 Space Invaders");

            newWindow.initModality(Modality.WINDOW_MODAL);

            SpaceInvaders si = new SpaceInvaders();
            try {
                si.start(newWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }

            newWindow.show();
        });

        // Add event listener to gamePlay button to display instructions in a new window.
        gamePlayButton.setOnAction(event -> {
            // Add instructions title image
            ImageView instructions = new ImageView();
            instructions.setImage(INSTRUCTIONS);

            // Add image to a HBox and center it at the top of the scene
            HBox hbxImg = new HBox();
            hbxImg.setAlignment(Pos.TOP_CENTER);
            hbxImg.setPadding(new Insets(30, 0, 15, 0));
            hbxImg.getChildren().add(instructions);

            // Add instructions text as a label.
            Label instructionsText = new Label(DIRECTIONS);
            instructionsText.setFont(Font.font(19));
            instructionsText.setStyle("-fx-text-fill: #00ff00");

            // Add and format the Return to Landing Page button
            Button goBack = new Button("RETURN TO LANDING PAGE");
            goBack.setPrefSize(200, 50);
            goBack.setStyle(" -fx-background-color: #00ff00");
            HBox button = new HBox();
            button.setAlignment(Pos.BOTTOM_CENTER);
            button.setPadding(new Insets(15, 5, 25, 0));
            button.getChildren().add(goBack);


            StackPane layout = new StackPane();

            // Set background colour
            layout.setStyle("-fx-background-color: BLACK");

            layout.getChildren().addAll(hbxImg, instructionsText, button);

            // Add layout to the scene
            Scene gameInstructScene = new Scene(layout, SpaceInvadersUtility.WIDTH * 0.75,
                    SpaceInvadersUtility.HEIGHT * 0.90);

            // Create window for instructions to be displayed in
            Stage gamePlayInstructionsWindow = new Stage();

            gamePlayInstructionsWindow.setTitle("GAMEPLAY INSTRUCTIONS");
            gamePlayInstructionsWindow.setScene(gameInstructScene);

            gamePlayInstructionsWindow.show();

            // Close window when pushing button
            goBack.setOnAction(event1 -> gamePlayInstructionsWindow.close());
        });

        // Add event listener to exit button to remove current canvas and replace it with main gameCanvas
        // returning the user to the main muck client canvas.
        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas, gamePane);
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(145); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(262);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });

    }


    /*****************************************************************************
     *
     * Function name: run
     * Purpose: To run the graphics for the Star background
     * Arguments: GraphicContext gc
     * Return: void
     * Reference: Based on code from
     *      URL: https://github.com/Gaspared/Space-Invaders.git
     *      Author: Gaspared
     *      Title: Space Invaders
     *****************************************************************************/
    private void run(GraphicsContext gc) {
        this.gc.setFill(Color.grayRgb(20));
        this.gc.fillRect(0, 0, SpaceInvadersUtility.WIDTH, SpaceInvadersUtility.HEIGHT);
        this.gc.setFill(Color.WHITE);
        Star.createAndMoveStars(gc, stars);
    }
}


