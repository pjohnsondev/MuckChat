package muck.client.card_games;

import java.sql.SQLOutput;

/**
 * Game Class. Instantiates an individual game.
 */
public class Game {
    //This needs to create an id that is an incremented number on the last created id
    private int game_id;
    //keeping track of who's turn it is.
    public static int current_round;
    //unique id to the game to send information to database for other player to receive.
    public static int round_id;
    //this player
    Player player1;
    //other player
    Other_player player2;
    Deck deck;
    //as long as active is true, the current round remains active. once it is changed to false, the turn ends
    boolean active;

    /**
     * Constructor Function for the Game Class
     */
    public Game(){
        current_round = 1;
        round_id = 0;
        player1 = new Player();
        player2 = new Other_player();
        deck = new Deck();
        ask = false;
    }

    void init_game(){
        deck.shuffle_cards();
        player1.hand.draw_hand(deck);
        player2.hand.draw_hand(deck);
    }

    void player_turn(int round_number){
        Player_turn player_go = new Player_turn();
        while (round_number == 1){
            player_go.take_turn(player1);
            current_round = 2;
            break;

        }

        while (round_number == 2){
            player_go.take_turn(player2);
            current_round = 1;
            break;

        }

        if (round_id == 5){
            //this is a test to break out of turn loops
            current_round = 3;
        }
    }

    void end_turn(){

    }


  public static void main(String[] args){
        // Creating instance of game
        Game game = new Game();
        // Initialising game
        game.init_game();
        // Game play
        // Loop manages turns as long as there are any cards still in play
        while (game.deck.cards.size() != 0 && game.player1.hand.cards.size() != 0
                && game.player2.hand.cards.size() != 0 && current_round != 3){
            if (current_round == 1){
                round_id ++;
                System.out.println(round_id);
                game.player_turn(1);
            }
            if (current_round == 2) {
                round_id ++;
                System.out.println(round_id);
                game.player_turn(2);
            }
        }
        System.out.println("Exiting turns. Game is finished.");
    }
}
