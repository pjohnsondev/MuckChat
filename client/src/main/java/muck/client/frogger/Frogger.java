package muck.client.frogger;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frogger {

    private AnimationTimer timer;

    private int framecount = 0;

    private Pane root;

    private GraphicsContext gc;

    private final List<Node> cars = new ArrayList<>();

    private Node frog;

    private static final Random RAND = new Random();

    private static final double WIDTH = 800;

    private static final double HEIGHT = 600;

    private static final double FROG_WIDTH = 40;

    private static final double FROG_HEIGHT = 39;

    private static final double CAR_WIDTH = 40;

    private static final double CAR_HEIGHT = 40;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        frog = initFrog();

        root.getChildren().add(frog);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {  // Called every 1/60
                framecount++;
                if (framecount % 2 == 0) {  // update every 2/60
                    framecount = 0;
                    onUpdate();
                }
            }
        };
        timer.start();

        return root;
    }

    // Create a Green Frog rectangle
    // TODO: Replace rect with Sprite
    private Node initFrog() {
        Rectangle rect = new Rectangle(FROG_WIDTH, FROG_HEIGHT, Color.GREEN);
        rect.setTranslateY(HEIGHT - FROG_HEIGHT);

        return rect;
    }

    // Spawn a Red Car rectangle
    // TODO: Replace rect with Sprite
    private Node spawnCar() {
        Rectangle rect = new Rectangle(CAR_WIDTH, CAR_HEIGHT, Color.RED);
        rect.setTranslateY((int)(Math.random() * 14) * CAR_HEIGHT);

        root.getChildren().add(rect);
        return rect;
    }

    // Every update push cars along and spawn new ones
    private void onUpdate() {
        for (Node car : cars)
            car.setTranslateX(car.getTranslateX() + Math.random() * 10);

        if (Math.random() < 0.075) {
            cars.add(spawnCar());
        }

        checkState();
    }

    // Check the game state to see if frog and car collision or if
    // made it across the game field and display win text
    private void checkState() {
        for (Node car : cars) {
            if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
                frog.setTranslateX(0);
                //Debug output for testing
                //System.out.println("Collision!");
                frog.setTranslateY(HEIGHT - FROG_HEIGHT);
                return;
            }
        }

        if (frog.getTranslateY() <= 0) {
            timer.stop();
            String win = "YOU WIN!";

            HBox hBox = new HBox();
            hBox.setTranslateX(300);
            hBox.setTranslateY(250);
            root.getChildren().add(hBox);

            for (int i = 0; i < win.toCharArray().length; i++) {
                char letter = win.charAt(i);

                Text text = new Text(String.valueOf(letter));
                text.setFont(Font.font(48));
                text.setOpacity(0);

                hBox.getChildren().add(text);

                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
                ft.setToValue(1);
                ft.setDelay(Duration.seconds(i * 0.15));
                ft.play();
            }
            // TODO: Setup post-win
        }
    }

    // Start the scene. Listen for inputs
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));

        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    frog.setTranslateY(frog.getTranslateY() - FROG_WIDTH);
                    break;
                case S:
                    if (frog.getTranslateY() + FROG_HEIGHT >= HEIGHT) {
                        frog.setTranslateY(HEIGHT - FROG_HEIGHT);
                    } else {
                        frog.setTranslateY(frog.getTranslateY() + FROG_WIDTH);
                    }
                    break;
                case A:
                    if (frog.getTranslateX() - FROG_WIDTH <= 0) {
                        frog.setTranslateX(0);
                    } else {
                        frog.setTranslateX(frog.getTranslateX() - FROG_WIDTH);
                    }
                    break;
                case D:
                    if (frog.getTranslateX() + FROG_WIDTH >= WIDTH) {
                        frog.setTranslateX(WIDTH - FROG_WIDTH);
                    } else {
                        frog.setTranslateX(frog.getTranslateX() + FROG_WIDTH);
                    }

                    break;
                default:
                    break;
            }
            //Debug output for testing
            //System.out.println("Frog X: " + frog.getTranslateX() + " Frog Y: " + frog.getTranslateY() + "\n");
        });

        stage.show();
    }

}
