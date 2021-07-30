package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTests {
	@Test
	void TestGetters() {
		var x = 2;
		var y = 3;

		var loc = new Location(x, y);
		var pair = new Pair<Integer, Integer>(x, y);

		assertEquals(x, loc.getX());
		assertEquals(y, loc.getY());
		assertEquals(pair, loc.getLocation());
	}

	@Test
	void TestEqualityForEqualLocations() {

		var x = 2;
		var y = 3;

		var loc = new Location(x, y);

		assertNotEquals(new Location(5, 6), loc);
		assertEquals(new Location(x, y), loc);
	}

	@Test
	void OnlyLocationsWithTheSameXYCoordsHaveTheSameHashCode() {

		var x = 2;
		var y = 3;

		var hash1 = new Location(x, y).hashCode();
		var hash2 = new Location(x, y).hashCode();
		var diffHash = new Location(1, 1).hashCode();
		assertEquals(hash1, hash2);
		assertNotEquals(hash1, diffHash);
	}

	@Test
	void LocationsWithDifferentXYCoordsHaveDifferentHashCodes() {
		var x = 2;
		var y = 3;

		var hash1 = new Location(x, y).hashCode();
		var hash2 = new Location(x, y).hashCode();

		assertNotEquals(hash1, hash2);
	}

	@Test
	void LocationsWithInverseXYCoordsHaveDifferentHashCodes() {
		var x = 2;
		var y = 3;

		var hash1 = new Location(x, y).hashCode();
		var hash2 = new Location(y, x).hashCode();

		assertNotEquals(hash1, hash2);
	}

    @Test
	void LocationsWithInverseXYCoordsAreNotEqual() {
		var x = 2;
		var y = 3;

		var loc1 = new Location(x, y);
		var loc2 = new Location(y, x);

		assertNotEquals(loc1, loc2);
    }

    @Test
	void LocationUpdatedProduceExpectedLocation() {

	var loc = new Location(1, 2).update(new Pair(2,3));
		var expected = new Location(2, 3);

		assertEquals(expected, loc);
		assertEquals(expected.hashCode(), loc.hashCode());
	}
}
