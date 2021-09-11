package muck.client.card_games;

/**
 * Player_turn Class. Instantiates a player's turn
 */
public class PlayerTurn extends Player {
    // Creating boolean to ensure players can only ask for a card once per turn
    boolean ask = false;
    int turn;
    Player player;

    /**
     * Constructor Function for the PlayerTurn Class
     * Takes a Player and turn number. Sets the turn number
     * @param turn
     */
    public PlayerTurn(){}

    void takeTurn(Player player) {
        this.turn = turn;
    }

    /**
     * highlightCard Method.
     * TODO: create header for function
     * Is this function needed?
     */
    void highlightCard(){
        /*
            if (card is clicked && no other cards are highlighted && ask == false){
                all cards of that value are highlighted
                }

            if (card is clicked && other cards are highlighted && ask == false){
                all cards of that value are highlighted and others are no longer highlighted
                }
         */
    }

    /**
     * askForCard Method.
     * TODO: create header for function
     */
    void askForCard(){
        /*
            if ("ask button" is clicked && ask == false && card(s) are highlighted) {
                ask other player for card
                ask = true;
            }
            if ("ask button" is clicked && ask == false){
                nothing happens
            }
         */
    }

    /**
     * createSet Method.
     * TODO: create header for function
     */
    void createSet(){
        /*
            if (four cards the same are highlighted && put set away button is clicked) {
                four cards are moved to player's
            }
         */
    }
}
