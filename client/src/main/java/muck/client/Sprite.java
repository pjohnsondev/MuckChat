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
    public void setDX(int deltaX) {this.dx = deltaX;}
    public void setDY(int deltaY) {this.dy = deltaY;}

}
