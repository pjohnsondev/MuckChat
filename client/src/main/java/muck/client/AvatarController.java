package muck.client;

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

public class AvatarController implements Initializable {

    // This will be the associated attributes of the user
    private int uniqueId;
    private static String uname; // Name added as a placeholder
    private String avatar;

    @FXML
    private Button submit;

    @FXML
    private Text username;

    @FXML
    private ImageView avatarFullBody;

    @FXML
    private Circle peach;

    @FXML
    private Circle goku;

    @FXML
    private Circle sailorMars;

    @FXML
    private Circle pikachu;

    // Image initialisation
    private Image peachFull = new Image("/images/peach.png");
    private Image peachPortrait = new Image("/images/peach-portrait2.png");
    private Image gokuFull = new Image("/images/goku.png");
    private Image gokuPortrait = new Image("/images/goku-portrait.png");
    private Image sailorMarsFull = new Image("/images/SailorMars.png");
    private Image sMarsPortrait = new Image("/images/SailorMars-portrait.png");
    private Image pikachuFull = new Image("/images/pikachu.png");
    private Image pikachuPortrait = new Image("/images/pikachu-portrait.png");
    private Image error = new Image("/images/error.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarFullBody.setPreserveRatio(true);
        peach.setFill(new ImagePattern(peachPortrait));
        goku.setFill(new ImagePattern(gokuPortrait));
        sailorMars.setFill(new ImagePattern(sMarsPortrait));
        pikachu.setFill(new ImagePattern(pikachuPortrait));

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

    /*
    Create a constructor that can be called with the players username as an argument. This constructor should also prompt the avatar selection window to open.
    Once selected, work on sending the updated avatar string back to the database to be stored with user details
    Create a getter for the avatar
    Update the avatars to sprites
     */

    public static void AvatarCreation(String username) {
        uname = username;
        run();
    }

    public static void run() {
        Application.launch(Avatar.class, new String[]{});
        // If there is a String stored in Avatar ID that isn't error you can display in image view.  Otherwise empty.
        // Will use this when user wants to change avatar
    }


    public String getAvatarSelection() {
        return avatar;
    }
    /*
Returns an image object of the full bodied Avatar image.
@param: userAvatar. This will be passed into the method from the server
 */
    public Image getAvatar(String avatarID) {
        // can I use a switch statement
        if (avatarID.equals("peach")) {
            return peachFull;
        } else if (avatarID.equals("goku")) {
            return gokuFull;
        } else if (avatarID.equals("sailorMars")) {
            return sailorMarsFull;
        } else if (avatarID.equals("pikachu")) {
            return pikachuFull;
        }
        return error;
    }

    /*
    Returns an image object of the portrait of their avatar.
    @param: userAvatar. This will be passed into the method from the server
     */
    public Image getPortrait(String avatarID) {
        //can I use a switch statement
        if (avatarID.equals("peach")) {
            return peachPortrait;
        } else if (avatarID.equals("goku")) {
            return gokuPortrait;
        } else if (avatarID.equals("sailorMars")) {
            return sMarsPortrait;
        } else if (avatarID.equals("pikachu")) {
            return pikachuPortrait;
        } else {
            return error;
        }
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

    // Would it look better to blur all other selections when one avatar portrait is selected?

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

    private void restorePortraitSize() {
        // Can you set a slow change in size effect here?
        //ScaleTransition peachScale = new ScaleTransition(Duration.millis(3000).peach); << This does not accept circle elements
        avatarFullBody.setFitHeight(538.0);
        peach.setRadius(80.0);
        goku.setRadius(80.0);
        sailorMars.setRadius(80.0);
        pikachu.setRadius(80.0);
    }

    private void centreImage() {
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
