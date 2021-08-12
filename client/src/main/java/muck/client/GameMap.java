package muck.client;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.*;

/**
 * The Game Map class...
 */
public class GameMap extends Canvas implements EventHandler<KeyEvent> {

    TileMapReader tm = new TileMapReader("/map.tmx");
    Sprite hero = new Sprite(300,300,5, 5); //Create the player sprite
    private int startX; //The first tile to be drawn
    private int startY; //The first tile to be drawn
    private int offX; //Tile offset in pixels as hero moves pixel by pixel
    private int offY; //Tile offset in pixels as hero moves pixel by pixel
    private double centerX; //Center of the screen
    private double centerY; //Center of the screen
    private double cameraX; //Top left corner of our viewport
    private double cameraY; //Top left corner of our viewport
    private int layer = 0;
    private int tileId = 0;
    private int GID = 0;
    int n =0; //water animation
    Rectangle rectangle = new Rectangle();
    double screenHeightInTiles;
    double screenWidthInTiles;
    GraphicsContext gc;
    Image image;
    double cameraMaxX;
    double cameraMaxY;

    /**
     * GameMap constructor accepts the canvas to be drawn onto.
     * Creates the hero sprite
     * Sets-up the camera viewport Credit: Toni Epple blog for viewport design https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
     * Draws the tiles around the hero (x,y) based on viewport size
     * Provides the animation loop using AnimationTimer
     * @param canvas The gameWindow canvas to be drawn onto.
     */
    public GameMap(Canvas canvas) {
        //Get the graphic context of the canvas
        gc = canvas.getGraphicsContext2D();
        //Load the image
        String imagePath = "/texture.png"; //hard coded needs to be passable
        image = new Image(imagePath);

        centerX = canvas.getWidth() / 2; //Viewport midpoint (half the width of the canvas size)
        centerY = canvas.getHeight() / 2; //Viewport midpoint (half the height of the canvas size)

        cameraMaxX = tm.getWidth() * tm.getTileWidth() - (centerX * 2); //width of tile map in tiles multiply by width of tiles in pixels. Minus width of screen in pixels
        cameraMaxY = tm.getHeight() * tm.getTileHeight() - (centerY * 2);

        screenHeightInTiles = (centerY * 2) / tm.getTileHeight();
        screenWidthInTiles = (centerX * 2) / tm.getTileWidth();

        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus()); //after map clicked listen for keyboard events

        canvas.setOnKeyPressed(this);
        canvas.setOnKeyReleased(this);

        //Game loop draw the canvas
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                hero.move(tm, hero, canvas);
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                cameraX = hero.getX() - centerX; //Camera top left relative to hero X
                cameraY = hero.getY() - centerY; //Camera top left relative to hero Y
                //Ensure the camera does not go outside the game boundaries
                cameraPositionCheck();
                startX = (int) (cameraX / tm.getTileWidth());
                startY = (int) (cameraY / tm.getTileHeight());
                offX = (int) (cameraX % tm.getTileWidth());
                offY = (int) (cameraY % tm.getTileWidth());

                drawLayer(0); //draws a single layer pass the layer number (floor)
                n++;
                drawLayer(1);
                if (n <15 ) {
                    drawLayer(3); //Water animation layer
                }
                if (n > 30) { n=0;} //reset water animation timer
                drawLayer(2);

                drawHero(gc, rectangle);
                //TODO render all other player sprites here
                drawLayer(4);
            }
        };
        timer.start();
    }

    /**
     *
     * @param layer
     */
    public void drawLayer(int layer) {
        for (int y = 0; y <= screenHeightInTiles + 1; y++) {
            for (int x = 0; x <= screenWidthInTiles + 1; x++) {

                GID = getTileIndex(x + startX, y + startY, layer);
                if (GID != -1) { //Don't render blank tiles in layers (0 with -1 offset)
                    gc.save();
                    //Translate the viewport around the hero. (Easier to relative draw)
                    gc.translate((x * tm.getTileWidth()) - offX, (y * tm.getTileHeight()) - offY);
                    drawTile(gc, GID, image, x, y);
                    //Restore the old state
                    gc.restore();
                }
            }
        }
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
    public int getTileIndex(int x, int y, int layer) {
        tileId = tm.getLayerId(layer,x, y);
        return tileId;
    }

    /**
     * The method cameraPositionCheck ensures the users viewport does
     * not go outside of the tmx map
     * cameraMaxX maximum width of TMX map minus the viewport width
     * cameraMaxY maximum height of the TMX map minus the viewport height
     */
    public void cameraPositionCheck() {

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

        if (hero.getX() < centerX ) {
            drawX = hero.getX();
        } else if (hero.getX() > (tm.getWidth()*tm.getTileWidth() - centerX)) { drawX = ((centerX) - ((tm.getWidth()*tm.getTileWidth()) - (double)hero.getX())) + (centerX); }
        else { drawX = centerX; }
        if (hero.getY() < centerY ) {
            drawY = hero.getY();
        } else if (hero.getY() > (tm.getHeight()*tm.getTileHeight() - centerY)) { drawY = ((centerY) - ((tm.getHeight()*tm.getTileHeight()) - (double)hero.getY())) + (centerY);
        } else { drawY = centerY; }


        gc.setFill(Color.BLUE);
        gc.fillRect(drawX -5,
                drawY -5 ,
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
            hero.setDX(1);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.D) {
            hero.setDX(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.S) {
            hero.setDY(1);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.S) {
            hero.setDY(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.A) {
            hero.setDX(-1);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.A) {
            hero.setDX(0);
        }
        if (type == "KEY_PRESSED" && keyCode == KeyCode.W) {
            hero.setDY(-1);
        }
        if (type == "KEY_RELEASED" & keyCode == KeyCode.W) {
            hero.setDY(0);
        }
    }
}

