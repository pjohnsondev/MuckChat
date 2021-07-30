package muck.client;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.*;

/* Hi guys. In order to implement this into the main window I had to change a lot of your code.
I needed to make this not an application firstly as it cannot run on its own and be a node in javaFX.
This now means that to test it you need to run launcher to see it.
Your code however is really good! I cannot wait to see what you come up with next
 */

public class GameMap extends Canvas {

    TileMapReader tm = new TileMapReader("/Test.tmx");
    Sprite hero = new Sprite(10,10,5, 5); //Create the player sprite
    private int startX; //The first tile to be drawn
    private int startY; //The first tile to be drawn
    private int offX; //Tile offset in pixels as hero moves pixel by pixel
    private int offY; //Tile offset in pixels as hero moves pixel by pixel
    private double centerX; //Center of the screen
    private double centerY; //Center of the screen
    private double cameraX; //Top left corner of our viewport
    private double cameraY; //Top left corner of our viewport

    /**
     * GameMap constuctor accepts the canvas to be drawn onto.
     * Creates the hero sprite
     * Sets-up the camera viewport Credit: Toni Epple blog for viewport design https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
     * Draws the tiles around the hero (x,y) based on viewport size
     * Provides the animation loop using AnimationTimer
     * @param canvas convas to be drawn onto.
     */
    public GameMap(Canvas canvas) {
        //Get the graphic context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Load the image
        String imagePath = "/terrain_atlas.png"; //hard coded needs to be passable
        Image image = new Image(imagePath);
        Sprite hero = new Sprite(10,10,5, 5); //Create the player sprite

        centerX = canvas.getWidth() / 2; //Viewport midpoint
        centerY = canvas.getHeight() / 2; //Viewport midpoint

        cameraX = hero.getX() - centerX; //Camera top left relative to hero X
        cameraY = hero.getY() - centerY; //Camera top left relative to hero Y

        //Ensure the camera does not go outside the game boundaries
        cameraPositionCheck();

        double screenHeightInTiles = canvas.getHeight() / tm.getTileHeight();
        double screenWidthInTiles = canvas.getWidth() / tm.getTileWidth();

        startX = (int) (cameraX / tm.getTileWidth());
        startY = (int) (cameraY / tm.getTileHeight());
        offX = (int) (cameraX % tm.getTileWidth());
        offY = (int) (cameraY % tm.getTileWidth());

        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus()); //after map clicked listen for keyboard events


        //Game loop draw the canvas
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (int y = 0; y < screenHeightInTiles; y++) {
                    for (int x = 0; x < screenWidthInTiles; x++) {
                        int GID = getTileIndex(x + startX, y + startY);
                        gc.save();
                        //Translate the viewport around the hero. (Easier to relative draw)
                        gc.translate((x * tm.getTileWidth())- offX, (y * tm.getTileHeight()) - offY);
                        drawTile(gc,GID, image, x, y);

                        //Restore the old state
                        gc.restore();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * The draw tile method draws a single tile based on the input parameters
     * @param gc : The graphics context for our canvas
     * @param tileIndex : The GID of the tile to be drawn
     * @param tileImage : The tileSet image
     * @param destX : The destination X
     * @param destY : The destination Y
     */
    public void drawTile(GraphicsContext gc, int tileIndex, Image tileImage, int destX, int destY) {
        int tileWidth = tm.getTileWidth();
        int tileHeight = tm.getTileHeight();
        int cols = tm.getTileColumns();

        int x = tileIndex % cols;
        int y = tileIndex / cols;

        gc.drawImage(tileImage, x * tileWidth, y* tileHeight, tileWidth, tileHeight, 0, 0, tileWidth, tileHeight);
    }

    /**
     * The getTileIndex method returns the GID of the tile to be drawn
     * @param x : The x position of the tile on the TMX map.
     * @param y : The y position of the tile on the TMX map.
     * @return : The GID of the tile to be drawn
     */
    public int getTileIndex(int x, int y) {
        int layer = 0; //future use
        int tileId = tm.getLayerId(layer,x, y);
        return tileId;
    }

    /**
     * The method cameraPositionCheck ensures the users viewport does
     * not go outside of the tmx map
     * cameraMaxX maximum width of TMX map minus the viewport width
     * cameraMaxY maximum height of the TMX map minus the viewport height
     */
    public void cameraPositionCheck() {
        double cameraMaxX = tm.getWidth() * tm.getTileWidth() - (centerX * 2);
        double cameraMaxY = tm.getHeight() * tm.getTileHeight() - (centerY * 2);
        if(cameraX >= cameraMaxX) {
            cameraX = cameraMaxX;
        }
        if(cameraY >= cameraMaxY) {
            cameraY = cameraMaxY;
        }
        if(cameraX < 0) {
            cameraX = 0;
        }
        if(cameraY < 0) {
            cameraY = 0;
        }
    }
}
