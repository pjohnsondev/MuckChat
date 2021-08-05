package muck.core.character;

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
        boolean databaseRetrievalSuccessful = StorageHandler.isPlayerValid(username);
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(username);
        }

        this.setAvatar("some avatar retrieved from backend"); //further colab with issue #7 required
        this.setIdentifier(username);
    }
    
    /**
     * Dummy constructor for a player object with a "null" identifier. Does not
     * check with backend storage for a valid username. Should only be used for unit tests that don't use backend
     */
    protected Player() {
        this.setIdentifier(null);
    }
    
    //TODO How will the player move? A player controller will need to be created
    // 30-JUL-21 - bnolan9 - SteveB of #9 and bnolan9 of #20 agreed for #20 to produce character movement within this class
    // as #20 also needs to work on character location.
    // #20 will plan some setters and getters, core functions etc and produce them here, but under #20 branch.
//    public playerController() {
//    }

    //TODO: How will a player be able to trade collectables with other players? (Issue 10)
    /**
     * Trades a single collectable currently held by the player with another existing player
     * @param collectable Collectable item to trade
     * @param otherPlayer Other player to trade with
     * @return If player to player collectable transaction is successful return true. Otherwise, return false
     */
    public boolean tradeCollectable(String collectable, Player otherPlayer) {
        boolean hasCollectable = false;
        boolean otherPlayerExists = false;
        boolean tradeSuccessful = false;

        String[] currentCollectables = this.getInventory();

        for(String item : currentCollectables) {
            //TODO: Does the player currently hold the collectable?
            if( item.equals(collectable) ) {
               hasCollectable = true;
                break;
            }
        }

        //TODO: Does the other player currently exist in the database?
        if(true) {
           otherPlayerExists = true;
        }

        if(hasCollectable && otherPlayerExists) {
            this.removeItemFromInventory(collectable);
            otherPlayer.addItemToInventory(collectable);
            tradeSuccessful = true;
        }

        return tradeSuccessful;
    }

    /**
    * @param item item to be added to inventory
    */
    public void addItemToInventory(String item) {
        //TODO - Interact with DB
    }

    /**
     * @param item item to be removed/consumed
     */
    public void removeItemFromInventory(String item) {
        //TODO - Interact with DB
    }

    public String[] getInventory() {
        //TODO - Interact with DB
        return null;
    }

    // TODO: How will player be able to store achievements?
    /**
     * Adds a single new achievement to players profile
     * @param achievement Name of achievement
     */
    public void addAchievement(String achievement) {
        //TODO - Interact with DB
    }

    /**
     * Retrieves players achievements
     * @return A String list of all player achievements
     */
    public String[] getAchievements() {
        //TODO - Interact with DB
        return null;
    }


}
