package muck.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AvatarController implements Initializable {

    // This will be the associated attributes of the user
    int uniqueId;
    String uname = "CamTest2249"; // Name added as a placeholder
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
    Image error = new Image("/images/error.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarFullBody.setPreserveRatio(true);
        peach.setFill(new ImagePattern(peachPortrait));
        goku.setFill(new ImagePattern(gokuPortrait));
        sailorMars.setFill(new ImagePattern(sMarsPortrait));
        pikachu.setFill(new ImagePattern(pikachuPortrait));

        // Get the user's username and unique ID
        // uname = ???;
        // uniqueID = ???;

        username.setText(uname);

        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            submit();
        });

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
        //ScaleTransition peachScale = new ScaleTransition(Duration.millis(3000).peach); << This does not accept circle elements
        avatarFullBody.setFitHeight(538.0);
        peach.setRadius(80.0);
        goku.setRadius(80.0);
        sailorMars.setRadius(80.0);
        pikachu.setRadius(80.0);
    }



    public static void submit() {
        // Send username, unique ID and Avatar to the server for storage and recall.  JSON???
        // Close Avatar Window.
        // Open chat window.
        System.out.println("Submit has been activated");
    }

    /*
    The following four selection methods set the full body image on the left, increase the size of the selected
    avatar portrait and set the avatar selection.
     */
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
        avatarFullBody.setFitHeight(250); // Changes the height of the image so pikachu is a more realistic
        // How do I set pikachu in the centre of the screen (by height)
        centreImage();
    }

    /*
    Returns an image object of the full bodied Avatar image.
    @param: userAvatar. This will be passed into the method from the server
     */
    public Image getAvatar(String userAvatar) {
        // can I use a switch statement
        if (userAvatar.equals("peach")) {
            return peachFull;
        } else if (userAvatar.equals("goku")) {
            return gokuFull;
        } else if (userAvatar.equals("sailorMars")) {
            return sailorMarsFull;
        } else if (userAvatar.equals("pikachu")) {
            return pikachuFull;
        }
        return error;
    }

    /*
    Returns an image object of the portrait of their avatar.
    @param: userAvatar. This will be passed into the method from the server
     */
    public Image getPortrait(String userAvatar) {
        //can I use a switch statement
        if (userAvatar.equals("peach")) {
            return peachPortrait;
        } else if (userAvatar.equals("goku")) {
            return gokuPortrait;
        } else if (userAvatar.equals("sailorMars")) {
            return sMarsPortrait;
        } else if (userAvatar.equals("pikachu")) {
            return pikachuPortrait;
        }
        return error;
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
