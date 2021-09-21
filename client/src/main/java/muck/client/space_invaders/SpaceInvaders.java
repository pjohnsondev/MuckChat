package muck.client.space_invaders;

import java.awt.*;
import java.util.*;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
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


// TODO: collision detection, additional levels with additional enemy-medium and enemy-small images,
// game over page, game completed page, change from mouse events to key events "w.a.s.d.space'

public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 35;
    private static final int PLAYER_LASER_SIZE = 24;
    private static final int ENEMY_SIZE = 75;
    SimpleIntegerProperty spriteDirectionCount = new SimpleIntegerProperty(0);

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
    KeyCombination SPACE = new KeyCodeCombination(KeyCode.SPACE);
    SimpleIntegerProperty directionKeyCount = new SimpleIntegerProperty(0);

    private SpriteAnimation playerLaser;
    // TODO: Only one keyCode is returned at a time, may need to use the mouse to shoot
    // Shooting key combinations
    //  KeyCombination SPACE = new KeyCodeCombination(KeyCode.SPACE);
    // KeyCombination ASPACE = new KeyCodeCombination(KeyCode.A, KeyCombination.SPACE);
    //  KeyCombination WSPACE = new KeyCodeCombination(KeyCode.W, KeyCode.SPACE);
    //  KeyCombination SSPACE = new KeyCodeCombination(KeyCode.S, KeyCode.SPACE);
    // KeyCombination DSPACE = new KeyCodeCombination(KeyCode.D,KeyCode.SPACE);


    //private double mouseX;
    private int lives;
    private int level =1;
    private int score = 0;
    private int maxMissiles;
    final int MAX_MISSILES = 50;

    List<StarBackground> stars;
    List<SpriteAnimation> playerLasers;
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
            if (A.match(e) || W.match(e) || S.match(e) || D.match(e)) {
                player.setDirection(e.getCode());
                directionKeyCount.set(directionKeyCount.get() + 1);
            } else {
                directionKeyCount.set(0);
            }
            //TODO: fix this up. If player does not move and 1 is added to directionKeyCount
            // an infinite loop occurs.
            if (SPACE.match(e) || (A.match(e) && SPACE.match(e)) || (S.match(e) && SPACE.match(e))
                    || (D.match(e) && SPACE.match(e)) || (W.match(e) && SPACE.match(e))) {

                if(directionKeyCount.get() == 0) {
                    shoot();
                    maxMissiles--;
                    //directionKeyCount.set(0);
                }else{
                    directionKeyCount.set(directionKeyCount.get() + 1);
                    shoot();
                    maxMissiles--;

                }
            }
        });

        scene.setOnKeyReleased(e -> {
            if (A.match(e) || W.match(e) || S.match(e) || D.match(e)) {
                directionKeyCount.set(0);
            }
        });

//      stage.setScene(new Scene(new StackPane(canvas)));
        stage.setScene(scene);
        stage.setTitle("Muck 2021 Space Invaders");
        stage.show();
    }


    private void setup() {
        stars = new ArrayList<>();
        playerLasers = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = 3;
        maxMissiles = MAX_MISSILES;
        player = new SpriteAnimation(imageURLs.get("PLAYER"), PLAYER_SIZE,
                (PLAYER_SIZE*2), WIDTH / 2, HEIGHT - (PLAYER_SIZE + 40),
                true, true, 5, 1, "player");
        if(level == 1) {
            levelUp(1);
        }
        if(level == 2) {
            levelUp(2);
        }
        if(level == 3) {
            levelUp(3);
        }
    }


    /**
     * Function name: run
     * Purpose: To display the graphics for the game
     * @param: gc - GraphicsContext variable to display graphics
     * Return: void
     */
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
        gc.fillText("Shots Left: " + maxMissiles, WIDTH - 100, 80);
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
                //case SPACE:
                // shoot();

                // case ESCAPE: pause timer break;
            }
        }


        // Draw enemy sprites
        for(int i = 0; i < enemies.size(); i++) {
            gc.drawImage(this.enemies.get(i), this.enemies.get(i).getX(), this.enemies.get(i).getY());

            if (enemies.get(i).getType() == "small_enemy") {

                if (enemies.get(i).getX() + enemies.get(i).getRequestedWidth() >= WIDTH) {
                    spriteDirectionCount.set(1);
                }
                if (enemies.get(i).getX() <= 0) {
                    spriteDirectionCount.set(0);
                }
                if (spriteDirectionCount.get() == 0) {
                    enemies.get(i).moveRight();
                }
                if (spriteDirectionCount.get() == 1) {
                    enemies.get(i).moveLeft();
                }
            }
        }


        // Draw player lasers
        for(int i= 0; i < playerLasers.size(); i++){
            Rectangle r1 = this.playerLasers.get(i).getBounds();
            gc.drawImage(this.playerLasers.get(i), this.playerLasers.get(i).getX(), this.playerLasers.get(i).getY());
            playerLasers.get(i).moveUp();

            if (playerLasers.get(i).getY() < 0){
                playerLasers.remove(i);
                continue;
            }

            // collision detection using SpriteAnimation.getBounds()
            // Also increments level and levelUp() when enemies.size() == 0
            for (int j = 0; j < enemies.size(); j++) {
                Rectangle r2 = this.enemies.get(j).getBounds();

                if (r1.intersects(r2)) {
                    score += 100;
                    playerLasers.remove(i);
                    enemies.remove(j);
                    if(enemies.size() == 0){
                        level++;
                        levelUp(level);
                    }
                }
            }
        }
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

        /**
         * Function name: draw
         * Purpose: To draw the stars for the Star background
         * Arguments: nil
         * Return: void
         * Reference: Based on code from
         *      URL: https://github.com/Gaspared/Space-Invaders.git
         *      Author: Gaspared
         *      Title: Space Invaders
         */
        public void draw() {
            if (opacity > 0.8) opacity -= 0.01;
            if (opacity < 0.1) opacity += 0.01;
            gc.setFill(Color.rgb(r, g, b, opacity));
            gc.fillOval(posX, posY, w, h);
            posY += 5;
        }
    }

    /**
     * Function name: levelUp
     * Purpose: To set the number of enemies in each level
     * @param: levelNumber - an integer representing the level
     * Return: void
     */

    public void levelUp(int levelNumber){

        if (levelNumber == 1){
            for (int j = 0; j < 5; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"),
                        ENEMY_SIZE, ENEMY_SIZE, 60 + j * WIDTH / 5, 150,
                        true, true, 1, 1,
                        "enemy"));
                //TODO: remove enemies if they move down.
                if (enemies.get(j).getY() > HEIGHT)
                    enemies.remove(j);
            }
        }

        if (levelNumber == 2){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                        true, 1, 1, "enemy"));
                if (enemies.get(i).getY() > HEIGHT)
                    enemies.remove(i);
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        150 + j * WIDTH / 5, 300, true, true,
                        1, 1, "enemy"));

                if (enemies.get(j).getY() > HEIGHT)
                    enemies.remove(j);
            }
        }

        if (levelNumber == 3){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                        true, 1, 1, "enemy"));
                if (enemies.get(i).getY() > HEIGHT)
                    enemies.remove(i);
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        150 + j * WIDTH / 5, 300, true,
                        true, 1, 1, "medium_enemy"));

                if (enemies.get(j).getY() > HEIGHT)
                    enemies.remove(j);
            }
            for(int k = 0; k < 1; k++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_SMALL"),
                        ENEMY_SIZE / 2, ENEMY_SIZE / 2,
                        150, 450, true, true, 1,
                        1, "small_enemy"));

                if (enemies.get(k).getY() > HEIGHT)
                    enemies.remove(k);
            }
        }
    }

    /**
     * Function name: shoot
     * Purpose: To allow the Sprites to shoot
     * Arguments: nil
     * Return: void
     */
    //TODO: create a shoot function for enemies to shoot.
    public void shoot() {

        playerLasers.add(new SpriteAnimation (imageURLs.get("PLAYER_LASER"), PLAYER_LASER_SIZE,
                (PLAYER_LASER_SIZE * 2), player.getX(), player.getY(),
                true, true, 5, 1, "player laser"));

    }

}

