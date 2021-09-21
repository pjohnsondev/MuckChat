package muck.client.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import muck.client.GameMap;

public class TTTLandingPage extends Node {

    private static final Image TITLE = new Image("images/TicTacToe/TicTacToeTitle.JPG");
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;

    final Button playButton = new Button("PLAY");
    final Button exitButton = new Button("EXIT");

    final GridPane grid = new GridPane();
    private final GraphicsContext gc;
    private final BorderPane gamePane;

    public TTTLandingPage(BorderPane stage, Canvas canvas) {
        gamePane = stage;
        gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> background(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH / 6.0);
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

        // Add Tic Tac Toe Title Image
        ImageView titleView = new ImageView();
        titleView.setFitWidth(340);
        titleView.setFitHeight(300);
        titleView.setImage(TITLE);

        grid.add(titleView, 1, 8, 2, 3);

        // Add Play Button
        playButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playButton.setStyle("-fx-background-color: rgba(227,89,89,0.98)");
        grid.add(playButton, 1, 19, 2, 3);
        playButton.setStyle("-fx-cursor: hand;");

        // Add Exit Button
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: rgba(227,89,89,0.98)");
        exitButton.setStyle("-fx-cursor: hand;");
        grid.add(exitButton, 1, 22, 2, 3);

        stage.getChildren().add(grid);

        // Opens the game in a new window
        playButton.setOnAction(event -> {
            Stage newWindow = new Stage();
            TicTacToe ttt = new TicTacToe();
            try {
                ttt.start(newWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Function when exiting the game
        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            GameMap gm = new GameMap(canvas, gamePane);
            gm.hero.sh = 0; //point hero downwards
            gm.hero.setPosX(815); // Sets the sprite outside the door they entered for the game
            gm.hero.setPosY(524);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });
    }
        //Background settings for landing page
        private void background (GraphicsContext gc) {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, WIDTH, HEIGHT);
            gc.setFill(Color.WHITE);
        }
    }