package muck.server;

import muck.core.Pair;
import muck.core.Triple;
import muck.core.AvatarLocation;
import muck.core.Id;
import muck.core.Location;
import muck.core.LocationResponseData;
import muck.core.MapId;
import muck.core.character.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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

	private ConcurrentHashMap<String, Triple<AvatarLocation, MapId, Location>> _clients;

	private final Logger logger = LogManager.getLogger(CharacterLocationTracker.class);

	public CharacterLocationTracker() {
		_clients = new ConcurrentHashMap<String, Triple<AvatarLocation, MapId, Location>>();
	}

	/**
	 * @return The internal list of all tracked clients, the associated character
	 *         and that character's location
	 */
	public List<Triple<Id<TrackingType>, AvatarLocation, Location>> getClients() {
		return _clients.keySet().stream()
				.map(i -> new Triple<Id<TrackingType>, AvatarLocation, Location>(new Id<TrackingType>(i),
						_clients.get(i).left(), _clients.get(i).right()))
				.collect(Collectors.toList());
	}

	/**
	 * Getter for pairs of character's and their locations
	 *
	 * @return An ArrayList of pairs of characters and their locations
	 */
	@Override
	public ArrayList<Triple<AvatarLocation, MapId, Location>> getAllPlayerLocations() {
		return new ArrayList<Triple<AvatarLocation, MapId, Location>>(
				_clients.values().stream().collect(Collectors.toList()));
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
	public void addClient(Id<TrackingType> clientId, AvatarLocation avatar, MapId mapId, Location loc) {
		_clients.put(clientId.id, new Triple<AvatarLocation, MapId, Location>(avatar, mapId, loc));
	}

	/**
	 * Method used for removing clients from a tracked list. To be used with
	 * disconnect-like events.
	 *
	 * @param id - The client identifier.
	 */
	@Override
	public void removeClientById(Id<TrackingType> id) {
		if (_clients.containsKey(id.id)) {
			_clients.remove(id.id);
		}
	}

	/**
	 * Method for getting locations except for a requesting clientId.
	 *
	 * @param clientId - The client initiating the request
	 * @returns An ArrayList of pairs of character's and their locations
	 */

	@Override
	public List<Triple<AvatarLocation, MapId, Location>> getAllLocationsExceptId(Id<TrackingType> clientId) {
		if (_clients.containsKey(clientId.id)) {
			var exclusion = _clients.get(clientId.id);
			return _clients.keySet().stream()
					.filter(p -> !p.equals(clientId.id) && exclusion.middle().equals(_clients.get(p).middle()))
					.map(p -> _clients.get(p)).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}

	}

	@Override
	public List<Triple<AvatarLocation, MapId, Location>> getPlayersWithin(Pair<MapId, Location> me, Integer dist) {
		return _clients.values().stream()
				.filter(p -> me.left().equals(p.middle()) && me.right().distance(p.right()) <= dist)
				.collect(Collectors.toList());
	}

	@Override
	public List<Triple<AvatarLocation, MapId, Location>> getPlayersWithinById(Id<TrackingType> id, Integer dist) {
		var myLoc = _clients.get(id.id);
		var toCheck = new Pair<MapId, Location>(myLoc.middle(), myLoc.right());
		return this.getPlayersWithin(toCheck, dist);
	}

	/**
	 * Method used for updating locations by client Id
	 *
	 * @param id  - The internal tracking id used to pair characters with clients.
	 * @param loc - The new location data to update
	 */
	@Override
	public void updateLocationById(Id<TrackingType> id, AvatarLocation avatar, MapId mapId, Location loc) {
		var newData = new Triple<AvatarLocation, MapId, Location>(avatar, mapId, loc);
		_clients.put(id.id, newData);
	}

	@Override
	public Location getLocationById(Id<TrackingType> id) {
		return _clients.get(id.id).right();
	}

	@Override
	public List<Triple<Id<TrackingType>, MapId, Location>> getAllClientLocationsExcept(Id<TrackingType> clientId) {
		return _clients.keySet().stream().filter(k -> k != clientId.id)
				.map(p -> new Triple<Id<TrackingType>, MapId, Location>(new Id<TrackingType>(p),
						_clients.get(clientId.id).middle(), _clients.get(clientId.id).right()))
				.collect(Collectors.toList());
	}

}
