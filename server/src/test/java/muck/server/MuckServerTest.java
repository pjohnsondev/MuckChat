package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import muck.core.character.AddCharacter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MuckServerTest {
    private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

    @BeforeEach
    public void beforeEach() {
        logger.info("This message gets printed before each test runs");
        MuckServer.getINSTANCE().tracker = new CharacterLocationTracker<String>();
    }

    @Test
    public void addCharacterObjectCreatesTheCharacter() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Player player = CharacterFactory.createOrLoadPlayer(username);

        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(player);

        assertNotNull(addCharacter);

        assertEquals(addCharacter.getCharacter().getIdentifier(), player.getIdentifier());
    }

    @Test
    public void addingCharacterAddsTheCharacterToTheTracker() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Player player = CharacterFactory.createOrLoadPlayer(username);

        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(player);

        assertNotNull(addCharacter);

        assertEquals(muckServer.tracker.getClients().get(0).left().id, player.getIdentifier());
    }
}
