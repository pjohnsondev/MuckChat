package anyonewelcome.character;

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

    
}
