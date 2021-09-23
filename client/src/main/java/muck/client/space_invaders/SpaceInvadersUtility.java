package muck.client.space_invaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class SpaceInvadersUtility {

    public static final int ENEMY_SIZE = 75;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int ENEMY_SMALL_LIVES = 1;
    public static final int ENEMY_MEDIUM_LIVES = 2;
    public static final int ENEMY_BIG_LIVES = 3;

    /**
     * Reference for Sprites
     * URL: https://ansimuz.itch.io/spaceship-shooter-environment
     * Title: Spaceship Shooter Environment
     * Author: Ansimuz
     * Licence: Free
     */
    public static Map<String, String> imageURLs = Map.ofEntries(
            entry("ENEMY_SMALL","/images/spaceinvaders/enemy-small.gif"),
            entry("ENEMY_MEDIUM", "/images/spaceinvaders/enemy-medium.gif"),
            entry("ENEMY_BIG", "/images/spaceinvaders/enemy-big.gif"),
            entry("PLAYER", "/images/spaceinvaders/player-ship.gif"),
            entry("PLAYER_LASER", "/images/spaceinvaders/player-laser.gif"),
            entry("ENEMY_LASER", "/images/spaceinvaders/enemy-laser.gif"),
            entry("EXPLOSION", "/images/spaceinvaders/explosion.gif")
    );

    /**
     * Function name: levelUp
     * Purpose: To set the number of enemies in each level
     * @param: levelNumber - an integer representing the level
     * @param: stageWidth - Stage size int used to divide enemy screen space
     * @param: enemies - List of SpriteAnimations to append enemies to
     * Return: void
     */
    public static void levelUp(int levelNumber, int stageWidth, List<SpriteAnimation> enemies){
        if (levelNumber == 1){
            for (int j = 0; j < 5; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"),
                        ENEMY_SIZE, ENEMY_SIZE, 60 + j * stageWidth / 5, 150,
                        true, true, ENEMY_BIG_LIVES, 1,
                        "ENEMY"));
            }
        }

        if (levelNumber == 2){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * stageWidth / 5, 150, true,
                        true, ENEMY_BIG_LIVES, 1, "ENEMY"));
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        160 + j * stageWidth / 5, 300, true, true,
                        ENEMY_MEDIUM_LIVES, 1, "ENEMY"));
            }
        }

        if (levelNumber == 3 || levelNumber == 4){
            for (int i = 0; i < 5; i++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_BIG"), ENEMY_SIZE,
                        ENEMY_SIZE, 60 + i * stageWidth / 5, 150, true,
                        true, ENEMY_BIG_LIVES, 1, "ENEMY"));
            }
            for (int j = 0; j < 4; j++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_MEDIUM"),
                        ENEMY_SIZE *0.75, ENEMY_SIZE * 0.75,
                        180 + j * stageWidth / 5, 300, true,
                        true, ENEMY_MEDIUM_LIVES, 1, "ENEMY"));
            }
            for(int k = 0; k < 3; k++) {
                enemies.add(new SpriteAnimation(imageURLs.get("ENEMY_SMALL"),
                        ENEMY_SIZE / 2, ENEMY_SIZE / 2,
                        280 + k * stageWidth /5, 400, true, true, ENEMY_SMALL_LIVES,
                        1, "ENEMY"));
            }
        }
    }

    /**
     * Function name: displayMessage
     * Purpose: Displays a final message to the user
     * @param message - String to display
     * @param textColor - A Paint Color object representing the desired text color
     * Return: void
     */
    public static void displayMessage(GraphicsContext gc, String message, int WIDTH, int HEIGHT, Color textColor) {

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(50));
        gc.setFill(textColor);
        gc.fillText(message, WIDTH / 2, HEIGHT / 4);
        gc.fillText("PLAY AGAIN?", WIDTH / 2, HEIGHT / 4 + 60);

    }

}
