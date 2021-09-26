package muck.client.card_games;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {
    ArrayList<Card> cards;
    String[] Suits = {"clubs", "diamonds", "hearts", "spades"};
    String[] cardNames = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
    String suit;
    int counter, cardsLeft;

    /**
     * Constructor function for the Deck Class
     */
    public Deck() {
        cards = new ArrayList<Card>();
        counter = 0;
        for (int i = 1; i < 53; i++) {
            if (i < 14) {
                suit = Suits[0];
            }
            else if (i < 27) {
                suit = Suits[1];
            }
            else if (i < 40) {
                suit = Suits[2];
            }
            else {
                suit = Suits[3];
            }
            cards.add(new Card(i, counter + 1, suit, cardNames[counter]));
            counter += 1;
            if (counter % 13 == 0) {
                counter = 0;
            }
        }
    }

    /**
     *  getCardsLeft method - Returns the number of cards in the deck as an int
     * @return int
     */
    public int getCardsleft() {
        return cards.size();
    }


    /**
     * Function to shuffle the deck
     */
    public void shuffleCards() {
        Collections.shuffle(cards);
    }
}
