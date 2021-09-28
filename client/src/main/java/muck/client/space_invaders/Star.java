package muck.client.space_invaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import java.util.List;
import java.util.Random;

public class Star extends Ellipse {

    private static final Random RAND = new Random();

    /**
     * Constructor:
     * Extends Ellipse class and automatically assigns values
     */
    public Star() {
        super(RAND.nextInt((int)SpaceInvadersUtility.WIDTH), 0, RAND.nextDouble() + 1, RAND.nextInt(5) + 1 );
        this.setFill(Color.WHITE);
        this.setOpacity((RAND.nextFloat() % 0.8) + 0.1);
    }

    /**
     * Function name: moveStar
     * Purpose: Changes the y position of the object to move the star
     * Return: void
     */
    void moveStar() {
        this.setCenterY(this.getCenterY() + 5);
    }


    /**
     * Function name: createAndMoveStars
     * Purpose: Generates, removes, and moves star objects
     * @param stars: List to add stars to
     * @param gc: Canvas on which the stars are to be drawn
     * Return: void
     */
    public static void createAndMoveStars(GraphicsContext gc, List<Star> stars){
        // Star generation
        stars.forEach(Star::moveStar);
        if (RAND.nextInt(10) > 2) {
            stars.add(new Star());
        }

        // Draw star on canvas
        for (int i = 0; i < stars.size(); i++) {
            gc.setFill(stars.get(i).getFill());
            gc.fillOval(stars.get(i).getCenterX(), stars.get(i).getCenterY(),
                    stars.get(i).getRadiusX(), stars.get(i).getRadiusY());

            // Remove star if it leaves the bounds of the canvas
            if (stars.get(i).getCenterY() > SpaceInvadersUtility.HEIGHT)
                stars.remove(i);
        }
    }
}
