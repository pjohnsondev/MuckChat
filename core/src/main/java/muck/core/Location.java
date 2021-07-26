package muck.core;

/**
 * Location datatype
 */
public class Location {
	private final Pair<Integer, Integer> location;

	public Location(Integer x, Integer y) {
		this.location = new Pair<Integer, Integer>(x, y);
	}

	public Pair<Integer, Integer> getLocation() {
		return location;
	}

	public Integer getX() {
		return location.left();
	}

	public Integer getY() {
		return location.right();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
