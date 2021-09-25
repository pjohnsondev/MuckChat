package muck.client.card_games;

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;

public class LandingPage extends Node {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView gameMenu;
    private static final Image gamePic = new Image("images/cards/landingPagePic.png");

    final Button newGame = new Button("New Game");
    final Button howToPlay = new Button("How to play");
    final Button exitGame = new Button("Exit Game");

    final GridPane grid = new GridPane();
    private BorderPane gamePane;

    public LandingPage(BorderPane stage, Canvas canvas){
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
        titleView.setImage(gamePic);
        grid.add(titleView, 0,15,3,5);

        // New Game button
        newGame.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        newGame.setStyle("-fx-background-color: #cebfbf");
        grid.add(newGame, 1, 25, 3, 5);

        // How to play
        howToPlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        howToPlay.setStyle("-fx-background-color: #cebfbf");
        grid.add(howToPlay, 1, 30, 3, 5);

        // Exit button
        exitGame.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitGame.setStyle("-fx-background-color: #cebfbf");
        grid.add(exitGame, 1, 35, 3, 5);

        stage.getChildren().add(grid);


        // Create on-click events
        newGame.setOnAction(event -> {

            Label secondLabel = new Label("Welcome!");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            Scene secondScene = new Scene(secondaryLayout, WIDTH, HEIGHT);

            // Lets crack open a new window
            Stage newWindow = new Stage();
            newWindow.setTitle("Go Fish");
            newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);

            // Set position of second window, related to primary window.
            newWindow.show();
        });
       /* exitGame.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas, gamePane);
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(559); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(200);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });*/
    }




    private void bground(GraphicsContext gc) {
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);
    }
}
