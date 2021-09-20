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
/********* End of Imports *********/


public class CardsGameController implements Initializable {
    public Game game;
    public ImageView[][] positionArray;
    public Image[][] images;
    public Card[][] cardPositions;
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


    public ArrayList<ImageView> positions = new ArrayList<ImageView>();
    public ArrayList<ImageView> setsMade = new ArrayList<ImageView>();

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

    @FXML // fx:id="opponents_sets"
    private ImageView opponents_sets;

    @FXML //fx:id="set1"
    private ImageView set1;

    @FXML // fx:id="set2"
    private ImageView set2;

    @FXML //fx:id="set3"
    private ImageView set3;

    @FXML //fx:id="set4"
    private ImageView set4;

    @FXML //fx:id="set5"
    private ImageView set5;

    @FXML //fx:id="set6"
    private ImageView set6;

    @FXML //fx:id="set7"
    private ImageView set7;

    @FXML //fx:id="set8"
    private ImageView set8;

    @FXML //fx:id="sets9"
    private ImageView set9;

    @FXML //fx:id="set10"
    private ImageView set10;

    @FXML //fx:id="set11"
    private ImageView set11;

    @FXML //fx:id="set12"
    private ImageView set12;

    @FXML //fx:id="set13"
    private ImageView set13;

    @FXML // fx:id="sets_made" - where the score will be kept
    private Label sets_made;

    @FXML // fx:id="opponents_sets_made" - - where the opponents score will be kept
    private Label opponents_sets_made;

    @FXML // fx:id="menu"
    private MenuBar menu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
        game.initGame();
        game.playersTurn();
        askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");

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

        List<ImageView> alist = Arrays.asList(set1, set2, set3, set4, set5, set6, set7, set8, set9, set10, set11, set12, set13);
        setsMade.addAll(alist);

        List<ImageView> anotherlist = Arrays.asList(cardRow1Card1, cardRow1Card2, cardRow1Card3, cardRow1Card4, cardRow1Card5, cardRow1Card6, cardRow1Card7, cardRow1Card8,
                cardRow1Card9, cardRow1Card10, cardRow1Card11, cardRow1Card12, cardRow1Card13, cardRow2Card1, cardRow2Card2, cardRow2Card3, cardRow2Card4, cardRow2Card5, cardRow2Card6,
                cardRow2Card7, cardRow2Card8, cardRow2Card9, cardRow2Card10, cardRow2Card11, cardRow2Card12, cardRow2Card13, cardRow3Card1, cardRow3Card2, cardRow3Card3, cardRow3Card4,
                cardRow3Card5, cardRow3Card6, cardRow3Card7, cardRow3Card8, cardRow3Card9, cardRow3Card10, cardRow3Card11, cardRow3Card12, cardRow3Card13, cardRow4Card1, cardRow4Card2,
                cardRow4Card3, cardRow4Card4, cardRow4Card5, cardRow4Card6, cardRow4Card7, cardRow4Card8, cardRow4Card9, cardRow4Card10, cardRow4Card11, cardRow4Card12,
                cardRow4Card13);
        positions.addAll(anotherlist);

        positionArray = new ImageView[13][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 13; j++){
                positionArray[j][i] = positions.get(0);
                positions.remove(0);
            }
        }
        images = new Image[26][4];
        cardPositions = new Card[13][4];

        Image settest1 = new Image("images/cards/2_of_clubs.png");
        Image settest2 = new Image("images/cards/3_of_hearts.png");
        Image settest3 = new Image("images/cards/5_of_diamonds.png");
        Image settest4 = new Image("images/cards/7_of_clubs.png");
        Image settest5 = new Image("images/cards/9_of_hearts.png");
        Image settest6 = new Image("images/cards/10_of_spades.png");

        set1.setImage(settest1);
        set2.setImage(settest2);
        set3.setImage(settest3);
        set4.setImage(settest4);
        set5.setImage(settest5);
        set6.setImage(settest6);
        set7.setImage(settest1);
        set8.setImage(settest2);
        set9.setImage(settest3);
        set10.setImage(settest4);
        set11.setImage(settest5);
        set12.setImage(settest6);
        set13.setImage(settest1);

        askForCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO: add a call to game.computersTurn() after picking up and making a set if necessary
            //TODO: make function body
            //NEED TO ADD THE FUNCTION FOR ASKING FOR A CARD
            if (game.player1.hand.checkSelected()) {
                try {
                    int ask = 0;
                    String cardName = "";
                    for (int i = 0; i < game.player1.hand.cards.size(); i++) {
                        if (game.player1.hand.cards.get(i).getSelectedValue()) {
                            ask = game.player1.hand.cards.get(i).getMatchId();
                            cardName =  game.player1.hand.cards.get(i).getCardName();
                        }
                    }

                    int newCards = game.playersAsk(ask);
                    if (newCards > 0) {

                        Button close = new Button();
                        close.setStyle("-fx-font-family: Times New Roman;");
                        close.setText("Okay!");
                        BorderPane root = new BorderPane();
                        Scene scene = new Scene(root, 300, 145);

                        //box for text area
                        HBox textHB = new HBox();
                        textHB.setAlignment(Pos.TOP_CENTER);
                        textHB.setStyle("-fx-font-family: Times New Roman;");
                        if (newCards > 1) {
                            textHB.getChildren().add(new TextArea("Player 2 gave you " + newCards + " " + cardName + "'s!"));
                        }
                        else {
                            textHB.getChildren().add(new TextArea("Player 2 gave you one " + cardName + "!"));
                        }
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
                            setHandImages();
                            stage.close();
                        });
                    }
                    else {
                        Button close = new Button();
                        close.setStyle("-fx-font-family: Times New Roman;");
                        close.setText("Go Fish");
                        BorderPane root = new BorderPane();
                        Scene scene = new Scene(root, 300, 145);

                        //box for text area
                        HBox textHB = new HBox();
                        textHB.setAlignment(Pos.TOP_CENTER);
                        textHB.setStyle("-fx-font-family: Times New Roman;");
                        textHB.getChildren().add(new TextArea("Player 2 does not have any " + cardName + "'s."));
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
                            game.player1.hand.drawTopCard(game.deck);
                            setHandImages();
                            stage.close();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*drawFromDeck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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


        });*/
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
        opponents_sets.setImage(backOfCard);

        setHandImages();

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                int finalI = i;
                int finalJ = j;
                positionArray[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    try {
                        if (positionArray[finalI][finalJ] != null) {
                            if (cardPositions[finalI][finalJ].getSelectedValue() == false) {
                                game.player1.hand.selectAll(cardPositions[finalI][finalJ]);
                                askForCard.setStyle("-fx-font-family: 'Times New Roman';");
                                setHandImages();
                            } else {
                                if (cardPositions[finalI][finalJ].getSelectedValue() == true) {
                                    game.player1.hand.deselectAll(cardPositions[finalI][finalJ]);
                                    askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                                    setHandImages();
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                    }

                });
            }
        }

        int p1score = game.player1.getScore();
        sets_made.setText(""+p1score);
        sets_made.setStyle("-fx-font-family: Times New Roman;");

        int p2score = game.player2.getScore();
        opponents_sets_made.setText(""+p2score);
        opponents_sets_made.setStyle("-fx-font-family: Times New Roman;");

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

    public void setHandImages(){
        // TODO : need to update to delete images when call is made so cards arent doubling up where they dont exist
        for (int i = 0, j = 0, k = 0; k < game.player1.hand.cards.size(); i++, k++) {
            Image filename0 = new Image(game.player1.hand.cards.get(k).getFileName());
            Image filename1 = new Image(game.player1.hand.cards.get(k).getBFileName());
            if (k != 0 && game.player1.hand.cards.get(k).getMatchId() == game.player1.hand.cards.get(k - 1).getMatchId()) {
                if (k > 1 && game.player1.hand.cards.get(k).getMatchId() == game.player1.hand.cards.get(k - 2).getMatchId()) {
                    i -= 1;
                    images[i][j + 2] = filename0;
                    images[i + 13][j + 1] = filename1;
                    cardPositions[i][j + 2] = game.player1.hand.cards.get(k);
                    if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                        positionArray[i][j + 2].setImage(images[i][j + 2]);
                    }
                    if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                        positionArray[i][j + 2].setImage(images[i + 13][j + 1]);
                    }
                } else {
                    i -= 1;
                    images[i][j + 1] = filename0;
                    images[i + 13][j + 2] = filename1;
                    cardPositions[i][j + 1] = game.player1.hand.cards.get(k);
                    if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                        positionArray[i][j + 1].setImage(images[i][j + 1]);
                    }
                    if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                        positionArray[i][j + 1].setImage(images[i + 13][j + 2]);
                    }
                }
            } else {
                images[i][j] = filename0;
                images[i + 13][j] = filename1;
                cardPositions[i][j] = game.player1.hand.cards.get(k);
                if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                    positionArray[i][j].setImage(images[i][j]);
                }
                if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                    positionArray[i][j].setImage(images[i + 13][j]);
                }
            }
        }
    }
}
