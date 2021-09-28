package muck.core;

import java.util.List;

public class ClientLocationsResponse {
	List<Triple<Id<ClientId>, MapId, Location>> locations;

	public ClientLocationsResponse(List<Triple<Id<ClientId>, MapId, Location>> loc) {
	locations = loc;
	}

	public ClientLocationsResponse() {

	}

}
