package muck.client;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import muck.client.card_games.Card;
import muck.client.card_games.Game;
import static muck.client.Achievements.*;

/**
 * CardsGameController Class. Connects the game to the player
 */
public class CardsGameController implements Initializable {
    public Game game;
    public ImageView[][] positionArray;
    public Image[][] images;
    public Card[][] cardPositions;
    public int count = 0;
    public String cardName;
    @FXML // fx:id="askForCard"
    public Button askForCard;
    public int setId;

    @FXML // fx:id="makeSet"
    public Button makeSet;

    //Creating ImageViews for positions to display cards in player 1's hand
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

    // Arrays to store ImageViews in for both the hand and the sets display
    public ArrayList<ImageView> positions = new ArrayList<ImageView>();
    public ArrayList<ImageView> setsMade = new ArrayList<ImageView>();

    // Giving the computer a small display of cards (displaying the backs)
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

    // ImageViews for the sets that Player 1 creates
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
    // ImageViews for the sets that Player 2 creates
    @FXML //fx:id="set1"
    private ImageView oset1;
    @FXML // fx:id="set2"
    private ImageView oset2;
    @FXML //fx:id="set3"
    private ImageView oset3;
    @FXML //fx:id="set4"
    private ImageView oset4;
    @FXML //fx:id="set5"
    private ImageView oset5;
    @FXML //fx:id="set6"
    private ImageView oset6;
    @FXML //fx:id="set7"
    private ImageView oset7;
    @FXML //fx:id="set8"
    private ImageView oset8;
    @FXML //fx:id="sets9"
    private ImageView oset9;
    @FXML //fx:id="set10"
    private ImageView oset10;
    @FXML //fx:id="set11"
    private ImageView oset11;
    @FXML //fx:id="set12"
    private ImageView oset12;
    @FXML //fx:id="set13"
    private ImageView oset13;

    // Player 1's score
    @FXML // fx:id="sets_made" - where Player 1's score will be kept
    private Label sets_made;
    //Player 2's score
    @FXML // fx:id="opponents_sets_made" - where the opponents score will be kept
    private Label opponents_sets_made;

    @FXML // fx:id="menu"
    private MenuBar menu;

    /**
     * Constructor function for the CardsGameController Class
     * Used to build controller objects and implement game play through a series of event handlers
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
        game.initGame();
        setId = 0;
        setScoreDisplay();
        askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
        makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");

        // Setting up display arrays with ImageViews
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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                positionArray[j][i] = positions.get(0);
                positions.remove(0);
            }
        }
        images = new Image[26][4];
        cardPositions = new Card[13][4];

        // setting opponents cards - visual only. No functionality
        Image backOfCard = new Image("images/cards/cardBack1.png");
        opponentCard1.setImage(backOfCard);
        opponentCard2.setImage(backOfCard);
        opponentCard3.setImage(backOfCard);
        opponentCard4.setImage(backOfCard);
        opponentCard5.setImage(backOfCard);

        // Displaying Player 1's hand
        setHandImages();
        // Displaying the sets Player 1 has made
        updateSetsDisplay();

        /**
         * Event handler for Make Set Button
         * This event is called by the player clicking on the makeSet button.
         * The button is only visible and actionable when the player has clicked on and selected a full set of cards
         * in their hand. Otherwise, the button is invisible.
         * When clicked, the makeSet button will give a pop-up that tells the player the set of cards is being put away.
         * Upon clicking to close the pop-up, another event handler is called which closes the pop-up window, moves the
         * set of cards, updates player 1's score and calls functions to update displays of the players score, set
         * display and hand display. It also checks if the game has finished and gets the player to pick up if their
         * hand is empty while the deck is not empty
         */
        makeSet.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Events only work if player 1 has currently selected a full book of cards
            if (count == 4) {
                try {
                    Button close = new Button();
                    close.setStyle("-fx-font-family: Times New Roman;");
                    close.setText("Okay!");
                    BorderPane root = new BorderPane(new TextArea());
                    Scene scene = new Scene(root, 300, 145);

                    //box for text area
                    HBox textHB = new HBox();
                    textHB.setAlignment(Pos.TOP_CENTER);
                    textHB.setStyle("-fx-font-family: Times New Roman;");
                    textHB.getChildren().add(new TextArea("You put away your set of " + cardName + "'s."));
                    root.setCenter(textHB);

                    HBox butbox = new HBox();
                    butbox.setAlignment(Pos.CENTER);
                    butbox.getChildren().add(close);
                    root.setBottom(butbox);

                    Stage stage = new Stage();
                    stage.setTitle("Put Away Set of Cards");
                    stage.setScene(scene);
                    stage.show();

                    close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {
                        askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                        makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                        game.player1.hand.makeSet(setId);
                        game.player1.addScore();
                        updateSetsDisplay();
                        setScoreDisplay();
                        // Checking if the game is still active
                        if (game.checkEndGame() == 1) {
                            endGame();
                        }
                        if (game.checkEndGame() == 2) {
                            if (game.player1.hand.cards.size() == 0){
                                Button okay = new Button();
                                okay.setStyle("-fx-font-family: Times New Roman;");
                                okay.setText("Okay!");
                                BorderPane rootA = new BorderPane(new TextArea());
                                Scene sceneA = new Scene(rootA, 300, 145);

                                //box for text area
                                HBox textHBA = new HBox();
                                textHBA.setAlignment(Pos.TOP_CENTER);
                                textHBA.setStyle("-fx-font-family: Times New Roman;");
                                textHBA.getChildren().add(new TextArea("You ran out of cards. Draw one more!"));
                                rootA.setCenter(textHBA);

                                HBox butboxA = new HBox();
                                butboxA.setAlignment(Pos.CENTER);
                                butboxA.getChildren().add(okay);
                                rootA.setBottom(butboxA);

                                Stage stageA = new Stage();
                                stageA.setTitle("Draw another card!");
                                stageA.setScene(sceneA);
                                stageA.show();

                                okay.addEventHandler(MouseEvent.MOUSE_CLICKED, shutA -> {
                                    game.player1.hand.drawTopCard(game.deck);
                                });
                            }
                            if (game.player2.hand.cards.size() == 0){
                                game.player2.hand.drawTopCard(game.deck);
                            }
                        }
                        game.player1.hand.deselectAll();
                        setScoreDisplay();
                        setHandImages();
                        stage.close();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Event handler for Ask For Card Button
         * This event is called by the player clicking on the askForCard button.
         * The button is only visible and actionable when the player has clicked on and selected any cards in their
         * hand. Otherwise, the button is invisible.
         * When clicked, the askForCard button will run a function the ask the computer for a card, retrieve the card
         * and the name of that card and return whether the computer has the card or not. If it does it will display
         * that player 2 gave player 1 the card(s) and how many, and closing the pop-up will run functions to update
         * display of the hand display and call for player 2's turn.
         * If the computer does not have the card, it will display that player 1 needs to "go fish". Clicking to close
         * the pop-up will call for player 1 to pick up a card from the deck, run functions to update display of the
         * hand display and call for player 2's turn.
         */
        askForCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (game.player1.hand.checkSelected() && count != 4) {
                try {
                    int ask = 0;
                    String cardName = "";
                    for (int i = 0; i < game.player1.hand.cards.size(); i++) {
                        if (game.player1.hand.cards.get(i).getSelectedValue()) {
                            ask = game.player1.hand.cards.get(i).getMatchId();
                            cardName = game.player1.hand.cards.get(i).getCardName();
                        }
                    }
                    game.player2.addToArray(game.player2.playerTurns, ask);
                    int newCards = game.playersAsk(ask);
                    // If player receives card(s) from player 2
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
                            textHB.getChildren().add(new TextArea("Player 2 gave you " + newCards + " " + cardName + "'s!\nHave another turn!"));
                        } else {
                            textHB.getChildren().add(new TextArea("Player 2 gave you one " + cardName + "!\nHave another turn!"));
                        }
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
                            game.player1.hand.deselectAll();
                            askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                            makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                            setScoreDisplay();
                            setHandImages();
                            stage.close();
                        });
                        // If player 2 didn't have the card that was asked for
                    } else {
                        Button close = new Button();
                        close.setStyle("-fx-font-family: Times New Roman;");
                        close.setText("Go Fish");
                        BorderPane root = new BorderPane();
                        Scene scene = new Scene(root, 300, 145);

                        //box for text area
                        HBox textHB = new HBox();
                        textHB.setAlignment(Pos.TOP_CENTER);
                        textHB.setStyle("-fx-font-family: Times New Roman;");
                        textHB.getChildren().add(new TextArea("Player 2 does not have any " + cardName + "'s.\nPick up a card from the deck."));
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
                            askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                            makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                            game.player1.hand.drawTopCard(game.deck);
                            game.player1.hand.deselectAll();
                            setScoreDisplay();
                            updateSetsDisplay();
                            setHandImages();
                            stage.close();
                            player2Turn();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Event Handler for Player 1's cards in hand
         * This event is called by the player clicking on one of the cards in their hand
         * The card that was clicked on will be selected, along with all cards in hand from the same set
         * If the full set is selected, the makeSet button will come visible. Otherwise, the askForCard button will
         * become visible.
         * The event handler also calls for the hand display to update.
         */
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                int finalI = i;
                int finalJ = j;
                positionArray[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    try {
                        if (positionArray[finalI][finalJ] != null) {
                            if (cardPositions[finalI][finalJ].getSelectedValue() == false) {
                                game.player1.hand.selectAll(cardPositions[finalI][finalJ]);
                                count = 0;
                                for (int k = 0; k < game.player1.hand.cards.size(); k++) {
                                    if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                                        count++;
                                        cardName = game.player1.hand.cards.get(k).getCardName();
                                        setId = game.player1.hand.cards.get(k).getMatchId();
                                    }
                                }
                                askForCard.setStyle("-fx-font-family: 'Times New Roman';");
                                if (count > 3) {
                                    makeSet.setStyle("-fx-font-family: 'Times New Roman';");
                                    askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                                }
                                if (count < 4) {
                                    makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                                }
                                setHandImages();
                            } else {
                                if (cardPositions[finalI][finalJ].getSelectedValue() == true) {
                                    game.player1.hand.deselectAll();
                                    askForCard.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                                    makeSet.setStyle(" -fx-text-fill: transparent; -fx-font-family: 'Times New Roman'; -fx-background-color: transparent;");
                                    setHandImages();
                                }
                            }
                        }
                    } catch (Exception e) {
                    }

                });
            }
        }
    }

    /**
     * setScoreDisplay function
     * Updates the labels that display player 1's score and player 2's score
     * @return is null
     */
    public void setScoreDisplay() {
        int p1score = game.player1.getScore();
        sets_made.setText("" + p1score);
        sets_made.setStyle("-fx-font-family: Times New Roman;");

        int p2score = game.player2.getScore();
        opponents_sets_made.setText("" + p2score);
        opponents_sets_made.setStyle("-fx-font-family: Times New Roman;");
    }

    /**
     * setHandImages  Function
     * Updates all ImageView images to null and disabled (so ImageView positions dont block clicking on cards)
     * The function goes through the players hand and determines where to display each card and sets its position to
     * no longer disabled. Also switches all selected cards to their alternate image which displays the cards as
     * "highlighted"
     * @return is null
     */
    public void setHandImages() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 4; j++) {
                if (i < 13) {
                    cardPositions[i][j] = null;
                    positionArray[i][j].setImage(null);
                    positionArray[i][j].setDisable(true);
                }
                images[i][j] = null;
            }
        }
        for (int i = 0, j = 0, k = 0; k < game.player1.hand.cards.size(); i++, k++) {
            Image filename0 = new Image(game.player1.hand.cards.get(k).getFileName());
            Image filename1 = new Image(game.player1.hand.cards.get(k).getBFileName());
            if (k != 0 && game.player1.hand.cards.get(k).getMatchId() == game.player1.hand.cards.get(k - 1).getMatchId()) {
                if (k > 1 && game.player1.hand.cards.get(k).getMatchId() == game.player1.hand.cards.get(k - 2).getMatchId()) {
                    if (k > 2 && game.player1.hand.cards.get(k).getMatchId() == game.player1.hand.cards.get(k - 3).getMatchId()) {
                        i -= 1;
                        images[i][j + 3] = filename0;
                        images[i + 13][j + 3] = filename1;
                        cardPositions[i][j + 3] = game.player1.hand.cards.get(k);
                        if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                            positionArray[i][j + 3].setImage(images[i][j + 3]);
                            positionArray[i][j + 3].setDisable(false);
                        }
                        if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                            positionArray[i][j + 3].setImage(images[i + 13][j + 3]);
                            positionArray[i][j + 3].setDisable(false);
                        }
                    } else {
                        i -= 1;
                        images[i][j + 2] = filename0;
                        images[i + 13][j + 2] = filename1;
                        cardPositions[i][j + 2] = game.player1.hand.cards.get(k);
                        if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                            positionArray[i][j + 2].setImage(images[i][j + 2]);
                            positionArray[i][j + 2].setDisable(false);
                        }
                        if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                            positionArray[i][j + 2].setImage(images[i + 13][j + 2]);
                            positionArray[i][j + 2].setDisable(false);
                        }
                    }
                } else {
                    i -= 1;
                    images[i][j + 1] = filename0;
                    images[i + 13][j + 1] = filename1;
                    cardPositions[i][j + 1] = game.player1.hand.cards.get(k);
                    if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                        positionArray[i][j + 1].setImage(images[i][j + 1]);
                        positionArray[i][j + 1].setDisable(false);
                    }
                    if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                        positionArray[i][j + 1].setImage(images[i + 13][j + 1]);
                        positionArray[i][j + 1].setDisable(false);
                    }
                }

            } else {
                images[i][j] = filename0;
                images[i + 13][j] = filename1;
                cardPositions[i][j] = game.player1.hand.cards.get(k);
                if (game.player1.hand.cards.get(k).getSelectedValue() == false) {
                    positionArray[i][j].setImage(images[i][j]);
                    positionArray[i][j].setDisable(false);
                }
                if (game.player1.hand.cards.get(k).getSelectedValue() == true) {
                    positionArray[i][j].setImage(images[i + 13][j]);
                    positionArray[i][j].setDisable(false);
                }

            }
        }

    }

    /**
     * updateSetsDisplay Function
     * Updates images for both players to display an card to represent each set they have collected in their sets pile
     * so far
     * @return is null
     */
    void updateSetsDisplay(){
        Image settest1 = new Image("images/cards/ace_of_clubs.png");
        Image settest2 = new Image("images/cards/2_of_diamonds.png");
        Image settest3 = new Image("images/cards/3_of_hearts.png");
        Image settest4 = new Image("images/cards/4_of_spades.png");
        Image settest5 = new Image("images/cards/5_of_clubs.png");
        Image settest6 = new Image("images/cards/6_of_diamonds.png");
        Image settest7 = new Image("images/cards/7_of_hearts.png");
        Image settest8 = new Image("images/cards/8_of_spades.png");
        Image settest9 = new Image("images/cards/9_of_clubs.png");
        Image settest10 = new Image("images/cards/10_of_diamonds.png");
        Image settest11 = new Image("images/cards/jack_of_hearts.png");
        Image settest12 = new Image("images/cards/queen_of_spades.png");
        Image settest13 = new Image("images/cards/king_of_clubs.png");
        for(int i = 0; i < game.player1.hand.sets.size(); i++) {
            if (game.player1.hand.sets.get(i).getMatchId() == 1) {
                set1.setImage(settest1);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 2) {
                set2.setImage(settest2);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 3) {
                set3.setImage(settest3);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 4) {
                set4.setImage(settest4);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 5) {
                set5.setImage(settest5);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 6) {
                set6.setImage(settest6);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 7) {
                set7.setImage(settest7);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 8) {
                set8.setImage(settest8);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 9) {
                set9.setImage(settest9);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 10) {
                set10.setImage(settest10);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 11) {
                set11.setImage(settest11);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 12) {
                set12.setImage(settest12);
            }
            if (game.player1.hand.sets.get(i).getMatchId() == 13) {
                set13.setImage(settest13);
            }
            for (int j = 0; j < game.player2.hand.sets.size(); j++) {
                if (game.player2.hand.sets.get(j).getMatchId() == 1) {
                    oset1.setImage(settest1);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 2) {
                    oset2.setImage(settest2);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 3) {
                    oset3.setImage(settest3);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 4) {
                    oset4.setImage(settest4);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 5) {
                    oset5.setImage(settest5);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 6) {
                    oset6.setImage(settest6);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 7) {
                    oset7.setImage(settest7);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 8) {
                    oset8.setImage(settest8);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 9) {
                    oset9.setImage(settest9);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 10) {
                    oset10.setImage(settest10);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 11) {
                    oset11.setImage(settest11);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 12) {
                    oset12.setImage(settest12);
                }
                if (game.player2.hand.sets.get(j).getMatchId() == 13) {
                    oset13.setImage(settest13);
                }
            }
        }
    }

    /**
     * player2Turn Function
     * Player2Turn calls functions for player 2 to select a card to ask for and creates a pop-up window to let player 1
     * know which card player 2 is asking for and either prompts the player to give the card, or prompts player 1 to
     * tell player 2 to "go fish" and draw a card from the deck. It then will call
     * functions to update displays of the players score, set display and hand display.
     *
     * @return is null
     */
    public void player2Turn() {
        int computerAsk = game.player2.askForCard();
        if (computerAsk != 0) {
            boolean goFish = true;
            String cardName = "";
            int count = 0;
            for (int i = 0; i < game.player2.hand.cards.size(); i++) {
                if (computerAsk == game.player2.hand.cards.get(i).getMatchId()) {
                    cardName = game.player2.hand.cards.get(i).getCardName();
                }
            }
            for (int i = 0; i < game.player1.hand.cards.size(); i++) {
                if (computerAsk == game.player1.hand.cards.get(i).getMatchId()) {
                    goFish = false;
                    count++;
                }
            }
            try {
                if (goFish == true) {
                    Button goFishC = new Button();
                    goFishC.setStyle("-fx-font-family: Times New Roman;");
                    goFishC.setText("Go fish!");
                    BorderPane rootC = new BorderPane();
                    Scene sceneC = new Scene(rootC, 300, 145);

                    //box for text area
                    HBox textHBC = new HBox();
                    textHBC.setAlignment(Pos.TOP_CENTER);
                    textHBC.setStyle("-fx-font-family: Times New Roman;");
                    textHBC.getChildren().add(new TextArea("Player 2 asked for any " + cardName + "'s."));
                    rootC.setCenter(textHBC);

                    //box for close button
                    HBox butboxC = new HBox();
                    butboxC.setAlignment(Pos.CENTER);
                    butboxC.getChildren().add(goFishC);
                    rootC.setBottom(butboxC);
                    Stage stageC = new Stage();
                    stageC.setTitle("Player 2's Turn!");
                    stageC.setScene(sceneC);
                    stageC.show();
                    goFishC.addEventHandler(MouseEvent.MOUSE_CLICKED, closeC -> {
                        game.player2.hand.drawTopCard(game.deck);
                        if (game.player2.hand.checkForSet(false) == true){
                            game.player2.addScore();
                            setScoreDisplay();
                            updateSetsDisplay();
                        }
                        if (game.checkEndGame() == 1) {
                            endGame();
                        }
                        if (game.checkEndGame() == 2) {
                            if (game.player1.hand.cards.size() == 0){
                                Button okay = new Button();
                                okay.setStyle("-fx-font-family: Times New Roman;");
                                okay.setText("Okay!");
                                BorderPane rootA = new BorderPane(new TextArea());
                                Scene sceneA = new Scene(rootA, 300, 145);

                                //box for text area
                                HBox textHBA = new HBox();
                                textHBA.setAlignment(Pos.TOP_CENTER);
                                textHBA.setStyle("-fx-font-family: Times New Roman;");
                                textHBA.getChildren().add(new TextArea("You ran out of cards. Draw one more!"));
                                rootA.setCenter(textHBA);

                                HBox butboxA = new HBox();
                                butboxA.setAlignment(Pos.CENTER);
                                butboxA.getChildren().add(okay);
                                rootA.setBottom(butboxA);

                                Stage stageA = new Stage();
                                stageA.setTitle("Put Away Set of Cards");
                                stageA.setScene(sceneA);
                                stageA.show();

                                okay.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {
                                    game.player1.hand.drawTopCard(game.deck);
                                });
                            }
                            if (game.player2.hand.cards.size() == 0){
                                game.player2.hand.drawTopCard(game.deck);
                            }
                        }
                        setHandImages();
                        stageC.close();
                    });
                } else {
                    Button giveCardC = new Button();
                    giveCardC.setStyle("-fx-font-family: Times New Roman;");
                    giveCardC.setText("Okay");
                    BorderPane rootC = new BorderPane();
                    Scene sceneC = new Scene(rootC, 300, 145);

                    //box for text area
                    HBox textHBC = new HBox();
                    textHBC.setAlignment(Pos.TOP_CENTER);
                    textHBC.setStyle("-fx-font-family: Times New Roman;");
                    if (count == 1) {
                        textHBC.getChildren().add(new TextArea("Player 2 asked for any " + cardName + "'s.\nGive Player 2 your " + cardName + ".\nPlayer 2 gets to go again."));
                    } else {
                        textHBC.getChildren().add(new TextArea("Player 2 asked for any " + cardName + "'s.\nGive Player 2 your " + cardName + "'s.\nPlayer 2 gets to go again."));
                    }

                    rootC.setCenter(textHBC);

                    //box for close button
                    HBox butboxC = new HBox();
                    butboxC.setAlignment(Pos.CENTER);
                    butboxC.getChildren().add(giveCardC);
                    rootC.setBottom(butboxC);
                    Stage stageC = new Stage();
                    stageC.setTitle("Player 2's Turn!");
                    stageC.setScene(sceneC);
                    stageC.show();
                    giveCardC.addEventHandler(MouseEvent.MOUSE_CLICKED, closeC -> {
                        game.giveComputerCard(computerAsk);
                        if (game.player2.hand.checkForSet(false) == true){
                            game.player2.addScore();
                            setScoreDisplay();
                            updateSetsDisplay();
                        }
                        setHandImages();
                        stageC.close();
                        player2Turn();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * endGame Method
     * Checks to see whether player 1 or player 2 has won the game by the highest score.
     * If player wins for the first time, it unlocks achievement 14
     * @return is null
     */
    public void endGame() {
        if (game.player1.getScore() > game.player2.getScore()) {
            try {
                Button close = new Button();
                close.setStyle("-fx-font-family: Times New Roman;");
                close.setText("Okay!");
                BorderPane root = new BorderPane(new TextArea());
                Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.setStyle("-fx-font-family: Times New Roman;");
                textHB.getChildren().add(new TextArea("Congratulations! You are the winner! \nYour final score is " +
                        game.player1.getScore()));
                root.setCenter(textHB);

                HBox butbox = new HBox();
                butbox.setAlignment(Pos.CENTER);
                butbox.getChildren().add(close);
                root.setBottom(butbox);

                Stage stage = new Stage();
                stage.setTitle("You Win!");
                stage.setScene(scene);
                stage.show();

                close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {
                    stage.close();
                    // Unlocks achievement 14 when the player wins a game of Go-Fish.
                    if (Achievements.achievement14_instance == null) {
                        Achievements.achievement14_instance = new Achievements(achievement14,
                                ACHIEVEMENT14TITLE, ACHIEVEMENT14DESCRIPTION);
                        achievement14_instance.achievementUnlock();
                        achievement14_instance.achievementPopUp();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Button close = new Button();
                close.setStyle("-fx-font-family: Times New Roman;");
                close.setText("Okay");
                BorderPane root = new BorderPane(new TextArea());
                Scene scene = new Scene(root, 300, 145);

                //box for text area
                HBox textHB = new HBox();
                textHB.setAlignment(Pos.TOP_CENTER);
                textHB.setStyle("-fx-font-family: Times New Roman;");
                textHB.getChildren().add(new TextArea("Player 2 won, better luck next time. \nYour final score is " +
                        game.player1.getScore()));
                root.setCenter(textHB);

                HBox butbox = new HBox();
                butbox.setAlignment(Pos.CENTER);
                butbox.getChildren().add(close);
                root.setBottom(butbox);

                Stage stage = new Stage();
                stage.setTitle("Game Over");
                stage.setScene(scene);
                stage.show();

                close.addEventHandler(MouseEvent.MOUSE_CLICKED, shut -> {
                    stage.close();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}