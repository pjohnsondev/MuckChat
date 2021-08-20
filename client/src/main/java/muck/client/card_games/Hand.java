package muck.client.card_games;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Hand extends Deck{

    public Hand(){
        cards = new ArrayList<Card>();
        ArrayList<Card> sets = new ArrayList<Card>();
        String cardValue;
        String cardName;
        Image backOfDeck = new ImageIcon("muck/client/card_games/images/backofdeck.png").getImage();
    }

    void draw_cards(Deck deck){
        cards.add(deck.cards.get(0));
        deck.cards.remove(0);
    }

    void draw_hand(Deck deck){
        for (int i= 0; i < 7; i++){
            cards.add(deck.cards.get(i));
            deck.cards.remove(i);
        }
    }
}
