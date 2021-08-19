package muck.core.character;

import java.util.ArrayList;

public class Player extends Character {
    /**
     * Player constructor. This class is an extension of the Character class for human players.
     * This should instantiate a player with a username that exists in the backend persistent storage.
     * (Will Issue #11 handle player username & password creation in the backend?)
     * If the player username doesn't exist, a CharacterDoesNotExist exception will be thrown.
     * Example usage: Player player1 = new Player("my_username");
     */
    private String username;

    public Player(String username) {
        //TODO - Retrieve the username identifier from the backend database, then populate all fields with 
        // player values from the database
        /*boolean databaseRetrievalSuccessful = StorageHandler.isPlayerValid(username);
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(username);
        }

        this.setAvatar("some avatar retrieved from backend"); //further colab with issue #7 required
        this.setIdentifier(username);
        */

        this.username = username;
    }

    /**
     * Dummy constructor for a player object with a "null" identifier. Does not
     * check with backend storage for a valid username. Should only be used for unit tests that don't use backend
     */
    public Player() {
        this.setIdentifier(null);
    }

    public String getUsername() {
        return username;
    }

    /**
     * Trades a single collectable currently held by the player with another existing player
     * Collaborate with Issue 10
     * @param collectable Collectable item to trade
     * @param otherPlayer Other player to trade with
     * @return If player to player collectable transaction is successful return true. Otherwise, return false
     */
    public boolean tradeCollectable(String collectable, String otherPlayer) {
        boolean hasCollectable = false;
        boolean otherPlayerExists = false;
        boolean tradeSuccessful = false;

        String[] currentCollectables = this.getInventory();

        // Check if the Player currently holds the collectable
        for(String item : currentCollectables) {
            if( item.equals(collectable) ) {
               hasCollectable = true;
               break;
            }
        }

        //TODO: Does the other player currently exist in the database?
        if(StorageHandler.isPlayerValid(otherPlayer)) {
           otherPlayerExists = true;
        }

        // Conduct trade if Player has collectable and other Player exists
        if(hasCollectable && otherPlayerExists) {
            //TODO: Confirm query
            String query = "INSERT INTO Player(identifier, inventory) VALUES (?, ?)";
            StorageHandler.addToPlayer(query, otherPlayer, collectable);
            this.removeItemFromInventory(collectable);
            tradeSuccessful = true;
        }

        return tradeSuccessful;
    }

    /**
     * Adds an item to the Players Inventory
     * @param item item to be added to inventory
     */
    public void addItemToInventory(String item) {
        //TODO: Confirm query
        String query = "INSERT INTO Player(identifier, inventory) VALUES (?, ?)";
        StorageHandler.addToPlayer(query, this.getIdentifier(), item);
    }

    /**
     * Removes an item from the Players Inventory
     * @param item item to be removed/consumed
     */
    public void removeItemFromInventory(String item) {
        //TODO: Confirm query
        String query = "DELETE FROM Player WHERE identifier = ? AND inventory = ?";
        StorageHandler.removeFromPlayer(query, this.getIdentifier(), item);
    }

    /**
     * Retrieves Players current inventory holdings
     * @return A String array of the players inventory
     */
    public String[] getInventory() {
        //TODO: Confirm query
        String query = "SELECT inventory FROM Player WHERE identifier = ?";
        ArrayList<String> inventoryList = StorageHandler.queryPlayer(query, this.getIdentifier());

        // Convert from ArrayList to Array
        String[] playerInventory = new String[inventoryList.size()];
        for(int i = 0; i < inventoryList.size(); i++){
            playerInventory[i] = inventoryList.get(i);
        }

        return playerInventory;
    }

    /**
     * Adds a single new achievement to players profile
     * @param achievement Name of achievement
     */
    public void addAchievement(String achievement) {
        //TODO: Confirm query
        String query = "INSERT INTO Player(identifier, achievements) VALUES (?, ?)";
        StorageHandler.addToPlayer(query, this.getIdentifier(), achievement);
    }

    /**
     * Retrieves Players achievements
     * @return A String array of all player achievements
     */
    public String[] getAchievements() {
        //TODO: Confirm query
        String query = "SELECT achievements FROM Player WHERE identifier = ?";
        ArrayList<String> achievementList = StorageHandler.queryPlayer(query, this.getIdentifier());

        // Convert from ArrayList to Array
        String[] playerAchievements = new String[achievementList.size()];
        for(int i = 0; i < achievementList.size(); i++){
            playerAchievements[i] = achievementList.get(i);
        }

        return playerAchievements;
    }


}
