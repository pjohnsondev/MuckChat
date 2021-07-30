package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TripleTests {
	@Test
	void TestGetters() {
		var x = "String";
		var y = 3;
		var z = 'C';

		var triple = new Triple(x, y, z);

		assertEquals(x, triple.left());
		assertEquals(y, triple.middle());
		assertEquals(z, triple.right());
	}

	@Test
	void PairsWithDifferentValuesHaveDifferentHashcodes() {

		var triple = new Triple(1, 2, 3).hashCode();
		var triple2 = new Triple(4, 5, 6).hashCode();

		assertNotEquals(triple, triple2);
	}

	@Test
	void TriplesWithSameValueshaveSameHashcodes() {

		var triple = new Triple(1, 2, 3).hashCode();
		var triple2 = new Triple(1, 2, 3).hashCode();

		assertEquals(triple, triple2);
	}

	@Test
	void TriplesWithInverseValuesHaveDifferentHashcodes() {
		var triple = new Triple(1, 2, 3).hashCode();
		var triple2 = new Triple(3, 2, 1).hashCode();

		assertNotEquals(triple, triple2);
	}

	@Test
	void TriplesWithSameValuesAreEqual() {

		var triple = new Triple(1, 2, 3);
		var triple2 = new Triple(1, 2, 3);

		assertEquals(triple, triple2);
	}

	@Test
	void TriplesWithInverseValuesAreNotEqual() {

		var triple = new Triple(1, 2, 3);
		var triple2 = new Triple(3, 2, 1);

		assertNotEquals(triple, triple2);
	}

    @Test
	void TriplesUpdatedProduceExpectedTriples() {

		var triple = new Triple(1, 2, 3).updateLeft(2).updateMiddle(3).updateRight(4);
		var expected = new Triple(2, 3, 4);

		assertEquals(expected, triple);
		assertEquals(expected.hashCode(), triple.hashCode());
    }
}
