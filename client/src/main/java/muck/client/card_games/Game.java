package muck.client.card_games;

public class Game {
    private int game_id;

    public Game(){ }

    //TODO:  make this function into its own class to run the if statements as functions instead
    static void player_turn(Player player) {
        // Creating boolean to ensure players can only ask for a card once per turn
        boolean ask = false;
        /* Pseudocode:
        if (card is clicked && no other cards are highlighted && ask == false){
            all cards of that value are highlighted
        }
        if (card is clicked && other cards are highlighted && ask == false){
            all cards of that value are highlighted and others are no longer highlighted
        }
        if ("ask button" is clicked && ask == false && card(s) are highlighted) {
            ask other player for card
            ask = true
        }
        if (four cards the same are highlighted && put set away button is clicked) {
            four cards are moved to player's
        }
        if (end turn button is clicked) {
            turn changes from 1 to 2 or from 2 to 1 and player_turn() ends
        }
        */

    }

    public static void main(String[] args){
        // Initial setup
        // Variable to manage turns
        int turn = 1;
        // Creating 2 players
        Player player1 = new Player();
        Player player2 = new Player();
        // Creating and shuffling deck
        Deck shuffled_deck = new Deck();
        shuffled_deck.shuffle_cards();
        // Dealing 7 cards to each player
        player1.hand.draw_hand(shuffled_deck);
        player2.hand.draw_hand(shuffled_deck);

        // Game play
        // Loop manages turns as long as there are any cards still in play
        while (shuffled_deck.cards.size() != 0 && player1.hand.cards.size() != 0
                && player2.hand.cards.size() != 0){
            if (turn == 1){
                player_turn(player1);
            }
            if (turn == 2) {
                player_turn(player2);
            }
        }
    }
}
