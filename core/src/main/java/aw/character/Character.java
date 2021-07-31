package aw.character;

import javafx.scene.image.Image;
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
    private int _health = 100; //player health
    private String _identifier; //identifier, eg: username, NPC ID, etc
    private Image _avatar; //Character avatar, collaboration needed with Issue #7
    private String _direction_facing = "down"; //What direction is the avatar facing?
    
    //TODO
    //Character location on the world map. x, y coords? Which group is handling world map?
//    private int _xCoord; - Example
    
    //TODO
    // Common stats next, eg: attack, defense, strength, constitution, wisdom, etc? 
    // Up to Issue: #14. Collaboration required
//    private int _strength; - Example

    /**
     * Set the unique ID for this Character. Should be used cautiously, should probably not be used externally
     */
    protected void setIdentifier(String identifier) {
        _identifier = identifier;
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
        //TODO implement with backend (whichever team does this)

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
        Sets the Character avatar based upon its string, returns true if successful, false if not
     @param userAvatar User avatar string
     @return Was the avatar successfully set?
     */
    public boolean setAvatar(String userAvatar) {
        //TODO Set the avatar as stored within backend storage
        
        return false;
    }

   
    
}
