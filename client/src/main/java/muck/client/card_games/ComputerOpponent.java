package muck.client.card_games;

import java.util.ArrayList;
import java.util.Random;

public class ComputerOpponent extends Player {
    private int score;
    public Hand hand;
    //TODO: Fix this with the memory queues use, or remove altogether
    //This will be a rating out of 5 implemented in memory queues
    public int level;
    public int[] computerTurns;
    public int[] computerTurnsBool;
    public int[] playerTurns;
    public int arrayLength;
    // Setting up random number generator
    Random rand = new Random();


    /**
     * ComputerOpponent Constructor Function
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
     *
     * @param array
     * @param matchId
     */
    public void addToArray(int[] array, int matchId) {
        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = matchId;
    }

    public void updateArray(int [] array, int a){
        array[a] = 0;
    }

    //TODO - See above

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
     * Returns the Match Id of the card the computer will ask for next
     * @return int matchId
     */
    // Choosing best move based on previous turns
    public int askForCard(){
        // The int i for loop compares the most recent player moves against
        // the int j for loop of the computers hand. If player has recently asked for a card
        // that the computer has, the computer will now ask for it. Otherwise, move to next
        // method of selecting.
        if (hand.cards.size() > 0) {
            for (int i = 0; i < (arrayLength) - 1; i++) {
                for (int j = 0; j < hand.cards.size(); j++) {
                    if (playerTurns[i] == hand.cards.get(j).getMatchId()) {
                        updateArray(playerTurns, i);
                        addToArray(computerTurns, hand.cards.get(j).getMatchId());
                        return hand.cards.get(j).getMatchId();
                    }
                }
            }
            //TODO Try and fix the following block, otherwise delete

            // If there are no matches, the computer now picks a random card in hand to ask
            // for, trying to avoid asking for something it recently asked for.

            // The int i for loop compares the most recent computer moves against
            // the int j loop of the computers hand. If a card has not been asked for recently,
            // the computer will now ask for it. If the computer searches through all cards and
            // finds all cards have recently been asked for, then move to the next method of selecting

            /*for (int j = 0; j < hand.cards.size(); j++) {
                if (checkForCard(computerTurns, hand.cards.get(j).getMatchId()) == false) {
                    int computerTry = hand.cards.get(j).getMatchId();
                    addToArray(computerTurns, computerTry);
                    return computerTry;
                }
            }*/
            // If all cards in hand have been asked for recently, computer will pick a random card in hand
            int random = rand.nextInt(hand.cards.size());
            addToArray(computerTurns, hand.cards.get(random).getMatchId());
            return hand.cards.get(random).getMatchId();
        }
        return 0;
    }

    // Keeping this code to use in case of bug checking
    /*public void printHand(){
        for (int i = 0; i < hand.cards.size(); i ++){
            System.out.println(hand.cards.get(i).getFileName());
        }
    }*/
}