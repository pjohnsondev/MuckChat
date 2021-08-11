package muck.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.application.Application;

public class CardsGameController implements Initializable {

    @FXML // fx:id="menu"
    private MenuBar menu;

    @FXML // fx:id="ask_for_card"
    private Button askForCard;

    @FXML // fx:id="go_fish"
    private Button goFish;

    @FXML // fx:id="sets_made"
    private Label setsMadeTotal;

    // Image initialisation
    private Image twoOfClubs = new Image("images/cards/2_of_clubs.png");
    private Image twoOfDiamonds = new Image("images/cards/2_of_diamonds.png");
    private Image twoOfHearts = new Image("images/cards/2_of_hearts.png");
    private Image threeOfClubs = new Image("images/cards/3_of_clubs.png");
    private Image threeOfDiamonds = new Image("images/cards/3_of_diamonds.png");
    private Image threeOfHearts = new Image("images/cards/3_of_hearts.png");
    private Image threeOfSpades = new Image("resources/images/cards/3_of_spades.png");
    private Image fourOfClubs = new Image("images/cards/4_of_clubs.png");
    private Image fourOfDiamonds = new Image("images/cards/4_of_diamonds.png");
    private Image fourOfHearts = new Image("images/cards/4_of_hearts.png");
    private Image fourOfSpades = new Image("images/cards/4_of_spades.png");
    private Image fiveOfClubs = new Image("images/cards/5_of_clubs.png");
    private Image fiveOfDiamonds = new Image("images/cards/5_of_diamonds.png");
    private Image fiveOfHearts = new Image("images/cards/5_of_hearts.png");
    private Image fiveOfSpades = new Image("images/cards/5_of_spades.png");
    private Image sixOfClubs = new Image("images/cards/6_of_clubs.png");
    private Image sixOfDiamonds = new Image("images/cards/6_of_diamonds.png");
    private Image sixOfHearts = new Image("images/cards/6_of_hearts.png");
    private Image sixOfSpades = new Image("images/cards/6_of_spades.png");
    private Image sevenOfClubs = new Image("images/cards/7_of_clubs.png");
    private Image sevenOfDiamonds = new Image("images/cards/7_of_diamonds.png");
    private Image sevenOfHearts = new Image("images/cards/7_of_hearts.png");
    private Image sevenOfSpades = new Image("images/cards/7_of_spades.png");
    private Image eightOfClubs = new Image("images/cards/8_of_clubs.png");
    private Image eightOfDiamonds = new Image("images/cards/8_of_diamonds.png");
    private Image eightOfHearts = new Image("images/cards/8_of_hearts.png");
    private Image eightOfSpades = new Image("images/cards/8_of_spades.png");
    private Image nineOfClubs = new Image("images/cards/9_of_clubs.png");
    private Image nineOfDiamonds = new Image("images/cards/9_of_diamonds.png");
    private Image nineOfHearts = new Image("images/cards/9_of_hearts.png");
    private Image nineOfSpades = new Image("images/cards/9_of_spades.png");
    private Image tenOfClubs = new Image("images/cards/10_of_clubs.png");
    private Image tenOfDiamonds = new Image("images/cards/10_of_diamonds.png");
    private Image tenOfHearts = new Image("images/cards/10_of_hearts.png");
    private Image tenOfSpades = new Image("images/cards/10_of_spades.png");
    private Image aceOfClubs = new Image("images/cards/ace_of_clubs.png");
    private Image aceOfDiamonds = new Image("images/cards/ace_of_diamonds.png");
    private Image aceOfHearts = new Image("images/cards/ace_of_hearts.png");
    private Image aceOfSpades = new Image("images/cards/ace_of_spades2.png");
    private Image jackOfClubs = new Image("images/cards/jack_of_clubs2.png");
    private Image jackOfDiamonds = new Image("images/cards/jack_of_diamonds2.png");
    private Image jackOfHearts = new Image("images/cards/jack_of_hearts2.png");
    private Image jackOfSpades = new Image("images/cards/jack_of_hearts2.png");
    private Image kingOfClubs = new Image("images/cards/king_of_clubs2.png");
    private Image kingOfDiamonds = new Image("images/cards/king_of_diamonds2.png");
    private Image kingOfHearts = new Image("images/cards/king_of_hearts2.png");
    private Image kingOfSpades = new Image("images/cards/king_of_spades2.png");
    private Image queenOfClubs = new Image("images/cards/queen_of_clubs2.png");
    private Image queenOfDiamonds = new Image("images/cards/queen_of_diamonds2.png");
    private Image queenOfHearts = new Image("images/cards/queen_of_hearts2.png");
    private Image queenOfSpades = new Image("images/cards/queen_of_spades2.png");



    protected CardsGameController(Button goFish, Label setsMadeTotal) {
        this.goFish = goFish;
        this.setsMadeTotal = setsMadeTotal;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        goFish.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //NEED TO ADD THE GO FISH FUNCTION HERE
        });
        askForCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //NEED TO ADD THE FUNCTION FOR ASKING FOR A CARD
        });
    }
}
