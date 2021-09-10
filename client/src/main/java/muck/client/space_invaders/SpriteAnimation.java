package muck.client.space_invaders;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class SpriteAnimation extends Image {

    // Instance variables
    private double requestedWidth;
    private double requestedHeight;
    private int x;
    private int y;
    private int health;
    private int damage;
    private boolean preserveRation;
    private boolean smooth;
    private String type;
    // Default sprite size
    private static final Double SPRITE_SIZE = 50.00;
    // Sprite movement distance
    private static final int SPRITE_MOVEMENT = 4;
    private KeyCode direction;

    /**
     * Default constructor
     */
    public SpriteAnimation(){
        super("/images/SpaceInvaders/ship.png");
        this.requestedWidth = SPRITE_SIZE;
        this.requestedHeight = SPRITE_SIZE;
        this.x = 0;
        this.y = 0;
        this.health = 1;
        this.damage = 1;
        this.preserveRation = true;
        this.smooth = true;
        this.type = "";
    }

    /**
     * Constructor:
     *
     * Extends Image class taking in the following parameters
     * (url, requestedWidth, requestedHeight and preserveRatio definitions taken from the java docs):
     * @param url - the string representing the URL to use in fetching the pixel data
     * @param requestedWidth - the image's bounding box width
     * @param requestedHeight - the image's bounding box height
     * @param x - x position of sprite
     * @param y - y position of sprite
     * @param preserveRatio - indicates whether to preserve the aspect ratio of the original image when scaling to fit the image within the specified bounding box
     * @param smooth - indicates whether to use a better quality filtering algorithm or a faster one when scaling this image to fit within the specified bounding box
     * @param health - set the health of the sprite
     * @param damage - set how much damage a sprite's shot will do
     * @param type - defines whether the sprite is a player, an enemy or a bullet.
     */
    public SpriteAnimation(String url,double requestedWidth, double requestedHeight, int x, int y,
                           boolean preserveRatio, boolean smooth, int health, int damage, String type) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.preserveRation = preserveRatio;
        this.smooth = smooth;
        this.type = type;
    }

    // Set and get methods
    public void setHealth(int health) { this.health = health; }

    public int getHealth() {
        return health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

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

    @Override
    public String toString() {
        return "SpriteAnimation{" +
                "requestedWidth=" + requestedWidth +
                ", requestedHeight=" + requestedHeight +
                ", health=" + health +
                ", damage=" + damage +
                ", preserveRation=" + preserveRation +
                ", smooth=" + smooth +
                ", type='" + type + '\'' +
                '}';
    }
}