package muck.client;

import javafx.application.Application;
import javafx.event.EventHandler;
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

/**
 * The Game Map class...
 */
public class GameMap extends Canvas implements EventHandler<KeyEvent> {

    TileMapReader tm = new TileMapReader("/Test.tmx");
    Sprite hero = new Sprite(300,300,5, 5); //Create the player sprite
    private int startX; //The first tile to be drawn
    private int startY; //The first tile to be drawn
    private int offX; //Tile offset in pixels as hero moves pixel by pixel
    private int offY; //Tile offset in pixels as hero moves pixel by pixel
    private double centerX; //Center of the screen
    private double centerY; //Center of the screen
    private double cameraX; //Top left corner of our viewport
    private double cameraY; //Top left corner of our viewport

    Rectangle rectangle = new Rectangle();

    /**
     * GameMap constuctor accepts the canvas to be drawn onto.
     * Creates the hero sprite
     * Sets-up the camera viewport Credit: Toni Epple blog for viewport design https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
     * Draws the tiles around the hero (x,y) based on viewport size
     * Provides the animation loop using AnimationTimer
     * @param canvas The gameWindow canvas to be drawn onto.
     */
    public GameMap(Canvas canvas) {
        //Get the graphic context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Load the image
        String imagePath = "/terrain_atlas.png"; //hard coded needs to be passable
        Image image = new Image(imagePath);

        centerX = canvas.getWidth() / 2; //Viewport midpoint
        centerY = canvas.getHeight() / 2; //Viewport midpoint

        double screenHeightInTiles = (centerY * 2) / tm.getTileHeight();
        double screenWidthInTiles = (centerX * 2) / tm.getTileWidth();

        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus()); //after map clicked listen for keyboard events

        canvas.setOnKeyPressed(this);
        canvas.setOnKeyReleased(this);

        //Game loop draw the canvas
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                hero.move();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                cameraX = hero.getX() - centerX; //Camera top left relative to hero X
                cameraY = hero.getY() - centerY; //Camera top left relative to hero Y
                //Ensure the camera does not go outside the game boundaries
                cameraPositionCheck();
                startX = (int) (cameraX / tm.getTileWidth());
                startY = (int) (cameraY / tm.getTileHeight());
                offX = (int) (cameraX % tm.getTileWidth());
                offY = (int) (cameraY % tm.getTileWidth());

                for (int y = 0; y <= screenHeightInTiles + 1; y++) {
                    for (int x = 0; x <= screenWidthInTiles + 1; x++) {
                        canvas.requestFocus();
                        int GID = getTileIndex(x + startX, y + startY);
                        gc.save();
                        //Translate the viewport around the hero. (Easier to relative draw)
                        gc.translate((x * tm.getTileWidth())- offX, (y * tm.getTileHeight()) - offY);
                        drawTile(gc,GID, image, x, y);
                        //Restore the old state
                        gc.restore();
                    }
                }
                canvas.requestFocus();
                drawHero(gc, rectangle);
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

    private void drawHero(GraphicsContext gc,Rectangle rect){
        double drawX = 0;
        double drawY = 0;
        if (hero.getX() < centerX) {
            drawX = hero.getX();
        } else  { drawX = centerX; }
        if (hero.getY() < centerY) {
            drawY = hero.getY();
        } else { drawY = centerY; }

        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(drawX,
                drawY,
                10,
                10);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
    }

    @Override
    public void handle(KeyEvent e) {

        // Get the Type of the Event
        String type = e.getEventType().getName();

        // Get the KeyCode of the Event
        KeyCode keyCode = e.getCode();

        // Handle Hero movement
        if (type == "KEY_PRESSED" && keyCode == KeyCode.D) {
            hero.setDX(5);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.D) {
            hero.setDX(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.S) {
            hero.setDY(5);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.S) {
            hero.setDY(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.A) {
            hero.setDX(-5);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.A) {
            hero.setDX(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.W) {
            hero.setDY(-5);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.W) {
            hero.setDY(0);
        }
    }
}
