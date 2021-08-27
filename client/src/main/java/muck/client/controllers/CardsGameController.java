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
    private Image two_of_clubs = new Image("images/cards/2_of_clubs.png");
    private Image two_of_diamonds = new Image("images/cards/2_of_diamonds.png");
    private Image two_of_hearts = new Image("images/cards/2_of_hearts.png");
    private Image three_of_clubs = new Image("images/cards/3_of_clubs.png");
    private Image three_of_diamonds = new Image("images/cards/3_of_diamonds.png");
    private Image three_of_hearts = new Image("images/cards/3_of_hearts.png");
    private Image three_of_spades = new Image("resources/images/cards/3_of_spades.png");
    private Image four_of_clubs = new Image("images/cards/4_of_clubs.png");
    private Image four_of_diamonds = new Image("images/cards/4_of_diamonds.png");
    private Image four_of_hearts = new Image("images/cards/4_of_hearts.png");
    private Image four_of_spades = new Image("images/cards/4_of_spades.png");
    private Image five_of_clubs = new Image("images/cards/5_of_clubs.png");
    private Image five_of_diamonds = new Image("images/cards/5_of_diamonds.png");
    private Image five_of_hearts = new Image("images/cards/5_of_hearts.png");
    private Image five_of_spades = new Image("images/cards/5_of_spades.png");
    private Image six_of_clubs = new Image("images/cards/6_of_clubs.png");
    private Image six_of_diamonds = new Image("images/cards/6_of_diamonds.png");
    private Image six_of_hearts = new Image("images/cards/6_of_hearts.png");
    private Image six_of_spades = new Image("images/cards/6_of_spades.png");
    private Image seven_of_clubs = new Image("images/cards/7_of_clubs.png");
    private Image seven_of_diamonds = new Image("images/cards/7_of_diamonds.png");
    private Image seven_of_hearts = new Image("images/cards/7_of_hearts.png");
    private Image seven_of_spades = new Image("images/cards/7_of_spades.png");
    private Image eight_of_clubs = new Image("images/cards/8_of_clubs.png");
    private Image eight_of_diamonds = new Image("images/cards/8_of_diamonds.png");
    private Image eight_of_hearts = new Image("images/cards/8_of_hearts.png");
    private Image eight_of_spades = new Image("images/cards/8_of_spades.png");
    private Image nine_of_clubs = new Image("images/cards/9_of_clubs.png");
    private Image nine_of_diamonds = new Image("images/cards/9_of_diamonds.png");
    private Image nine_of_hearts = new Image("images/cards/9_of_hearts.png");
    private Image nine_of_spades = new Image("images/cards/9_of_spades.png");
    private Image ten_of_clubs = new Image("images/cards/10_of_clubs.png");
    private Image ten_of_diamonds = new Image("images/cards/10_of_diamonds.png");
    private Image ten_of_hearts = new Image("images/cards/10_of_hearts.png");
    private Image ten_of_spades = new Image("images/cards/10_of_spades.png");
    private Image ace_of_clubs = new Image("images/cards/ace_of_clubs.png");
    private Image ace_of_diamonds = new Image("images/cards/ace_of_diamonds.png");
    private Image ace_of_hearts = new Image("images/cards/ace_of_hearts.png");
    private Image ace_of_spades = new Image("images/cards/ace_of_spades.png");
    private Image jack_of_clubs = new Image("images/cards/jack_of_clubs.png");
    private Image jack_of_diamonds = new Image("images/cards/jack_of_diamonds.png");
    private Image jack_of_hearts = new Image("images/cards/jack_of_hearts.png");
    private Image jack_of_spades = new Image("images/cards/jack_of_hearts.png");
    private Image king_of_clubs = new Image("images/cards/king_of_clubs.png");
    private Image king_of_diamonds = new Image("images/cards/king_of_diamonds.png");
    private Image king_of_hearts = new Image("images/cards/king_of_hearts.png");
    private Image king_of_spades = new Image("images/cards/king_of_spades.png");
    private Image queen_of_clubs = new Image("images/cards/queen_of_clubs.png");
    private Image queen_of_diamonds = new Image("images/cards/queen_of_diamonds.png");
    private Image queen_of_hearts = new Image("images/cards/queen_of_hearts.png");
    private Image queen_of_spades = new Image("images/cards/queen_of_spades.png");



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
