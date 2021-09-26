package muck.client.utilities;

import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.core.structures.PointsStructure;
import muck.core.structures.UserStructure;

/**
 * Can give or take points using these static functions. Will modify points on active user session and send the points to the
 * backend to be stored in the database.
 *
 * author: Ethan Carlsson ecarlsso@myune.edu.au
 */
public class Points {
    public static void givePlayerPoints(int points) {

        // Make sure there's a user to give points to before giving them points
        if (ActiveUser.getInstance().getUser() != null) {
            UserStructure activeUser = ActiveUser.getInstance().getUser();
            activeUser.points += points;
            PointsStructure pointsStructure = new PointsStructure();

            pointsStructure.points = activeUser.points;


            MuckClient.getINSTANCE().getClient().sendTCP(activeUser);
        }
    }

    public static void takePlayerPoints(int points) {
        Points.givePlayerPoints(points*-1);
    }
}
