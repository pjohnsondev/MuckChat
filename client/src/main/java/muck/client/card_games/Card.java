package muck.client.card_games;
import javafx.scene.image.Image;

//TODO: Create card class

public class Card {



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
