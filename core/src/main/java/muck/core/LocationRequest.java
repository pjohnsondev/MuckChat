package muck.core;

public class LocationRequest {
	public final Id<ClientId> id;

	public LocationRequest(Id<ClientId> id) {
		this.id = id;
	}

	public LocationRequest() {
		this.id = null;
	}
}
