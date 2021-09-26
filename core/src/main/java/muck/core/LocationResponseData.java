package muck.core;

public class LocationResponseData {

	public AvatarLocation avatar;
	public Integer x;
	public Integer y;
	public MapId mapId;

	public LocationResponseData() {}

    public LocationResponseData(Triple<AvatarLocation, MapId, Location> p) {
		avatar = p.left();
		x = p.right().getX();
		y = p.right().getY();
		mapId = p.middle();
	}
}
