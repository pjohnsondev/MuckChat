package muck.client.card_games;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Player {
    private int score;
    ArrayList<Card> sets;
    ArrayList<Card> hand;

    public Player(){
        this.score = 0;
        hand = new ArrayList<Card>();
        sets = new ArrayList<Card>();

    }

    void add_score(){
        this.score += 10;
    }

    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        System.out.println("Testing players. Player 1's score: " + player1.score
                + ". Player 2's score: " + player2.score);
        player1.add_score();
        player2.add_score();
        System.out.println("Testing adding to score function. Player 1's score: "
                + player1.score + ". Player 2's score: " + player2.score);
    }

}
