package muck.client;

import com.google.errorprone.annotations.FormatMethod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.stage.Stage;
import muck.client.card_games.Player;
import muck.client.card_games.Player_turn;
import muck.client.card_games.Game;
//TODO: why are these below not working?
import muck.client.card_games.Deck;
import muck.client.card_games.Card;
import muck.client.card_games.Hand;


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

    //Image initialisation
    private Image twoOfClubs = new Image("images/cards/2_of_clubs.png");
    private Image twoOfDiamonds = new Image("images/cards/2_of_diamonds.png");
    private Image twoOfHearts = new Image("images/cards/2_of_hearts.png");
    private Image threeOfClubs = new Image("images/cards/3_of_clubs.png");
    private Image threeOfDiamonds = new Image("images/cards/3_of_diamonds.png");
    private Image threeOfHearts = new Image("images/cards/3_of_hearts.png");
    private Image threeOfSpades = new Image("images/cards/3_of_spades.png");
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
    private Image jackOfClubs = new Image("images/cards/jack_of_clubs.png");
    private Image jackOfDiamonds = new Image("images/cards/jack_of_diamonds.png");
    private Image jackOfHearts = new Image("images/cards/jack_of_hearts.png");
    private Image jackOfSpades = new Image("images/cards/jack_of_spades.png");
    private Image kingOfClubs = new Image("images/cards/king_of_clubs.png");
    private Image kingOfDiamonds = new Image("images/cards/king_of_diamonds.png");
    private Image kingOfHearts = new Image("images/cards/king_of_hearts.png");
    private Image kingOfSpades = new Image("images/cards/king_of_spades.png");
    private Image queenOfClubs = new Image("images/cards/queen_of_clubs.png");
    private Image queenOfDiamonds = new Image("images/cards/queen_of_diamonds.png");
    private Image queenOfHearts = new Image("images/cards/queen_of_hearts.png");
    private Image queenOfSpades = new Image("images/cards/queen_of_spades.png");
    private Image backOfCard = new Image("images/cards/backofdeck.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Game game = new Game();
        go_fish.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //go fish needs to basically pass back a variable switch that makes the
            //draw card popup visible for other player.
            //"Player 2 does not have any *card value*:
            //               Pickup card"
            //TODO: create a boolean variable for go fish to make the response pop up
            // to other player.
            //NEED TO ADD THE GO FISH FUNCTION HERE
            try {
                Button close = new Button();
                close.setText("Close");
                BorderPane root = new BorderPane(new TextArea());
                Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.getChildren().add(new TextArea("You've gone fishin' !\n\nYou've caught a CARD SUIT/COLOR card"));
                // just add the card that the player's picked up
                root.setCenter(textHB);

                HBox butbox = new HBox();
                butbox.setAlignment(Pos.CENTER);
                butbox.getChildren().add(close);
                root.setBottom(butbox);

                Stage stage = new Stage();
                stage.setTitle("Go Fish!");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        ask_for_card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: make function body
            //NEED TO ADD THE FUNCTION FOR ASKING FOR A CARD
            try {
                Button close = new Button();
                close.setText("Close");
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.getChildren().add(new TextArea("You have asked for the card: ***\n\nYour opponent DOES/DOES NOT have that card"));
                // need to add the cards that the player asks for and maybe also add if the other player has/hasnt got that card
                root.setCenter(textHB);

                //box for close button
                HBox butbox = new HBox();
                butbox.setAlignment(Pos.CENTER);
                butbox.getChildren().add(close);
                root.setBottom(butbox);
                Stage stage = new Stage();
                stage.setTitle("Ask for a card!");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        draw_from_deck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            game.player1.hand.draw_top_card(game.deck);
            game.print_cards(1);
            //I got this function fully written. Another separate one that deals the hand.
            //Maybe just need to change it so it doesn't need the deck name input? Some this.deck scenario.
            //MIGHT ADD A COUNTER OR SOMETHING SO WHEN THEY START THE GAME - FIRST UP THEY
            // CLICK ON THE DECK TO DEAL CARDS THEN AFTER THEY HAVE BEEN DEALT THE FIRST TIME
            // IT CHANGES TO JUST DRAWING CARDS - thats up to you. im happy to just do an automatic
            //deal as part of initialising the game.
           //NEED TO ADD FUNCTION FOR PICKING UP FROM DECK - done.

            try {
               Button close = new Button();
               close.setText("Close");
               BorderPane root = new BorderPane(new TextArea());
               Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.getChildren().add(new TextArea("You have received the cards: " + game.card_list)); // need to add the cards that the player gets
                root.setCenter(textHB);

                //box for button
               HBox butbox = new HBox();
               butbox.setAlignment(Pos.CENTER);
               butbox.getChildren().add(close);
               root.setBottom(butbox);

               Stage stage = new Stage();
               stage.setTitle("New Cards!");
               stage.setScene(scene);
               stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        //THIS IS JUST A THOUGHT ABOUT HOW TO HIGHLIGHT CARDS WHEN PRESSED
        cardRow1Card1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            cardRow1Card1.setLayoutX(408);
            cardRow1Card1.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                cardRow1Card1.setLayoutX(416);
            });
        });

        
        
        
    }

    public static void set_score(){
                // I did have an increment score function made under :
                // Player.add_score();
               // THIS WILL BE THE CODE FOR THE SCORE PUT INTO 'SETS_MADE'
    };

    public static void set_opponent_score(){
        //THIS WILL BE THE SCORE FOR PLAYER2 PUT INTO 'OPPONENT_SET_MADE'
        //is this just about keeping "opponents score: " value on screen updated?
    };

    public static void opponents_card(){
        //DO SOMETHING LIKE:
        // IF(OPPONENTS_SCORE BETWEEN 0-10 THEY HAVE 5 CARDS SHOWING)
        // IF(OPPONENTS_SCORE BETWEEN 10-20 THEY HAVE 4 CARDS SHOWING)
        // IF(OPPONENTS_SCORE BETWEEN 20-30 THEY HAVE  CARDS SHOWING)
        // ECT.. SO THAT THEIR CARDS GET LESS SLOWLY

    }
    
    




}
