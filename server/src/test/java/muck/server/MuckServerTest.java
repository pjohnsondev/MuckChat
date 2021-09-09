package muck.server;

import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import muck.core.character.AddCharacter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import muck.core.ClientId;
import muck.core.Id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MuckServerTest {
    private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);

    @BeforeEach
    public void beforeEach() {
        logger.info("This message gets printed before each test runs");
        MuckServer.getINSTANCE().tracker = (ICharacterLocationTracker<ClientId>)new CharacterLocationTracker<ClientId>();
    }

    @Test
    public void addCharacterObjectCreatesTheCharacter() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Player player = CharacterFactory.createOrLoadPlayer(username);

        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(new Id<ClientId>(), player);

        assertNotNull(addCharacter);

        assertEquals(addCharacter.getCharacter().getIdentifier(), player.getIdentifier());
    }

    @Test
    public void addingCharacterAddsTheCharacterToTheTracker() throws CharacterDoesNotExistException, DuplicateLoginException {
        String username = "test_username";
        Player player = CharacterFactory.createOrLoadPlayer(username);
		var id = new Id<ClientId>();
        MuckServer muckServer = MuckServer.getINSTANCE();
        AddCharacter addCharacter = muckServer.addCharacter(id, player);

        assertNotNull(addCharacter);

        assertEquals(muckServer.tracker.getAllPlayerLocations().get(0).left(), null);
		assertEquals(muckServer.tracker.getAllPlayerLocations().get(0).right(), new muck.core.Location(0, 0));
    }
}
