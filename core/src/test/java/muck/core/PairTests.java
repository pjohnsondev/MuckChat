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
}
