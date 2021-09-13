package muck.client.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;



public class TicTacToeController {

    @FXML // fx:id="one"
    private ImageView one; // Value injected by FXMLLoader

    @FXML // fx:id="two"
    private ImageView two; // Value injected by FXMLLoader

    @FXML // fx:id="three"
    private ImageView three; // Value injected by FXMLLoader

    @FXML // fx:id="four"
    private ImageView four; // Value injected by FXMLLoader

    @FXML // fx:id="five"
    private ImageView five; // Value injected by FXMLLoader

    @FXML // fx:id="six"
    private ImageView six; // Value injected by FXMLLoader

    @FXML // fx:id="seven"
    private ImageView seven; // Value injected by FXMLLoader

    @FXML // fx:id="eight"
    private ImageView eight; // Value injected by FXMLLoader

    @FXML // fx:id="nine"
    private ImageView nine; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Text status; // Value injected by FXMLLoader

    @FXML // fx:id="game"
    private GridPane game; // Value injected by FXMLLoader

    @FXML // fx:id="exit"
    private Button exit; // Value injected by FXMLLoader

    @FXML // fx:id="play"
    private Button play; // Value injected by FXMLLoader

    Image cross = new Image("images/x-png-35400.png");
    Image circle = new Image("images/blue-circle-634067.png");

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        one.setOnMouseClicked(event -> playersTurn(one));
        two.setOnMouseClicked(event -> playersTurn(two));
        three.setOnMouseClicked(event -> playersTurn(three));
        four.setOnMouseClicked(event -> playersTurn(four));
        five.setOnMouseClicked(event -> playersTurn(five));
        six.setOnMouseClicked(event -> playersTurn(six));
        seven.setOnMouseClicked(event -> playersTurn(seven));
        eight.setOnMouseClicked(event -> playersTurn(eight));
        nine.setOnMouseClicked(event -> playersTurn(nine));
        play.setOnAction(this::newGame);
        exit.setOnAction(this::exitGame);
    }

    //Adds cross to square based on the players selection via a mouse click
    public void playersTurn(ImageView img) {
        if (img.getImage() == null) {
            img.setImage(cross);
            img.setDisable(true);
            checkWinner();
            if (!checkWinner()) {
                computersTurn();
            }
        }
    }

    //Adds a circle to an empty square on behalf of the computers
    public void computersSelection() {
        ImageView img = getRandomSquare();
        if (img.getImage() == null) {
            img.setImage(circle);
            img.setDisable(true);
            checkWinner();
        } else if (img.getImage() != null) {
            computersSelection();
        }
    }

    //Pauses before the computer so it looks like the computer is thinking
    public void computersTurn() {
        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline;
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0.5),
                        event -> {
                            i.set(i.get() + 1);
                            if (!checkWinner()) {
                                computersSelection();
                            }
                        }
                )
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    //Gets a random number
    public ImageView getRandomSquare() {
        ImageView selectedImage;
        Random rand = new Random();
        int number = rand.nextInt(9) + 1;
        System.out.println(number);
        switch(number) {
            case 1:
                selectedImage = one;
                break;
            case 2:
                selectedImage = two;
                break;
            case 3:
                selectedImage = three;
                break;
            case 4:
                selectedImage = four;
                break;
            case 5:
                selectedImage = five;
                break;
            case 6:
                selectedImage = six;
                break;
            case 7:
                selectedImage = seven;
                break;
            case 8:
                selectedImage = eight;
                break;
            case 9:
                selectedImage = nine;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
        return selectedImage;
    }

    //Checks if there is a winner and updates the text field
    public boolean checkWinner() {
        if ((one.getImage()==cross && two.getImage()==cross && three.getImage()==cross) ||
                (four.getImage()==cross && five.getImage()==cross && six.getImage()==cross) ||
                (seven.getImage()==cross && eight.getImage()==cross && nine.getImage()==cross) ||
                (one.getImage()==cross && four.getImage()==cross && seven.getImage()==cross) ||
                (two.getImage()==cross && five.getImage()==cross && eight.getImage()==cross) ||
                (three.getImage()==cross && six.getImage()==cross && nine.getImage()==cross) ||
                (one.getImage()==cross && five.getImage()==cross && nine.getImage()==cross) ||
                (three.getImage()==cross && five.getImage()==cross && seven.getImage()==cross)
        ) {
            status.setText("You win!!!");
            game.setDisable(true);
            return true;
        } else if ((one.getImage()==circle && two.getImage()==circle && three.getImage()==circle) ||
                (four.getImage()==circle && five.getImage()==circle && six.getImage()==circle) ||
                (seven.getImage()==circle && eight.getImage()==circle && nine.getImage()==circle) ||
                (one.getImage()==circle && four.getImage()==circle && seven.getImage()==circle) ||
                (two.getImage()==circle && five.getImage()==circle && eight.getImage()==circle) ||
                (three.getImage()==circle && six.getImage()==circle && nine.getImage()==circle) ||
                (one.getImage()==circle && five.getImage()==circle && nine.getImage()==circle) ||
                (three.getImage()==circle && five.getImage()==circle && seven.getImage()==circle)
        ){
            status.setText("Sorry you lose");
            game.setDisable(true);
            return true;
        } else if (one.getImage() != null && two.getImage() != null && three.getImage() != null && four.getImage() != null
        && five.getImage() != null && six.getImage() != null && seven.getImage() != null && eight.getImage() != null && nine.getImage() != null) {
            status.setText("It's a draw!");
            game.setDisable(true);
            return true;
        } else {
            return false;
        }
    }

    //Resets the grid
    public void newGame(ActionEvent e) {
        one.setImage(null);
        one.setDisable(false);
        two.setImage(null);
        two.setDisable(false);
        three.setImage(null);
        three.setDisable(false);
        four.setImage(null);
        four.setDisable(false);
        five.setImage(null);
        five.setDisable(false);
        six.setImage(null);
        six.setDisable(false);
        seven.setImage(null);
        seven.setDisable(false);
        eight.setImage(null);
        eight.setDisable(false);
        nine.setImage(null);
        nine.setDisable(false);
        game.setDisable(false);
        status.setText(null);
    }

    //Method that quits the game
    public void exitGame(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}


