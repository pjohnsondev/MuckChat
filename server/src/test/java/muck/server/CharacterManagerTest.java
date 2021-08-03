package muck.server;

import aw.character.Character;
import aw.character.CharacterDoesNotExistException;
import aw.character.Player;
import muck.core.Id;
import muck.core.Location;
import muck.protocol.connection.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterManagerTest {

    private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

    @Test
    public void CharacterGetsCreatedOnLoginWithSuppliedUsername() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Login login = new Login(username, "test_password");
        CharacterManager characterManager = new CharacterManager();

        Character character = characterManager.loginCharacter(login);

        assertNotNull(character);
        assertEquals(username, character.getIdentifier());
    }

    @Test
    public void ExceptionIsThrownWhenThereIsADuplicateLogin() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Login login = new Login(username, "test_password");
        CharacterManager characterManager = new CharacterManager();

        characterManager.loginCharacter(login);

        assertThrows(DuplicateLoginException.class, ()-> characterManager.loginCharacter(login));
    }
}
