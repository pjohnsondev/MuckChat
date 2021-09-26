package muck.client.utilities;

import com.esotericsoftware.kryonet.Client;
import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.core.structures.UserStructure;

/**
 * Can give or take points using these static functions. Will modify points on active user session and send the points to the
 * backend to be stored in the database.
 *
 * author: Ethan Carlsson ecarlsso@myune.edu.au
 */
public class Points {
    /**
     * @param points how many points you want to give.
     */
    public static void givePlayerPoints(int points) {
        // Make sure there's a user to give points to before giving them points
        if (ActiveUser.getInstance().getUser() != null) {
            UserStructure activeUser = ActiveUser.getInstance().getUser();
            activeUser.points += points;
            Client client = MuckClient.getINSTANCE().getClient();
            // No client in testing
            if (client != null) {
                client.sendTCP(activeUser);
            }
        }
    }

    /**
     * This is equivalent to Points.givePlayerPoints(-points).
     *
     * @param points how many points you want to take away.
     */
    public static void takePlayerPoints(int points) {
        Points.givePlayerPoints(points*-1);
    }
}
