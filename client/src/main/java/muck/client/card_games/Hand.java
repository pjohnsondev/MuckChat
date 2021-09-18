package muck.client.card_games;

import java.util.ArrayList;
import java.util.Collections;

public class Hand extends Deck {
    public ArrayList<Card> sets;
    public ArrayList<Card> cards;
    public int thisMatchId;

    /**
     * Constructor Function for the Hand Class
     *
     * //@param cardValue
     * //@param cardName
     */
    public Hand() {
        cards = new ArrayList<Card>();
        sets = new ArrayList<Card>();
        String cardValue;
        String cardName;
    }

    /**
     * drawTopCard Method.
     * Takes a deck as a parameter. Takes a Card object from that deck and places it into the Hand
     */
    public void drawTopCard(Deck deck) {
        this.cards.add(deck.cards.get(0));
        deck.cards.remove(0);
        reorderHand();
    }

    /**
     * drawHand Method.
     * Takes a deck as a parameter. Takes 7 Card objects from that deck and places it into the Hand
     */
    public void drawHand(Deck deck) {
        if (deck.cards.size() >= 7) {
            for (int i = 0; i < 7; i++) {
                this.cards.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        } else {
            for (int i = 0; i < deck.cards.size(); i++) {
                this.sets.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        }
        reorderHand();
        //This stops either player from getting a set when they draw their hand
        if (checkForSet(true)){
            for (int i = 0; i < cards.size(); i++) {
                deck.cards.add(cards.get(i));
                this.cards.remove(i);
            }
            deck.shuffleCards();
            drawHand(deck);
        }
    }

    /**
     * selectAll Method.
     * Takes a card as input and changes that card and others with the same value's selected value to true
     * Also makes sure all other cards are changed to false
     */
    void selectAll(Card thisCard) {
        int count = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i).getMatchId() == thisCard.getMatchId()) {
                cards.get(i).setSelected(true);
                count ++;
                thisMatchId = cards.get(i).getMatchId();
            } else {
                cards.get(i).setSelected(false);
            }
        }
        if (count == 4){
            //TODO make pop up appear for "Make set" and call makeSet()
        }
        else {
            //TODO make pop up appear for "Ask for card" and call game.playersAsk() and return a
            // pop up with either go fish (calling to draw a card) or announcing cards received
            // if a set is made, prompt player to make a set
        }
    }

    /**
     * makeSet Method.
     * Takes a card as input and finds all cards with the same value and moves them to sets Hand if they
     * are all currently selected
     * This is to be called when 4 cards of one type are in the hand together, giving that card type
     * for input
     *    TODO: make sure it only makes set if there's four of the same.
     */
    void makeSet(int thisMatchId) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getSelectedValue() &&
                    this.cards.get(i).getMatchId() == thisMatchId) {
                this.sets.add(this.cards.get(i));
                this.cards.remove(i);
            }
        }
    }

    void reorderHand() {
        boolean swapped = false;
        for (int i = 0; i < this.cards.size() - 1; i++) {
            if (this.cards.get(i).getMatchId() > this.cards.get(i + 1).getMatchId()) {
                Collections.swap(this.cards, i, (i + 1));
                swapped = true;
            }
            if (this.cards.get(i).getMatchId() == this.cards.get(i + 1).getMatchId()) {
                if (this.cards.get(i).getCardId() > this.cards.get(i + 1).getCardId()) {
                    Collections.swap(this.cards, i, (i + 1));
                    swapped = true;
                }
            }
        }
        if (swapped == true){
            reorderHand();
        }
    }

    public boolean checkForSet(boolean start){
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).getMatchId() == cards.get(i + 3).getMatchId()){
                if (!start){
                    makeSet(cards.get(i).getMatchId());
                }
                return true;
            }
        }
        // Default case
        return false;
    }
}
