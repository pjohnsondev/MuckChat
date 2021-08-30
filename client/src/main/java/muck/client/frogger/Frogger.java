package muck.client.frogger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Frogger {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private GraphicsContext gc;

    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Frogger!");
        stage.show();
    }

    private void run(GraphicsContext gc) {}
}
