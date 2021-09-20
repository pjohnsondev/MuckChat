package muck.client.space_invaders;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.util.List;
import java.util.Map;
import muck.client.space_invaders.SpaceInvadersUtility;

import static java.util.Map.entry;

public class SpriteAnimation extends Image {

    // Instance variables
    private double width;
    private double height;
    private int x;
    private int y;
    private int lives;
    private int damage;
    private boolean preserveRatio;
    private boolean smooth;
    private String type;
    private static final Double SPRITE_SIZE = 50.00;
    private static final int SPRITE_MOVEMENT = 4;
    private static final int PLAYER_LASER_SIZE = 24;
    private static final int EXPLOSION_SIZE = 72;
    private KeyCode direction;


    /**
     * Default constructor
     */
    public SpriteAnimation(){
        super(SpaceInvadersUtility.imageURLs.get("PLAYER"));
        this.width = SPRITE_SIZE;
        this.height = SPRITE_SIZE;
        this.x = 0;
        this.y = 0;
        this.lives = 1;
        this.damage = 1;
        this.preserveRatio = true;
        this.smooth = true;
        this.type = "";
    }

    /**
     * Constructor:
     *
     * Extends Image class taking in the following parameters
     * (url, width, requestedHeight and preserveRatio definitions taken from the java docs):
     * @param url - the string representing the URL to use in fetching the pixel data
     * @param width - the image's bounding box width
     * @param height - the image's bounding box height
     * @param x - x position of sprite
     * @param y - y position of sprite
     * @param preserveRatio - indicates whether to preserve the aspect ratio of the original image when scaling to fit the image within the specified bounding box
     * @param smooth - indicates whether to use a better quality filtering algorithm or a faster one when scaling this image to fit within the specified bounding box
     * @param lives - set the health of the sprite
     * @param damage - set how much damage a sprite's shot will do
     * @param type - defines whether the sprite is a player, an enemy or a bullet.
     */
    public SpriteAnimation(String url, double width, double height, int x, int y,
                           boolean preserveRatio, boolean smooth, int lives, int damage, String type) {
        super(url, width, height, preserveRatio, smooth);
        this.x = x;
        this.y = y;
        this.lives = lives;
        this.damage = damage;
        this.preserveRatio = preserveRatio;
        this.smooth = smooth;
        this.type = type;
    }

    // Set and get methods
    public void setLives(int lives) { this.lives = lives; }

    public int getLives() { return lives; }

    public void setDamage(int damage) { this.damage = damage; }

    public int getDamage() { return damage; }

    public int getX() { return x; }

    public void doubleGetX() {double x = getX();}

    public void setX(int x) { this.x = x;}

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    public KeyCode getDirection() { return direction; }

    // Movement methods
    public void moveRight(){
        this.setX(this.getX() + SPRITE_MOVEMENT);
    }

    public void moveLeft(){
        this.setX(this.getX() - SPRITE_MOVEMENT);
    }

    public void moveUp(){
        this.setY(this.getY() - SPRITE_MOVEMENT);
    }

    public void moveDown(){
        this.setY(this.getY() + SPRITE_MOVEMENT);
    }

    // Overloaded movement method for enemies
    public void moveDown(int movementRate){
        this.setY(this.getY() + movementRate);
    }

    // Method used for collision detection between SpriteAnimation objects
    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), (int) this.getRequestedWidth(),
                (int) this.getRequestedHeight());
    }

    /**
     * Function name: shoot
     * Purpose: To allow the Sprites to shoot
     * Arguments: nil
     * Return: void
     */
    public void shoot(List<SpriteAnimation> shootingList) {
        shootingList.add(new SpriteAnimation (SpaceInvadersUtility.imageURLs.get(this.type + "_LASER"), PLAYER_LASER_SIZE,
                (PLAYER_LASER_SIZE * 2), this.getX(), this.getY(),
                true, true, 1, 1, this.type + "_LASER"));
    }


    /**
     * Function name: shoot
     * Purpose: To allow the Sprites to shoot
     * Arguments: nil
     * Return: void
     */
    public void shoot(List<SpriteAnimation> shootingList, int x, int y) {
        shootingList.add(new SpriteAnimation (SpaceInvadersUtility.imageURLs.get(this.type + "_LASER"), PLAYER_LASER_SIZE * 1.5,
                (PLAYER_LASER_SIZE * 2), x, y,
                true, true, 1, 1, this.type + "_LASER"));
    }


    /**
     * Function name: explode
     * Purpose: To draw the explosion gif when collision is detected
     * @param explosionList - SpriteAnimation list to add image to.
     * Return: void
     */
    public void explode(List<SpriteAnimation> explosionList) {
        explosionList.add(new SpriteAnimation(SpaceInvadersUtility.imageURLs.get("EXPLOSION"), EXPLOSION_SIZE,
                EXPLOSION_SIZE, this.getX(), this.getY(), true, true,
                20, 1, "EXPLOSION"));
    }

    @Override
    public String toString() {
        return "SpriteAnimation{" +
                "requestedWidth=" + width +
                ", requestedHeight=" + height +
                ", health=" + lives +
                ", damage=" + damage +
                ", preserveRation=" + preserveRatio +
                ", smooth=" + smooth +
                ", type='" + type + '\'' +
                '}';
    }
}
