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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Server class used for tracking locations of clients and their character
 * locations.
 *
 * @param TrackingType - Generic placeholder that is passed to the Id for
 *                     tracking purposes
 */
public class CharacterLocationTracker<TrackingType> implements ICharacterLocationTracker<TrackingType> {
	// String is a stand-in for a unique ID, clientID?
	private HashMap<Id<TrackingType>, Pair<String, Location>> _clients;

    private final Logger logger = LogManager.getLogger(CharacterLocationTracker.class);

	public CharacterLocationTracker() {
		_clients = new HashMap<Id<TrackingType>, Pair<String, Location>>();
	}

	/**
	 * @return The internal list of all tracked clients, the associated character
	 *         and that character's location
	 */
	public List<Triple<Id<TrackingType>, String, Location>> getClients() {
		logger.info("Recieved request for all clients");
		return _clients.keySet().stream().map(
				i -> new Triple<Id<TrackingType>, String, Location>(i, _clients.get(i).left(), _clients.get(i).right()))
				.collect(Collectors.toList());
	}

	/**
	 * Getter for pairs of character's and their locations
	 *
	 * @return An ArrayList of pairs of characters and their locations
	 */
	@Override
	public ArrayList<Pair<String, Location>> getAllPlayerLocations() {
		logger.info("Received request for getting all locations");
		return new ArrayList<Pair<String, Location>>(_clients.values());
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
	public void addClient(Id<TrackingType> clientId, String avatar, Location loc) {
	    logger.info(String.format("Receieved request to update clientId: %s with avatar: %s and location: %s", clientId.toString(), avatar,
				      loc.toString()));
	    _clients.put(clientId, new Pair<String, Location>(avatar, loc));
	}

	/**
	 * Method used for removing clients from a tracked list. To be used with
	 * disconnect-like events.
	 *
	 * @param id - The client identifier.
	 */
	@Override
	public void removeClientById(Id<TrackingType> id) {
	    logger.info(String.format("Recieved request to remove client by id: %s", id.toString()));
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
	public List<Pair<String, Location>> getAllLocationsExceptId(Id<TrackingType> clientId) {
	    logger.info(String.format("Recieved request to get locations of clients exceptId: %s", clientId.toString()));
		return _clients.keySet().stream().filter(p -> !p.equals(clientId)).map(p -> _clients.get(p))
				.collect(Collectors.toList());

	}

	@Override

	public List<Pair<String, Location>> getPlayersWithin(Pair<String, Location> me, Integer dist) {
	    logger.info(String.format("Received request to get players within distance: %s of location %s", dist.toString(),
			    me.toString()));
		return _clients.values().stream().filter(p -> me.right() != p.right() && me.right().distance(p.right()) <= dist)
				.collect(Collectors.toList());
	}

	@Override
	public List<Pair<String, Location>> getPlayersWithinById(Id<TrackingType> id, Integer dist) {
	    logger.info(String.format("Recieved request for all players with distance of %s of clientId %s", id.toString(), dist.toString()));
	    var myLoc = _clients.get(id);
		return this.getPlayersWithin(myLoc, dist);
	}

	/**
	 * Method used for updating locations by client Id
	 *
	 * @param id  - The internal tracking id used to pair characters with clients.
	 * @param loc - The new location data to update
	 */
	@Override
	public void updateLocationById(Id<TrackingType> id, String avatar, Location loc) {
	    logger.info(String.format("Received request to update client %s location %s and avatar %s", id.toString(), avatar, loc.toString()));
		var newData = new Pair<String, Location>(avatar, loc);
		if (!_clients.containsKey(id)) {
			_clients.put(id, newData);
		} else {
			_clients.replace(id, newData);
		}

	}

	@Override
	public Location getLocationById(Id<TrackingType> id) {
	    logger.info(String.format("Recieved request for location of clientId %s", id.toString()));
		return _clients.get(id).right();
	}

}
