package muck.server;

import muck.core.Pair;
import muck.core.Triple;
import muck.core.Id;
import muck.core.Location;
import muck.core.character.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Server class used for tracking locations of clients and their character
 * locations. //* @param TrackingType - Generic placeholder that is passed to
 * the Id for tracking purposes
 */
public class CharacterLocationTracker<TrackingType> implements ICharacterLocationTracker<TrackingType> {
	// String is a stand-in for a unique ID, clientID?
	private HashMap<Id<TrackingType>, Pair<Character, Location>> _clients;

	public CharacterLocationTracker() {
		_clients = new HashMap<Id<TrackingType>, Pair<Character, Location>>();
	}

	/**
	 * @return The internal list of all tracked clients, the associated character
	 *         and that character's location
	 */
	public List<Triple<Id<TrackingType>, Character, Location>> getClients() {

		return _clients.keySet().stream().map(i -> new Triple<Id<TrackingType>, Character, Location>(i,
				_clients.get(i).left(), _clients.get(i).right())).collect(Collectors.toList());
	}

	/**
	 * Getter for pairs of character's and their locations
	 *
	 * @return An ArrayList of pairs of characters and their locations
	 */
	@Override
	public ArrayList<Pair<Character, Location>> getAllCharacterLocations() {
		return new ArrayList<Pair<Character, Location>>(_clients.values());
	}

	/**
	 * Method for adding a new client by individual pieces
	 *
	 * @param clientId  - The identifier from a client wrapped in the Id type
	 * @param character - The character information from the target client
	 * @param loc       - The location of the current character (May be deprecated
	 *                  in future if this is put onto the character class)
	 *
	 */
	@Override
	public void addClient(Id<TrackingType> clientId, Character character, Location loc) {
		_clients.put(clientId, new Pair<Character, Location>(character, loc));

	}

	/**
	 * Method used for removing clients from a tracked list. To be used with
	 * disconnect-like events.
	 *
	 * @param id - The client identifier.
	 */
	@Override
	public void removeClientById(Id<TrackingType> id) {
		if (_clients.containsKey(id)) {
			_clients.remove(id);
		}
	}

	/**
	 * Method for getting locations except for a requesting clientId.
	 *
	 * @param clientId - The client initiating the request
	 * @returns An ArrayList of pairs of character's and their locations
	 */

	@Override
	public List<Pair<Character, Location>> getAllLocationsExceptId(Id<TrackingType> clientId) {
		return _clients.keySet().stream().filter(p -> !p.equals(clientId)).map(p -> _clients.get(p))
				.collect(Collectors.toList());

	}

	@Override

	public List<Pair<Character, Location>> getCharactersWithin(Pair<Character, Location> me, Integer dist) {
		return _clients.values().stream().filter(p -> me.right() != p.right() && me.right().distance(p.right()) <= dist)
				.collect(Collectors.toList());
	}

	@Override
	public List<Pair<Character, Location>> getCharactersWithinById(Id<TrackingType> id, Integer dist) {
		var myLoc = _clients.get(id);

		return this.getCharactersWithin(myLoc, dist);

	}

	/**
	 * Method used for updating locations by client Id
	 *
	 * @param id  - The internal tracking id used to pair characters with clients.
	 * @param loc - The new location data to update
	 */
	@Override
	public void updateLocationById(Id<TrackingType> id, Location loc) {
		var p = _clients.get(id);
		_clients.replace(id, new Pair<Character, Location>(p.left(), loc));

	}

	@Override
	public Location getLocationById(Id<TrackingType> id) {
		return _clients.get(id).right();
	}

}
