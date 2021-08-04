package muck.server;

import java.util.ArrayList;
import java.util.List;

import muck.core.Location;
import muck.core.Pair;
import muck.core.character.Character;
import muck.core.Id;
import muck.core.Triple;

/**
 * Interface for using CharacterLocationTracker
 */
public interface ICharacterLocationTracker<TrackingType> {
	List<Pair<Character, Location>> getAllCharacterLocations();

	List<Pair<Character, Location>> getAllLocationsExceptId(Id<TrackingType> clientId);

	List<Pair<Character, Location>> getCharactersWithin(Pair<Character, Location> me, Integer dist);

	void addClient(Id<TrackingType> clientId, Character character, Location location);

	void removeClientById(Id<TrackingType> id);

	void updateLocationById(Id<TrackingType> id, Location loc);

	Location getLocationById(Id<TrackingType> id);

}
