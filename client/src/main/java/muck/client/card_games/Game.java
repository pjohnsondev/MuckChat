package muck.client.card_games;

public class Game {
    private int game_id;
    public static int current_turn;
    public static int player_turn;

    public Game(){ }

    public static void main(String[] args){
        // Initial setup
        // Variable to manage turns
        current_turn = 1;
        player_turn = 1;
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
            if (player_turn == 1){
                //Player_turn(player1);
            }
            if (player_turn == 2) {
                //Player_turn(player2);
            }
        }
    }
}
