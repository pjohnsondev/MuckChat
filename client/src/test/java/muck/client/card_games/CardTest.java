package muck.client.card_games;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//TODO Implement tests for all functions
//TODO comment method and class headers

// This file runs tests on the methods of each of the classes created to make the Go Fish Card Game.
// All tests are in this file - separated by /************** CLASS NAME HERE TESTS **************/

// Some tests to write

/*
    Test that the same card can't exist in more than one place (could use id as it's unique)
    Test that the length of the hands and the deck is accurate in certain situations, like after dealing, after picking from the pile etc.
    Test that each card has an image - not null. (Once it's been implemented)

 */

public class CardTest {
    Card card;
    Deck myDeck;
    Hand myHand;
    ComputerOpponent opponent;
    Game game;
    Player player;
    PlayerTurn playerTurn;
    private static final Logger logger = LogManager.getLogger(Deck.class);
    Random rand;

    @BeforeEach
    void setup() {
        card = new Card(5, 5, "clubs", "ace");
        myDeck = new Deck();
        rand = new Random();
        myHand = new Hand();
        opponent = new ComputerOpponent(1);
        game = new Game();
        player = new Player();
        playerTurn = new PlayerTurn(1);

    }
    @Test
    public void testDeckSize() {
        // logic to date works on a standard size deck of 52 cards.
        logger.info("Testing created deck is equal to 52 cards");

        assertEquals(52, myDeck.cards.size());
    }


    @Test
    public void testShuffle() {
        logger.info("Testing that a random card in the deck is not in the same place after a shuffle");
        int randomID = rand.nextInt(52);
        int beforeShuffle = myDeck.cards.get(randomID).getCardId();
        myDeck.shuffleCards();
        int afterShuffle = myDeck.cards.get(randomID).getCardId();
        assertNotEquals(beforeShuffle, afterShuffle);
        logger.info("Testing that a random card in the deck is not in the same place after a second shuffle");
        myDeck.shuffleCards();
        int afterSecondShuffle = myDeck.cards.get(randomID).getCardId();
        assertNotEquals(afterShuffle, afterSecondShuffle);
    }

    @Test
    public void testDrawCard() {
        logger.info("Testing that drawing the top card from the deck, deletes it from the main deck array and adds ths same card to the hand");
        int cardID = myDeck.cards.get(0).getCardId();
        myHand.drawTopCard(myDeck);

        assertAll(
                () -> assertEquals(51, myDeck.cards.size()),
                () -> assertEquals(1, myHand.cards.size()),
                () -> assertNotEquals(cardID, myDeck.cards.get(0).getCardId()),
                () -> assertEquals(myHand.cards.get(0).getCardId(), cardID),
                () -> assertEquals(myHand.cards.size(), 1)
        );
    }

    @Test
    public void testSelectCard() {
        logger.info("Testing that the set selected method sets selected to true and false as passed in");
        int randomID = rand.nextInt(52);
        Card card = myDeck.cards.get(randomID);
        assertEquals(card.getSelectedValue(), false);
        card.setSelected(true);
        assertEquals(card.getSelectedValue(), true);
        card.setSelected(false);
        assertEquals(card.getSelectedValue(), false);
    }

    /*
    @Test
    public void testGameSetup() {
        // needs to be implemented
    }
    */


}
