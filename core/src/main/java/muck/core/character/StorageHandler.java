package muck.core.character;

import java.util.ArrayList;
import java.sql.*;

/**
 * Handles the storing and retrieval of Character objects (Player or NPC)
 * 
 * Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
 *  limit our original feature scope
 */
public class StorageHandler {
    // Update: Backend feature not fully integrated into final build (group size limited to 2 members, we had to
    //    limit our original feature scope

    /**
     * Save the character object to backend storage
     * @param character Character object to save to database
     * @return True if save was successful, otherwise return false
     */
    public static boolean saveCharacter(Character character) {
        boolean saveSuccessful = false;
        if (character.getClass() == Player.class) {
            //TODO save Player object
            // Nearly done (collab with Issue #24), but ran out of time and only 2 group members left
        }
        else if (character.getClass() == NPC.class) {
            //TODO save NPC object
            // Nearly done (collab with Issue #24), but ran out of time and only 2 group members left

            return false;
        }
        return saveSuccessful;
    }

    /**
     * Attempt to retrieve the specified player from backend storage. Returns true if it's valid.
     * @param username Identifier for the Player
     * @return True is Player is in database, otherwise return false
     */
    public static boolean isPlayerValid(String username) {
        boolean playerValid = false;

        return playerValid;
    }

    /**
     * Queries the database and stores the results in a list
     * @param query SQL query to execute
     * @param identifier Players identifier
     * @return ArrayList of items from the executed query
     */
    public static ArrayList<String> queryPlayer(String query, String identifier) {
        ArrayList<String> list = new ArrayList<String>();
        //TODO: implement with db
        return list;
    }

    /**
     * Adds an item to the database for the given Player
     * @param query SQL query to execute
     * @param identifier Players identifier
     * @param item Item to add
     * @param description Item description
     */
    public static void addToPlayer(String query, String identifier, String item, String description) {
        //TODO: implement with db
    }

    /**
     * Adds an item to the database for the given Player
     * @param query SQL query to execute
     * @param identifier Players identifier
     * @param item Item to add
     */
    public static void addToPlayer(String query, String identifier, String item) {
        //TODO: implement with db
    }

    /**
     * Removes an item from the database for the given Player
     * @param query SQL query to execute
     * @param identifier Players identifier
     * @param item Item to remove
     */
    public static void removeFromPlayer(String query, String identifier, String item) {
        //TODO: implement with db
    }
}
