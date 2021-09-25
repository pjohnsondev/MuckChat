package muck.server;

import muck.core.character.AddCharacter;
import muck.core.character.CharacterDoesNotExistException;
import muck.core.character.CharacterFactory;
import muck.core.character.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InteractionListenerTest {

      private static final Logger logger = LogManager.getLogger(CharacterLocationTrackerTests.class);
      InteractionListener interactionListener = new InteractionListener();

        @BeforeEach
        public void beforeEach() {

        }

        @BeforeAll
        public static void beforeAll() {

        }

        @Test
        public void testCommandValidator(){
            String validCommand = "/wave";   //the only valid command currently
            String noSlash = "wave"; //invalid
            String notACommand = "/slap";   //invalid
            String contentAfter = "/wave "; //invalid

            assertAll("InteractionListener recognises valid/invalid commands",
                    () -> assertTrue(interactionListener.isValidCommand(validCommand)),
                    () -> assertFalse(interactionListener.isValidCommand(noSlash)),
                    () -> assertFalse(interactionListener.isValidCommand(notACommand)),
                    () -> assertFalse(interactionListener.isValidCommand(contentAfter))
                    );
            }

    }
