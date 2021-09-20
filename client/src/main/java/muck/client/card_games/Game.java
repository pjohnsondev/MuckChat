package muck.client.card_games;

/**
 * Game Class. Instantiates an individual game.
 */
public class Game {
    // Keeping track of whose turn it is.
    public static int currentRound;
    // This player
    public Player player1;
    // Player 2 is a computer
    public ComputerOpponent player2;
    public Deck deck;
    //as long as active is true, the current round remains active. once it is changed to false, the turn ends
    public String cardList;
    public int winner;


    /**
     * Constructor Function for the Game Class
     */
    public Game(){
        currentRound = 1;
        player1 = new Player();
        player2 = new ComputerOpponent(1);
        deck = new Deck();
    }

    //TODO - Using this method??
    /**
     * printCards Method
     *
     * @param number
     */
    public void printCards(int number){
        cardList = player1.hand.cards.get(player1.hand.cards.size() - 1).getCardName() + " of " +
                player1.hand.cards.get(player1.hand.cards.size() - 1).getSuit() + ".";
    }


    /**
     * initGame Method
     * Sets up the game with a shuffled deck, and assigns a hand for both players
     */
    public void initGame(){
        deck.shuffleCards();
        player1.hand.drawHand(deck);
        player2.hand.drawHand(deck);
    }

    // TODO
    /**
     * playersTurn Method
     *
     */
    public void playersTurn(){
        //TODO: make player go fish or player receiving cards trigger a pop up that changes variable when closing
        if ((player1.hand.cards.size() == 0 && deck.cards.size() == 0)
                || (deck.cards.size() == 0 && player2.hand.cards.size() == 0)){
            endGame();
        }
    }


    /**
     * computersTurn Method
     * Controls the logic for the opponents turn
     */
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
            endGame();
        }
        else {
            currentRound = 1;
            playersTurn();
        }
    }


    /**
     * playersAsk Method
     * Controls the player receiving cards that have been asked for
     * If the opponent has the cards, they will be transferred to the player, and removed from the opponents hand.
     * @param matchId
     * @return int receive The number of matching cards the opponent had in their hand
     */
    public int playersAsk(int matchId){
        int receive = 0;
        for (int i = player2.hand.cards.size() - 1; i > 0; i--){
            if (matchId == player2.hand.cards.get(i).getMatchId()){
                player1.hand.cards.add(player2.hand.cards.get(i));
                player2.hand.cards.remove(i);
                receive += 1;
            }
        }
        player1.hand.reorderHand();
        return receive;
    }


    /**
     * checkForMatch Method
     * Returns either true or false based on whether there is a matching card
     * @param matchId
     * @return boolean
     */
    public boolean checkForMatch(int matchId){
        for (int i = 0; i < player1.hand.cards.size(); i++) {
            if (matchId == player1.hand.cards.get(i).getMatchId()) {
                return true;
            }
        }
        return false;
    }


    /**
     * giveComputerCard Method
     * This function is called by an event handler if the computer asked for a card the player has
     * The card(s) will be removed from the players hand and added to the opponent
     * @param matchId
     */
    public void giveComputerCard(int matchId){
        for (int i = player1.hand.cards.size() - 1; i > 0; i--){
            if (matchId == player1.hand.cards.get(i).getMatchId()){
                player2.hand.cards.add(player1.hand.cards.get(i));
                player1.hand.cards.remove(player1.hand.cards.get(i));
            }
        }
    }


    /**
     * endGame Method
     * Checks to see whether player 1 or player 2 has won the game by the highest score
     */
    public void endGame(){
        if (player1.getScore() > player2.getScore()){
            winner = 1;
        }
        else {
            winner = 2;
        }
    }
}