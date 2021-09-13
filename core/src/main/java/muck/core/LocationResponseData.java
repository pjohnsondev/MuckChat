package muck.core;

public class LocationResponseData {

	public String avatar;
	public Integer x;
	public Integer y;

	public LocationResponseData() {}

	public LocationResponseData(Pair<String, Location> p) {
		avatar = p.left();
		x = p.right().getX();
		y = p.right().getY();
	}
}
