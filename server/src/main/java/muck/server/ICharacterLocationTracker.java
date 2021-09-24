package muck.server;

import java.util.List;

import muck.core.Location;
import muck.core.Pair;
import muck.core.Id;
import muck.core.Triple;
import muck.core.MapId;
import muck.core.AvatarLocation;

/**
 * Interface for using CharacterLocationTracker
 */
public interface ICharacterLocationTracker<TrackingType> {
	List<Triple<AvatarLocation, MapId, Location>> getAllPlayerLocations();

	List<Triple<AvatarLocation, MapId, Location>> getAllLocationsExceptId(Id<TrackingType> clientId);

	List<Triple<AvatarLocation, MapId, Location>> getPlayersWithin(Pair<MapId, Location> me, Integer dist);

	List<Triple<AvatarLocation, MapId, Location>> getPlayersWithinById(Id<TrackingType> id, Integer dist);

	void addClient(Id<TrackingType> clientId, AvatarLocation avatar, MapId id, Location location);

	void removeClientById(Id<TrackingType> id);

	void updateLocationById(Id<TrackingType> id, AvatarLocation avatar, MapId mapId, Location loc);

	Location getLocationById(Id<TrackingType> id);

}
