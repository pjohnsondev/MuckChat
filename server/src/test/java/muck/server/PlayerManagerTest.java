package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.Player;
import muck.core.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerTest {

    private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

    @Test
    public void CharacterGetsCreatedOnLoginWithSuppliedUsername() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Login login = new Login(username, "test_password");
        PlayerManager playerManager = new PlayerManager();

        Player player = playerManager.loginPlayer(login);

        assertNotNull(player);
        assertEquals(username, player.getUsername());
    }

    @Test
    public void ExceptionIsThrownWhenThereIsADuplicateLogin() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Login login = new Login(username, "test_password");
        PlayerManager playerManager = new PlayerManager();

        playerManager.loginPlayer(login);

        assertThrows(DuplicateLoginException.class, ()-> playerManager.loginPlayer(login));
    }
}
