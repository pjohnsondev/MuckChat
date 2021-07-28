package muck.client.card_games;

//TODO: Create card class

public class Card {

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }

    private int value;
    private Suit suit;


    public Card(int value, Suit suit) {
        setValue(value);
        setSuit(suit);
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public static void main(String[] args) {
        Card card;

        card = new Card(5, Suit.HEARTS);

        System.out.println("TESTING");
        System.out.println(card.value + " of " + card.suit);
    }
}
