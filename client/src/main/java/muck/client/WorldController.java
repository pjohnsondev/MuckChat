package muck.client;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import muck.client.enduring_fantasy.LandingPageEf;
import muck.client.frogger.LandingPageFrogger;
import muck.client.space_invaders.LandingPage;
import muck.client.tictactoe.TTTLandingPage;
import muck.core.Location;
import static muck.client.Achievements.*;

/**
 * The WordController class controls movement between worlds.
 * It allows the doors in the HomeTown to act as doorways to the mini-games.
 */
public class WorldController {
    static int x, y;
    private static BorderPane gamePane;

    /**
     * The location check class checks a players location on the map to see if they have,
     * landed on a special position.
     * @param player the users hero
     * @param borderPane this is the parent borderPane for the gameMap allowing a new canvas for mini-games
     * @param id this is the map id/world id that the user is on 1=homeTown 2=Cave
     * @param canvas Canvas that the world/mini-map is rendered to
     * @return
     */
    public static int locationCheck(Location player, BorderPane borderPane, int id, Canvas canvas) {
        x = player.getX();
        y = player.getY();
        gamePane = borderPane;
        //id 1 = world (homeTown.tmx)
        if (id == 1 ){
            if (x > 100 && x < 120) { // Cave
                if (y > 352 && y < 390) {
                    // Unlocks achievement 4 when the cave is visited.
                    Achievements achieve4 = new Achievements(achievement4, achievement4Title, achievement4Description);
                    achieve4.achievementUnlock(achieve4);
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
                    // Unlocks achievement 1 when the cave is visited.
                    Achievements achieve1 = new Achievements(achievement1, achievement1Title, achievement1Description);
                    achieve1.achievementUnlock(achieve1);
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
                    // Unlocks achievement 3 when the house is visited.
                    Achievements achieve3 = new Achievements(achievement3, achievement3Title, achievement3Description);
                    achieve3.achievementUnlock(achieve3);
                    //Opens Enduring Fantasy
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

        //id 1 = homeTown
        if (id == 1) {
            if (x > 778 && x < 793) { //Shop house
                if (y > 288 && y < 295) {
                    // Unlocks achievement 2 when the shop is visited.
                    Achievements achieve2 = new Achievements(achievement2, achievement2Title, achievement2Description);
                    achieve2.achievementUnlock(achieve2);
                    //Opens Frogger game
                    gamePane.getChildren().clear();
                    Canvas FrCanvas = new Canvas();
                    FrCanvas.setHeight(canvas.getHeight());
                    FrCanvas.setWidth(canvas.getWidth());
                    gamePane.setCenter(FrCanvas);
                    BorderPane.setAlignment(FrCanvas, Pos.CENTER);
                    new LandingPageFrogger(gamePane, FrCanvas);
                    return 1;
                }
            }
        }
        //id 1 = homeTown
        if (id == 1) {
            if (x > 808 && x < 822) { //Cottage house
                if (y > 512 && y < 519) {
                    // Unlocks achievement 5 when the cottage is visited.
                    Achievements achieve5 = new Achievements(achievement5, achievement5Title, achievement5Description);
                    achieve5.achievementUnlock(achieve5);
                    //Opens tic-tac-toe game
                    gamePane.getChildren().clear();
                    Canvas TTTCanvas = new Canvas();
                    TTTCanvas.setHeight(canvas.getHeight());
                    TTTCanvas.setWidth(canvas.getWidth());
                    gamePane.setCenter(TTTCanvas);
                    BorderPane.setAlignment(TTTCanvas, Pos.CENTER);
                    new TTTLandingPage(gamePane, TTTCanvas);
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

        //Secret Cave = 2
        /** Not yet complete but GoFish at the base of the waterfall in the secret cave
        if (id == 2 ){
            if (x > 269 && x < 287) { // Cave entrance
                if (y > 516 && y < 543) {
                    //Opens Go-Fish game
                    gamePane.getChildren().clear();
                    Canvas GFCanvas = new Canvas();
                    GFCanvas.setHeight(canvas.getHeight());
                    GFCanvas.setWidth(canvas.getWidth());
                    gamePane.setCenter(GFCanvas);
                    BorderPane.setAlignment(GFCanvas, Pos.CENTER);
                    //new GoFish(gamePane, GFCanvas);
                    return 1;
                }
            }
        } **/

        return 0; //If no location explored (won't kill this instance of the GameMap in GameMap.java
    }
}
