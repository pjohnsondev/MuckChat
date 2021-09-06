package muck.core.character;

public class NPC extends Character {
    private int _difficulty;
    public INPCState stateBehaviour;
    public NPCState npcState;

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
        setState(NPCState.None);
    }

    /**
     * Dummy constructor for a NPC object with a "null" identifier. Does not
     * check with backend storage for a valid username. Should only be used for unit tests that don't use backend
     */
    public NPC() {
        this.setIdentifier(null);
    }
    
    //TODO - NPC should have a separate controller to the player. May incorporate AI based movement, behaviour etc.
//    public npcController() {
//    }

    // To be called once per pre-determined fixed timestep
    public void Update() {
        stateBehaviour.handle();
    }
    
    /* Sets the state of the NPC object */
    public void setState(NPCState npcState) {
        if (npcState == NPCState.None) {
            this.stateBehaviour = new NPCStateNone();
        }
        else if (npcState == NPCState.RandomWalk) {
            this.stateBehaviour = new NPCStateRandomWalk(this);
        }
    }
    
    /* 
        Retrieve the current NPC state
     */
    public NPCState getState() {
        return this.npcState;
    }

    /**
     * Set NPC stats. Should only be called once on creation of new or existing NPC.
     * @param health NPC health
     * @param attack NPC attack level
     * @param defence NPC defence level
     * @param difficulty NPC difficulty level
     */
    public void setNPCStats(int health, int attack, int defence, int difficulty) {
        this.setHealth(health);
        this.setAttack(attack);
        this.setDefence(defence);
        this.setDifficulty(difficulty);
    }

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

    /**
     * Increases and retrieves the NPC difficulty level.
     * @param level NPC difficulty level to increase
     * @return NPC difficulty level
     */
    public int increaseDifficulty(int level) {
        setDifficulty(level + getDifficulty());
        return this._difficulty;
    }
}
