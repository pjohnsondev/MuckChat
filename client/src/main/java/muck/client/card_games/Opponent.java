package muck.client.card_games;

public class Opponent extends Player {
    private int score;
    Hand hand;
    int scoreIncr = 10;

    public Opponent(){
        this.score = 0;
        hand = new Hand();
    }

    void add_score(){
        this.score += scoreIncr;
    }

    int get_score(){
        return this.score;
    }
}
