package muck.client.card_games;

import java.util.ArrayList;
import java.util.Collections;

public class Hand extends Deck {
    public ArrayList<Card> sets;
    public ArrayList<Card> cards;
    public int thisMatchId;


    /**
     * Constructor Function for the Hand Class
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
     * @param deck
     */
    public void drawTopCard(Deck deck) {
        this.cards.add(deck.cards.get(0));
        deck.cards.remove(0);
        reorderHand();
    }


    /**
     * drawHand Method.
     * Takes a deck as a parameter. Takes 7 Card objects from that deck and places it into the Hand
     * Also reorders the cards in the hand
     * @param deck
     */
    public void drawHand(Deck deck) {
        if (deck.cards.size() >= 7) {
            for (int i = 7; i > 0; i--) {
                this.cards.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        } else {
            for (int i = deck.cards.size() - 1; i > 0; i--) {
                this.sets.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        }
        reorderHand();
        //This stops either player from getting a set when they draw their hand
        if (checkForSet(true)){
            for (int i = cards.size() - 1; i > 0; i--) {
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
     * @param thisCard
     */
    public void selectAll(Card thisCard) {
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
    }


    /**
     * deSelectAll Method
     * Deselects all highlighted cards in the hand
     */
    public void deselectAll() {
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i).getSelectedValue() == true) {
                cards.get(i).setSelected(false);
            }
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
    public void makeSet(int thisMatchId) {
        for (int i = this.cards.size() - 1; i > 0; i--) {
            if (this.cards.get(i).getSelectedValue() &&
                    this.cards.get(i).getMatchId() == thisMatchId) {
                this.sets.add(this.cards.get(i));
                this.cards.remove(i);
            }
        }
    }


    /**
     * reOrderHand Method
     * Orders the cards in the hand to be in ascending order
     */
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


    /**
     * checkForSet Method
     * Checks to see if a full set of 4 card has been made
     * @param start
     * @return boolean
     */
    public boolean checkForSet(boolean start){
        for (int i = 0; i < cards.size() - 3; i++){
            if (cards.get(i).getMatchId() == cards.get(i + 3).getMatchId()){
                if (!start){
                    selectAll(cards.get(i));
                    makeSet(cards.get(i).getMatchId());
                }
                return true;
            }
        }
        // Default case
        return false;
    }


    /**
     * checkSelected Method
     * Checks all cards in the hand and returns either true or false if one is selected
     * @return boolean
     */
    public boolean checkSelected(){
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).getSelectedValue()){
                return true;
            }
        }
        return false;
    }
}
