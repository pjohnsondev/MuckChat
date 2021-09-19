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
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
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
    public ImageView the_deck;

    ArrayList<ImageView> positions = new ArrayList<ImageView>();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Game game = new Game();
        game.initGame();
        game.playersTurn();
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
                close.setStyle("-fx-font-family: Times New Roman;");
                close.setText("Close");
                BorderPane root = new BorderPane(new TextArea());
                Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.setStyle("-fx-font-family: Times New Roman;");
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

                close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {stage.close(); });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<ImageView> anotherlist = Arrays.asList(cardRow1Card1, cardRow1Card2, cardRow1Card3, cardRow1Card4, cardRow1Card5, cardRow1Card6, cardRow1Card7, cardRow1Card8,
                cardRow1Card8, cardRow1Card10, cardRow1Card11, cardRow1Card12, cardRow1Card13, cardRow2Card1, cardRow2Card2, cardRow2Card3, cardRow2Card4, cardRow2Card5, cardRow2Card6,
                cardRow2Card7, cardRow2Card8, cardRow2Card9, cardRow2Card10, cardRow2Card11, cardRow2Card12, cardRow2Card13, cardRow3Card1, cardRow3Card2, cardRow3Card3, cardRow3Card4,
                cardRow3Card5, cardRow3Card6, cardRow3Card7, cardRow3Card8, cardRow3Card9, cardRow3Card10, cardRow3Card11, cardRow3Card12, cardRow3Card13, cardRow4Card1, cardRow4Card2,
                cardRow4Card3, cardRow4Card4, cardRow4Card5, cardRow4Card6, cardRow4Card7, cardRow4Card8, cardRow4Card9, cardRow4Card10, cardRow4Card11, cardRow4Card12,
                cardRow4Card13);
        positions.addAll(anotherlist);


        askForCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: add a call to game.computersTurn() after picking up and making a set if necessary
            //TODO: make function body
            //NEED TO ADD THE FUNCTION FOR ASKING FOR A CARD
            if (game.player1.hand.checkSelected()) {
                try {
                    Button close = new Button();
                    close.setStyle("-fx-font-family: Times New Roman;");
                    close.setText("Close");
                    BorderPane root = new BorderPane();
                    Scene scene = new Scene(root, 300, 145);

                    //box for text area
                    HBox textHB = new HBox();
                    textHB.setAlignment(Pos.TOP_CENTER);
                    textHB.setStyle("-fx-font-family: Times New Roman;");
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
                    close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {
                        int ask = 0;
                        for (int i = 0; i < game.player1.hand.cards.size(); i++) {
                            if (game.player1.hand.cards.get(i).getSelectedValue()) {
                                ask = game.player1.hand.cards.get(i).getMatchId();
                            }
                        }
                        stage.close();
                        game.playersAsk(ask);
                        game.computersTurn();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        drawFromDeck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: add a call to game.computerTurn() after picking up and making a set if necessary
            game.player1.hand.drawTopCard(game.deck);
            game.printCards(1);

            try {
               Button close = new Button();
               close.setText("Close");
               close.setStyle("-fx-font-family: Times New Roman;");

               BorderPane root = new BorderPane(new TextArea());
               Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.setStyle("-fx-font-family: Times New Roman;");
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

                close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {stage.close(); });

            } catch (Exception e) {
                e.printStackTrace();
            }


        });
        //tenOfDiamonds
        final boolean displayHigh = true;
        //THIS IS JUST A THOUGHT ABOUT HOW TO HIGHLIGHT CARDS WHEN PRESSED
      /*  Image filename0 = new Image(game.player1.hand.cards.get(0).getFileName());
        Image filename1 = new Image(game.player1.hand.cards.get(0).getBFileName());
        cardRow1Card1.setImage(filename0);
        cardRow1Card1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            cardRow1Card1.setImage(filename1);
        });*/

        // setting opponents cards - visual only. No functionality ATM
        Image backOfCard = new Image("images/cards/cardBack1.png");
        opponentCard1.setImage(backOfCard);
        opponentCard2.setImage(backOfCard);
        opponentCard3.setImage(backOfCard);
        opponentCard4.setImage(backOfCard);
        opponentCard5.setImage(backOfCard);
        the_deck.setImage(backOfCard);

        for (int i = 0; i < game.player1.hand.cards.size(); i ++) {
            int getCardID = game.player1.hand.cards.get(i).getCardId();
            Image filename0 = new Image(game.player1.hand.cards.get(i).getFileName());
            Image filename1 = new Image(game.player1.hand.cards.get(i).getBFileName());
            positions.get(getCardID).setImage(filename0);

            positions.get(getCardID).addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (game.player1.hand.cards.get(0).getSelectedValue() == false) {
                    game.player1.hand.cards.get(0).setSelected(true);
                    positions.get(getCardID).setImage(filename1);
                } else {
                    if (game.player1.hand.cards.get(0).getSelectedValue() == true) {
                        game.player1.hand.cards.get(0).setSelected(false);
                        positions.get(getCardID).setImage(filename0);
                    }
                }

            });

        }
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
