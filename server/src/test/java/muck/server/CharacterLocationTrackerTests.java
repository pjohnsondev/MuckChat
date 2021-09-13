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
import muck.server.CharacterLocationTracker;
import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.core.ClientId;

public class CharacterLocationTrackerTests {

	private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

	@Test
	public void AddingClientsViaUpdateMethodCorrectlyAddsClientsToInternalList() {
		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		assertEquals(0, tracker.getAllPlayerLocations().size());

		tracker.addClient(new Id<ClientId>("1234"), new String("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("3232"), new String("Test Name 2"), new Location(4, 2));
		assertEquals(2, tracker.getAllPlayerLocations().size());
	}

	@Test
	public void AddingAClientThatAlreadyExistsInTheTrackerUpdatesExistingRecord() {
		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		tracker.addClient(new Id<ClientId>("1234"), new String("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1234"), new String("Test Name 2"), new Location(3, 2));

		assertEquals(1, tracker.getAllPlayerLocations().size());
		assertEquals(new Location(3, 2), tracker.getAllPlayerLocations().get(0).right());
	}

	@Test
	public void PlayerTrackingInformationCanBeUpdatedByClientId() {

		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

		var trackingId = new Id<ClientId>("1234");
		tracker.addClient(trackingId, new String("Test Name"), new Location(1, 2));
		tracker.updateLocationById(trackingId, "Test Name", new Location(3, 4));

		assertEquals(1, tracker.getAllPlayerLocations().size());
		assertEquals(new Location(3, 4), tracker.getLocationById(trackingId));
	}

	@Test
	public void TrackerCanReturnAllTrackedLocationsExcludingASpecifiedId() {

		ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();
		var trackingId = new Id<ClientId>("1234");
		tracker.addClient(trackingId, new String("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1232"), new String("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<ClientId>("1233"), new String("Test Name"), new Location(1, 2));

		assertEquals(3, tracker.getAllLocationsExceptId(trackingId).size());
	}

	@Test
	public void ReturnUsersWithinSetDistance()
	{
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new String("Me"), new Location(0,0));
		track.addClient(new Id<ClientId>("1234"), new String("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new String("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new String("Test Name 3"), new Location(0, 1));

		var result = track.getPlayersWithin(track.getAllPlayerLocations().get(0), 1).size();
		assertEquals(2, result);
	}

	@Test
	public void ReturnUsersWithinSetDistanceUsingId()
	{
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new String("Me"), new Location(0,0));
		track.addClient(new Id<ClientId>("1234"), new String("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new String("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new String("Test Name 3"), new Location(0, 1));

		assertEquals(2, track.getPlayersWithinById(new Id<ClientId>("1111"), 1).size());
	}

	@Test
	public void GetLocationUsingClientId()
	{
		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();

		track.addClient(new Id<ClientId>("1111"), new String("Me"), new Location(0,0));
		track.addClient(new Id<ClientId>("1234"), new String("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<ClientId>("1231"), new String("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<ClientId>("1232"), new String("Test Name 3"), new Location(0, 1));

		Id<ClientId> testId = new Id<ClientId>("1111");

		assertEquals(new Location(0,0), track.getLocationById(testId));
	}

    @Test
	public void UpdatingLocationsShouldBeIdempotent() {

		ICharacterLocationTracker<ClientId> track = new CharacterLocationTracker<ClientId>();
		var id = new Id<ClientId>();

		track.addClient(id, "test", new Location(1,1));

		for (var i = 1; i < 100; i++) {
			track.addClient(id, "test Updated", new Location(2, 2));
		}

		assertEquals(1, track.getAllPlayerLocations().size());
    }
}
