package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PairTests {

	@Test
	void TestGetters() {
		var x = "String";
		var y = 3;
		var pair = new Pair<String, Integer>(x, y);

		assertEquals(x, pair.left());
		assertEquals(y, pair.right());
	}

	@Test
	void PairsWithDifferentValuesHaveDifferentHashcodes() {

		var pair = new Pair(1, 2).hashCode();
		var pair2 = new Pair(4, 5).hashCode();

		assertNotEquals(pair, pair2);
	}

	@Test
	void PairsWithSameValueshaveSameHashcodes() {

		var pair = new Pair(1, 2).hashCode();
		var pair2 = new Pair(1, 2).hashCode();

		assertEquals(pair, pair2);
	}

	@Test
	void PairsWithInverseValuesHaveDifferentHashcodes() {
		var pair = new Pair(1, 2).hashCode();
		var pair2 = new Pair(2, 1).hashCode();

		assertNotEquals(pair, pair2);
	}

	@Test
	void PairsWithSameValuesAreEqual() {

		var pair = new Pair(1, 2);
		var pair2 = new Pair(1, 2);

		assertEquals(pair, pair2);
	}

	@Test
	void PairsWithInverseValuesAreNotEqual() {

		var pair = new Pair(1, 2);
		var pair2 = new Pair(2, 1);

		assertNotEquals(pair, pair2);
	}

	@Test
	void PairsUpdatedProduceExpectedPairs() {

	    var pair = new Pair(1, 2).updateLeft(2).updateRight(4);
		var expected = new Pair(2, 4);

		assertEquals(expected, pair);
		assertEquals(expected.hashCode(), pair.hashCode());
	}

}
