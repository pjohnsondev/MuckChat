package muck.client.card_games;

//import java.util.ArrayList;
import java.util.Random;

/**
 * ComputerOpponent Class. Instantiates a Computer Player.
 */
public class ComputerOpponent extends Player {
    //private int score;
    public Hand hand;
    //TODO: Fix this with the memory queues use, or remove altogether
    // This will be a rating out of 5 implemented in memory queues
    public int level;
    // Memory Queues
    public int[] computerTurns;
    public int[] computerTurnsBool;
    public int[] playerTurns;
    public int arrayLength;
    // Setting up random number generator
    Random rand = new Random();


    /**
     * ComputerOpponent Constructor Function
     * Sets the score, level, calculates the arrayLength and passes it to the arrays
     * @param level
     */
    // level will be limited by the player only being able to select a level from a range
    public ComputerOpponent(int level) {
        this.score = 0;
        hand = new Hand();
        this.level = 1;
        this.arrayLength = (level *2);
        computerTurns = new int[arrayLength];
        computerTurnsBool = new int[arrayLength];
        playerTurns = new int[arrayLength];
    }

    /**
     * addToArray function
     * Moves all the array values back one, deleting the last one, and adds the Player
     * and Computer moves to the appropriate arrays
     * @param array
     * @param matchId
     */
    public void addToArray(int[] array, int matchId) {
        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = matchId;
    }

    /**
     * updateArray function
     * Updates the array value to 0 when the computer asks for a card to avoid unnecessarily
     * repeating guesses
     * @param array
     * @param a
     */
    public void updateArray(int [] array, int a){
        array[a] = 0;
    }

    /**
     * checkForCard function
     * Checks through the given array for a specific id number and returns true if the id
     * is in the array, or false if it isn't
     * @param array
     * @param id
     */
    boolean checkForCard(int [] array, int id){
        for (int i = 0; i < arrayLength; i ++){
            if (id == array[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * askForCard Method
     * Returns the matchId of the card the computer will ask for next. The function will
     * attempt to choose a smart moved based on previous asks, otherwise will return a random
     * matchId from hand. The random pick will automatically favour choosing a matchId that
     * the computer player is holding more of.
     * @return int matchId
     */
    public int askForCard(){
        // Checking if the Computer still has cards in hand before attempting to choose one
        if (hand.cards.size() > 0) {
            // The outer int i for loop compares the most recent player moves against
            // the inner int j for loop of the computers hand. If player has recently asked for a card
            // that the computer has, the computer will now ask for it. Otherwise, move to next
            // method of selecting. Once asking for the card, the computer will call updateArray to
            // update the value to 0 to avoid calling it again immediately.
            for (int i = 0; i < (arrayLength) - 1; i++) {
                for (int j = 0; j < hand.cards.size(); j++) {
                    if (playerTurns[i] == hand.cards.get(j).getMatchId()) {
                        updateArray(playerTurns, i);
                        addToArray(computerTurns, hand.cards.get(j).getMatchId());
                        return hand.cards.get(j).getMatchId();
                    }
                }
            }
            // If there are no matches above, the computer now picks a random card in hand to ask
            // for, trying to avoid asking for something it recently asked for.
            // The outer int i for loop gives the computer 5 attempts at picking a random matchId
            // If a randomly chosen card is checked against the computerTurns array and has not been
            // asked for recently, the computer will now ask for it. If the computer chooses 5 random cards and
            // finds all cards have recently been asked for, then move to the next method of selecting
            for (int i = 0; i < 5; i++){
                int random = rand.nextInt(hand.cards.size());
                if (checkForCard(computerTurns, hand.cards.get(random).getMatchId()) == false) {
                    int computerTry = hand.cards.get(random).getMatchId();
                    addToArray(computerTurns, computerTry);
                    return computerTry;
                }
            }
            // If the 5 random cards in hand have been asked for recently, computer will pick a random card in hand
            // regardless of if it has been asked for or not
            int random = rand.nextInt(hand.cards.size());
            addToArray(computerTurns, hand.cards.get(random).getMatchId());
            return hand.cards.get(random).getMatchId();
        }
        // Default case
        return 0;
    }

    /**
     * printHand Method
     * Prints all cards in player 2's hand. This function is useful for testing purposes
     * @return int matchId
     */
    public void printHand(){
        for (int i = 0; i < hand.cards.size(); i ++){
            System.out.println(hand.cards.get(i).getFileName());
        }
    }
}