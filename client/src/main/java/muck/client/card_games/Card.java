package muck.client.card_games;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//TODO: Create card class

public class Card{

    /*@Override
        public void start(Stage primaryStage) throws Exception{

    }*/



    private int id;
    private String suit, cardName;


    public Card(int id, String suit, String cardName) {
        setValue(id);
        setSuit(suit);
        setCardName(cardName);
    }

    public int getValue() {
        return id;
    }

    public String getSuit() {
        return suit;
    }

    public void setValue(int id) {
        this.id = id;
    }

    public void setSuit(String suit) {
        suit = suit.toLowerCase();
        this.suit = suit;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        cardName = cardName.toLowerCase();
        this.cardName = cardName;
    }

    public static void main(String[] args) {
        Card card;

        card = new Card(5, "Hearts", "5");

        System.out.println("TESTING");
        System.out.println(card.cardName + " of " + card.suit);
    }
}
