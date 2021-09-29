package muck.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import muck.core.Id;
import muck.core.Location;
import muck.core.MapId;
import muck.core.AvatarLocation;
import muck.core.ClientId;

public class ProximityFilterTest {

    @Test
    public void testDistFuncction1() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(0,0,0,0) == 0.0);
    }
    @Test
    public void testDistFuncction2() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(2,2,2,-4) == 6.0);
    }
    @Test
    public void testDistFuncction3() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(0,3,0,5) == 2.0);
    }
    @Test
    public void testDistFuncction4() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(-2,1,-2,3) == 2.0);
    }
    @Test
    public void testGetIDsInRangeOf() {
        ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();
        ProximityFilter PF = new ProximityFilter();

        track.addClient(new Id<ClientId>("1111"), new AvatarLocation("Me"), new MapId(2), new Location(0, 0));
		track.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 1"), new MapId(2), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new AvatarLocation("Test Name 2"), new MapId(2), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name 3"), new MapId(2), new Location(0, 1));

        assertTrue(PF.getIDsInRangeOf(new Id<ClientId>("1111"), 10, track).size() == 3);
    }
}
