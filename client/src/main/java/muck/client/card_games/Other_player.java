package muck.client.card_games;

public class Other_player extends Player {
    private int score;
    Hand hand;
    int score_incr = 10;

    public Other_player(){
        this.score = 0;
        hand = new Hand();
    }

    void add_score(){
        this.score += score_incr;
    }

    int get_score(){
        return this.score;
    }
}
