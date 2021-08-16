package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import muck.core.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

/**
 * Class to manager character registration, login and removal.
 */
public class PlayerManager {
    private Player player;
    private HashSet<String> loggedInPlayers = new HashSet();
    private static final Logger logger = LogManager.getLogger(MuckServer.class);

    /**
     * Method to log in the new character to the game
     * @param login
     * @return
     * @throws CharacterDoesNotExistException
     * @throws DuplicateLoginException
     */
    public Player loginPlayer(Login login) throws CharacterDoesNotExistException, DuplicateLoginException {
        // TODO: validate the user details etc.

        if (player == null) {
            player = CharacterFactory.createOrLoadPlayer(login.getUsername());
        }

        if (loggedInPlayers.contains(player.getUsername())) {
            logger.info("This is a duplicate login for {}", login.getUsername());

            throw new DuplicateLoginException(login.getUsername());
        }

        // Add user to hashset of logged in users
        loggedInPlayers.add(login.getUsername());

        // User has logged in successfully
        return player;
    }
}
