package aw.character;

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
}
