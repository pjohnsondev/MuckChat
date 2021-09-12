package muck.client.card_games;

/**
 * Game Class. Instantiates an individual game.
 */
public class Game {
    //This needs to create an id that is an incremented number on the last created id
    public int game_id;
    //keeping track of who's turn it is.
    public static int currentRound;
    //unique id to the game to send information to database for other player to receive.
    public static int roundId;
    //this player
    public Player player1;
    //other player
    public Opponent player2;
    public Deck deck;
    //as long as active is true, the current round remains active. once it is changed to false, the turn ends
    public boolean active;
    public String card_list;

    /**
     * Constructor Function for the Game Class
     */
    public Game(){
        currentRound = 1;
        roundId = 0;
        player1 = new Player();
        player2 = new Opponent();
        deck = new Deck();
    }

    void initGame(){
        deck.shuffle_cards();
        player1.hand.drawHand(deck);
        player2.hand.drawHand(deck);
    }

    void playerTurn(int round_number){
        PlayerTurn player_go = new PlayerTurn();
        while (round_number == 1){
            player_go.take_turn(player1);
            currentRound = 2;
            break;

        }

        while (round_number == 2){
            player_go.take_turn(player2);
            currentRound = 1;
            break;

        }

        if (roundId == 5){
            //this is a test to break out of turn loops
            currentRound = 3;
        }
    }

    void end_turn(){

    }

    public void printCards(int number){
        card_list = player1.hand.cards.get(player1.hand.cards.size() - 1).getCardName() + " of " +
                player1.hand.cards.get(player1.hand.cards.size() - 1).getSuit() + ".";
    }

  public static void main(String[] args){
        // Creating instance of game
        Game game = new Game();
        // Initialising game
        game.initGame();
        // Game play
        // Loop manages turns as long as there are any cards still in play
        while (game.deck.cards.size() != 0 && game.player1.hand.cards.size() != 0
                && game.player2.hand.cards.size() != 0 && currentRound != 3){
            if (currentRound == 1){
                roundId++;
                System.out.println(roundId);
                game.playerTurn(1);
            }
            if (currentRound == 2) {
                roundId++;
                System.out.println(roundId);
                game.playerTurn(2);
            }
        }
        System.out.println("Exiting turns. Game is finished.");
    }
}
