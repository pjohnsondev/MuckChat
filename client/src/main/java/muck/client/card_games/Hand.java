package muck.client.card_games;

import muck.client.CardsGameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Hand extends Deck {
    public ArrayList<Card> sets;
    public ArrayList<Card> cards;

    /**
     * Constructor Function for the Hand Class
     *
     * @param cardValue
     * @param cardName
     */
    public Hand() {
        cards = new ArrayList<Card>();
        sets = new ArrayList<Card>();
        String cardValue;
        String cardName;
        Image backOfDeck = new ImageIcon("muck/client/card_games/images/backofdeck.png").getImage();
    }

    /**
     * draw_top_card Method.
     * Takes a deck as a parameter. Takes a Card object from that deck and places it into the Hand
     */
    public void drawTopCard(Deck deck) {
        this.cards.add(deck.cards.get(0));
        deck.cards.remove(0);
        reorderHand();
    }

    /**
     * draw_hand Method.
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
    }

    /**
     * select_all Method.
     * Takes a card as input and changes that card and others with the same value's selected value to true
     * Also makes sure all other cards are changed to false
     */
    void selectAll(Card thisCard) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i).getMatchId() == thisCard.getMatchId()) {
                cards.get(i).setSelected(true);
            } else {
                cards.get(i).setSelected(false);
            }
        }
    }

    /**
     * make_set Method.
     * Takes a card as input and finds all cards with the same value and moves them to sets Hand if they
     * are all currently selected
     * This is to be called when 4 cards of one type are in the hand together, giving that card type
     * for input
     *    TODO: make sure it only makes set if there's four of the same.
     */
    void makeSet(Card thisCard) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getSelectedValue() &&
                    this.cards.get(i).getMatchId() == thisCard.getMatchId()) {
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


    public static void main(String[] args) {
        Hand hand = new Hand();
        Deck deck = new Deck();
        deck.shuffle_cards();
        // Trying to draw enough that we have multiple of the same
        for (int i = 0; i < 10; i++) {
            hand.drawTopCard(deck);
        }
        for (int i = 0; i < 10; i++){
            System.out.println("next card is: " + hand.cards.get(i).getCardName());
        }
        System.out.println("reordering");
        /*hand.selectAll(hand.cards.get(0));
        for (int i = 0; i < hand.cards.size(); i++){
            if (hand.cards.get(i).getSelectedValue()) {
                System.out.println(hand.cards.get(i).getCardName());
            }
        }
        hand.selectAll(hand.cards.get(1));
        for (int i = 0; i < hand.cards.size(); i++){
            if (hand.cards.get(i).getSelectedValue()) {
                System.out.println(hand.cards.get(i).getCardName());
            }
        }
        hand.makeSet(hand.cards.get(0));
        System.out.println("Player has collected all: ");
        for (int i = 0; i < hand.sets.size(); i++){
            if (i%4 == 0) {
                System.out.print(hand.sets.get(i).getCardName());
                System.out.println("'s.\n");
            }
        }
        System.out.println("next card is: " + hand.cards.get(0).getCardName());
        hand.selectAll(hand.cards.get(0));
        hand.makeSet(hand.cards.get(0));
        System.out.println("Player has collected all: ");
        for (int i = 0; i < hand.sets.size(); i++){
            if (i%4 == 0) {
            System.out.print(hand.sets.get(i).getCardName() + "'s.\n");
            }
        }
    }*/
        hand.reorderHand();
        for (int i = 0; i < 10; i++){
            System.out.println("next card is: " + hand.cards.get(i).getCardName());
        }
    }
}
