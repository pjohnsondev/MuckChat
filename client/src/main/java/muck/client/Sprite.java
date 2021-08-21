package muck.client;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/*
Collaboration:
bnolan9
dandre20

Player movement class

Requirements:
Sprite.java
 */
public class Sprite {
    private enum Direction {
        Left, Right, Up, Down
    }
    //direction x - should always be at 0 to be still, otherwise it's moving
    //direction y - should always be at 0 to be still, otherwise it's moving
    private int dx, dy; // define directional x and y var

    private Image image; // define image var
    private int imageOffsetX = 1; // offset left and top screen boundaries for player image
    private int imageOffsetY = 4; // offset right and bottom screen boundaries for player image
    private int playerImageSizeW = 10; // player image width
    private int playerImageSizeH = 10; // player image height
    private String avatar;

    private final String avatarImageLoc = "client/src/main/resources/images/"; // image location of avatar images

    private int x, y, width, height;

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
    public Sprite(int x, int y, int width, int height, String avatar) { //Add laterString url
        this.x = x; //spawn location
        this.y = y; //spawn location
        this.width = width;
        this.height = height;
        this.avatar = avatar;

        avatar = "/images/pikachu.png";
        this.image = new Image(avatar);
    }

    public void move(TileMapReader tm, Sprite hero, Canvas canvas) {
        int newX = hero.getPosX() + dx;
        int newY = hero.getPosY() + dy;

        //Layer 1 solid
        int GID = tm.getLayerId(1, Math.abs(newX/tm.getTileWidth()), Math.abs(newY/tm.getTileHeight()) );
        if (GID == -1 ) { //collision detection
            if(newX > 5 && newX < tm.getWidth()*tm.getTileWidth()-5) {
                x += dx;
            }
            if(newY > 5 && newY < tm.getHeight()*tm.getTileHeight()-5)
                y += dy;
            }
    }

    // getters and setters
    public void setDx(int dx) { this.dx = dx; } // set direction x
    public void setDy(int dy) { this.dy = dy; } // set direction y
    public int getPosX() { return x; } // get player x Pos
    public int getPosY() { return y; } // get player y pos
    public String getPlayerPos() { return getPosX()+","+getPosY(); } // concatenate x and y pos as x,y - will be used to retrieve current player pos
    public Image getImage() { return image; } // get image (resized one)

    public static void drawHero(GraphicsContext gc, Rectangle rect, TileMapReader tm, Sprite hero, double centerX, double centerY) {

        double drawX = 0;
        double drawY = 0;

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
        gc.drawImage(hero.image, 0,0, 190,200, drawX - 13,drawY - 28,26,30);
    }
}

