package muck.core.character;

public class NPC extends Character implements INPCColleague {
    private int _difficulty;
    private INPCState stateBehaviour;
    private NPCState npcState;

    // If this NPC is currently interacting with another NPC, this is the other NPC's identifier & Action
    private String otherNPCIdentifier;  
    private Action otherNPCAction;

    // source image width and height
    private int sx;
    private int sh;

    // motion and movement
    private NPCStateRandomWalk npcStateRandomWalk;
    private int move = 0;
    private boolean change = false;

    /**
     * NPC constructor. This class is an extension of the Character class for NPC/monster characters.
     * This should instantiate an NPC with an identifier that exists in the backend persistent storage.
     * If the identifier/NPC ID doesn't exist, a CharacterDoesNotExist exception will be thrown. 
     * Example usage: NPC npc_id = new NPC("npc_id");
     */
    public NPC(String NPCId) throws CharacterDoesNotExistException {
        boolean databaseRetrievalSuccessful = true; // keep this true to avoid integration test failure, until
                                                    // actual implementation of database server from Issue #24
                                                    // is provided
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(NPCId);
        }

        setIdentifier(NPCId);
        setState(NPCState.None);
    }

    /**
     * Dummy constructor for a NPC object with a "null" identifier.
     * Does not check with backend storage for a valid username.
     * Will be removed if database is implemented
     */
    public NPC() {
        this.setIdentifier(null);
    }

    /**
     * To be called once per pre-determined fixed timestep
     */
    public void Update() {
        getStateBehaviour().handle();
    }
    
    /**
     * Sets the state of the NPC object
     */
    public void setState(NPCState npcState) {
        if (npcState == NPCState.None) {
            this.setStateBehaviour(new NPCStateNone());
        }
        else if (npcState == NPCState.RandomWalk) {
            this.setStateBehaviour(new NPCStateRandomWalk(this));
        }
    }
    
    /**
     * Retrieve the current NPC state
     */
    public NPCState getState() {
        return this.getNpcState();
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


    /**
     * Send an Action message to another NPC object, via the ConcreteNPCMediator
     */
    public void messageOtherNPC(String targetNPCIdentifier, Action action) {
        GlobalTracker.concreteNPCMediator.messageToOtherNPC(this, targetNPCIdentifier, action);
    }
    
    /**
     * Implementation of the INPCColleague interface
     */
    @Override
    public void receive(String sendingNPCIdentifier, Action action) {
        this.setOtherNPCIdentifier(sendingNPCIdentifier);
        this.setOtherNPCAction(action);
        
        // We can further extend the NPC-to-NPC logic with any individual NPC instance (possibly using decorator)
    }

    public Action getOtherNPCAction() {
        return otherNPCAction;
    }

    public void setOtherNPCAction(Action otherNPCAction) {
        this.otherNPCAction = otherNPCAction;
    }

    public String getOtherNPCIdentifier() {
        return otherNPCIdentifier;
    }

    public void setOtherNPCIdentifier(String otherNPCIdentifier) {
        this.otherNPCIdentifier = otherNPCIdentifier;
    }

    public NPCState getNpcState() {
        return npcState;
    }

    public void setNpcState(NPCState npcState) {
        this.npcState = npcState;
    }

    public INPCState getStateBehaviour() {
        return stateBehaviour;
    }

    public void setStateBehaviour(INPCState stateBehaviour) {
        this.stateBehaviour = stateBehaviour;
    }

    /**
     * Set NPC Random Walk speed and times
     * @param speed Speed of NPC walk
     * @param timeWait Time waiting in a spot
     * @param timeWalk Time while walking
     */
    public void setNpcRandomWalk(double speed, float timeWait, float timeWalk) {
        npcStateRandomWalk = new NPCStateRandomWalk(this, speed, timeWait, timeWalk);
        setState(NPCState.RandomWalk);
    }

    /**
     * Implements npcStateRandomWalk and allows NPC to walk in random directions
     */
    public void handle() {
        this.npcStateRandomWalk.handle();

        // walking
        if (this.npcStateRandomWalk.walking) {
            if (this.npcStateRandomWalk.elapsed % ((int)(this.npcStateRandomWalk.timeWalk / 8) ) == 0) {
                change = true;
                move++;
            }
            if (change && move < 3) {
                sx += 48;
            } else if (change && move < 5) {
                sx -= 48;
            } else if (change) {
                move = 0;
            }
            change = false;
        }
    }

    /**
     * Set source width coordinate
     * @param sx source width
     */
    public void setSx(int sx) {this.sx = sx;}

    /**
     * Set source height coordinate
     * @param sh source height
     */
    public void setSh(int sh) {this.sh = sh;}

    /**
     * Return source rectangle width and height coordinates
     * @return Array of source rectangle width and source rectangle height [sx, sh]
     */
    public int[] getSourceRectangle() {
        return new int[]{this.sx, this.sh};
    }
}
