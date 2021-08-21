package muck.client.card_games;
import javafx.scene.image.Image;

/**
 * Card Class. Instantiates an individual regular playing card.
 */
public class Card{
    private int id;
    private String suit, cardName;
    private Image cardImage;
    private String fileName;

    /**
     * Constructor Function for the Card Class
     * Sets the Cards Id, Suit and Card Name
     * Sets the image file location, and the individual Card Image
     * @param id
     * @param suit
     * @param cardName
     */
    public Card(int id, String suit, String cardName) {
        setValue(id);
        setSuit(suit);
        setCardName(cardName);
        fileName = "/images/cards/" + cardName + "_of_" + suit.toLowerCase() + ".png";
        cardImage = new Image(fileName);
    }

    /**
     * getValue Method.
     * Returns the ID of any Card. Each Card has a unique idea between 1 and 52
     * @return id
     */
    public int getValue() {
        return id;
    }

    /**
     * getSuit Method.
     * Returns the name of the suit for an individual card
     * @return suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * getCardName Method.
     * Returns the individual card name as a string. from "two" through to "ace"
     * @return cardName
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * getCardImage Method.
     * Returns the Image of an individual card. Connected through the filename - path to the card image.
     * @return cardImage
     */
    public Image getCardImage() {
        return cardImage;
    }

    /**
     * setValue Method.
     * Sets the unique card ID as an Int
     * @param id
     */
    public void setValue(int id) {
        this.id = id;
    }

    /**
     * setSuit Method.
     * Sets the individual card suit name as a String
     * @param suit
     */
    public void setSuit(String suit) {
        suit = suit.toLowerCase();
        this.suit = suit;
    }

    /**
     * setCardName Method.
     * Sets the individual card name as a lowercase string. From "two" through to "ace"
     * @param cardName
     */
    public void setCardName(String cardName) {
        cardName = cardName.toLowerCase();
        this.cardName = cardName;
    }


//    public static void main(String[] args) {
//        Card card;
//
//        card = new Card(5, "Hearts", "5");
//
//        System.out.println("TESTING");
//        System.out.println(card.cardName + " of " + card.suit);
//    }
}