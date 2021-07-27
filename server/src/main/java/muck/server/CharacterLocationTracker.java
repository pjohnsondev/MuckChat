package muck.server;

import muck.core.Pair;
import muck.core.Triple;
import muck.core.Id;
import muck.core.Location;
import aw.character.Character;
import java.util.ArrayList;

/**
 * Server class used for tracking locations of clients and their character
 * locations.
 */
public class CharacterLocationTracker<T> implements ICharacterLocationTracker<T> {
	// String is a stand-in for a unique ID, clientID?
	private ArrayList<Triple<String, Character, Location>> clients;

	public CharacterLocationTracker() {
		clients = new ArrayList<Triple<String, Character, Location>>();
	}

	public ArrayList<Triple<String, Character, Location>> getClients() {
		return clients;
	}

        @Override
	public ArrayList<Pair<Character, Location>> getAllCharacterLocations() {
		var result = new ArrayList<Pair<Character, Location>>();
		for (var triple : clients) {
		    result.add(new Pair<Character, Location>(triple.middle(), triple.right()));
		}
		return result;
	}

	public void addClient(String clientId, Character character, Location loc) {
		clients.add(new Triple<String, Character, Location>(clientId, character, loc));
	}

	public void removeClientById(String id) {
		clients.removeIf((p) -> p.left() == id);
	}

	@Override
	public ArrayList<Pair<Character, Location>> getAllLocationsExceptMine(Id<T> clientId) {
		// TODO Auto-generated method stub
		return null;
	}
}
