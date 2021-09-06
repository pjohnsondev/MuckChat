package muck.client.card_games;

import java.util.ArrayList;
import java.util.Random;

public class ComputerOpponent extends Player {
    private int score;
    Hand hand;
    int scoreIncr = 10;
    //This will be a rating out of 5 implemented in memory queues
    public int level;
    public int[] computerTurns;
    public int[] computerTurnsBool;
    public int[] playerTurns;
    public int arrayLength;
    // Setting up random number generator
    Random rand = new Random();

    public ComputerOpponent(int level) {
        /*TODO limit level so it only takes 1-5. probably implemented through a button,
           or option box*/
        this.score = 0;
        hand = new Hand();
        this.level = 1;
        this.arrayLength = (level *2);
        computerTurns = new int[arrayLength];
        computerTurnsBool = new int[arrayLength];
        playerTurns = new int[arrayLength];
    }

    public void addingToComputerTurns(Card card){
        for (int i = (arrayLength) - 2; i > 0; i--) {
            if (computerTurns[i] != 0){
                computerTurns[i + 1] = computerTurns[i];
            }
        }
        computerTurns[0] = card.getMatchId();
    }

    public void addingToPlayerTurns(Card card){
        for (int i = (arrayLength) - 2; i > 0; i--) {
            if (playerTurns[i] != 0){
                playerTurns[i + 1] = playerTurns[i];
            }
        }
        computerTurns[0] = card.getMatchId();
    }

    public void addToArray(int[] array, Card card) {
        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = card.getMatchId();

    }

    // Choosing best move based on previous turns
    public int askForCard(){
        // The int i for loop compares the most recent player moves against
        // the int j for loop of the computers hand. If player has recently asked for a card
        // that the computer has, the computer will now as for it.
        for (int i = 0; i < (arrayLength) - 1; i++){
            for (int j = 0; j < hand.cards.size(); j++){
                if (playerTurns[i] == hand.cards.get(j).getMatchId()){
                    return hand.cards.get(j).getMatchId();
                }
            }
        }
        // If there are no matches, the computer now picks a random card in hand to ask
        // for, trying to avoid asking for something it recently asked for.

        // The int i for loop compares the most recent computer moves against
        // the int j loop of the computers hand. For all matches, the card's selected
        // value is marked as true and the computer will avoid picking it, unless all
        // cards are marked true.
        for (int i = 0; i < (arrayLength) - 1; i++){
            for (int j = 0; j < hand.cards.size(); j++){
                if (computerTurns[i] == hand.cards.get(j).getMatchId()){
                    // TODO would rather not have to mark cards as true.
                    //  what better way can i do this?
                    hand.cards.get(j).setSelected(true);
                }
            }
        }
        //TODO figure out best way to pick a random card that hasn't been recently asked for

        // If all cards in hand have been asked for recently, computer will pick a random card in hand
        int random = rand.nextInt(hand.cards.size());
        return hand.cards.get(random).getMatchId();
    }

    void addScore(){
        this.score += scoreIncr;
    }

    int getScore(){
        return this.score;
    }
}
