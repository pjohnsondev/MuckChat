package muck.client.space_invaders;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


//TODO: collision detection, additional levels with additional enemy-medium and enemy-small images,
// game over page, game completed page, change from mouse events to key events "w.a.s.d.space'

public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 35;
    private static final int ENEMY_SIZE = 75;
    private static final Image PLAYER = new Image("/images/spaceinvaders/player-ship.gif");
    private static final Image PLAYER_LASER = new Image("/images/spaceinvaders/" +
            "player-laser.gif");
    private static final Image ENEMY_BIG = new Image
            ("/images/spaceinvaders/" +
                    "enemy-big.gif");
    private static final Image ENEMY_MEDIUM = new Image
            ("/images/spaceinvaders/enemy-medium.gif");
    private static final Image ENEMY_SMALL = new Image
            ("/images/spaceinvaders/enemy-small.gif");



    private GraphicsContext gc;

    private SISprite player;

    private double mouseX;
    private int score;
    private int level;
    private int lives;
    final int MAX_MISSILES = 50;

    List<StarBackground> stars;
    List<PlayerMissiles> playerMissiles;
    List<Enemy> enemies;

    public void start(Stage stage) throws Exception {


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e ->
                playerMissiles.add(player.shoot())
        );
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Muck 2021 Space Invaders");
        stage.show();

    }


    private void setup() {
        stars = new ArrayList<>();
        playerMissiles = new ArrayList<>();
        enemies = new ArrayList<>();
        score = 0;
        level = 1;
        lives = 3;
        player = new SISprite
                (WIDTH / 2, HEIGHT -
                        (PLAYER_SIZE + 40), PLAYER_SIZE,
                        (PLAYER_SIZE*2), "player", PLAYER);

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
        gc.fillText("Score: " + score, WIDTH/2, 40);
        gc.setFont(Font.font(20));
        gc.fillText("Level: " + level, 80, 40);
        gc.fillText("Lives: " + lives, WIDTH - 80, 40);
        stars.forEach(StarBackground::draw);

        if (RAND.nextInt(10) > 2) {
            stars.add(new StarBackground());
        }
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).posY > HEIGHT)
                stars.remove(i);
        }
        player.drawSprite();
        player.x = (int) mouseX - PLAYER_SIZE/2;

        for (int i = playerMissiles.size() - 1; i >=0 ; i--) {
            PlayerMissiles playerMissile = playerMissiles.get(i);
            if(playerMissile.posY < 0 || playerMissile.toRemove)  {
                //playerMissiles.remove(i);
                //    continue;
            }
            playerMissile.update();
            playerMissile.draw();

        }
        enemies.forEach(Enemy::drawEnemy);
        levelUp();

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
    public class SISprite {
        int x, y, w, h;
        final String type;

        Image img;
        SISprite(int x, int y, int w, int h, String type, Image img) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.type = type;
            this.img = img;
        }


        public PlayerMissiles shoot() {

            return new PlayerMissiles(x  + h / 2 - PlayerMissiles.size / 2,
                    y - PlayerMissiles.size);
        }

        public void drawSprite(){
            gc.drawImage(img, x, y, w, h);

        }
    }


    public class PlayerMissiles {

        public boolean toRemove;

        int posX, posY, speed = 10;
        static final int size = 6;
        int width= 24;
        int length = 48;
        public PlayerMissiles(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }


        public void update() {
            posY -= speed;
        }


        public void draw() {

            gc.drawImage(PLAYER_LASER, posX- (PLAYER_SIZE/2+width/2), posY - length/2,
                    width, length);

        }
    }

    public class Enemy extends SISprite {

        public Enemy(int x, int y, int w, int h, String type, Image img) {
            super(x, y, w, h, type, img);
        }

        public void drawEnemy() {
            gc.drawImage(img, x, y,
                    w, h);
        }

    }


    public void levelUp(){

        for (int j = 0; j < 5; j++) {
            enemies.add(new Enemy(60 + j*WIDTH/5, 150, ENEMY_SIZE, ENEMY_SIZE, "enemy", ENEMY_BIG));
            if (enemies.get(j).y > HEIGHT)
                enemies.remove(j);
        }
    }

}

