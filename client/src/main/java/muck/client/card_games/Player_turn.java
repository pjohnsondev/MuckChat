package muck.client.card_games;

public class Player_turn {
    // Creating boolean to ensure players can only ask for a card once per turn
    boolean ask = false;
    int turn;
    Player player;

    public Player_turn(Player player, int turn) {
        this.turn = turn;
    }

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

    void create_set(){
        /*
            if (four cards the same are highlighted && put set away button is clicked) {
                four cards are moved to player's
            }
         */
    }

    public static void main(String[] args) {
        
    }
}
