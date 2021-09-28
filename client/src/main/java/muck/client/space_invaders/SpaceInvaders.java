package muck.client.space_invaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static muck.client.Achievements.*;

public class SpaceInvaders {

    private static final Random RAND = new Random();
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int PLAYER_SIZE = 35;
    SimpleIntegerProperty spriteDirectionCount = new SimpleIntegerProperty(0);
    SimpleIntegerProperty spriteYDirectionCount = new SimpleIntegerProperty(0);

    private GraphicsContext gc;

    private SpriteAnimation player;

    // Direction key combinations
    KeyCombination A = new KeyCodeCombination(KeyCode.A);
    KeyCombination W = new KeyCodeCombination(KeyCode.W);
    KeyCombination S = new KeyCodeCombination(KeyCode.S);
    KeyCombination D = new KeyCodeCombination(KeyCode.D);
    SimpleIntegerProperty directionKeyCount = new SimpleIntegerProperty(0);

    private int level;
    private int score;
    private int maxMissiles;
    final int MAX_MISSILES = 140;
    private int CHANCE = 5;
    private static final int EXPLOSION_SIZE = 72;

    private boolean endGame = false;
    boolean winCheck = false;


    List<Star> stars;
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
        playAgainButton.setPrefSize(100,20);
        playAgainButton.setStyle("-fx-background-color: #00ff00");
        javafx.scene.control.Button exitButton = new Button(" EXIT GAME ");
        exitButton.setPrefSize(100,20);
        exitButton.setStyle("-fx-background-color: #00ff00");
        HBox buttonBar = new HBox(780, exitButton, playAgainButton);
        buttonBar.setStyle("-fx-padding: 8px; -fx-background-color:transparent");
        buttonBar.setAlignment(Pos.BOTTOM_CENTER);

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
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            exitButton.setOnAction(e -> stage.close());
        });

        // Event handler for when the Play Again button is pressed.
        // The game restarts from level 1.
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            playAgainButton.setOnAction(e -> {
                try {
                    timeline.stop();
                    restart(stage);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
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

        player = new SpriteAnimation(SpaceInvadersUtility.imageURLs.get("PLAYER"), PLAYER_SIZE,
                (PLAYER_SIZE * 2), WIDTH / 2, HEIGHT - (PLAYER_SIZE + 40),
                true, true, 5, 1, "PLAYER");

        SpaceInvadersUtility.levelUp(1, WIDTH, enemies);
    }


    /**
     * Function name: run
     * Purpose: To display the graphics for the game
     *
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
        gc.fillText("Score: " + score, WIDTH / 2, 40);
        gc.setFont(Font.font(20));
        gc.fillText("Level: " + level, 80, 40);
        gc.fillText("Lives: " + player.getLives(), WIDTH - 80, 40);
        gc.fillText("Shots Left: " + maxMissiles, WIDTH - 100, 80);


        // Star generation and movement
        Star.createAndMoveStars(gc, stars);

        // Draw and move player
        if (endGame == false) {
            gc.drawImage(player, player.getX(), player.getY());
            player.boundPlayer(PLAYER_SIZE, PLAYER_SIZE, WIDTH, HEIGHT);
        }

        if (directionKeyCount.get() > 0) {
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
        for (int i = 0; i < enemies.size(); i++) {
            Rectangle r2 = enemies.get(i).getBounds();
            if (endGame == false) {
                gc.drawImage(enemies.get(i), enemies.get(i).getX(), enemies.get(i).getY());
            }
            if (spriteDirectionCount.get() == 0) {
                enemies.get(i).moveRight();
            }

            if (spriteDirectionCount.get() == 1) {
                enemies.get(i).moveLeft();

                if (spriteYDirectionCount.get() == 1) {
                    enemies.get(i).moveDown(1);
                }
            }

            // Collision detection between enemies and player
            // Draws explosion gif and reduces lives by 1.
            Rectangle r1 = player.getBounds();
            if (r2.intersects(r1)) {
                enemies.get(i).setLives(enemies.get(i).getLives() - 1);
                player.setLives(player.getLives() - 1);
                player.explode(explosion);
            }

            if(enemies.get(i).getLives() == 0 || enemies.get(i).getY() > HEIGHT) {
                enemies.remove(i);
            }
        }

        // Generate enemy lasers and set Direction count for enemies if they
        // reach the boundaries of the window.
        for (SpriteAnimation enemies : enemies) {
            var enemyLaserGenerator = new Random();
            int enemyShot = enemyLaserGenerator.nextInt(100);

            if (enemyShot == CHANCE) {
                enemies.shoot(enemyLasers, enemies.getX() + (int) enemies.getRequestedWidth() / 2,
                        enemies.getY() + (int) enemies.getRequestedHeight() / 2);
            }

            int x = enemies.getX();

            if (x + enemies.getRequestedWidth() >= WIDTH) {
                spriteDirectionCount.set(1);
                if (level == 4) {
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
            Rectangle r4 = enemyLasers.get(i).getBounds();
            if (endGame == false) {
                gc.drawImage(enemyLasers.get(i), enemyLasers.get(i).getX(),
                        enemyLasers.get(i).getY());
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
            else if (enemyLasers.get(i).getY() > HEIGHT) {
                enemyLasers.remove(i);
            }
        }

        // Draw player lasers
        for (int i = 0; i < playerLasers.size(); i++) {
            Rectangle r3 = playerLasers.get(i).getBounds();
            if (endGame == false) {
                gc.drawImage(playerLasers.get(i), playerLasers.get(i).getX(),
                        playerLasers.get(i).getY());
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
                Rectangle r2 = enemies.get(j).getBounds();

                if (r3.intersects(r2)) {
                    enemies.get(j).setLives(enemies.get(j).getLives() - 1);
                    enemies.get(j).explode(explosion);
                    playerLasers.remove(i);
                    if (enemies.get(j).getLives() == 0) {
                        score += 100;
                        enemies.remove(j);
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

            if (explosion.get(i).getLives() == 0) {
                explosion.remove(i);
            }
        }

        // Level up
        if (enemies.size() == 0) {
            if (level < 4){
                level++;
                SpaceInvadersUtility.levelUp(level, WIDTH, enemies);
            }
            else{
                winCheck = true;
                endGame = true;
            }
        }

        // Lives or missles are depleted end game.
        if (player.getLives() == 0) {
            endGame = true;
            SpaceInvadersUtility.displayMessage(gc, "Aw no, you lose!", WIDTH, HEIGHT, Color.RED);
        }

        if (maxMissiles == 0) {
            endGame = true;
            SpaceInvadersUtility.displayMessage(gc, "Out of ammo!! Nooooo!", WIDTH, HEIGHT, Color.YELLOW);
        }

        // If player beats level 4 win the game. Unlock achievement if player hasn't done so already.
        if (winCheck) {
            SpaceInvadersUtility.displayMessage(gc, "Woooo you win!", WIDTH, HEIGHT, Color.GREEN);
            if (Achievements.achievement12_instance == null) {
                Achievements.achievement12_instance = new Achievements(achievement12,
                        ACHIEVEMENT12TITLE, ACHIEVEMENT12DESCRIPTION);
                achievement12_instance.achievementUnlock();
                achievement12_instance.achievementPopUp();
            }
        }

    }

    /**
     * Function name: restart
     * Purpose: To restart the game from level 1
     *
     * @param newStage - a Stage variable to initiate a new stage when the game restarts
     *                 Return: void
     */
    void restart(Stage newStage) throws Exception {

        // set sprite Y direction to 0 if player reaches level 4
        // and restarts so enemies don't move down.
        spriteYDirectionCount.set(0);
        endGame = false;
        winCheck = false;
        start(newStage);

    }
}

