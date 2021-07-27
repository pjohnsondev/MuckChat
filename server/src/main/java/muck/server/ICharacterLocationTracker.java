package muck.server;

import java.util.ArrayList;

import muck.core.Location;
import muck.core.Pair;
import aw.character.Character;
import muck.core.Id;

/**
 * Interface for using CharacterLocationTracker
 */
public interface ICharacterLocationTracker<T> {
	ArrayList<Pair<Character, Location>> getAllCharacterLocations();

	ArrayList<Pair<Character, Location>> getAllLocationsExceptMine(Id<T> clientId);
}
