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

    /**
     * initGame Method
     * Sets up the game with a shuffled deck, and assigns a hand for both players
     */
    public void initGame(){
        deck.shuffleCards();
        player1.hand.drawHand(deck);
        player2.hand.drawHand(deck);
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
        for (int i = player2.hand.cards.size() - 1; i >= 0; i--){
            if (matchId == player2.hand.cards.get(i).getMatchId()){
                player1.hand.cards.add(player2.hand.cards.get(i));
                player2.hand.cards.remove(i);
                receive += 1;
            }
        }
        player1.hand.reorderHand();
        player2.hand.reorderHand();
        return receive;
    }

    /**
     * giveComputerCard Method
     * This function is called by an event handler if the computer asked for a card the player has
     * The card(s) will be removed from the players hand and added to the opponent
     * @param matchId
     */
    public void giveComputerCard(int matchId){
        for (int i = player1.hand.cards.size() - 1; i >= 0; i--){
            if (matchId == player1.hand.cards.get(i).getMatchId()){
                player2.hand.cards.add(player1.hand.cards.get(i));
                player1.hand.cards.remove(player1.hand.cards.get(i));
                player2.hand.reorderHand();
                player2.hand.checkForSet(false);
            }
        }
    }

    public int checkEndGame(){
        // Checking all cards are in either sets pile
        if (player1.hand.sets.size() + player2.hand.sets.size() == 52){
            return 1;
        }
        if (deck.getCardsleft() != 0 && (player1.hand.sets.size() == 0 || player2.hand.sets.size() == 0)){
            return 2;
        }
        return 0;
    }
}