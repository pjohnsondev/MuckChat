package muck.client.card_games;

//TODO: Create deck class

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (int i = 1; i < 14; i++) {
            cards.add(new Card(i, Card.Suit.HEARTS));
        }
        for (int i = 1; i < 14; i++) {
            cards.add(new Card(i, Card.Suit.SPADES));
        }
        for (int i = 1; i < 14; i++) {
            cards.add(new Card(i, Card.Suit.CLUBS));
        }
        for (int i = 1; i < 14; i++) {
            cards.add(new Card(i, Card.Suit.DIAMONDS));
        }
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        for (int i = 0; i < deck.cards.size(); i++ ) {
            System.out.println(deck.cards.get(i).getValue() + " of " +  deck.cards.get(i).getSuit() );
        }

    }


}
