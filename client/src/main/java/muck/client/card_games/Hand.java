package muck.client.card_games;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Hand extends Deck{
    ArrayList<Card> sets;
    /**
     * Constructor Function for the Hand Class
     *
     * @param cardValue
     * @param cardName
     */
    public Hand(){
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
    void draw_top_card(Deck deck){
        this.cards.add(deck.cards.get(0));
        deck.cards.remove(0);
    }

    /**
     * draw_hand Method.
     * Takes a deck as a parameter. Takes 7 Card objects from that deck and places it into the Hand
     */
    void draw_hand(Deck deck){
        if (deck.cards.size() >= 7) {
            for (int i = 0; i < 7; i++) {
                this.cards.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        }
        else {
            for (int i = 0; i < deck.cards.size(); i++) {
                this.sets.add(deck.cards.get(i));
                deck.cards.remove(i);
            }
        }
    }

    /**
     * select_all Method.
     * Takes a card as input and changes that card and others with the same value's selected value to true
     * Also makes sure all other cards are changed to false
     */
    void select_all(Card this_card){
        for (int i = 0; i < this.cards.size(); i++){
            if (cards.get(i).getCardName() == this_card.getCardName()){
                cards.get(i).setSelected(true);
            }
            else {
                cards.get(i).setSelected(false);
            }
        }
    }

    /**
     * make_set Method.
     * Takes a card as input and finds all cards with the same value and moves them to sets Hand if they
     *    are all currently selected
     * This is to be called when 4 cards of one type are in the hand together, giving that card type
     *    for input
     *    TODO: make sure it only makes set if there's four of the same.
     */
    void make_set(Card this_card){
        for (int i = 0; i < this.cards.size(); i++){
            if (this.cards.get(i).getSelectedValue() &&
                    this.cards.get(i).getCardName() == this_card.getCardName()){
                this.sets.add(this.cards.get(i));
                this.cards.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        Hand hand = new Hand();
        Deck deck = new Deck();

        // Trying to draw enough that we should have multiple of the same
        for (int i = 0; i < 52; i++){
            hand.draw_top_card(deck);
        }
        hand.select_all(hand.cards.get(0));
        for (int i = 0; i < hand.cards.size(); i++){
            if (hand.cards.get(i).getSelectedValue()) {
                System.out.println(hand.cards.get(i).getCardName());
            }
        }
        hand.select_all(hand.cards.get(1));
        for (int i = 0; i < hand.cards.size(); i++){
            if (hand.cards.get(i).getSelectedValue()) {
                System.out.println(hand.cards.get(i).getCardName());
            }
        }
        hand.make_set(hand.cards.get(0));
        System.out.println("Player has collected all: ");
        for (int i = 0; i < hand.sets.size(); i++){
            if (i%4 == 0) {
                System.out.print(hand.sets.get(i).getCardName());
                System.out.println("'s.\n");
            }
        }
        System.out.println("next card is: " + hand.cards.get(0).getCardName());
        hand.select_all(hand.cards.get(0));
        hand.make_set(hand.cards.get(0));
        System.out.println("Player has collected all: ");
        for (int i = 0; i < hand.sets.size(); i++){
            if (i%4 == 0) {
            System.out.print(hand.sets.get(i).getCardName() + "'s.\n");
            }
        }
    }
}