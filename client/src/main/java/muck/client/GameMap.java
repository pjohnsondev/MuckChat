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
    int startX = 10;
    int startY = 0;

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public GameMap(Canvas canvas) {
        //Get the graphic context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Load the image
        String imagePath = "/terrain_atlas.png";
        Image image = new Image(imagePath);

        double screenHeightInTiles = canvas.getHeight() / tm.getTileHeight();
        double screenWidthInTiles = canvas.getWidth() / tm.getTileWidth();

        for (int y = 0; y < screenHeightInTiles; y++) {
            for(int x = 0; x < screenWidthInTiles; x++) {
                drawTile(gc, getTileIndex(x + startX, y + startY), image, x, y);
            }
        }
    }

    public void drawTile(GraphicsContext gc, int tileIndex, Image tileImage, int destX, int destY) {
        int tileWidth = tm.getTileWidth();
        int tileHeight = tm.getTileHeight();
        int cols = tm.getTileColumns();

        int x = tileIndex % cols;
        int y = tileIndex / cols;

        gc.drawImage(tileImage, x * tileWidth, y* tileHeight, tileWidth, tileHeight, destX * tileWidth, destY  * tileWidth, tileWidth, tileHeight);
    }

    public int getTileIndex(int x, int y) {
        int layer = 0; //future use
        int tileId = tm.getLayerId(layer,x, y);
        return tileId;
    }


}
