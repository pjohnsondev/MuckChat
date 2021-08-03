package muck.server;

import aw.character.Character;
import aw.character.CharacterDoesNotExistException;
import aw.character.Player;
import muck.protocol.connection.AddCharacter;
import muck.protocol.connection.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MuckServerTest {
    private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

    @Test
    public void addCharacterObjectCreatesTheCharacter() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Character character = new Player(username);

        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(character);

        assertNotNull(addCharacter);

        assertEquals(addCharacter.getCharacter().getIdentifier(), character.getIdentifier());
    }

    @Test
    public void addingCharacterAddsTheCharacterToTheTracker() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Character character = new Player(username);

        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(character);

        assertNotNull(addCharacter);

        assertEquals(muckServer.tracker.getClients().get(0).left().id, character.getIdentifier());
    }
}
