package muck.core;

public class UpdatePlayerRequest {
	public final Id<ClientId> id;
	public final AvatarLocation avatar;
	public final MapId mapId;
	public final Location location;

	public UpdatePlayerRequest(Id<ClientId> id, AvatarLocation avatar, MapId mapId, Location loc) {
		this.id = id;
		this.location = loc;
		this.avatar = avatar;
		this.mapId = mapId;
	}

	public UpdatePlayerRequest() {
		id = null;
		avatar = null;
		location = null;
		mapId = null;
	}
}
