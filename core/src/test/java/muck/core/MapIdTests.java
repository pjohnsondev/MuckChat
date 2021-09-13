package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapIdTests {

    	private static final Logger logger = LogManager.getLogger(MapIdTests.class);
	@Test
	public void HashcodesAreSameForSameInternalId() {
		MapId a = new MapId();
		MapId b = new MapId(a.id);
		logger.debug("a: " + a.hashCode());
		logger.debug("b: " + b.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void HashcodesAreDifferentForDifferentId() {
		MapId a = new MapId(3);
		MapId b = new MapId(3);

		assertNotEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void Equality() {
		MapId a = new MapId(4);
		MapId b = new MapId(4);

		assertEquals(a, b);
	}

	@Test
	public void NotEquality() {
		MapId a = new MapId();
		MapId b = new MapId(2);

		assertNotEquals(a, b);
	}

}
