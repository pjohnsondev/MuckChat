package muck.client.card_games;

/**
 * Player Class. Instantiates a Player
 */
public class Player {
    public int score;
    public Hand hand;
    int scoreIncrement = 1;


    /**
     * Constructor Function for the Player Class
     * Sets the score as 0 and initialises the Player's Hand
     */
    public Player(){
        score = 0;
        hand = new Hand();
    }


    /**
     * addScore Method.
     * Increments player's score by scoreIncrement
     */
    public void addScore(){
        this.score += scoreIncrement;
    }


    /**
     * getScore Method.
     * Returns player's score as an int
     */
    public int getScore(){
        return this.score;
    }
}