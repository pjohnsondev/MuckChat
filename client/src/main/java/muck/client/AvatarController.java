package muck.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import muck.protocol.*;
import muck.protocol.connection.*;

public class AvatarController implements Initializable {

    //Notes. Do I need to create a new avatar class that you can pass a username
    // into a controller and it stores the uname and avatar in one unique object???

    String uname;

    @FXML
    Button submit;

    @FXML
    Text username;

    @FXML
    ImageView avatarFullBody;

    @FXML
    Circle peach;

    @FXML
    Circle goku;

    @FXML
    Circle sailorMars;

    @FXML
    Circle pikachu;

    Image peachPortrait = new Image("/images/peach-portrait2.png");
    Image gokuPortrait = new Image("/images/goku-portrait.png");
    Image sMarsPortrait = new Image("/images/SailorMars-portrait.png");
    Image pikachuPortrait = new Image("/images/pikachu-portrait.png");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        peach.setFill(new ImagePattern(peachPortrait));
        goku.setFill(new ImagePattern(gokuPortrait));
        sailorMars.setFill(new ImagePattern(sMarsPortrait));
        pikachu.setFill(new ImagePattern(pikachuPortrait));

        //submit.setOnAction need to add functions
        //avatarFullBody.setOnMousePressed(new EventHandler<MouseEvent>() )

        //peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
           // peachSelection();
        //});
    }

    /*
    Will recall the players username so it then can be used throughout the game
    This may not be
     */
    /*public String getUsername() {
        return username;
    }*/

    //public ?? getAvatar() {
    // How to I pull the correct avatar image
    // Will it require a lot of if else statements??
    // Error if can't get for some reason
    // How will it then

    private void submit(ActionEvent event) {
        //Store the avatar selection
        //how to I get the window to close and open the chat window
    }

    private void updateUsername() {
        // Call the 'log in screen' class to get the username
        // Get username
        // Assign username to variable
        // Replace username in FXML
    }

    private void peachSelection() {
        // When you click the peach avatar object the #avatarFullBody Imageview changes to the full body image
        // Make it so that the image stays the same size?
        // Somehow store peach in the avatar selection object
    }

    private void gokuSelection() {
        // When you click the goku avatar object the #avatarFullBody Imageview changes to the full body image
        // Make it so that the image stays the same size?
        // Somehow store goku in the avatar selection object
    }

    private void wonderWomanSelection() {
        // When you click the wonderwoman avatar object the #avatarFullBody Imageview changes to the full body image
        // Make it so that the image stays the same size?
        // Somehow store peach in the wonderwoman selection object
    }

    private void pikachuSelection() {
        // When you click the pikachu avatar object the #avatarFullBody Imageview changes to the full body image
        // Make it so that the image stays the same size?
        // Somehow store pikachu in the avatar selection object
    }

}
