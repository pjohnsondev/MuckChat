package muck.client.card_games;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private int score;
    ArrayList<Card> sets;
    ArrayList<Card> hand;
    int score_incr = 10;
    int deck_size = 52;


    public Player(){
        this.score = 0;
        hand = new ArrayList<Card>();
        sets = new ArrayList<Card>();

    }

    void add_score(){
        this.score += score_incr;
    }

    public static void main(String[] args) {
        Deck shuffled_deck = new Deck();
        Player player1 = new Player();
        Player player2 = new Player();
        shuffled_deck.shuffle_cards();
        System.out.println("Testing players. Player 1's score: " + player1.score
                + ". Player 2's score: " + player2.score);
        player1.add_score();
        player2.add_score();
        System.out.println("Testing adding to score function. Player 1's score: "
                + player1.score + ". Player 2's score: " + player2.score);
        for (int i = 0; i < shuffled_deck.cards.size(); i++ ) {
            System.out.println(shuffled_deck.cards.get(i).getCardName() + " of " +  shuffled_deck.cards.get(i).getSuit() );
            System.out.println("This cards ID is " + shuffled_deck.cards.get(i).getValue());
        }
    }

}
