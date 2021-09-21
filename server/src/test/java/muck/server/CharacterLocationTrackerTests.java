package muck.server;

import muck.core.character.Character;
import muck.core.Triple;
import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

import muck.server.ICharacterLocationTracker;
import muck.core.Id;
import muck.core.Location;
import muck.core.MapId;
import muck.core.Pair;
import muck.server.CharacterLocationTracker;
import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.core.AvatarLocation;
import muck.core.ClientId;

public class CharacterLocationTrackerTests {

	private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

	@Test
	public void AddingClientsViaUpdateMethodCorrectlyAddsClientsToInternalList() {
		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		assertEquals(0, tracker.getAllPlayerLocations().size());

		tracker.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name"), new MapId(5), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("3232"), new AvatarLocation("Test Name 2"), new MapId(2),
				new Location(4, 2));
		assertEquals(2, tracker.getAllPlayerLocations().size());
	}

	@Test
	public void AddingAClientThatAlreadyExistsInTheTrackerUpdatesExistingRecord() {
		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		tracker.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 2"), new MapId(4),
				new Location(3, 2));

		assertEquals(1, tracker.getAllPlayerLocations().size());
		assertEquals(new Location(3, 2), tracker.getAllPlayerLocations().get(0).right());
	}

	@Test
	public void PlayerTrackingInformationCanBeUpdatedByClientId() {

		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		var trackingId = new Id<ClientId>("1234");
		tracker.addClient(trackingId, new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));
		tracker.updateLocationById(trackingId, new AvatarLocation("Test Name"), new MapId(4), new Location(3, 4));

		assertEquals(1, tracker.getAllPlayerLocations().size());
		assertEquals(new Location(3, 4), tracker.getLocationById(trackingId));
	}

	@Test
	public void TrackerCanReturnAllTrackedLocationsExcludingASpecifiedId() {

		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();
		var trackingId = new Id<ClientId>("1234");
		tracker.addClient(trackingId, new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1233"), new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));

		assertEquals(2, tracker.getAllLocationsExceptId(trackingId).size());
	}

    @Test
	public void TrackerCanReturnAllTrackedLocationsExcludingASpecifiedIdReturnsNothingForDifferentMapIds() {

		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();
		var trackingId = new Id<ClientId>("1234");
		tracker.addClient(trackingId, new AvatarLocation("Test Name"), new MapId(2), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name"), new MapId(3), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1233"), new AvatarLocation("Test Name"), new MapId(4), new Location(1, 2));

		assertEquals(0, tracker.getAllLocationsExceptId(trackingId).size());
	}

	@Test
	public void ReturnUsersWithinSetDistance() {
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new AvatarLocation("Me"), new MapId(2), new Location(0, 0));
		track.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 1"), new MapId(2), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new AvatarLocation("Test Name 2"), new MapId(2), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name 3"), new MapId(2), new Location(0, 1));

		var head = track.getAllPlayerLocations().get(0);
		var toTest = new Pair<MapId, Location>(head.middle(), head.right());
		var result = track.getPlayersWithin(toTest, 1).size();
		assertEquals(3, result);
	}

    	@Test
	public void ReturnUsersWithinSetDistanceReturnsNothingForDifferentMapIds() {
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new AvatarLocation("Me"), new MapId(2), new Location(0, 0));
		track.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 1"), new MapId(3), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new AvatarLocation("Test Name 2"), new MapId(4), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name 3"), new MapId(5), new Location(0, 1));

		var head = track.getAllPlayerLocations().get(0);
		var toTest = new Pair<MapId, Location>(head.middle(), head.right());
		var result = track.getPlayersWithin(toTest, 1).size();
		assertEquals(1, result);
	}


	@Test
	public void ReturnUsersWithinSetDistanceUsingId() {
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new AvatarLocation("Me"), new MapId(1), new Location(0, 0));
		track.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 1"), new MapId(1), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new AvatarLocation("Test Name 2"), new MapId(1), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name 3"), new MapId(1), new Location(0, 1));

		assertEquals(3, track.getPlayersWithinById(new Id<ClientId>("1111"), 1).size());
	}

	@Test
	public void GetLocationUsingClientId() {
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new AvatarLocation("Me"), new MapId(), new Location(0, 0));
		track.addClient(new Id<ClientId>("1234"), new AvatarLocation("Test Name 1"), new MapId(), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new AvatarLocation("Test Name 2"), new MapId(), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new AvatarLocation("Test Name 3"), new MapId(), new Location(0, 1));

		Id<ClientId> testId = new Id<ClientId>("1111");

		assertEquals(new Location(0, 0), track.getLocationById(testId));
	}
}
