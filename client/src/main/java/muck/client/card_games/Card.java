package muck.client.card_games;
import javafx.application.Application;
import javafx.scene.image.Image;
import muck.client.CardsGameController;

import javax.swing.*;
import java.util.Locale;

/**
 * Card Class. Instantiates an individual regular playing card.
 */
public class Card {
    private int cardId, matchId;
    private String suit, cardName;
    private Image cardImage;
    private String fileName, bFileName;
    private Boolean selected;

    /**
     * Constructor Function for the Card Class
     * Sets the Cards Id, Suit and Card Name
     * Sets the image file location, and the individual Card Image
     * @param cardId
     * @param suit
     * @param cardName
     */
    public Card(int cardId, int matchId, String suit, String cardName) {
        setCardId(cardId);
        setSuit(suit);
        setCardName(cardName);
        setMatchId(matchId);
        selected = false;
        fileName = "/images/cards/" + cardName + "_of_" + suit.toLowerCase() + ".png";
        bFileName = "/images/cards/B_" + cardName + "_of_" + suit.toLowerCase() + ".png";

    }


    /**
     * getValue Method.
     * Returns the ID of any Card. Each Card has a unique idea between 1 and 52
     * @return id
     */
    public int getCardId() {
        return cardId;
    }


    public String getFileName() {
        return fileName;
    }

    public String getBFileName() { return bFileName;}


    public boolean getSelectedValue() {
        return selected;
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
     *
     * @return matchId
     */
    public int getMatchId() {
        return matchId;
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
     * setCardId Method.
     * Sets the unique card ID as an Int
     * @param id
     */
    public void setCardId(int id) {
        this.cardId = id;
    }


    /**
     *
     * @param setValue
     */
    public void setSelected(boolean setValue) {
        selected = setValue;
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


    /**
     *
     * @param matchId
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }


    /**
     *
     * @return card name of suit as a String - e.g "ace of hearts"
     */
    public String toString() {
        return cardName.substring(0, 1).toUpperCase() + cardName.substring(1) + " of " + suit.substring(0,1).toUpperCase() + suit.substring(1);
    }

    //working with controller to get cards diplayed
    /*void setCardDisplay(){
            String filename = getFileName();
            new ImageIcon(filename);

            CardsGameController.set_card();
        }
*/

//    public static void main(String[] args) {
//        Card card;
//
//        card = new Card(5, 5,"Hearts", "king");
//
//        System.out.println(card);
//    }
}