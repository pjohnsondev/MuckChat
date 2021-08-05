package muck.server;

import muck.core.character.Character;
import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.protocol.connection.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

/**
 * Class to manager character registration, login and removal.
 */
public class CharacterManager {
    private Character character;
    private HashSet<String> loggedInUsers = new HashSet();
    private static final Logger logger = LogManager.getLogger(MuckServer.class);

    /**
     * Method to log in the new character to the game
     * @param login
     * @return
     * @throws CharacterDoesNotExistException
     * @throws DuplicateLoginException
     */
    public Character loginCharacter(Login login) throws CharacterDoesNotExistException, DuplicateLoginException {
        // TODO: validate the user details etc.

        if (character == null) {
            character = new Player(login.getUsername());
        }

        if (loggedInUsers.contains(character.getIdentifier())) {
            logger.info("This is a duplicate login for {}", login.getUsername());

            throw new DuplicateLoginException(login.getUsername());
        }

        // Add user to hashset of logged in users
        loggedInUsers.add(login.getUsername());

        // User has logged in successfully
        return character;
    }
}
