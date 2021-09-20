package muck.client.space_invaders;

import java.awt.*;
import java.util.*;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import muck.client.Achievements;
import muck.client.Sprite;

import static  java.util.Map.entry;
import static muck.client.Achievements.*;


public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 35;
    private static final int PLAYER_LASER_SIZE = 24;
    private static final int ENEMY_SIZE = 75;
    SimpleIntegerProperty spriteDirectionCount = new SimpleIntegerProperty(0);
    SimpleIntegerProperty spriteYDirectionCount = new SimpleIntegerProperty(0);

    private static Map<String, String> imageURLs = Map.ofEntries(
            entry("ENEMY_SMALL","/images/spaceinvaders/enemy-small.gif"),
            entry("ENEMY_MEDIUM", "/images/spaceinvaders/enemy-medium.gif"),
            entry("ENEMY_BIG", "/images/spaceinvaders/enemy-big.gif"),
            entry("PLAYER", "/images/spaceinvaders/player-ship.gif"),
            entry("PLAYER_LASER", "/images/spaceinvaders/player-laser.gif"),
            entry("ENEMY_LASER", "/images/spaceinvaders/enemy-laser.gif"),
            entry("EXPLOSION", "/images/spaceinvaders/explosion.gif")
    );

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
    //  KeyCombination SPACE = new KeyCodeCombination(KeyCode.SPACE);
    // KeyCombination ASPACE = new KeyCodeCombination(KeyCode.A, KeyCombination.SPACE);
    //  KeyCombination WSPACE = new KeyCodeCombination(KeyCode.W, KeyCode.SPACE);
    //  KeyCombination SSPACE = new KeyCodeCombination(KeyCode.S, KeyCode.SPACE);
    // KeyCombination DSPACE = new KeyCodeCombination(KeyCode.D,KeyCode.SPACE);


    private int lives;
    private int level;
    private int score;
    private int maxMissiles;
    private int explosionStep;
    final int MAX_MISSILES = 70;
    private int CHANCE = 5;
    private static final int EXPLOSION_SIZE = 72;
    private static final int EXPLOSION_STEPS = 20;
    private boolean endGame = false;
    boolean winCheck = false;

    List<StarBackground> stars;
    List<SpriteAnimation> playerLasers;
    List<SpriteAnimation> enemies;
    List<SpriteAnimation> enemyLasers;
    List<SpriteAnimation> explosion;


    public void start(Stage stage) throws Exception {
        // Create animation event loop and background
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setup();

        // Add Exit Game and Play Again buttons
        javafx.scene.control.Button playAgainButton = new javafx.scene.control.Button("PLAY AGAIN");
        playAgainButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playAgainButton.setStyle("-fx-background-color: #00ff00");
        javafx.scene.control.Button exitButton = new Button(" EXIT GAME ");
        exitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        exitButton.setStyle("-fx-background-color: #00ff00");
        HBox buttonBar = new HBox(790, exitButton, playAgainButton);
        buttonBar.setStyle("-fx-padding: 8px; -fx-background-color:transparent");
        buttonBar.setAlignment(Pos.BOTTOM_LEFT);

        BorderPane root = new BorderPane();

        root.setBottom(buttonBar);
        // Enable key event detection for standard AWSD configuration and shooting
        Scene scene = new Scene(new StackPane(canvas, root));

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
        });

        scene.setOnKeyReleased(e -> {
            if (A.match(e) || W.match(e) || S.match(e) || D.match(e)) {
                directionKeyCount.set(0);
            }
        });

        // Event handler to make player shoot when space bar is pressed
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE && maxMissiles > 0 && endGame == false) {
                shoot();
                maxMissiles--;
            }
        });

        // Event handler for when the Exit button is pressed.
        // The window closes.
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->{
            exitButton.setOnAction(e ->  stage.close());
        }) ;

        // Event handler for when the Play Again button is pressed.
        // The game restarts from level 1.
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->{
            playAgainButton.setOnAction(e -> {
                try {
                    timeline.stop();
                    restart(stage);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }) ;
        });

        stage.setScene(scene);
        stage.setTitle("Muck 2021 Space Invaders");
        stage.show();
    }


    private void setup() {
        stars = new ArrayList<>();
        playerLasers = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyLasers = new ArrayList<>();
        explosion = new ArrayList<>();
        level = 1;
        score = 0;
        lives = 3;
        endGame = false;
        maxMissiles = MAX_MISSILES;

        player = new SpriteAnimation(imageURLs.get("PLAYER"), PLAYER_SIZE,
                (PLAYER_SIZE*2), WIDTH / 2, HEIGHT - (PLAYER_SIZE + 40),
                true, true, 5, 1, "player");

        // The below code has been added for testing. This will be removed once the game
        // is finished and replaced with levelUp(1);
        levelUp(1);
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
        if(endGame == false) {
            gc.drawImage(player, player.getX(), player.getY());
        }

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
            Rectangle r2 = this.enemies.get(i).getBounds();
            if(endGame == false) {
                gc.drawImage(enemies.get(i), enemies.get(i).getX(), enemies.get(i).getY());
            }
            if (spriteDirectionCount.get() == 0) {
                enemies.get(i).moveRight();
            }

            if (spriteDirectionCount.get() == 1) {
                enemies.get(i).moveLeft();

                if(spriteYDirectionCount.get() == 1){
                    enemies.get(i).moveDown(1);
                }
            }

            if(enemies.get(i).getY() > HEIGHT){
                enemies.remove(i);
            }
            // Collision detection between enemies and player
            // Draws explosion gif and reduces lives by 1.
            Rectangle r1 = player.getBounds();
            if (r2.intersects(r1)) {
                enemies.remove(i);
                explode(player.getX(), player.getY());
                lives--;
                if (lives < 1){
                    lives = 0;
                }
            }
            if(enemies.size() == 0){
                level++;
                levelUp(level);
                if (level > 4){
                    level = 4;
                }
            }
        }

        // Generate enemy lasers and set Direction count for enemies if they
        // reach the boundaries of the window.
        for (SpriteAnimation enemies : enemies) {
            var enemyLaserGenerator = new Random();
            int enemyShot = enemyLaserGenerator.nextInt(100);

            if (enemyShot == CHANCE) {
                enemyShoot(enemies.getX() + (int) enemies.getRequestedWidth() / 2,
                        enemies.getY() + (int) enemies.getRequestedHeight()/2);
            }

            int x = enemies.getX();

            if (x + enemies.getRequestedWidth() >= WIDTH) {
                spriteDirectionCount.set(1);
                if(level == 4) {
                    spriteYDirectionCount.set(1);
                }else {
                    spriteYDirectionCount.set(0);
                }
            }
            if (x <= 0) {
                spriteDirectionCount.set(0);
            }
        }

        // Draw enemy lasers
        for (int i = 0; i < enemyLasers.size(); i++) {
            Rectangle r4 = this.enemyLasers.get(i).getBounds();
            if(endGame == false) {
                gc.drawImage(this.enemyLasers.get(i), this.enemyLasers.get(i).getX(),
                        this.enemyLasers.get(i).getY());
                enemyLasers.get(i).moveDown();
            }
            // Collision detection between enemy lasers and player
            // Removes enemy laser reduces lives by 1 and
            // draws explosion gif
            Rectangle r1 = player.getBounds();
            if (r4.intersects(r1)) {
                enemyLasers.remove(i);

                explode(player.getX(), player.getY());
                lives--;
                if (lives < 1){
                    lives = 0;
                }

            }
            if (enemyLasers.get(i).getY() > HEIGHT) {
                enemyLasers.remove(i);
            }
        }

        // Draw player lasers
        for(int i= 0; i < playerLasers.size(); i++){
            Rectangle r3 = this.playerLasers.get(i).getBounds();
            gc.drawImage(this.playerLasers.get(i), this.playerLasers.get(i).getX(),
                    this.playerLasers.get(i).getY());
            playerLasers.get(i).moveUp();

            if (playerLasers.get(i).getY() < 0){
                playerLasers.remove(i);
                continue;
            }
            // Collision detection using SpriteAnimation.getBounds()
            // Also increments level and levelUp() when enemies.size() == 0
            // and displays explosion gif.
            for (int j = 0; j < enemies.size(); j++) {
                Rectangle r2 = this.enemies.get(j).getBounds();

                if (r3.intersects(r2)) {
                    score += 100;
                    explode(enemies.get(j).getX(), enemies.get(j).getY());
                    playerLasers.remove(i);
                    enemies.remove(j);

                    if(enemies.size() == 0){
                        level++;
                        levelUp(level);
                        if (level > 4){
                            level = 4;
                        }
                    }
                }
            }
        }

        // Add and draw explosion gif when the explode() method is called on
        // detection of a collision.
        // Explosions are added to an array and then removed when
        // explosionStep > EXPLOSION_STEPS
        for (int i = 0; i < explosion.size(); i++) {

            gc.drawImage(explosion.get(i), explosion.get(i).getX(),
                    explosion.get(i).getY(), EXPLOSION_SIZE, EXPLOSION_SIZE);

            explosionStep++;
            if(explosionStep > EXPLOSION_STEPS) {
                explosionStep = 0;
                explosion.remove(i);
            }
        }

        if (lives == 0){
            endGame = true;
            youLose();
        }

        if (lives > 0 && level == 4 && enemies.size() == 0){
            endGame = true;
            youWin();
            // Unlocks achievement 12 when the player beats Space Invaders.
            if (!winCheck) {
                winCheck = true;
                Achievements achieve12 = new Achievements(achievement12, achievement12Title, achievement12Description);
                achieve12.achievementUnlock(achieve12);
            }
        }

        if(maxMissiles ==0){
            endGame = true;
            noAmmo();
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
            }
        }

        if (levelNumber == 2){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                        true, 1, 1, "enemy"));
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        160 + j * WIDTH / 5, 300, true, true,
                        1, 1, "enemy"));
            }
        }

        if (levelNumber == 3 || levelNumber == 4){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                        true, 1, 1, "enemy"));
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        180 + j * WIDTH / 5, 300, true,
                        true, 1, 1, "medium_enemy"));
            }
            for(int k = 0; k < 3; k++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_SMALL"),
                        ENEMY_SIZE / 2, ENEMY_SIZE / 2,
                        280 + k * WIDTH/5, 400, true, true, 1,
                        1, "small_enemy"));
            }
        }
    }


    /**
     * Function name: shoot
     * Purpose: To allow the Sprites to shoot
     * Arguments: nil
     * Return: void
     */
    public void shoot() {

        playerLasers.add(new SpriteAnimation (imageURLs.get("PLAYER_LASER"), PLAYER_LASER_SIZE,
                (PLAYER_LASER_SIZE * 2), player.getX(), player.getY(),
                true, true, 5, 1, "player laser"));

    }


    /**
     * Function name: explode
     * Purpose: To draw the explosion gif when collision is detected
     * @param x - an integer used to get the x position of the collision
     * @param y - an integer used to get the y position of the collision
     * Return: void
     */
    public void explode(int x, int y) {

        explosionStep = 0;
        explosion.add(new SpriteAnimation(imageURLs.get("EXPLOSION"), EXPLOSION_SIZE,
                EXPLOSION_SIZE, x, y, true, true,
                5, 1, "explosion"));

    }


    /**
     * Function name: enemyShoot
     * Purpose: To allow the enemy sprites to shoot
     * @param x - an integer used to get the x value of the enemy sprite
     * @param y - an integer used to get the y value of the enemy sprite
     * Return: void
     */
    public void enemyShoot(int x, int y) {

        enemyLasers.add(new SpriteAnimation(imageURLs.get("ENEMY_LASER"), PLAYER_LASER_SIZE * 1.5,
                PLAYER_LASER_SIZE * 1.5, x,y, true, true,
                5, 1, "enemy laser"));

    }


    /**
     * Function name: restart
     * Purpose: To restart the game from level 1
     * @param newStage - a Stage variable to initiate a new stage when the game restarts
     * Return: void
     */
    void restart(Stage newStage) throws Exception {

        // set sprite Y direction to 0 if player reaches level 4
        // and restarts so enemies don't move down.
        spriteYDirectionCount.set(0);
        start(newStage);

    }


    /**
     * Function name: youLose
     * Purpose: To display a You Lose message when the player loses
     * Return: void
     */
    public void youLose() {

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(50));
        gc.setFill(Color.RED);
        gc.fillText("YOU LOSE", WIDTH / 2, HEIGHT / 4);
        gc.fillText("PLAY AGAIN?", WIDTH / 2, HEIGHT / 4 + 60);

    }


    /**
     * Function name: youWin
     * Purpose: To display a You Win message when the player wins
     * Return: void
     */
    public void youWin() {

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(50));
        gc.setFill(Color.GREEN);
        gc.fillText("YOU WIN!!! ", WIDTH / 2, HEIGHT/4);
        gc.fillText("PLAY AGAIN?", WIDTH / 2, HEIGHT / 4 + 60);

    }


    /**
     * Function name: noAmmo
     * Purpose: To display an Ammunition Exhausted message when the player is
     *          out of ammo
     * Return: void
     */
    public void noAmmo() {

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(50));
        gc.setFill(Color.YELLOW);
        gc.fillText("OUT OF AMMUNITION!!! ", WIDTH / 2, HEIGHT/4);
        gc.fillText("PLAY AGAIN?", WIDTH / 2, HEIGHT / 4 + 60);

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

}

