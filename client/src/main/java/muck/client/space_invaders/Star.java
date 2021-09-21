package muck.client.space_invaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import java.util.Random;
import muck.client.space_invaders.SpaceInvadersUtility;

public class Star extends Ellipse {

    private static final Random RAND = new Random();

    /**
     * Constructor:
     * Extends Ellipse class and automatically assigns values
     */
    public Star() {
        super(RAND.nextInt(SpaceInvadersUtility.WIDTH), 0, RAND.nextDouble() + 1, RAND.nextInt(5) + 1 );
        this.setFill(Color.WHITE);
        this.setOpacity((RAND.nextFloat() % 0.8) + 0.1);
    }

    /**
     * Function name: moveStar
     * Purpose: Changes the y position of the object to move the star
     * Return: void
     */
    public void moveStar() {
        this.setCenterY(this.getCenterY() + 5);
    }

}
