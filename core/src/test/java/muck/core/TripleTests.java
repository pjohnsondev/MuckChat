package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TripleTests {
    @Test
    void TestGetters() {
	var x = "String";
	var y = 3;
	var z = new ArrayList<Integer>();
	z.add(1);
	z.add(2);
	z.add(3);
	var triple = new Triple<String, Integer, ArrayList<Integer>>(x,y,z);

	assertEquals(x, triple.left());
	assertEquals(y, triple.middle());
	assertEquals(z, triple.right());
    }
}
