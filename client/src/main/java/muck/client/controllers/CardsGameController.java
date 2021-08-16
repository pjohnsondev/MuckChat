package muck.client.controllers;

import com.google.errorprone.annotations.FormatMethod;
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
import muck.client.card_games.Player;

public class CardsGameController implements Initializable {
    @FXML // fx:id="ask_for_card"
    public Button ask_for_card;

    @FXML // fx:id="go_fish"
    public Button go_fish;

    @FXML // set up for cards for row 1 - this will fill first 
    public ImageView cardRow1Card1;
    public ImageView cardRow1Card2;
    public ImageView cardRow1Card3;
    public ImageView cardRow1Card4;
    public ImageView cardRow1Card5;
    public ImageView cardRow1Card6;
    public ImageView cardRow1Card7;
    public ImageView cardRow1Card8;
    public ImageView cardRow1Card9;
    public ImageView cardRow1Card10;
    public ImageView cardRow1Card11;
    public ImageView cardRow1Card12;
    public ImageView cardRow1Card13;
    

    @FXML //set up for cards for row 2 - this will fill second 
    public ImageView cardRow2Card1;
    public ImageView cardRow2Card2;
    public ImageView cardRow2Card3;
    public ImageView cardRow2Card4;
    public ImageView cardRow2Card5;
    public ImageView cardRow2Card6;
    public ImageView cardRow2Card7;
    public ImageView cardRow2Card8;
    public ImageView cardRow2Card9;
    public ImageView cardRow2Card10;
    public ImageView cardRow2Card11;
    public ImageView cardRow2Card12;
    public ImageView cardRow2Card13;
    

    @FXML //set up for cards for row 3 - fill up third 
    public ImageView cardRow3Card1;
    public ImageView cardRow3Card2;
    public ImageView cardRow3Card3;
    public ImageView cardRow3Card4;
    public ImageView cardRow3Card5;
    public ImageView cardRow3Card6;
    public ImageView cardRow3Card7;
    public ImageView cardRow3Card8;
    public ImageView cardRow3Card9;
    public ImageView cardRow3Card10;
    public ImageView cardRow3Card11;
    public ImageView cardRow3Card12;
    public ImageView cardRow3Card13;


    @FXML // set up for top row 4 of cards - fills up last
    public ImageView cardRow4Card1;
    public ImageView cardRow4Card2;
    public ImageView cardRow4Card3;
    public ImageView cardRow4Card4;
    public ImageView cardRow4Card5;
    public ImageView cardRow4Card6;
    public ImageView cardRow4Card7;
    public ImageView cardRow4Card8;
    public ImageView cardRow4Card9;
    public ImageView cardRow4Card10;
    public ImageView cardRow4Card11;
    public ImageView cardRow4Card12;
    public ImageView cardRow4Card13;


    @FXML // SET UP CARDS FOR OPPONENT 
    private ImageView opponentCard1;
    @FXML
    private ImageView opponentCard2;
    @FXML
    private ImageView opponentCard3;
    @FXML
    private ImageView opponentCard4;
    @FXML
    private ImageView opponentCard5;

    @FXML // fx:id="sets_made" - where the score will be kept
    private Label sets_made;

    @FXML // fx:id="opponents_sets_made" - - where the opponents score will be kept
    private Label opponents_sets_made;
    
    @FXML //fx:id="draw from deck" - pick up from deck 
    public Button draw_from_deck;


    @FXML // fx:id="menu"
    private MenuBar menu;




    // Image initialisation
<<<<<<< HEAD
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
    private Image backOfCard = new Image("images/cards/backofdeck.png");
=======
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
>>>>>>> 7a161c74432d20818b3afbb9d3fec04244597d90


    protected CardsGameController(Button goFish, Label sets_made) {
        this.go_fish = goFish;
        this.sets_made = sets_made;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        go_fish.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //NEED TO ADD THE GO FISH FUNCTION HERE
        });
        ask_for_card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //NEED TO ADD THE FUNCTION FOR ASKING FOR A CARD
        });
        
        draw_from_deck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //MIGHT ADD A COUNTER OR SOMETHING SO WHEN THEY START THE GAME - FIRST UP THEY
            // CLICK ON THE DECK TO DEAL CARDS THEN AFTER THEY HAVE BEEN DEALT THE FIRST TIME
            // IT CHANGES TO JUST DRAWING CARDS
           //NEED TO ADD FUNCTION FOR PICKING UP FROM DECK      
        });

        //THIS IS JUSTT A THOUGHT ABOUT HOW TO HIGHLIGHT CARDS WHEN PRESSSED
        cardRow1Card1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            cardRow1Card1.setLayoutX(408);
            cardRow1Card1.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                cardRow1Card1.setLayoutX(416);
            });
        });

        
        
        
    }

    public static void set_score(){
               // THIS WILL BE THE CODE FOR THE SCORE PUT INTO 'SETS_MADE'
    };

    public static void set_opponent_score(){
        //THIS WILL BE THE SCORE FOR PLAYER2 PUT INTO 'OPPONENT_SET_MADE'
    };

    public static void opponents_card(){
        //DO SOMETHING LIKE:
        // IF(OPPONENTS_SCORE BETWEEN 0-10 THEY HAVE 5 CARDS SHOWING)
        // IF(OPPONENTS_SCORE BETWEEN 10-20 THEY HAVE 4 CARDS SHOWING)
        // IF(OPPONENTS_SCORE BETWEEN 20-30 THEY HAVE  CARDS SHOWING)
        // ECT.. SO THAT THEIR CARDS GET LESS SLOWLEY

    }
    
    




}
