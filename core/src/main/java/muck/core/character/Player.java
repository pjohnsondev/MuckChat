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

    /**
     * Create new Player or retrieve an existing Player from database
     * @param username Player username
     */
    public Player(String username) {
        this.username = username;
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        
        //Retrieve the username identifier from the backend database, then populate all fields with
        // player values from the database
        /*
        boolean databaseRetrievalSuccessful = StorageHandler.isPlayerValid(username);
        if (!databaseRetrievalSuccessful) {
            throw new CharacterDoesNotExistException(username);
        }
        this.setAvatar("some avatar retrieved from backend"); //further colab with issue #7 required
        this.setIdentifier(username);
        */
    }

    /**
     * Create new Player or retrieve an existing Player from database
     * @param username Player username
     * @param create True if creating new Player. False if retrieving an existing Player
     */
    public Player(String username, boolean create) {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        this.username = username;
        this.setIdentifier(username);

        if (create) {
            // TODO: add new Player to db? or when saveCharacter is called?
        }
    }

    /**
     * Returns the Players username
     * @return Player username
     */
    public String getUsername() {
        return username;
    }

    /***
     * Set Player stats. Should only be called once on creation of new or existing Player
     * @param health Player health
     * @param attack Player attack level
     * @param defence Player defence level
     */
    public void setPlayerStats(int health, int attack, int defence) {
        this.setHealth(health);
        this.setAttack(attack);
        this.setDefence(defence);
    }

    /**
     * Adds a single new achievement to players profile
     * @param achievement Name of achievement
     */
    public void addAchievement(String achievement, String achievementDescription) {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        String query = "INSERT INTO Player(identifier, achievement, description) VALUES (?, ?, ?)";
        StorageHandler.addToPlayer(query, this.getIdentifier(), achievement, achievementDescription);
    }

    /**
     * Retrieves Players achievements
     * @return A String array ArrayList of all player achievements
     */
    public ArrayList<String[]> getAchievements() {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        String query = "SELECT achievement, achievementDescription FROM Player WHERE identifier = ?";
        ArrayList<String> achievementList = StorageHandler.queryPlayer(query, this.getIdentifier());
        ArrayList<String[]> playerAchievements = new ArrayList<>();

        for(int i = 0; i < achievementList.size(); i += 2) {
            playerAchievements.add(new String[]{achievementList.get(i), achievementList.get(i+1)});
        }
        return playerAchievements;
    }

    /**
     * Feature has been abandoned current group, however, it may be picked up later on
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

        ArrayList<String> currentCollectables = this.getInventory();

        // Check if the Player currently holds the collectable
        for(String item : currentCollectables) {
            if( item.equals(collectable) ) {
               hasCollectable = true;
               break;
            }
        }

        if(StorageHandler.isPlayerValid(otherPlayer)) {
           otherPlayerExists = true;
        }

        // Conduct trade if Player has collectable and other Player exists
        if(hasCollectable && otherPlayerExists) {
            String query = "INSERT INTO Player(identifier, inventory) VALUES (?, ?)";
            StorageHandler.addToPlayer(query, otherPlayer, collectable);
            this.removeItemFromInventory(collectable);
            tradeSuccessful = true;
        }

        return tradeSuccessful;
    }

    /**
     * Feature has been abandoned current group, however, it may be picked up later on
     * Adds an item to the Players Inventory
     * @param item item to be added to inventory
     */
    public void addItemToInventory(String item) {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        String query = "INSERT INTO Player(identifier, inventory) VALUES (?, ?)";
        StorageHandler.addToPlayer(query, this.getIdentifier(), item);
    }

    /**
     * Feature has been abandoned current group, however, it may be picked up later on
     * Removes an item from the Players Inventory
     * @param item item to be removed/consumed
     */
    public void removeItemFromInventory(String item) {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        String query = "DELETE FROM Player WHERE identifier = ? AND inventory = ?";
        StorageHandler.removeFromPlayer(query, this.getIdentifier(), item);
    }

    /**
     * Feature has been abandoned current group, however, it may be picked up later on
     * Retrieves Players current inventory holdings
     * @return A String array of the players inventory
     */
    public ArrayList<String> getInventory() {
        // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
        //    limit our original feature scope
        String query = "SELECT inventory FROM Player WHERE identifier = ?";
        ArrayList<String> inventoryList = StorageHandler.queryPlayer(query, this.getIdentifier());

        return inventoryList;
    }
}
