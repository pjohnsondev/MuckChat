package muck.server;

import muck.core.Pair;
import muck.core.Triple;
import muck.core.Id;
import muck.core.Location;
import muck.core.character.Character;

import java.util.ArrayList;

/**
 * Server class used for tracking locations of clients and their character
 * locations.
 //* @param TrackingType - Generic placeholder that is passed to the Id for tracking purposes
 */
public class CharacterLocationTracker<TrackingType> implements ICharacterLocationTracker<TrackingType> {
	// String is a stand-in for a unique ID, clientID?
	private ArrayList<Triple<Id<TrackingType>, Character, Location>> clients;

	public CharacterLocationTracker() {
		clients = new ArrayList<Triple<Id<TrackingType>, Character, Location>>();
	}

	/**
	 * @return The internal list of all tracked clients, the associated character
	 *         and that character's location
	 */
	public ArrayList<Triple<Id<TrackingType>, Character, Location>> getClients() {
		return clients;
	}

	/**
	 * Getter for pairs of character's and their locations
	 *
	 * @return An ArrayList of pairs of characters and their locations
	 */
	@Override
	public ArrayList<Pair<Character, Location>> getAllCharacterLocations() {
		var result = new ArrayList<Pair<Character, Location>>();
		for (var triple : clients) {
			result.add(new Pair<Character, Location>(triple.middle(), triple.right()));
		}
		return result;
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
		clients.add(new Triple<Id<TrackingType>, Character, Location>(clientId, character, loc));
	}

	/**
	 * Method used for removing clients from a tracked list. To be used with
	 * disconnect-like events.
	 *
	 * @param id - The client identifier.
	 */
	@Override
	public void removeClientById(Id<TrackingType> id) {
		clients.removeIf((p) -> p.left() == id);
	}

	/**
	 * Method for getting locations except for a requesting clientId.
	 *
	 * @param clientId - The client initiating the request
	 * @returns An ArrayList of pairs of character's and their locations
	 */

	@Override
	public ArrayList<Pair<Character, Location>> getAllLocationsExceptId(Id<TrackingType> clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pair<Character, Location>> getCharactersWithin(Pair<Character, Location> me, Integer dist)
	{
		var result = new ArrayList<Pair<Character, Location>>();

		Location meLoc = me.right();

		for (var triple : clients)
		{
			if (triple.middle() != me.left())
			{
				if(meLoc.distance(triple.right()) <= dist)
				{
					result.add(new Pair<Character, Location>(triple.middle(), triple.right()));
				}
			}
		}

		return result;
	}

	@Override
	public void updateLocationById(Id<TrackingType> id, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocationById(Id<TrackingType> id) {
		// TODO Auto-generated method stub
		return null;
	}

}
