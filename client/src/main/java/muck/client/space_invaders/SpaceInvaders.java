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

    private int level;
    private int score;
    private int maxMissiles;
    final int MAX_MISSILES = 70;
    private int CHANCE = 5;
    private static final int EXPLOSION_SIZE = 72;

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

        Scene scene = new Scene(new StackPane(canvas, root));

        // Enable key event detection for standard AWSD configuration and shooting
        scene.setOnKeyPressed(e -> {
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
                player.shoot(playerLasers);
                maxMissiles--;
            }
        });

        // God mode
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.TAB) {
                player.setLives(1000);
                maxMissiles = 100000;
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
        endGame = false;
        maxMissiles = MAX_MISSILES;

        player = new SpriteAnimation(imageURLs.get("PLAYER"), PLAYER_SIZE,
                (PLAYER_SIZE*2), WIDTH / 2, HEIGHT - (PLAYER_SIZE + 40),
                true, true, 5, 1, "PLAYER");

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
        gc.fillText("Lives: " + player.getLives(), WIDTH - 80, 40);
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
                enemies.get(i).setLives(enemies.get(i).getLives() - 1);
                player.setLives(player.getLives() - 1);
                player.explode(explosion);
            }
            if(enemies.size() == 0){
                level++;
                levelUp(level);
                if (level > 4){
                    endGame = true;
                }
            }
        }

        // Generate enemy lasers and set Direction count for enemies if they
        // reach the boundaries of the window.
        for (SpriteAnimation enemies : enemies) {
            var enemyLaserGenerator = new Random();
            int enemyShot = enemyLaserGenerator.nextInt(100);

            if (enemyShot == CHANCE) {
                enemies.shoot(enemyLasers,enemies.getX() + (int) enemies.getRequestedWidth() / 2,
                        enemies.getY() + (int) enemies.getRequestedHeight()/2);
            }

            int x = enemies.getX();

            if (x + enemies.getRequestedWidth() >= WIDTH) {
                spriteDirectionCount.set(1);
                if(level == 4) {
                    spriteYDirectionCount.set(1);
                } else {
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
                player.setLives(player.getLives() - 1);
                player.explode(explosion);
            }
            if (enemyLasers.get(i).getY() > HEIGHT) {
                enemyLasers.remove(i);
            }
        }

        // Draw player lasers
        for(int i= 0; i < playerLasers.size(); i++){
            Rectangle r3 = this.playerLasers.get(i).getBounds();
            if(endGame == false){
                gc.drawImage(this.playerLasers.get(i), this.playerLasers.get(i).getX(),
                        this.playerLasers.get(i).getY());
                playerLasers.get(i).moveUp();
            }

            if (playerLasers.get(i).getY() < 0) {
                playerLasers.remove(i);
                continue;
            }
            // Collision detection using SpriteAnimation.getBounds()
            // Also increments level and levelUp() when enemies.size() == 0
            // and displays explosion gif.
            for (int j = 0; j < enemies.size(); j++) {
                Rectangle r2 = this.enemies.get(j).getBounds();

                if (r3.intersects(r2)) {
                    this.enemies.get(j).setLives( this.enemies.get(j).getLives() - 1 );
                    playerLasers.remove(i);
                    if(enemies.get(j).getLives() == 0){
                        enemies.get(j).explode(explosion);
                        score += 100;
                        enemies.remove(j);
                    }
                    if(enemies.size() == 0){
                        level++;
                        levelUp(level);
                        if (level > 4){
                            endGame = true;
                        }
                    }
                }
            }
        }

        // Add and draw explosion gif when the explode() method is called on
        // detection of a collision.
        // Explosions are added to an array and then removed when
        // the explosion sprite's lives == 0
        for (int i = 0; i < explosion.size(); i++) {

            gc.drawImage(explosion.get(i), explosion.get(i).getX(),
                    explosion.get(i).getY(), EXPLOSION_SIZE, EXPLOSION_SIZE);

            explosion.get(i).setLives(explosion.get(i).getLives() - 1);

            if(explosion.get(i).getLives() == 0){
                explosion.remove(i);
            }
        }

        if (player.getLives() == 0){
            endGame = true; // TODO: Not sure this needs to be here
            displayMessage("Aw no, you lose!", Color.RED);
        }

        if (player.getLives() > 0 && endGame == true) {
            displayMessage("Woooo you win!", Color.GREEN);
            if (Achievements.achievement12_instance == null) {
                Achievements.achievement12_instance = new Achievements(achievement12,
                        achievement12Title, achievement12Description);
                achievement12_instance.achievementUnlock();
                achievement12_instance.achievementPopUp();
            }
        }

            if(maxMissiles == 0){
                endGame = true; // TODO: Not sure this needs to be here
                displayMessage("Out of ammo!! Nooooo!", Color.YELLOW);
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
                            "ENEMY"));
                }
            }

            if (levelNumber == 2){
                for (int i = 0; i < 5; i++) {
                    enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                            ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                            true, 1, 1, "ENEMY"));
                }
                for (int j = 0; j < 4; j++) {
                    enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                            ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                            160 + j * WIDTH / 5, 300, true, true,
                            1, 1, "ENEMY"));
                }
            }

            if (levelNumber == 3 || levelNumber == 4){
                for (int i = 0; i < 5; i++) {
                    enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                            ENEMY_SIZE, 60 + i * WIDTH / 5, 150, true,
                            true, 1, 1, "ENEMY"));
                }
                for (int j = 0; j < 4; j++) {
                    enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                            ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                            180 + j * WIDTH / 5, 300, true,
                            true, 1, 1, "ENEMY"));
                }
                for(int k = 0; k < 3; k++) {
                    enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_SMALL"),
                            ENEMY_SIZE / 2, ENEMY_SIZE / 2,
                            280 + k * WIDTH/5, 400, true, true, 1,
                            1, "ENEMY"));
                }
            }
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
         * Function name: displayMessage
         * Purpose: Displays a final message to the user
         * @param message - String to display
         * @param textColor - A Paint Color object representing the desired text color
         * Return: void
         */
        public void displayMessage(String message, Color textColor) {

            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(50));
            gc.setFill(textColor);
            gc.fillText(message, WIDTH / 2, HEIGHT / 4);
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

