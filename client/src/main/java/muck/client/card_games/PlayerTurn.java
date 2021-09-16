package muck.client.card_games;

/**
 * Player_turn Class. Instantiates a player's turn
 */
public class PlayerTurn  {
    // Creating boolean to ensure players can only ask for a card once per turn
    boolean ask;
    int turn;

    /**
     * Constructor Function for the PlayerTurn Class
     * Takes a Player and turn number. Sets the turn number
     *
     */
    public PlayerTurn(int round){
        ask = false;
        turn = 0;
    }

    void takeTurn(Player player) {
        this.turn = turn;
    }

}
