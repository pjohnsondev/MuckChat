package muck.client.card_games;
/*import javafx.application.Application;
import javafx.scene.image.Image;
import muck.client.CardsGameController;

import javax.swing.*;
import java.util.Locale;*/

/**
 * Card Class. Instantiates an individual regular playing card.
 */
public class Card {
    private int cardId, matchId;
    private String suit, cardName;
    final String fileName, bFileName;
    private Boolean selected;

    /**
     * Constructor Function for the Card Class
     * Sets the Card's ID, Suit and Card Name
     * Sets the image file location, and the individual Card Image
     * @param cardId
     * @param matchId
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
     * @return int cardId
     */
    public int getCardId() {
        return cardId;
    }


    /**
     * useFileName Method
     * Returns the b (highlighted card image) filename if the card is selected,
     * or the regular filename if not.
     * @return String bFileName, fileName
     */
    public String useFileName() {
        if (selected == true){
            return bFileName;
        }
        return fileName;
    }

    /**
     * getFileName Method
     * Returns the fileName for the non-highlighted card
     * @return String fileName
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * getBFileName Method
     * Returns the fileName for the highlighted card
     * @return String bFileName
     */
    public String getBFileName() { return bFileName;}


    /**
     * getSelectedValue Method
     * Returns the selected state of the card as a boolean
     * @return boolean selected
     */
    public boolean getSelectedValue() {
        return selected;
    }


    /**
     * getSuit Method.
     * Returns the name of the suit for an individual card
     * @return String suit
     */
    public String getSuit() {
        return suit;
    }


    /**
     * getCardName Method.
     * Returns the individual card name as a string. from "two" through to "ace"
     * @return String cardName
     */
    public String getCardName() {
        return cardName;
    }


    /**
     * getMatchId Method
     * Returns the matchId of the card as an int
     * @return int matchId
     */
    public int getMatchId() {
        return matchId;
    }


    /**
     * setCardId Method.
     * Sets the unique cardID as an Int
     * @param id
     */
    public void setCardId(int id) {
        this.cardId = id;
    }


    /**
     * setSelected Method
     * Sets the selected value of the card to be true or false
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
     * setMatchId Method
     * Sets the matchId as an int between 1 and 13 based on the card's value
     * @param matchId
     */
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }


    /**
     * toString Method
     * @return card name of suit as a String - e.g "ace of hearts"
     */
    public String toString() {
        return cardName.substring(0, 1).toUpperCase() + cardName.substring(1) + " of " + suit.substring(0,1).toUpperCase() + suit.substring(1);
    }

}