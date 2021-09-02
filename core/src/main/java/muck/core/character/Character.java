package muck.core.character;

//import javafx.scene.image.Image;
import java.util.Arrays;

/**
 Abstract class to represent the concept of a "Character". Cannot be instantiated directly, but 
 serves as a blueprint for commonalities between both Player & NPC, both of which can be instantiated.  
 If wishing to conduct checks against common attributes of both a Player and an NPC, cast the object as 
 this type, eg: (Character)
 **/
public abstract class Character {
    /* === PRIVATE (can be ignored) ===
    Private fields, please access and modify these stats via the corresponding public API getters/setters instead
     */
    private String _identifier; //identifier, eg: username, NPC ID, etc
    private String _avatar; //Character avatar, collaboration needed with Issue #7
    private String _direction_facing = "down"; //What direction is the avatar facing?
    
    //TODO
    // Character location on the world map. x, y coords? Which group is handling world map?
    //    private int _xCoord; - Example
    
    //TODO
    // Common stats next, eg: attack, defense, strength, constitution, wisdom, etc? 
    // Up to Issue: #14. Collaboration required

    // Character stats
    private int _health;
    private int _attack;
    private int _defence;

    /**
     * Set the unique ID for this Character. Should be used cautiously, should probably not be used externally
     */
    protected void setIdentifier(String identifier) {
        _identifier = identifier;
    }
    
    // Move character up
    public void moveUp() {
        //TODO - Collaboration needed with team doing movement controller
    }
    
    // Move character down
    public void moveDown() {
        //TODO - Collaboration needed with team doing movement controller
    }
    
    // Move character left
    public void moveLeft() {
        //TODO - Collaboration needed with team doing movement controller
    }
    
    // Move character right
    public void moveRight() {
        //TODO - Collaboration needed with team doing movement controller
    }
    
    
    /* === PUBLIC API ===
    Public getters/setters appear below here. These serve as an API to all external modules to interact with
    this class.
    */

    /**
     * Update all backend storage attributes with the values currently in this class
     * @return Boolean whether the save was successful or not
     */
    public boolean saveToBackendStorage() {
        // Instead of updating the database after every stat modification, we may only wish to do this periodically,
        // to prevent an overload of transactions
        //TODO implement with backend - Issue #24 and Issue #32

        return StorageHandler.saveCharacter(this);
    }
    
    /**
     * Retrieve the character's health. 0 should indicate death
     * @return Character health
     */
    public int getHealth() {
        return _health;
    }

    /**
     * Sets the character's health.
     * @param health Set the character's health to this value. Negative values are converted to 0
     */
    public void setHealth(int health) {
        if (health < 0) {
            //0 represents death, health cannot be set below 0
            health = 0;
        }
        
        _health = health;
    }

    /**
     * Increases the health of the given character by the specified amount
     * @param amount Amount of health to increase character by
     */
    public void increaseHealth(int amount) {
        setHealth(getHealth() + amount);
    }

    /**
     * Decreases health of the given character by the specified amount, and returns whether or not
     * the character has died (health = 0 returns true, otherwise false) as a result of this
     * @param amount Amount of health to decrease character by
     * @return true if character has died due to a result of the health decrease, false if not
     */
    public boolean decreaseHealth(int amount) {
        int health = getHealth();
        setHealth(health - amount);
        return getHealth() == 0;
    }

    /**
     * Retrieve the Character's attack strength.
     * @return Character attack strength
     */
    public int getAttack() {
        return this._attack;
    }

    /**
     * Sets the Character's attack strength.
     * @param level Set the character's attack to this value. Negative values are converted to 0
     */
    public void setAttack(int level) {
        if(level < 0) {
            level = 0;
        }
        this._attack = level;
    }

    /**
     * Retrieve the Character's defence.
     * @return Character defence
     */
    public int getDefence() {
        return this._defence;
    }

    /**
     * Sets the Character's defence.
     * @param level Set the character's defence to this value. Negative values are converted to 0
     */
    public void setDefence(int level) {
        if(level < 0) {
            level = 0;
        }
        this._defence = level;
    }

    /**
     * Retrieve the direction the Character/avatar is currently facing
     * @return Return "up", "down", "left", or "right" as a string
     */
    public String getDirection() {
        //Specifics of how direction is handled is up to 
        return _direction_facing;
    }

    /**
     * Set the direction the Character avatar is facing. Returns true if successful, false if not
     * @param direction Should be either "up", "down", "left", or "right"
     * @return If direction argument is valid, and direction successfully set, returns true. Otherwise, returns false
     */
    public boolean setDirection(String direction) {
        //Ensure the given direction is valid
        String[] validDirections = {"up", "down", "left", "right"};
        if (Arrays.asList(validDirections).contains(direction)) {
            //Now set the Character direction
            _direction_facing = direction;
            return true;
        }
        
        //Character direction wasn't successfully set, most likely due to invalid direction input
        return false;
    }

    /**
     * Returns the unique identifier for this Character
     */
    public String getIdentifier() {
        return _identifier;
    }

    /**
     Gets the Character avatar based upon its string
     @return String representing the user's avatar (subject to confirmation)
     */
    public String getAvatar() {
        return _avatar;
    }
    
    /**
    Sets the Character avatar based upon its string, returns true if successful, false if not
     @param userAvatar User avatar string
     @return Was the avatar successfully set?
     */
    public boolean setAvatar(String userAvatar) {
        _avatar = userAvatar; //TODO: Sanitization check, ensure userAvatar is valid as determined by Avatar class
        saveToBackendStorage(); //Do a character save after setting the Avatar
        
        return false;
    }

   
    
}
