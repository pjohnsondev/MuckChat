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

public class CharacterLocationTrackerTests {

	private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

	@Test
	public void AddingClientsViaUpdateMethodCorrectlyAddsClientsToInternalList() {
		ICharacterLocationTracker<String> tracker = new CharacterLocationTracker<String>();

		assertEquals(0, tracker.getAllCharacterLocations().size());

		tracker.addClient(new Id<String>("1234"), new Player("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<String>("3232"), new Player("Test Name 2"), new Location(4, 2));
		assertEquals(2, tracker.getAllCharacterLocations().size());
	}

	@Test
	public void AddingAClientThatAlreadyExistsInTheTrackerUpdatesExistingRecord() {
		ICharacterLocationTracker<String> tracker = new CharacterLocationTracker<String>();

		tracker.addClient(new Id<String>("1234"), new Player("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<String>("1234"), new Player("Test Name 2"), new Location(3, 2));

		assertEquals(1, tracker.getAllCharacterLocations().size());
		assertEquals(new Location(3, 2), tracker.getAllCharacterLocations().get(0).right());
	}

	@Test
	public void CharacterTrackingInformationCanBeUpdatedByClientId() {

		ICharacterLocationTracker<String> tracker = new CharacterLocationTracker<String>();

		var trackingId = new Id<String>("1234");
		tracker.addClient(trackingId, new Player("Test Name"), new Location(1, 2));
		tracker.updateLocationById(trackingId, new Location(3, 4));
		assertEquals(new Location(3, 4), tracker.getLocationById(trackingId));
	}

	@Test
	public void TrackerCanReturnAllTrackedLocationsExcludingASpecifiedId() {

		ICharacterLocationTracker<String> tracker = new CharacterLocationTracker<String>();
		var trackingId = new Id<String>("1234");
		tracker.addClient(trackingId, new Player("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<String>("1232"), new Player("Test Name"), new Location(1, 2));
		tracker.addClient(new Id<String>("1233"), new Player("Test Name"), new Location(1, 2));

		assertEquals(2, tracker.getAllLocationsExceptId(trackingId).size());
	}

	@Test
	public void ReturnUsersWithinSetDistance()
	{
		ICharacterLocationTracker<String> track = new CharacterLocationTracker<String>();

		track.addClient(new Id<String>("1111"), new Player("Me"), new Location(0,0));
		track.addClient(new Id<String>("1234"), new Player("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<String>("1231"), new Player("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<String>("1232"), new Player("Test Name 3"), new Location(0, 1));

		var result = track.getCharactersWithin(track.getAllCharacterLocations().get(0), 1).size();
		assertEquals(2, result);
	}

	@Test
	public void ReturnUsersWithinSetDistanceUsingId()
	{
		ICharacterLocationTracker<String> track = new CharacterLocationTracker<String>();

		track.addClient(new Id<String>("1111"), new Player("Me"), new Location(0,0));
		track.addClient(new Id<String>("1234"), new Player("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<String>("1231"), new Player("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<String>("1232"), new Player("Test Name 3"), new Location(0, 1));

		assertEquals(2, track.getCharactersWithinById(new Id<String>("1111"), 1).size());
	}

	@Test
	public void GetLocationUsingClientId()
	{
		ICharacterLocationTracker<String> track = new CharacterLocationTracker<String>();

		track.addClient(new Id<String>("1111"), new Player("Me"), new Location(0,0));
		track.addClient(new Id<String>("1234"), new Player("Test Name 1"), new Location(1, 0));
		track.addClient(new Id<String>("1231"), new Player("Test Name 2"), new Location(2, 0));
		track.addClient(new Id<String>("1232"), new Player("Test Name 3"), new Location(0, 1));

		Id<String> testId = new Id<String>("1111");

		assertEquals(new Location(0,0), track.getLocationById(testId));
	}
}
