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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class AvatarController implements Initializable {

    // This will be the associated attributes of the user
    int uniqueId;
    String uname;
    String avatar;

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

    // Image initialisation
    Image peachFull = new Image("/images/peach.png");
    Image peachPortrait = new Image("/images/peach-portrait2.png");
    Image gokuFull = new Image("/images/goku.png");
    Image gokuPortrait = new Image("/images/goku-portrait.png");
    Image sailorMarsFull = new Image("/images/SailorMars.png");
    Image sMarsPortrait = new Image("/images/SailorMars-portrait.png");
    Image pikachuFull = new Image("/images/pikachu.png");
    Image pikachuPortrait = new Image("/images/pikachu-portrait.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        peach.setFill(new ImagePattern(peachPortrait));
        goku.setFill(new ImagePattern(gokuPortrait));
        sailorMars.setFill(new ImagePattern(sMarsPortrait));
        pikachu.setFill(new ImagePattern(pikachuPortrait));

        //submit.setOnAction need to add functions
        //avatarFullBody.setOnMousePressed(new EventHandler<MouseEvent>() )

        peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            peachSelection();
        });

        goku.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gokuSelection();
        });

        sailorMars.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            sailorMarsSelection();
        });

        pikachu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            pikachuSelection();
        });
    }

    public void restorePortraitSize() {
        // Can you set a slow change in size effect here?
        peach.setRadius(80.0);
        goku.setRadius(80.0);
        sailorMars.setRadius(80.0);
        pikachu.setRadius(80.0);
    }

    private void submit(ActionEvent event) {
        //Store the avatar selection
        //how to I get the window to close and open the chat window
    }

    private void peachSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        peach.setRadius(100.0);
        avatar = "peach";
        avatarFullBody.setImage(peachFull);
        centreImage();
    }

    private void gokuSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        goku.setRadius(100.0);
        avatar = "goku";
        avatarFullBody.setImage(gokuFull);
        centreImage();
    }

    private void sailorMarsSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        sailorMars.setRadius(100.0);
        avatar = "sailorMars";
        avatarFullBody.setImage(sailorMarsFull);
        centreImage();
    }

    private void pikachuSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        pikachu.setRadius(100.0);
        avatar = "pikachu";
        avatarFullBody.setImage(pikachuFull);
        centreImage();
    }

    /*public String getUsername() {
        return username;
    }*/

    public void /*Image*/ getAvatar() {

        // How to I pull the correct avatar image
        // Will it require a lot of if else statements??
        // Error if can't get for some reason
        // How will it then
    }

    private void updateUsername() {
        // Call the 'log in screen' class to get the username
        // Get username
        // Assign username to variable
        // Replace username in FXML
    }

    public void centreImage() {
        Image img = avatarFullBody.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = avatarFullBody.getFitWidth() / img.getWidth();
            double ratioY = avatarFullBody.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            avatarFullBody.setX((avatarFullBody.getFitWidth() - w) / 2);
            avatarFullBody.setY((avatarFullBody.getFitHeight() - h) / 2);

        }
    }
}
