package muck.client.space_invaders;

import java.awt.*;

import java.util.*;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import static  java.util.Map.entry;
import muck.client.space_invaders.SpriteAnimation;


// TODO: collision detection, additional levels with additional enemy-medium and enemy-small images,
// game over page, game completed page, change from mouse events to key events "w.a.s.d.space'

public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 35;
    private static final int ENEMY_SIZE = 75;

    private static Map<String, String> imageURLs = Map.ofEntries(
            entry("ENEMY_SMALL","/images/spaceinvaders/enemy-small.gif"),
            entry("ENEMY_MEDIUM", "/images/spaceinvaders/enemy-medium.gif"),
            entry("ENEMY_BIG", "/images/spaceinvaders/enemy-big.gif"),
            entry("PLAYER", "/images/spaceinvaders/player-ship.gif"),
            entry("PLAYER_LASER", "/images/spaceinvaders/player-laser.gif")
    );

    private static final Image PLAYER_LASER = new Image(imageURLs.get("PLAYER_LASER"));

    private GraphicsContext gc;

    private  SpriteAnimation player;

    // Direction key combinations
    KeyCombination A = new KeyCodeCombination(KeyCode.A);
    KeyCombination W = new KeyCodeCombination(KeyCode.W);
    KeyCombination S = new KeyCodeCombination(KeyCode.S);
    KeyCombination D = new KeyCodeCombination(KeyCode.D);
    SimpleIntegerProperty directionKeyCount = new SimpleIntegerProperty(0);


    // TODO: Only one keyCode is returned at a time, may need to use the mouse to shoot
    // Shooting key combinations
//    KeyCombination SPACE = new KeyCodeCombination(KeyCode.SPACE);
//    KeyCombination ASPACE = new KeyCodeCombination(KeyCode.A, KeyCombination.);
//    KeyCombination WSPACE = new KeyCodeCombination(KeyCode.W, KeyCode.SPACE);
//    KeyCombination SSPACE = new KeyCodeCombination(KeyCode.S, KeyCode.SPACE);
//    KeyCombination DSPACE = new KeyCodeCombination(KeyCode.D,KeyCode.SPACE);


    //private double mouseX;
    private int score;
    private int level;
    private int lives;
    final int MAX_MISSILES = 50;

    List<StarBackground> stars;
    List<PlayerMissiles> playerMissiles;
//    List<Enemy> enemies;
    List<SpriteAnimation> enemies;

    public void start(Stage stage) throws Exception {
        // Create animation event loop and background
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setup();

        // Enable key event detection for standard AWSD configuration and shooting
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> {
            // If keys match a direction it will increase the directionKeyCount used to test whether the player
            // should move in the run method. The AWSD key code is initialised and stored against the SpriteAnimation.
            // If any other key is pressed it resets the directionKeyCount preventing unexpected behaviour or having
            // the sprite continue to move as the condition would be true.
            if(A.match(e) || W.match(e) || S.match(e) || D.match(e)) {
                player.setDirection(e.getCode());
                directionKeyCount.set(directionKeyCount.get() + 1);
            }
            else {
                directionKeyCount.set(0);
            }
        });

        scene.setOnKeyReleased(e -> {
            if (A.match(e) || W.match(e) || S.match(e) || D.match(e)) {
                directionKeyCount.set(0);
            }
        });

//        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setScene(scene);
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
        player = new SpriteAnimation(imageURLs.get("PLAYER"), PLAYER_SIZE,  (PLAYER_SIZE*2), WIDTH / 2, HEIGHT -
                (PLAYER_SIZE + 40),true, true, 5, 1, "player");
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

        // Draw and move player
        gc.drawImage(player, player.getX(), player.getY());
        if(directionKeyCount.get() > 0){
            switch (player.getDirection()) {
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case W:
                    player.moveUp();
                    break;
                case S:
                    player.moveDown();
                    break;
                // TODO: Add key combination for shooting and movement i.e. ASPACE
                // TODO: Add shoot method to SpriteAnimation and add it in here as a standalone key combination
                //case SPACE: player.shoot(); break;
                // case ESCAPE: pause timer break;

            }
        }

        for (int i = playerMissiles.size() - 1; i >=0 ; i--) {
            PlayerMissiles playerMissile = playerMissiles.get(i);
            if(playerMissile.posY < 0 || playerMissile.toRemove)  {
                //playerMissiles.remove(i);
                //    continue;
            }
            playerMissile.update();
            playerMissile.draw();

        }


        // Draw enemy sprites
        for(int i = 0; i < enemies.size(); i++){
            gc.drawImage(enemies.get(i), enemies.get(i).getX(), enemies.get(i).getY());
        }

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

            posY += 5;
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

        // Constructor
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

    public void levelUp(){
        for (int j = 0; j < 5; j++) {
            enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE, ENEMY_SIZE,
                    60 + j * WIDTH/5, 150, true, true, 1, 1, "enemy" ));
            if (enemies.get(j).getY() > HEIGHT)
                enemies.remove(j);
        }
    }
}

