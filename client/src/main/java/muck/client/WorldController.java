package muck.client;

import javafx.scene.canvas.Canvas;
import muck.core.Location;

public class WorldController {
    static int x, y;

    public static int locationCheck(Location player, int id, Canvas canvas) {
        x = player.getX();
        y = player.getY();
        //id 1 = world (homeTown.tmx)
        if( id == 1) {
            if (x > 128 && x < 160) { //Inn
                if (y > 224 && y < 260) {
                    //TODO Achievement "You explored the Inn"
                    TileMapReader tm = new TileMapReader("/maps/homeTown.tmx");
                    GameMap gm = new GameMap(canvas, "/tilesets/texture.png", tm);
                    gm.worldID = 1;
                    gm.hero.setPosX(145);
                    gm.hero.setPosY(290);
                    return 1;
                }
            }
        }
        //id 1 = world (homeTown.tmx)
        if (id == 1 ){
            if (x > 100 && x < 120) { // Cave
                if (y > 352 && y < 390) {
                    System.out.println("You made it to the cave!");
                    //TODO Achievement "You explored the secret cave!"
                    TileMapReader tm = new TileMapReader("/maps/cave.tmx");
                    GameMap gm = new GameMap(canvas, "/tilesets/terrain_atlas.png", tm);
                    gm.worldID = 2;
                    gm.hero.sh = 165; //point hero upwards
                    gm.hero.setPosX(512);
                    gm.hero.setPosY(988);
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
                    GameMap gm = new GameMap(canvas, "/tilesets/texture.png", tm);
                    gm.worldID = 1;
                    gm.hero.setPosX(110);
                    gm.hero.setPosY(390);
                    return 1;
                }
            }
        }
        return 0;
    }
}
