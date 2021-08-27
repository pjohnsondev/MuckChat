package muck.server;

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
	List<Pair<String, Location>> getAllPlayerLocations();

	List<Pair<String, Location>> getAllLocationsExceptId(Id<TrackingType> clientId);

	List<Pair<String, Location>> getPlayersWithin(Pair<String, Location> me, Integer dist);

	List<Pair<String, Location>> getPlayersWithinById(Id<TrackingType> id, Integer dist);

	void addClient(Id<TrackingType> clientId, String avatar, Location location);

	void removeClientById(Id<TrackingType> id);

    void updateLocationById(Id<TrackingType> id, String avatar, Location loc);

	Location getLocationById(Id<TrackingType> id);

}
