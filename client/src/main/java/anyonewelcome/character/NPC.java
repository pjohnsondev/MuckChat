package anyonewelcome.character;

//TODO **IMPORTANT**
//Unknown yet how this will interact with JavaFX and backend storage. This is a mere prototype, subject to
//rapid change until the specifics of the project are worked out. Don't rely on any of these methods yet

public class NPC extends Character {
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
    
    //TODO - NPC should have a separate controller to the player. May incorporate AI based movement, behaviour etc.
//    public npcController() {
//    }


}
