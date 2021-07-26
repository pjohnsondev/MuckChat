package muck.protocol.connection;


public class CharacterLocation {
	private Location _location;

	public CharacterLocation(Location location) {
		_location = location;
	}

	public void updateLocation(Location location) {
		_location = location;
	}

	public Location getLocation() {
		return _location;
	}
}
