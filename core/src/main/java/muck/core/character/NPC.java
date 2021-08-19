package muck.core.character;

public class NPC extends Character {
    private int _difficulty = 1;

    /**
     * NPC constructor. This class is an extension of the Character class for NPC/monster characters.
     * This should instantiate an NPC with an identifier that exists in the backend persistent storage.
     * If the identifier/NPC ID doesn't exist, a CharacterDoesNotExist exception will be thrown. 
     * Example usage: NPC npc_id = new NPC("npc_id");
     */
    public NPC(String NPCId) throws CharacterDoesNotExistException {
        //TODO - Retrieve the identifier/NPC ID from the backend database, then populate all fields with 
        // NPC values from the database
        boolean databaseRetrievalSuccessful = false;
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(NPCId);
        }

        setIdentifier(NPCId);
    }

    /**
     * Dummy constructor for a NPC object with a "null" identifier. Does not
     * check with backend storage for a valid username. Should only be used for unit tests that don't use backend
     */
    protected NPC() {
        this.setIdentifier(null);
    }
    
    //TODO - NPC should have a separate controller to the player. May incorporate AI based movement, behaviour etc.
//    public npcController() {
//    }

    /**
     * Sets the NPC difficulty. Must have difficulty of 1 or higher.
     * @param level Set the NPC difficulty to this value. Negative and zero values are converted to 1
     */
    public void setDifficulty(int level) {
        if(level < 0) {
            level = 1;
        }
        this._difficulty = level;
    }

    /**
     * Retrieve the NPC difficulty level.
     * @return NPC difficulty level
     */
    public int getDifficulty() {
        return this._difficulty;
    }

}
