package muck.client.card_games;

//TODO: Create deck class

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;
    String[] Suits = {"clubs", "diamonds", "hearts", "spades"};
    String[] cardNames = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
    String cardValue;
    String cardName;
    String suit;
    int counter;
    Image backOfDeck;



    public Deck() {

        cards = new ArrayList<Card>();
        Image backOfDeck = new ImageIcon("muck/client/card_games/images/backofdeck.png").getImage();
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

            cards.add(new Card(i, suit, cardNames[counter]));
            counter += 1;
            if (counter % 13 == 0) {
                counter = 0;
            }
        }

    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        for (int i = 0; i < deck.cards.size(); i++ ) {
            System.out.println(deck.cards.get(i).getCardName() + " of " +  deck.cards.get(i).getSuit() );
            System.out.println("This cards ID is " + deck.cards.get(i).getValue());
        }

    }


}
