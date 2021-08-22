package muck.client.card_games;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Hand extends Deck{

    /**
     * Constructor Function for the Hand Class
     *
     * @param cardValue
     * @param cardName
     */
    public Hand(){
        cards = new ArrayList<Card>();
        ArrayList<Card> sets = new ArrayList<Card>();
        String cardValue;
        String cardName;
        Image backOfDeck = new ImageIcon("muck/client/card_games/images/backofdeck.png").getImage();
    }

    /**
     * draw_top_card Method.
     * Takes a deck as a parameter. Takes a Card object from that deck and places it into the Hand
     */
    void draw_top_card(Deck deck){
        cards.add(deck.cards.get(0));
        deck.cards.remove(0);
    }

    /**
     * draw_hand Method.
     * Takes a deck as a parameter. Takes 7 Card objects from that deck and places it into the Hand
     */
    void draw_hand(Deck deck){
        for (int i= 0; i < 7; i++){
            cards.add(deck.cards.get(i));
            deck.cards.remove(i);
        }
    }
}
