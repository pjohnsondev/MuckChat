package muck.client;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import muck.client.AvatarController;

/*
Collaboration:
bnolan9
dandre20

Player movement class

Requirements:
Sprite.java
 */
public class Sprite {
    //direction x - should always be at 0 to be still, otherwise it's moving
    //direction y - should always be at 0 to be still, otherwise it's moving
    private double dx, dy; // define directional x and y var
    private Image image; // Image of the avatar to be drawn
    public int sh; //The starting height for the sprite sheet 0/55/110/165 LEFT/RIGHT/UP/DOWN

    private double x, y;
    private int width, height;

    // Constructor
    /*
    Received:
    x pos
    y pos
    width of avatar
    height of avatar
    avatar - name of the avatar e.g. peach
        will then pull peach.png from the avatarImageLoc path
     */
    public Sprite(int x, int y) {
        this.x = x; //spawn location
        this.y = y; //spawn location
        String avatarInitial = "/images/" + AvatarController.getAvatarId() + "Sprite.png";
        this.image = new Image(avatarInitial);
    }

    /**
     * The move method is called each time the player moves using WASD keys
     * @param tm is a reference to the current map object
     * @param hero is a reference to the hero sprite object
     */
    public void move(TileMapReader tm, Sprite hero) {
        changeDirection();  //Make sure the sprite is facing the way of movement
        double newX = hero.getPosX() + dx;  //newX is checked for collision or other action here
        double newY = hero.getPosY() + dy;  //newY is checked for collision or other action here

        //Layer 1 solid not passable (Collision)
        int GID = tm.getLayerId(2,  (int)Math.floor(newX/tm.getTileWidth()), (int)Math.abs(newY/tm.getTileHeight()) );
        if (GID == -1 ) { //collision detection -1 is nothing drawn on that layer (no collision)
            if(newX > 5 && newX < tm.getWidth()*tm.getTileWidth()-5) { //map boundaries x
                x += dx;
            }
            if(newY > 5 && newY < tm.getHeight()*tm.getTileHeight()-5) //map boundaries y
                y += dy;
            }
    }

	public String getAvatar() {
		return this.image.getUrl();
	}
    // getters and setters
    public void setDx(double dx) { this.dx = dx; } // set direction x
    public void setDy(double dy) { this.dy = dy; } // set direction y
    public double getPosX() { return x; } // get player x Pos
    public double getPosY() { return y; } // get player y pos
    public void setPosX(double x) { this.x = x; } // get player x Pos
    public void setPosY(double y) { this.y = y; } // get player y pos
    public String getPlayerPos() { return getPosX()+","+getPosY(); } // concatenate x and y pos as x,y - will be used to retrieve current player pos
    public Image getImage() { return image; } // get image (resized one)


    /**
     * The drawHero method draws the users avatar.
     * @param gc The graphics context to draw to (map)
     * @param tm The world we are currently on (map)
     * @param hero The sprite object for the user (hero)
     * @param centerX The Viewport midpoint (half the width of the canvas size)
     * @param centerY Viewport midpoint (half the height of the canvas size)
     */
    public static void drawHero(GraphicsContext gc, TileMapReader tm, Sprite hero, double centerX, double centerY) {
        double drawX = 0;
        double drawY = 0;

        //This code prevents the camera moving outside boundaries of the map
        //This code centers the hero on the map except for when that would cause the camera,
        // to move off the boundaries of the map. Then the hero can free roam to the edges.
        if (hero.getPosX() < centerX) {
            drawX = hero.getPosX();
        } else if (hero.getPosX() > (tm.getWidth() * tm.getTileWidth() - centerX)) {
            drawX = ((centerX) - ((tm.getWidth() * tm.getTileWidth()) - (double) hero.getPosX())) + (centerX);
        } else {
            drawX = centerX;
        }
        if (hero.getPosY() < centerY) {
            drawY = hero.getPosY();
        } else if (hero.getPosY() > (tm.getHeight() * tm.getTileHeight() - centerY)) {
            drawY = ((centerY) - ((tm.getHeight() * tm.getTileHeight()) - (double) hero.getPosY())) + (centerY);
        } else {
            drawY = centerY;
        }
        //Draw avatar
        gc.drawImage(hero.image, 0, hero.sh, 60,55, drawX - 13,drawY - 28,26,30);
    }

    /**
     * The changeDirection method controls the direction the player is moving in,
     * the left and right direction is displayed when moving diagonally.
     * Sprite height is 55 pixels hence (0,55,110,165)
     */
    public void changeDirection() {
        //dx positive = RIGHT
        //dx negative = LEFT
        //dy positive = UP
        //dy negative = DOWN
        //LEFT RIGHT used when diagonal up or down
        if(dx > 0) {
            this.sh = 110; //direction RIGHT
        } else if (dx < 0) {
            this.sh = 55; //direction LEFT
        } else if (dy > 0) {
            this.sh = 0; //direction DOWN
        } else if (dy < 0) {
            this.sh = 165;//direction UP
        }
    }
}
