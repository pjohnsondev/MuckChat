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
// All tests are in this file - separated by /************** {CLASS NAME HERE} TESTS **************/

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
    private static final Logger logger = LogManager.getLogger(Deck.class);
    Random rand;

    @BeforeEach
    void setup() {
        card = new Card(1, 1, "clubs", "ace");
        myDeck = new Deck();
        rand = new Random();
        myHand = new Hand();
        opponent = new ComputerOpponent(1);
        game = new Game();
        player = new Player();

    }


    // ************************************** CARD TESTS ****************************************** /

    @Test
    public void testGetCardId() {
        logger.info("Testing getting the correct card ID");
        assertEquals(1, card.getCardId());
    }


    @Test
    public void testUseFileName() {
        logger.info("Testing that the correct filename is returned based on the selected value");
        assertEquals("/images/cards/ace_of_clubs.png", card.useFileName());
        card.setSelected(true);
        assertEquals("/images/cards/B_ace_of_clubs.png", card.useFileName());

    }


    @Test
    public void testGetFileName() {

        logger.info("Testing the filename is correct");
        assertEquals("/images/cards/ace_of_clubs.png", card.getFileName());
    }


    @Test
    public void testGetBFileName() {
        logger.info("Testing that the highlighted image filename is correct");
        assertEquals("/images/cards/B_ace_of_clubs.png", card.getBFileName());
    }


    @Test
    public void testSelectCard() {
        logger.info("Testing that the set selected method sets selected to true and false as passed in");
        int randomID = rand.nextInt(52);
        Card card = myDeck.cards.get(randomID);
        assertFalse(card.getSelectedValue());
        card.setSelected(true);
        assertTrue(card.getSelectedValue());
        card.setSelected(false);
        assertFalse(card.getSelectedValue());
    }


    @Test
    public void testGetSuit() {
        logger.info("Testing the correct suit is returned");
        assertEquals("clubs", card.getSuit());
    }


    @Test
    public void testGetCardName() {
        logger.info("Testing that the returned card name is correct");
        assertEquals("ace", card.getCardName());
    }


    @Test
    public void testGetMatchId() {
        logger.info("Testing that the correct Match ID number is returned");
        assertEquals(1, card.getMatchId());
    }


    @Test
    public void testSetCardId() {
        logger.info("Testing that setting the card Id is working");
        assertNotEquals(5, card.getCardId());
        card.setCardId(5);
        assertEquals(5, card.getCardId());
    }


    @Test
    public void testSetSuit() {
        logger.info("Testing that setting the suit is working");
        assertNotEquals("hearts", card.getSuit());
        card.setSuit("hearts");
        assertEquals("hearts", card.getSuit());
    }


    @Test
    public void testSetCardName() {
        logger.info("Testing that setting the card name is working");
        assertNotEquals("five", card.getCardName());
        card.setCardName("five");
        assertEquals("five", card.getCardName());
    }


    @Test
    public void testSetMatchId() {
        logger.info("Testing that setting the match id is working");
        assertNotEquals(7, card.getMatchId());
        card.setMatchId(7);
        assertEquals(7, card.getMatchId());
    }


    @Test
    public void testToString() {
        logger.info("Testing that returning the string of the full card name, and capitalised is working");
        assertEquals("Ace of Clubs", card.toString());
    }




    // ************************************** DECK TESTS ****************************************** /
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


    // ************************************** COMPUTER OPPONENT TESTS ****************************************** /

    //TODO - Adding to computer/player array in the computer opponent class

    //TODO
    @Test
    public void testAskForCard() {
        assertEquals(1, 1);
    }

    // ************************************** GAME TESTS ****************************************** /

    //init game

    //TODO test that player1 and player2 hand size is 7, deck[0] is not id 1
    //assert all


    //playersTurn

    //TODO wait for logic in this method


    //computersTurn

    //TODO check for goFish being true (no matches) then checking players hand grows by 1
    // and not true, player2 (computer) hand grows by at least 1


    //playersAsk(match id : int)

    //TODO make a match - check that players hand grows by at least and player2 hand decreases
    // by at least 1 but make sure its the same card


    //checkForMatch(matchId : int)

    //TODO check that a match returns true and not matching returns false

    //giveComputerCard(matchId : int)



    // ************************************** HAND TESTS ****************************************** /
    @Test
    public void testDrawTopCard() {
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
    public void testDrawHand() {
        logger.info("Testing that drawing a hand from the deck is functioning correctly");
        myHand.drawHand(myDeck);
        assertEquals(7, myHand.cards.size());
        assertEquals(45, myDeck.cards.size());
    }


    //TODO
    @Test
    public void testSelectAll() {
        logger.info("Testing that the select all cards method is working as intended");
        // draw 20 cards from the deck and add to the cards array
        // get the value of the first card
        // add all matching cards to another empty array
        // test the selected value of all cards in the new array
    }

    // TODO
    @Test
    public void testDeselectAll() {
        // Same as above but just choose 20 random cards in a new array
        // set all the values to true with a for loop
        // deselect all then test all cards for selected value
    }

    // TODO
    // TODO May need to update this test once the check for 4 is put into place
    @Test
    public void testMakeSets() {
        // add 52 cards from the deck to the cards array ( no need to remove from Deck)
        // pass in the first matchid to the makeset method
        // check the size of sets is array is 4

    }

    @Test
    public void testReorderHand() {
        // add 1 hand to the cards array
        // test if card 1 is higher than card 2 etc until true
        // reorder the hand, test again
    }


    //TODO create this test
    @Test
    public void testCheckForSet() {

    }



    // ************************************** PLAYER TESTS ****************************************** /


    @Test
    public void testAddScoreAndGetScore() {
        logger.info("testing that the player score can be obtained and is incremented");
        assertEquals(0, player.getScore());
        player.addScore();
        assertEquals(1, player.getScore());
    }
}
