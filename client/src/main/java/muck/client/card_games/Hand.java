package muck.client.card_games;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Hand extends Deck{

    public Hand(){
        cards = new ArrayList<Card>();
        ArrayList<Card> sets = new ArrayList<Card>();
        Image backOfDeck = new ImageIcon("muck/client/card_games/images/backofdeck.png").getImage();
    }

    void draw_cards(Deck deck){
        cards.add(deck.cards.get(0));
        deck.cards.remove(0);
    }
}
