package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import muck.server.Exceptions.ModelNotFoundException;
import muck.server.services.UserService;
import muck.core.structures.UserStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashSet;

/**
 * Class to manager character registration, login and removal.
 */
public class PlayerManager {
    private Player player;
    private HashSet<String> loggedInPlayers = new HashSet();
    private static final Logger logger = LogManager.getLogger(MuckServer.class);
    private UserService userService;

    public PlayerManager(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to log in the new character to the game
     * @return
     * @throws CharacterDoesNotExistException
     * @throws DuplicateLoginException
     */
    public Player loginPlayer(UserStructure userStructure) throws CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        // TODO: validate the user details etc.
        logger.info("Player is attempting to log in: {}.", userStructure.username);

        try {
            if (!userService.authenticateUser(userStructure)) {
                logger.error("Login credentials are invalid. User: {}.", userStructure.username);
                throw new AuthenticationFailedException("Invalid credentials provided. Username: " + userStructure.username);
            };
        } catch (SQLException ex) {
            logger.error("Exception: " + ex);
            throw new RuntimeException("Error occurred while signing in user: " + userStructure.username);
        } catch (ModelNotFoundException ex) {
            // repeat the same error message here so we don't tell users if it was password or username that failed.
            logger.error(ex.getMessage());
        }

        if (player == null) {
            player = CharacterFactory.createOrLoadPlayer(userStructure.username);
        }

        if (loggedInPlayers.contains(userStructure.username)) {
            logger.info("This is a duplicate login for {}", userStructure.username);

            throw new DuplicateLoginException(userStructure.username);
        }

        // Add user to hashset of logged in users
        loggedInPlayers.add(userStructure.username);

        // User has logged in successfully
        return player;
    }

    /**
     * Method to create a new Player using supplied username and password
     *
     * @param userStructure
     * @return Player
     * @throws BadRequestException
     *
     */
    public Player signupPlayer(UserStructure userStructure) throws BadRequestException {
        // Validate the user details
        if(userStructure.username == null || userStructure.username.equals("") ) {
            throw new BadRequestException("Please provide valid user name. Username cannot be null or empty");
        } else if (userStructure.password == null || userStructure.password.equals("")) {
            throw new BadRequestException("Please provide valid password. Password cannot be null or empty");
        } else if (userStructure.displayName == null || userStructure.displayName.equals("")) {
            throw new BadRequestException("Please provide valid profile name. Profile name cannot be null or empty");
        }

        try {
            // Verify that supplier username does not already exist
            if(userService.findByUsername(userStructure.username) != null) {
                throw new BadRequestException("A user with the username %s already exists." + userStructure.username);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        // Create the new user
        try {


            userService.registerNewUser(userStructure);

            player = CharacterFactory.createOrLoadPlayer(userStructure.username);

            return player;
        } catch (Exception ex) {
            logger.error("Unable to create new user: {}", userStructure.username);

            throw new RuntimeException(String.format("Unable to create new user: %s.", userStructure.username));
        }
    }
}
