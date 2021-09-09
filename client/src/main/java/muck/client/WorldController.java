package muck.client;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import muck.client.enduring_fantasy.LandingPageEf;
import muck.client.space_invaders.LandingPage;
import muck.core.Location;

/**
 * The WordController class controls movement between worlds.
 * It allows the doors in the HomeTown to act as doorways to the mini-games.
 */
public class WorldController {
    static int x, y;
    private static BorderPane gamePane;

    public static int locationCheck(Location player, BorderPane borderPane, int id, Canvas canvas) {
        gamePane = borderPane;
        //id 1 = world (homeTown.tmx)
        if (id == 1 ){
            if (x > 100 && x < 120) { // Cave
                if (y > 352 && y < 390) {
                    System.out.println("You made it to the cave!");
                    //TODO Achievement "You explored the secret cave!"
                    TileMapReader tm = new TileMapReader("/maps/cave.tmx");
                    GameMap gm = new GameMap(canvas, gamePane,  "/tilesets/terrain_atlas.png", tm);
                    gm.worldID = 2;
                    gm.hero.sh = 165; //point hero upwards
                    gm.hero.setPosX(512);
                    gm.hero.setPosY(988);
                    return 1;
                }
            }
        }
        //id 1 = homeTown
        if (id == 1) {
            if (x > 128 && x < 160) { //Inn
                if (y > 224 && y < 260) {
                    //Opens space invaders game
                    gamePane.getChildren().clear();
                    Canvas SICanvas = new Canvas();
                    SICanvas.setHeight(canvas.getHeight());
                    SICanvas.setWidth(canvas.getWidth());
                    gamePane.setCenter(SICanvas);
                    BorderPane.setAlignment(SICanvas, Pos.CENTER);
                    new LandingPage(gamePane, SICanvas);
                    return 1;
                }
            }
        }
        //id 1 = homeTown
        if (id == 1) {
            if (x > 552 && x < 565) { //Small house
                if (y > 192 && y < 198) {
                    //Opens space invaders game
                    gamePane.getChildren().clear();
                    Canvas EFCanvas = new Canvas();
                    EFCanvas.setHeight(canvas.getHeight());
                    EFCanvas.setWidth(canvas.getWidth());
                    gamePane.setCenter(EFCanvas);
                    BorderPane.setAlignment(EFCanvas, Pos.CENTER);
                    new LandingPageEf(gamePane, EFCanvas);
                    return 1;
                }
            }
        }

        //Secret Cave = 2
        if (id == 2 ){
            if (x > 480 && x < 540) { // Cave entrance
                if (y > 988) {
                    System.out.println("You returned from the cave!");
                    TileMapReader tm = new TileMapReader("/maps/homeTown.tmx");
                    GameMap gm = new GameMap(canvas, gamePane, "/tilesets/texture.png", tm);
                    gm.worldID = 1;
                    gm.hero.setPosX(110);
                    gm.hero.setPosY(390);
                    return 1; //Kills this instance of GameMap
                }
            }
        }
        return 0; //If no location explored (won't kill this instance of the GameMap in GameMap.java
    }

}
