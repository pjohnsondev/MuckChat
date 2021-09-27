package muck.client;

import muck.client.components.ActiveUser;
import muck.client.utilities.Points;
import muck.core.structures.UserStructure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointsTest {

    @Test
    public void testGivePlayerPoints_withActiveUserInstantiated() {
        UserStructure userStructure = new UserStructure();
        userStructure.points = 0;
        assertEquals(0, userStructure.points);
        ActiveUser.getInstance().setUserStructure(userStructure);
        Points.givePlayerPoints(20);
        assertEquals(20, userStructure.points);
        ActiveUser.destroyInstance();
    }

    @Test
    public void testTakePlayerPoints_withActiveUserInstantiated() {
        UserStructure userStructure = new UserStructure();
        userStructure.points = 0;
        assertEquals(0, userStructure.points);
        ActiveUser.getInstance().setUserStructure(userStructure);
        Points.takePlayerPoints(20);
        assertEquals(-20, userStructure.points);
        ActiveUser.destroyInstance();
    }


}
