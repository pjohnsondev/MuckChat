package muck.client.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import muck.client.GameMap;



public class TTTLandingPage extends Node {


    private static final Image TITLE = new Image("images/TicTacToe/TicTacToeTitle.JPG");

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1000;

    final Button playButton = new Button("PLAY");
    final Button gamePlayButton = new Button("GAMEPLAY INSTRUCTIONS");
    final Button exitButton = new Button("EXIT");

    final GridPane grid = new GridPane();
    private GraphicsContext gc;

    public TTTLandingPage(BorderPane stage, Canvas canvas) {

        gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> background(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints(WIDTH / 6);
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


        // Create event when Play Button is clicked. For now it opens up a new window
        playButton.setOnAction(event -> {

            Stage newWindow = new Stage();
            newWindow.setTitle("Muck Tic Tac Toe");

            newWindow.initModality(Modality.WINDOW_MODAL);

            TicTacToe ttt = new TicTacToe();
            try {
                ttt.start(newWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }

            newWindow.show();
        });

        gamePlayButton.setOnAction(event -> {
            // Add elements
            Label dummyText = new Label("If you don't know how to play this game then you need to get out more");
            Button goBack = new Button("Return to landing page");

            // Create layout of the window
            VBox layout = new VBox(20);
            layout.getChildren().addAll(dummyText, goBack);
            Scene gameInstructScene = new Scene(layout, WIDTH / 2, HEIGHT / 2);

            // Create window for instructions to be displayed in
            Stage gamePlayInstructionsWindow = new Stage();
            gamePlayInstructionsWindow.setTitle("GAMEPLAY INSTRUCTIONS");
            gamePlayInstructionsWindow.setScene(gameInstructScene);
            gamePlayInstructionsWindow.show();

            // Close window when pushing button
            goBack.setOnAction(event1 -> gamePlayInstructionsWindow.close());
        });

        exitButton.setOnAction(event -> {
            stage.getChildren().removeAll(grid, canvas);
            new GameMap(canvas);
            canvas.setId("gameCanvas");
            stage.getChildren().add(canvas);
        });
    }
        private void background (GraphicsContext gc) {
            this.gc.setFill(Color.WHITE);
            this.gc.fillRect(0, 0, WIDTH, HEIGHT);
            this.gc.setFill(Color.WHITE);
        }

    }


