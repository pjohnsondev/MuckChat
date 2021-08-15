package muck.client.card_games;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Some tests to write

/*
    Test that the same card can't exist in more than one place (could use id as it's unique)
    Test that the length of the hands and the deck is accurate in certain situations, like after dealing, after picking from the pile etc.
    Test that each card has an image - not null. (Once it's been implemented)

 */

public class CardTest {

    private static final Logger logger = LogManager.getLogger(Deck.class);

    @Test
    public void testDeckSize() {
        // logic to date works on a standard size deck of 52 cards.

        Deck myDeck = new Deck();
        assertEquals(52, myDeck.cards.size());
    }

    /*
    @Test
    public void testGameSetup() {
        // needs to be implemented
    }
    */


}
