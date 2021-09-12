package muck.core;

import java.math.*;

/**
 * Location datatype - Acts as a wrapper around the data type Pair<Int, Int> to
 * prevent ambiguous references.
 */
public class Location {
	private final Pair<Integer, Integer> location;

	public Location() {
		this.location = new Pair<Integer, Integer>(0, 0);
	}

	public Location(Integer x, Integer y) {
		this.location = new Pair<Integer, Integer>(x, y);
	}

	/**
	 * Public getter for the pair representing the location
	 *
	 * @return The underlying Pair<Int, Int> datastructure for the location
	 */
	public Pair<Integer, Integer> getLocation() {
		return location;
	}

	/**
	 * public getter for the x value of the location.
	 *
	 * @returns The x value of the location
	 */
	public Integer getX() {
		return location.left();
	}

	/**
	 * public getter for the y value of the location.
	 *
	 * @returns the y value of the location
	 */
	public Integer getY() {
		return location.right();
	}

	/**
	 * @param newLoc the new (x,y) pair to be used
	 * @returns a new location with the provided (x,y) pair
	 */
	public Location update(Pair<Integer, Integer> newLoc) {
		return new Location(newLoc.left(), newLoc.right());
	}

	/**
	 * @param x - the new x value to be used
	 * @param y - the new y value to be used
	 * @returns a new location with the provided (x,y) pair
	 */
	public Location update(Integer x, Integer y) {
		return new Location(x, y);
	}

	public Double distance(Location y) {
		return Math.sqrt(Math.pow(location.left() - y.getX(), 2) + Math.pow(location.right() - y.getY(), 2));

	}

	@Override
	public String toString() {

		return this.location.toString();
	}

	@Override
	public int hashCode() {
		return location.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location cast = (Location) obj;
		return location.equals(cast.getLocation());
	}
}
