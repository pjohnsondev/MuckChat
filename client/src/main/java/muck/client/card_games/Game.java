package muck.client.card_games;

/**
 * Game Class. Instantiates an individual game.
 */
public class Game {
    //This needs to create an id that is an incremented number on the last created id
    public int gameId;
    //keeping track of whose turn it is.
    public static int currentRound;
    //unique id to the game to send information to database for other player to receive.
    public static int roundId;
    //this player
    public Player player1;
    //other player
    public ComputerOpponent player2;
    public Deck deck;
    //as long as active is true, the current round remains active. once it is changed to false, the turn ends
    public boolean active;
    public String card_list;
    public boolean gameStatus;

    /**
     * Constructor Function for the Game Class
     */
    public Game(){
        gameStatus = true;
        currentRound = 1;
        roundId = 0;
        player1 = new Player();
        player2 = new ComputerOpponent(1);
        deck = new Deck();
    }

    public void printCards(int number){
        card_list = player1.hand.cards.get(player1.hand.cards.size() - 1).getCardName() + " of " +
                player1.hand.cards.get(player1.hand.cards.size() - 1).getSuit() + ".";
    }

    public void initGame(){
        deck.shuffleCards();
        player1.hand.drawHand(deck);
        player2.hand.drawHand(deck);
    }

    public void playersTurn(){
        //TODO: make player go fish or player receiving cards trigger a pop up that changes variable when closing
        while (currentRound == 1) {

        }
        if ((player1.hand.cards.size() == 0 && deck.cards.size() == 0)
                || (deck.cards.size() == 0 && player2.hand.cards.size() == 0)){
            gameStatus = false;
        }
        else {
            computersTurn();
        }
    }

    public void computersTurn(){
        int card = player2.askForCard();
        boolean goFish = checkForMatch(card);
        if (goFish == true){
            //TODO: popup with button that says "go fish" to close window, which will prompt:
            player2.hand.drawTopCard(deck);
        }
        else {
            //TODO: popup with button that says "Player 2 asked for *** " to close window
            giveComputerCard(card);
        }
        if ((player1.hand.cards.size() == 0 && deck.cards.size() == 0)
                || (deck.cards.size() == 0 && player2.hand.cards.size() == 0)){
            gameStatus = false;
        }
        else {
            currentRound = 1;
            playersTurn();
        }
    }

    public void playersAsk(int matchId){
        for (int i = 0; i < player2.hand.cards.size(); i++){
            if (matchId == player2.hand.cards.get(i).getMatchId()){
                player1.hand.cards.add(player2.hand.cards.get(i));
                player2.hand.cards.remove(i);
            }
        }
    }

    public boolean checkForMatch(int matchId){
        for (int i = 0; i < player1.hand.cards.size(); i++) {
            if (matchId == player1.hand.cards.get(i).getMatchId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //This function is called by an event handler if the computer asked for a card the player has
    public void giveComputerCard(int matchId){
        for (int i = 0; i < player1.hand.cards.size(); i++){
            if (matchId == player1.hand.cards.get(i).getMatchId()){
                player2.hand.cards.add(player1.hand.cards.get(i));
                player1.hand.cards.remove(player1.hand.cards.get(i));
            }
        }
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
            }
            if (currentRound == 2) {
                roundId++;
                System.out.println(roundId);
            }
        }
        System.out.println("Exiting turns. Game is finished.");
    }
}
