package muck.client.utilities;

import com.esotericsoftware.kryonet.Client;
import muck.client.MuckClient;
import muck.client.components.ActiveUser;
import muck.core.structures.PointsStructure;
import muck.core.structures.UserStructure;

public class Points {
    public static void givePlayerPoints(int points) {
        UserStructure activeUser = ActiveUser.getInstance().getUser();
        activeUser.points += points;
        PointsStructure pointsStructure = new PointsStructure();

        pointsStructure.points = activeUser.points;


        MuckClient.getINSTANCE().getClient().sendTCP(activeUser);
    }
    public static void takePlayerPoints(int points) {
        Points.givePlayerPoints(points*-1);
    }
}
