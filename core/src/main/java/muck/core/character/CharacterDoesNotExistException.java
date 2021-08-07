package muck.core.character;

/**
 * All player's and NPCs must be instantiated with a valid ID that exists in the backend storage. 
 * This can be automatically done using NPCFactory, however player's must be instantiated with a valid username. 
 * In the event the given ID does not appear in the backend storage, this exception will be thrown, and should be
 * handled by the caller
 */
public class CharacterDoesNotExistException extends Exception {
    
    public CharacterDoesNotExistException(String characterId) {
        super(String.format("Attempted to create character with ID %s, but this ID does not exist" +
                " in the backend storage", characterId));
    }
}
