package muck.client;

import javafx.scene.canvas.Canvas;
import muck.core.Location;

public class WorldController {
    static int x, y;

    public static int locationCheck(Location player, int id, Canvas canvas, Sprite hero) {
        x = player.getX();
        y = player.getY();
        if( id == 1) {
            if (x > 128 && x < 160) {
                if (y > 224 && y < 260) {
                    System.out.println("You have made it here!");
                    TileMapReader tm = new TileMapReader("/map.tmx");
                    GameMap gm = new GameMap(canvas, "/texture.png", tm);
                    gm.worldID = 2;
                    gm.hero.setPosX(10);
                    gm.hero.setPosY(10);
                    return 1;
                }
            }
        }
        return 0;
    }
}
