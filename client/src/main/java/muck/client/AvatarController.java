package muck.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.application.Application;

public class AvatarController implements Initializable {

    // This will be the associated attributes of the user
    private static String uname;
    private static int muckPoints = 160; //Dummy value for testing purposes TODO: Remove
    private static String avatar = "error";  //Dummy value for testing purposes TODO: Remove
    private final int OPEN_SKELETON = 100; // Muck points required to activate skeleton avatar
    private final int OPEN_WW = 120; // Muck points required to activate Wonder Woman avatar
    private final int OPEN_YOSHI = 150; // Muck points required to activate Yoshi avatar

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
    private Circle pikachu;

    @FXML
    private Circle skeleton;

    @FXML
    private Circle wonderWoman;

    @FXML
    private Circle yoshi;

    // IMAGE INITIALISATION
    // Peach
    private final Image peachFull = new Image("/images/peach.png");
    private static final Image peachPortrait = new Image("/images/peach-portrait2.png");

    // Goku TODO: Update these images
    private final Image gokuFull = new Image("/images/goku.png");
    private static final Image gokuPortrait = new Image("/images/goku-portrait.png");

    // Pikachu
    private final Image pikachuFull = new Image("/images/pikachu.png");
    private static final Image pikachuPortrait = new Image("/images/pikachu-portrait.png");

    // Skeleton
    private final Image skeletonFull = new Image("/images/skeleton.png");
    private static final Image skeletonPortrait = new Image("/images/skeleton-portrait.png");

    // Wonder Woman
    private final Image wonderWomanFull = new Image("/images/wonderWoman.png");
    private static final Image wonderWomanPortrait = new Image("/images/wonderWoman-portrait.png");

    // Yoshi
    private final Image yoshiFull = new Image("/images/yoshi.png");
    private static final Image yoshiPortrait = new Image("/images/yoshi-portrait.png");


    // Default
    private static final Image error = new Image("/images/error.png");
    private final Image unavailable = new Image("/images/Unknown.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarFullBody.setPreserveRatio(true);

            peach.setFill(new ImagePattern(peachPortrait));
            goku.setFill(new ImagePattern(gokuPortrait));
            pikachu.setFill(new ImagePattern(pikachuPortrait));

            // The below three avatars are only available once the user achieves a certain number of muck points
            if (muckPoints >= OPEN_SKELETON) {
                skeleton.setFill(new ImagePattern(skeletonPortrait));
                skeleton.setCursor(Cursor.HAND);
            } else {
                skeleton.setFill(new ImagePattern(unavailable));
            }

            if (muckPoints >= OPEN_WW) {
                wonderWoman.setFill(new ImagePattern(wonderWomanPortrait));
                wonderWoman.setCursor(Cursor.HAND);
            } else {
                wonderWoman.setFill(new ImagePattern(unavailable));
            }

            if (muckPoints >= OPEN_YOSHI) {
                yoshi.setFill(new ImagePattern(yoshiPortrait));
                yoshi.setCursor(Cursor.HAND);
            } else {
                yoshi.setFill(new ImagePattern(unavailable));
            }

            // If there is already an avatar associated with a user, display the avatar
            // Will be used in the case of an avatar change
            if (!avatar.equals("error")) { //TODO: What if avatar set to null??
                selection(avatar);
            }
        } catch (NullPointerException e) {
            // TODO: What if image isn't available exception
            System.out.print("In initialize");

        }

        username.setText(uname);

        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            submit();
        });

        peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("peach");
        });

        goku.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("goku");
        });

        pikachu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("pikachu");
        });

        skeleton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("skeleton");
        });

        wonderWoman.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("wonderWoman");
        });

        yoshi.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("yoshi");
        });
    }

    /**
     * Sets the username for the avatar interaction so as to store the selection later.
     * Calls the database to determine if there is an existing avatar selected for the user and the user's current muck
     * point value.
     * @param username: the player's username.
     */
    public static void AvatarCreation(String username) {
        uname = username;
            // TODO: Need to call the database for current avatar and muck point value
        Application.launch(Avatar.class, new String[]{});
    }

    /**
     * Selection method.
     * Once an avatar is selected, increases the size of their portrait and blurs all other avatars. Displays full body
     * image of selected avatar on the left. Finally, sets the 'avatar' value for future storage against the username.
     * @param character: the specific character the user has currently selected
     */
    private void selection(String character) {
        try {
            restorePortraitSize();
            blur();
            switch (character) {
                case "peach":
                    peach.setRadius(80.0);
                    peach.setEffect(null);
                    avatar = "peach";
                    avatarFullBody.setImage(peachFull);
                    break;
                case "goku":
                    goku.setRadius(80.0);
                    goku.setEffect(null);
                    avatar = "goku";
                    avatarFullBody.setImage(gokuFull);
                    break;
                case "pikachu":
                    pikachu.setRadius(80.0);
                    pikachu.setEffect(null);
                    avatar = "pikachu";
                    avatarFullBody.setImage(pikachuFull);
                    avatarFullBody.setFitHeight(250); // Changes the height of the image so pikachu is a more realistic
                    // How do I set pikachu in the centre of the screen (by height)
                    break;
                case "skeleton":
                    if (muckPoints >= OPEN_SKELETON) {
                        skeleton.setRadius(80.0);
                        skeleton.setEffect(null);
                        avatar = "skeleton";
                        avatarFullBody.setImage(skeletonFull);
                        break;
                    }
                case "wonderWoman":
                    if (muckPoints >= OPEN_WW) {
                        wonderWoman.setRadius(80.0);
                        wonderWoman.setEffect(null);
                        avatar = "wonderWoman";
                        avatarFullBody.setImage(wonderWomanFull);
                        break;
                    }
                case "yoshi":
                    if (muckPoints >= OPEN_YOSHI) {
                        yoshi.setRadius(80.0);
                        yoshi.setEffect(null);
                        avatar = "yoshi";
                        avatarFullBody.setImage(yoshiFull);
                        avatarFullBody.setFitHeight(300);
                        break;
                    }
                default:
                    break;
            }
            centreImage();
        } catch (NullPointerException e) {
            // TODO: What if the image isn't available exception
            System.out.print("In Selection");

        }
    }

    // TODO: Add something for when you press enter. The below doesn't work
    /*@FXML
    private void onEnter(ActionEvent event) {
        if (!avatar.equals("error")) {
            submit();
        }
    }*/

    public static void submit() {
        // TODO: Send username and avatar back to the server for storage
        // What will we do in the case of avatar change
        // Platform.exit(); <<< Closes the whole JAVAFX program
        //openChat();
        // Close Avatar Window.
        // Open chat window.

        System.out.println("Submit has been activated");
    }

    public static void openChat() {
        MuckController.MuckController(uname);
    }


    //MOVE THESE TO TOP WHEN REST OF CODE IS DONE
    /**
     * DO WE ACTUALLY NEED THIS METHOD?
     Returns an image object of the full bodied Avatar image.
     @param: avatarID. This will be passed into the method from the server
     */
    public Image getAvatar(String avatarID) {
        // can I use a switch statement
        // TODO: Switch and add three remaining characters
        switch (avatarID) {
            case "peach":
                return peachFull;
            case "goku":
                return gokuFull;
            case "pikachu":
                return pikachuFull;
            case "skeleton":
                return skeletonFull;
            case "wonderWoman":
                return wonderWomanFull;
            case "yoshi":
                return yoshiFull;
            default:
                return error;
        }
    }

    /**
     Returns an image object of the portrait of their avatar.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getPortrait(String avatarID) {
        switch (avatarID) {
            case "peach":
                return peachPortrait;
            case "goku":
                return gokuPortrait;
            case "pikachu":
                return pikachuPortrait;
            case "skeleton":
                return skeletonPortrait;
            case "wonderWoman":
                return wonderWomanPortrait;
            case "yoshi":
                return yoshiPortrait;
            default:
                return error;
        }
    }


    private void restorePortraitSize() {
        // Can you set a slow change in size effect here?
        //ScaleTransition peachScale = new ScaleTransition(Duration.millis(3000).peach); << This does not accept circle elements
        avatarFullBody.setFitHeight(538.0);
        peach.setRadius(75.0);
        goku.setRadius(75.0);
        pikachu.setRadius(75.0);
        skeleton.setRadius(75.0);
        wonderWoman.setRadius(75.0);
        yoshi.setRadius(75.0);
    }

    private void blur() {
        GaussianBlur blur = new GaussianBlur(10);
        peach.setEffect(blur);
        goku.setEffect(blur);
        pikachu.setEffect(blur);
        if (muckPoints >= OPEN_SKELETON) {
            skeleton.setEffect(blur);
        }
        if (muckPoints >= OPEN_WW) {
            wonderWoman.setEffect(blur);
        }
        if (muckPoints >= OPEN_YOSHI) {
            yoshi.setEffect(blur);
        }
    }

    private void centreImage() {
        Image img = avatarFullBody.getImage();
        if (img != null) {
            double w;
            double h;

            double ratioX = avatarFullBody.getFitWidth() / img.getWidth();
            double ratioY = avatarFullBody.getFitHeight() / img.getHeight();

            double reducCoeff;
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

// REDUNDANT CODE.  KEEPING HERE JUST IN CASE
/*private void peachSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        peach.setRadius(80.0);
        avatar = "peach";
        avatarFullBody.setImage(peachFull);
        centreImage();
    }

    private void gokuSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        goku.setRadius(80.0);
        avatar = "goku";
        avatarFullBody.setImage(gokuFull);
        centreImage();
    }

    private void sailorMarsSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        sailorMars.setRadius(80.0);
        avatar = "sailorMars";
        avatarFullBody.setImage(sailorMarsFull);
        centreImage();
    }

    private void pikachuSelection() {
        restorePortraitSize();
        // Can you add a change in size effect here
        pikachu.setRadius(80.0);
        avatar = "pikachu";
        avatarFullBody.setImage(pikachuFull);
        avatarFullBody.setFitHeight(250); // Changes the height of the image so pikachu is a more realistic
        // How do I set pikachu in the centre of the screen (by height)
        centreImage();
    }*/
