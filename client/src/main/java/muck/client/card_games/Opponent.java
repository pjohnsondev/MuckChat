package muck.client.card_games;

public class Opponent extends Player {
    private int score;
    Hand hand;
    int scoreIncr = 10;

    public Opponent(){
        this.score = 0;
        hand = new Hand();
    }

    void addScore(){
        this.score += scoreIncr;
    }

    int getScore(){
        return this.score;
    }
}
