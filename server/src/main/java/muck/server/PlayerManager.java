package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import muck.core.Login;
import muck.core.user.SignUpInfo;
import muck.server.models.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.Char;

import java.sql.SQLException;
import java.util.HashSet;

/**
 * Class to manager character registration, login and removal.
 */
public class PlayerManager {
    private Player player;
    private HashSet<String> loggedInPlayers = new HashSet();
    private static final Logger logger = LogManager.getLogger(MuckServer.class);
    private User user;

    public PlayerManager(User user) {
        this.user = user;
    }

    /**
     * Method to log in the new character to the game
     * @param login
     * @return
     * @throws CharacterDoesNotExistException
     * @throws DuplicateLoginException
     */
    public Player loginPlayer(Login login) throws CharacterDoesNotExistException, DuplicateLoginException, AuthenticationFailedException {
        // TODO: validate the user details etc.
        logger.info("Player is attempting to log in: {}.", login.getUsername());

        try {
            //Verify the supplied login credentials
            if(!user.authenticateUser(login.getUsername(), login.getPassword())) {
                logger.error("Login credentials are invalid. User: {}.", login.getUsername());
                throw new AuthenticationFailedException("Invalid credentials provided. Username: " + login.getUsername());
            }
            logger.info("User with provided credentials found in the database. Username: {}.", user.getUserName());
        } catch (SQLException ex) {
            logger.error("Exception: " + ex);
            throw new RuntimeException("Error occurred while signing in user: " + user.getUserName());
        }

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

    /**
     * Method to create a new Player using supplied username and password
     *
     * @param signUpInfo
     * @return Player
     * @throws BadRequestException
     *
     */
    public Player signupPlayer(SignUpInfo signUpInfo) throws BadRequestException {
        // Validate the user details
        if(signUpInfo.getUsername() == null || signUpInfo.getUsername().equals("") ) {
            throw new BadRequestException("Please provide valid user name. Username cannot be null or empty");
        } else if (signUpInfo.getPassword() == null || signUpInfo.getPassword().equals("")) {
            throw new BadRequestException("Please provide valid password. Password cannot be null or empty");
        } else if (signUpInfo.getDisplayName() == null || signUpInfo.getDisplayName().equals("")) {
            throw new BadRequestException("Please provide valid profile name. Profile name cannot be null or empty");
        }

        try {
            // Verify that supplier username does not already exist
            if(user.findUserByUsername(signUpInfo.getUsername())) {
                throw new BadRequestException("A user with the username %s already exists." + signUpInfo.getUsername());
            }
        } catch (Exception ex) {
            logger.error("User already exists: {}", signUpInfo.getUsername());

            throw new RuntimeException(String.format("A user with the username %s already exists.", signUpInfo.getUsername()));
        }

        // Create the new user
        try {
            user.registerNewUser(signUpInfo.getUsername(), signUpInfo.getPassword());

            player = CharacterFactory.createOrLoadPlayer(user.getUserName());

            return player;
        } catch (Exception ex) {
            logger.error("Unable to create new user: {}", signUpInfo.getUsername());

            throw new RuntimeException(String.format("Unable to create new user: %s.", signUpInfo.getUsername()));
        }
    }
}
