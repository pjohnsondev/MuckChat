package muck.client.card_games;

/**
 * Player_turn Class. Instantiates a player's turn
 */
public class Player_turn {
    // Creating boolean to ensure players can only ask for a card once per turn
    boolean ask = false;
    int turn;
    Player player;

    /**
     * Constructor Function for the Player_turn Class
     * Takes a Player and turn number. Sets the turn number
     * @param turn
     */
    public Player_turn(Player player, int turn) {
        this.turn = turn;
    }

    /**
     * highlight_card Method.
     * TODO: create header for function
     * Is this function needed?
     */
    void highlight_card(){
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
     * ask_for_card Method.
     * TODO: create header for function
     */
    void ask_for_card(){
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
     * create_set Method.
     * TODO: create header for function
     */
    void create_set(){
        /*
            if (four cards the same are highlighted && put set away button is clicked) {
                four cards are moved to player's
            }
         */
    }
}
