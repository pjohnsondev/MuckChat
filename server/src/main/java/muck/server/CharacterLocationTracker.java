package muck.server;

import muck.core.Pair;
import muck.core.Location;

import java.util.List;

/**

*/
public class CharacterLocationTracker {
	// String is a stand-in for a unique ID, clientID?
	private List<Pair<String, Location>> clients;

	public List<Pair<String, Location>> getClients() {
		return clients;
	}

	public void addClient(Pair<String, Location> loc) {
		clients.add(loc);
	}

	public void removeClientById(String id) {
		clients.removeIf((p) -> p.left() == id);
	}
}
