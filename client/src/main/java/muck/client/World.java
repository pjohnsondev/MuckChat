package muck.client;

// team-name-here

public class World {
    public int width;
    public int height;
    public int[][] texture_grid;   // used to map textures to tiles on the grid
    public int[][] collision_grid; // map used when player attempts to move. If the cell they attempt to move to has value 0, it is rejected

    // constructor
    public World(int w, int h) {
        width = w;
        height = h;
        texture_grid = new int[width][height];
        collision_grid = new int[width][height];
    }
}
