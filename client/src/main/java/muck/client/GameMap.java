package muck.client;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.animation.*;

public class GameMap {

    TileMapReader tm = new TileMapReader("/Test.tmx");
    int startX = 10;
    int startY = 0;

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public GameMap(Stage stage) {
        //Create the canvas
        Canvas canvas = new Canvas(600, 600);
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

        //Create the Pane
        Pane root = new Pane();
        //Set the Style-properties of the Pane
        //Add the Canvas to the Pane
        root.getChildren().add(canvas);
        //Create the scene
        Scene scene = new Scene(root);
        //Add the scene to the stage
        stage.setScene(scene);
        //Set the title of teh stage
        stage.setTitle("Drawing an Image on a Canvas");
        //Display the stage
        stage.show();
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
