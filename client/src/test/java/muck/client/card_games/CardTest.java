package muck.client.card_games;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
