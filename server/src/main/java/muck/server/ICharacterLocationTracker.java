package muck.server;

import java.util.ArrayList;

import muck.core.Location;
import muck.core.Pair;
import muck.core.character.Character;
import muck.core.Id;
import muck.core.Triple;

/**
 * Interface for using CharacterLocationTracker
 */
public interface ICharacterLocationTracker<TrackingType> {
	ArrayList<Pair<Character, Location>> getAllCharacterLocations();

	ArrayList<Pair<Character, Location>> getAllLocationsExceptId(Id<TrackingType> clientId);

	ArrayList<Pair<Character, Location>> getCharactersWithin(Pair<Character, Location> me, Integer dist);

	ArrayList<Pair<Character, Location>> getCharactersWithinById(Id<TrackingType> id, Integer dist);

	void addClient(Id<TrackingType> clientId, Character character, Location location);

	void removeClientById(Id<TrackingType> id);

	void updateLocationById(Id<TrackingType> id, Location loc);

	Location getLocationById(Id<TrackingType> id);

}
