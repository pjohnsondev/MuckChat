package muck.client.card_games;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Player Class. Instantiates a Player
 */
public class Player {
    private int score;
    Hand hand;
    int score_incr = 10;

    /**
     * Constructor Function for the Player Class
     * Sets the score as 0 and initialises the Player's Hand
     * @param score
     * @param Hand
     */
    public Player(){
        this.score = 0;
        hand = new Hand();
    }

    /**
     * add_score Method.
     * Increments player's score by score_incr
     */
    void add_score(){
        this.score += score_incr;
    }

    int get_score(){
        return this.score;
    }

    public static void main(String[] args) {

    }

}