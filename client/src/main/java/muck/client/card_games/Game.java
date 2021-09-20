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

    public void printCards(int number){
        cardList = player1.hand.cards.get(player1.hand.cards.size() - 1).getCardName() + " of " +
                player1.hand.cards.get(player1.hand.cards.size() - 1).getSuit() + ".";
    }

    public void initGame(){
        deck.shuffleCards();
        player1.hand.drawHand(deck);
        player2.hand.drawHand(deck);
    }

    public void playersTurn(){
        //TODO: make player go fish or player receiving cards trigger a pop up that changes variable when closing
        if ((player1.hand.cards.size() == 0 && deck.cards.size() == 0)
                || (deck.cards.size() == 0 && player2.hand.cards.size() == 0)){
            endGame();
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
            endGame();
        }
        else {
            currentRound = 1;
            playersTurn();
        }
    }

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
        for (int i = player1.hand.cards.size() - 1; i > 0; i--){
            if (matchId == player1.hand.cards.get(i).getMatchId()){
                player2.hand.cards.add(player1.hand.cards.get(i));
                player1.hand.cards.remove(player1.hand.cards.get(i));
            }
        }
    }

    public void endGame(){
        if (player1.getScore() > player2.getScore()){
            winner = 1;
        }
        else {
            winner = 2;
        }
    }
}