package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTests {
    @Test
    void TestGetters() {
	var x = 2;
	var y = 3;

	var loc = new Location(x,y);
	var pair = new Pair<Integer, Integer>(x,y);

	assertEquals(x, loc.getX());
	assertEquals(y, loc.getY());
	assertEquals(pair, loc.getLocation());
    }
}
