package muck.core;

public class UpdatePlayerRequest {
	public final Id<ClientId> id;
	public final String avatar;
	public final Location location;

	public UpdatePlayerRequest(Id<ClientId> id, String avatar, Location loc) {
		this.id = id;
		this.location = loc;
		this.avatar = avatar;
	}

	public UpdatePlayerRequest() {
		id = null;
		avatar = null;
		location = null;
	}
}
