package muck.client;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import muck.client.card_games.Card;
import muck.client.card_games.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
//TODO: why are these below not working?


public class CardsGameController implements Initializable {
    @FXML // fx:id="ask_for_card"
    public Button askForCard;

    @FXML // fx:id="go_fish"
    public Button goFish;

    @FXML // set up for cards for row 1 - this will fill first 
    public ImageView cardRow1Card1;
    public Rectangle card1row1select;
    public ImageView cardRow1Card2;
    public Rectangle card1Row2select;
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
    public Button drawFromDeck;

    @FXML // fx:id="menu"
    private MenuBar menu;

    //Image initialisation
    private static final Image twoOfClubs = new Image("images/cards/2_of_clubs.png");
    private static final Image twoOfDiamonds = new Image("images/cards/2_of_diamonds.png");
    //private Image twoOfDiamonds = new Image("images/cards/2_of_diamonds.png");
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
/*
    private Image BtwoOfHearts = new Image("images/cards/B_2_of_hearts.PNG");
    private Image BtwoOfClubs = new Image("images/cards/B_2_of_clubs.PNG");
    private Image BthreeOfDiamonds = new Image("images/cards/B_3_of_diamonds.png");
    private Image BthreeOfHearts = new Image("images/cards/B_3_of_hearts.PNG");
    private Image BthreeOfSpades = new Image("images/cards/B_3_of_spades.PNG");
    private Image BfourOfClubs = new Image("images/cards/B_4_of_clubs.PNG");
    private Image BfourOfDiamonds = new Image("images/cards/B_4_of_diamonds.PNG");
    private Image BfourOfHearts = new Image("images/cards/B_4_of_hearts.PNG");
    private Image BfourOfSpades = new Image("images/cards/B_4_of_spades.PNG");
    private Image BfiveOfClubs = new Image("images/cards/B_5_of_clubs.PNG");
    private Image BfiveOfDiamonds = new Image("images/cards/B_5_of_diamonds.PNG");
    private Image BfiveOfHearts = new Image("images/cards/B_5_of_hearts.PNG");
    private Image BfiveOfSpades = new Image("images/cards/B_6_of_diamonds.PNG");
    private Image BsixOfClubs = new Image("images/cards/B_6_of_clubs.PNG");
    private Image BsixOfDiamonds = new Image("images/cards/B_6_of_diamonds.PNG");
    private Image BsixOfHearts = new Image("images/cards/B_6_of_hearts.PNG");
    private Image BsixOfSpades = new Image("images/cards/B_6_of_spades.png");
    private Image BsevenOfClubs = new Image("images/cards/B_7_of_clubs.PNG");
    private Image BsevenOfDiamonds = new Image("images/cards/7_of_diamonds.png");
    private Image BsevenOfHearts = new Image("images/cards/7_of_hearts.png");
    private Image BsevenOfSpades = new Image("images/cards/B_6_of_spades.PNG");
    private Image BeightOfClubs = new Image("images/cards/B_8_of_clubs.png");
    private Image BeightOfDiamonds = new Image("images/cards/8_of_diamonds.png");
    private Image BeightOfHearts = new Image("images/cards/8_of_hearts.png");
    private Image BeightOfSpades = new Image("images/cards/8_of_spades.png");
    private Image BnineOfClubs = new Image("images/cards/9_of_clubs.png");
    private Image BnineOfDiamonds = new Image("images/cards/9_of_diamonds.png");
    private Image BnineOfHearts = new Image("images/cards/9_of_hearts.png");
    private Image BnineOfSpades = new Image("images/cards/9_of_spades.png");
    private Image BtenOfClubs = new Image("images/cards/10_of_clubs.png");
    private Image BtenOfDiamonds = new Image("images/cards/10_of_diamonds.png");
    private Image BtenOfHearts = new Image("images/cards/10_of_hearts.png");
    private Image BtenOfSpades = new Image("images/cards/10_of_spades.png");
    private Image BaceOfClubs = new Image("images/cards/ace_of_clubs.png");
    private Image BaceOfDiamonds = new Image("images/cards/ace_of_diamonds.png");
    private Image BaceOfHearts = new Image("images/cards/ace_of_hearts.png");
    private Image BaceOfSpades = new Image("images/cards/ace_of_spades2.png");
    private Image BjackOfClubs = new Image("images/cards/jack_of_clubs.png");
    private Image BjackOfDiamonds = new Image("images/cards/jack_of_diamonds.png");
    private Image BjackOfHearts = new Image("images/cards/jack_of_hearts.png");
    private Image BjackOfSpades = new Image("images/cards/jack_of_spades.png");
    private Image BkingOfClubs = new Image("images/cards/king_of_clubs.png");
    private Image BkingOfDiamonds = new Image("images/cards/king_of_diamonds.png");
    private Image BkingOfHearts = new Image("images/cards/king_of_hearts.png");
    private Image BkingOfSpades = new Image("images/cards/king_of_spades.png");
    private Image BqueenOfClubs = new Image("images/cards/queen_of_clubs.png");
    private Image BqueenOfDiamonds = new Image("images/cards/queen_of_diamonds.png");
    private Image BqueenOfHearts = new Image("images/cards/queen_of_hearts.png");
    private Image BqueenOfSpades = new Image("images/cards/queen_of_spades.png");
    private Image backOfCard = new Image("images/cards/backofdeck.png");
*/



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Game game = new Game();
        game.initGame();
        goFish.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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


        askForCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: add a call to game.computersTurn() after picking up and making a set if necessary
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

        drawFromDeck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: add a call to game.computerTurn() after picking up and making a set if necessary
            game.player1.hand.drawTopCard(game.deck);
            game.printCards(1);
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
                textHB.getChildren().add(new TextArea("You have received the cards: " + game.cardList)); // need to add the cards that the player gets
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
        //tenOfDiamonds
        final boolean displayHigh = true;
        //THIS IS JUST A THOUGHT ABOUT HOW TO HIGHLIGHT CARDS WHEN PRESSED
        Image filename0 = new Image(game.player1.hand.cards.get(0).getFileName());
        cardRow1Card1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            cardRow1Card1.setImage(filename0);
            /*if (displayHigh) {
                card1row1select.setFill(Color.valueOf("#f6ff14"));
            }
            */
        });
        cardRow2Card1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            card1Row2select.setFill(Color.valueOf("#f6ff14"));
        });
            cardRow1Card1.setImage(filename0);

    }
/*
// this wants an image value for setImage and i am working to figure out how to give it that
    void set_card(String filename){
    //below wont work - ImageIO.read is for a buffered image
        Image cardpic = ImageIO.read(new File(filename));
        this.cardRow1Card1.setImage(cardpic);
    }
*/


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
