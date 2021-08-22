package muck.client.space_invaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 60;

    private GraphicsContext gc;

    private SIPlayer player;

    private double mouseX;
    private int score;

    List<StarBackground> stars;


    public void start(Stage stage) throws Exception {


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());

        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Muck 2021 Space Invaders");
        stage.show();

    }


    private void setup() {
        stars = new ArrayList<>();
        score = 0;
        player = new SIPlayer
                (WIDTH / 2, HEIGHT - (PLAYER_SIZE + 20), PLAYER_SIZE,
                        PLAYER_SIZE, "player", Color.BLUE);
    }


    /*****************************************************************************
     *
     * Function name: run
     * Purpose: To display the graphics for the game
     * Arguments: nil
     * Return: void
     * Reference: Based on code from
     *          *      URL: https://github.com/Gaspared/Space-Invaders.git
     *          *      Author: Gaspared
     *          *      Title: Space Invaders
     *****************************************************************************/
    private void run(GraphicsContext gc) {
        this.gc.setFill(Color.grayRgb(20));
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);
        this.gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(30));
        gc.setFill(Color.YELLOW);
        gc.fillText("Score: " + score, 80, 40);

        stars.forEach(StarBackground::draw);

        if (RAND.nextInt(10) > 2) {
            stars.add(new StarBackground());
        }
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).posY > HEIGHT)
                stars.remove(i);
        }
        player.drawPlayer();
        player.x = (int) mouseX - PLAYER_SIZE/2;
    }


    // StarBackground class
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
         * Purpose: To draw the stars for the Star background
         * Arguments: nil
         * Return: void
         * Reference: Based on code from
         *      URL: https://github.com/Gaspared/Space-Invaders.git
         *      Author: Gaspared
         *      Title: Space Invaders
         *****************************************************************************/
        public void draw() {
            if (opacity > 0.8) opacity -= 0.01;
            if (opacity < 0.1) opacity += 0.01;
            gc.setFill(Color.rgb(r, g, b, opacity));
            gc.fillOval(posX, posY, w, h);
            posY += 20;
        }
    }


    // Player class
    // TODO: create this class to accept images hopefully from Avatar class
    public class SIPlayer {
        // boolean dead = false;
        int x, y, w, h;
        final String type;
        Color color;

        SIPlayer(int x, int y, int w, int h, String type, Color color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.type = type;
            this. color = color;

        }

        public void drawPlayer(){
            gc.setFill(color);
            gc.fillRect(x, y, w, h);
        }
    }

}

