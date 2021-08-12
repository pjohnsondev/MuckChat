package muck.client;


import javafx.scene.canvas.Canvas;

public class Sprite {
    private enum Direction {
        Left, Right, Up, Down
    }

    private int x, y, width, height;
    int dx, dy;

    public Sprite(int x, int y, int width, int height) { //Add laterString url
        this.x = x; //spawn location
        this.y = y; //spawn location
        this.width = width;
        this.height = height;
    }

    public void move(TileMapReader tm, Sprite hero, Canvas canvas) {
        int newX = hero.getX() + dx;
        int newY = hero.getY() + dy;

        int GID = tm.getLayerId(2, Math.abs(newX/tm.getTileWidth()), Math.abs(newY/tm.getTileHeight()) );
        if (GID == -1 ) { //collision detection
            if(newX > 5 && newX < canvas.getWidth()-5) {
                x += dx;
            }
            if(newY > 5 && newY < canvas.getHeight()-5)
                y += dy;
            }
    }

    public int getX() { return x;}
    public int getY() { return y;}
    public void setDX(int deltaX) {this.dx = deltaX;}
    public void setDY(int deltaY) {this.dy = deltaY;}

}
