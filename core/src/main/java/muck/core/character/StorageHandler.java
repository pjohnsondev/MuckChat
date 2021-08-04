package muck.core.character;

/**
 * Handles the storing and retrieval of Character objects (Player or NPC)
 */
public class StorageHandler {
    
    /**
        Save the character object to backend storage
     */
    public static boolean saveCharacter(Character character) {
        if (character.getClass() == Player.class) {
            //TODO save Player object
        }
        else if (character.getClass() == NPC.class) {
            //TODO save NPC object

        }
        
        return false;
    }

    /**
     Attempt to retrieve the specified player from backend storage. Returns true if it's valid.
     */
    public static boolean isPlayerValid(String username) {
        return false; //TODO - implement with db
    }
}
