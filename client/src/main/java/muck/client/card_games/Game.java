package muck.client.card_games;

/**
 * Game Class. Instantiates an individual game.
 */
public class Game {
    private int game_id;
    public static int current_turn;
    public static int player_turn;

    /**
     * Constructor Function for the Game Class
     */
    public Game(){ }

  public static void main(String[] args){
      Deck shuffled_deck = new Deck();
      System.out.println("There are " + shuffled_deck.cards.size() + " cards in the deck. \nShuffling the " +
              "deck and dealing cards to both players.");
      Player player1 = new Player();
      Other_player player2 = new Other_player();
      shuffled_deck.shuffle_cards();
      System.out.println("Testing players. Player 1's score: " + player1.get_score()
              + ". Player 2's score: " + player2.get_score());
      player1.add_score();
      player2.add_score();
      System.out.println("Testing add score function. Player 1's score: "
              + player1.get_score() + ". Player 2's score: " + player2.get_score() + "\nNow testing Draw hand" +
              " function:");
      player1.hand.draw_hand(shuffled_deck);
      player2.hand.draw_hand(shuffled_deck);
      System.out.println("Player 1 has " + player1.hand.cards.size() + " cards in their hand, and " +
              "Player 1 has " + player1.hand.cards.size());
      System.out.println("The deck now has " + shuffled_deck.cards.size() + " cards left in it.");

      for (int i = 0; i < player1.hand.cards.size(); i++ ) {
          System.out.println(shuffled_deck.cards.get(i).getCardName() + " of " +  shuffled_deck.cards.get(i).getSuit() );
      }

  }
        /*
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
    }*/
}
