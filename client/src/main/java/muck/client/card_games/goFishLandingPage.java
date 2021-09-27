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
import muck.client.GameMap;
import muck.client.TileMapReader;
import muck.client.frogger.Frogger;
import muck.client.GoFish;

public class goFishLandingPage extends Node {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;
    private GraphicsContext gc;

    private ImageView titleView;
    //private static final Image TITLE = new Image("/images/cards/goFish.png");
    private static final Image TITLE = new Image("images/cards/landingPagePic.png");

    final Button playButton = new Button("PLAY GO FISH");
    final Button howToPlayButton = new Button("HOW TO PLAY");
    final Button exitButton = new Button("EXIT");

    final GridPane grid = new GridPane();
    private BorderPane gamePane;

    public goFishLandingPage(BorderPane stage, Canvas canvas) {
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
        titleView.setFitWidth(700);
        titleView.setFitHeight(550);
        titleView.setImage(TITLE);
        grid.add(titleView, 0,11,2,5);

        // Play button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: #FF4033");
        grid.add(playButton, 1, 18, 2, 3);

        // Instructions
        howToPlayButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        howToPlayButton.setStyle("-fx-background-color: #FF4033");
        grid.add(howToPlayButton, 1, 21, 2, 3);

        // Exit button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #FF4033");
        grid.add(exitButton, 1, 24, 2, 3);

        stage.getChildren().add(grid);

        // Create on-click events
        playButton.setOnAction(event -> {
            Stage newWindow = new Stage();
            newWindow.setTitle("Go Fish");
            //newWindow.setScene(secondScene);
            newWindow.initModality(Modality.WINDOW_MODAL);

            GoFish fish = new GoFish();
            try {
                fish.start(newWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            TileMapReader tm = new TileMapReader("/maps/cave.tmx");
            GameMap gm = new GameMap(canvas, gamePane,  "/tilesets/terrain_atlas.png", tm);
            gm.worldID = 2;
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(260); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(530);
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
