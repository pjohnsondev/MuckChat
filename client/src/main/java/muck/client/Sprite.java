package muck.client;

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

    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() { return x;}
    public int getY() { return y;}
    public void setX(int deltaX) {this.dx = deltaX;}
    public void setY(int deltaY) {this.dx = deltaY;}

}
