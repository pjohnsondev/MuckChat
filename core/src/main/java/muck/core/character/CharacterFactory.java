package muck.core.character;

/**
 * An easy factory to create Players, and NPCs with default values, avatars, and other things
 */
public class CharacterFactory {
    
    //Clients should have a simple way to provide a username, and load the corresponding character if it already exists,
    //or instead create a new character if it doesn't, and then have that Player object returned either way.
    static public Player createOrLoadPlayer(String username) {
        String identifier = userExists(username);

        Player newPlayer = new Player(username);
        newPlayer.setIdentifier(identifier);

        return newPlayer;
    }

    static public String userExists(String username) {
        // TODO: query the database to see if this user is found
        // if user found then return the identifier
        // if not, insert player to database and then return the identifier
        // for now, (temporary) just return a randomised string id as the identifier
        return java.util.UUID.randomUUID().toString();
    }

    /** Create a new NPC character within the backend with a unique identifier automatically. Return this newly
     * created NPC object.
     * @return The generated NPC object
     */
    static public NPC createNewNPC() throws CharacterDoesNotExistException {
        NPC npc = new NPC("dummy");
        StorageHandler.saveCharacter(npc);
        
        return npc;
    }
    
    /** Create a new NPC character within the backend with the given custom identifier string. 
     * Please ensure the identifier doesn't already exist. Return this newly created NPC object
     * @param identifier The custom identifier to use
     * @return The generated NPC object
     */
    static public NPC createNewNPC(String identifier) throws CharacterDoesNotExistException {
        NPC npc = new NPC(identifier);
        StorageHandler.saveCharacter(npc);

        return npc;
    }

    /** Example method only to create a new specialized NPC (eg: a villager) from scratch */
    static public NPC createNewVillager() throws CharacterDoesNotExistException {
        String villagerId = "villager1"; // ID will be uniquely generated by this method, caller doesn't need to worry
        NPC villager = createNewNPC(villagerId);
        
        // Villager should have random walk behaviour
        NPCStateRandomWalk npcStateRandomWalk = new NPCStateRandomWalk(villager);
        villager.setState(NPCState.RandomWalk);
        
        // Villagers should have a pre-determined stats
        villager.setNPCStats(30,0,0, 1);
        
        return villager; // If wishing to retrieve the villager id from this object, use villager.getIdentifier(); 
    }
}
