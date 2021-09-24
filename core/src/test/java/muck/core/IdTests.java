package muck.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import muck.core.Id;

public class IdTests {

	@Test
	public void HashcodesAreSameForSameIdString() {
		Id<String> a = new Id<String>();
		Id<String> b = new Id<String>(a.id);

		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void HashcodesAreDifferentForDifferentIdStrings() {

		Id<String> a = new Id<String>("a");
		Id<String> b = new Id<String>("b");

		assertNotEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void HashcodesAreSameForDifferentIdTypes() {
		Id<Integer> a = new Id<Integer>();
		Id<String> b = new Id<String>(a.id);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void EqualityHoldsForSameIdStrings() {
		Id<String> a = new Id<String>();
		Id<String> b = new Id<String>(a.id);
		assertEquals(a, b);

	}

	@Test
	public void EqualityDoesNotHoldForDifferentIdStrings() {

		Id<String> a = new Id<String>("a");
		Id<String> b = new Id<String>("b");
		assertNotEquals(a, b);
	}
}
