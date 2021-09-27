package muck.client.frogger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
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
    private static final Image INSTRUCTIONS = new Image("/images/frogger/instructions.png");

    final Button playButton = new Button("PLAY");
    final Button instructionsButton = new Button("INSTRUCTIONS");
    final Button exitButton = new Button("EXIT GAME");

    final GridPane grid = new GridPane();
    private BorderPane gamePane;

    private static final String DIRECTIONS =
            "*   WASD to move\n" +
                    "\n" +
                    "*   Make your way to the top of screen\n" +
                    "\n" +
                    "*   Avoid the Red Cars!\n" +
                    "\n" +
                    "*   You win if you reach the top!\n" +
                    "\n" +
                    "*   You lose if you are run over by a car!\n" +
                    "\n" +
                    "*   Good luck lil' froggie!\n" +
                    "\n";

    public LandingPageFrogger(BorderPane stage, Canvas canvas) {
        gamePane = stage;
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

        Label secondLabel = new Label("Welcome!");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, WIDTH, HEIGHT);
        Stage newWindow = new Stage();
        newWindow.setTitle("Frogger");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Create on-click events
        playButton.setOnAction(event -> {

            if (!newWindow.isShowing()) {
                Frogger frg = new Frogger();
                try {
                    frg.start(newWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Set position of second window, related to primary window.
                newWindow.show();
            }
        });

        // Create window for instructions to be displayed in
        Stage gamePlayInstructionsWindow = new Stage();

        instructionsButton.setOnAction(event -> {

            if (!gamePlayInstructionsWindow.isShowing()) {

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
                Button goBack = new Button("CLOSE INSTRUCTIONS");
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
                Scene gameInstructScene = new Scene(layout, WIDTH * 0.75, HEIGHT * 0.90);


                gamePlayInstructionsWindow.setTitle("GAMEPLAY INSTRUCTIONS");
                gamePlayInstructionsWindow.setScene(gameInstructScene);

                gamePlayInstructionsWindow.show();

                // Close window when pushing button
                goBack.setOnAction(event1 -> gamePlayInstructionsWindow.close());
            }
        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas, gamePane);
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(786); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(299);
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
