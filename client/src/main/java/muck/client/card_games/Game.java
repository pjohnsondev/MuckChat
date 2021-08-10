package muck.client.card_games;

public class Game {
    private int game_id;

    public Game(){

    }

    public static void main(String[] args){
        Player player1 = new Player();
        Player player2 = new Player();
        Deck shuffled_deck = new Deck();
        shuffled_deck.shuffle_cards();
        player1.hand.draw_hand(shuffled_deck);
        player2.hand.draw_hand(shuffled_deck);

    }
}
