package aw.character;

//TODO **IMPORTANT**
//Unknown yet how this will interact with JavaFX and backend storage. This is a mere prototype, subject to
//rapid change until the specifics of the project are worked out. Don't rely on any of these methods yet

public class Player extends Character {
    /**
     * Player constructor. This class is an extension of the Character class for human players.
     * This should instantiate a player with a username that exists in the backend persistent storage.
     * (Will Issue #11 handle player username & password creation in the backend?)
     * If the player username doesn't exist, a CharacterDoesNotExist exception will be thrown.
     * Example usage: Player player1 = new Player("my_username");
     */
    public Player(String username) throws CharacterDoesNotExistException {
        //TODO - Retrieve the username identifier from the backend database, then populate all fields with 
        // player values from the database
        boolean databaseRetrievalSuccessful = false;
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(username);
        }
        
        setIdentifier(username);
    }
    
    //TODO How will the player move? A player controller will need to be created
//    public playerController() {
//    }

    //TODO: How will a player be able to trade collectables with other players? (Issue 10)
    // Can you hold more than one item?
    /**
     * @param collectable Collectable item to trade
     * @param otherPlayer Other player to trade with
     * @return If player to player collectable transaction is successful return true. Otherwise, return false
     */
    public boolean tradeCollectable(String collectable, Player otherPlayer) {
        //TODO: Does the player currently hold the collectable?

        //TODO: Does the other player currently exist in the database?

        return false;
    }

    /**
    * @param item item to be added to inventory
    */
    public void addItemToInventory(String item) {
        //TODO
    }

    // TODO: How are items inside your inventory consumed?
    /**
     * @param item item to be removed/consumed
     */
    public void removeItemFromInventory(String item) {
        //TODO
    }

    // TODO: How do players retrieve what in their inventory
    public String[] getInventory() {
        return null;
    }

    // TODO: How will player be able to store achievements
    /**
     * @param achievement Name of achievement
     */
    public void addAchievement(String achievement) {
    }

    /**
     * Retrieves players achievements
     */
    public String[] getAchievements() {
        return null;
    }


}
