package muck.client.card_games;

/**
 * Player Class. Instantiates a Player
 */
public class Player {
    public int score;
    public Hand hand;
    int scoreIncrement = 10;

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
        this.score += scoreIncrement;
    }

    int get_score(){
        return this.score;
    }

    public static void main(String[] args) {

    }

}