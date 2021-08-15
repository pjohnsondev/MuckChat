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
    private Circle batman;

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
    private static final Image peachFull = new Image("/images/peach.png");
    private static final Image peachPortrait = new Image("/images/peach-portrait.png");
    private static final Image peachSprite = new Image("/images/peachSprite.png");

    // Batman
    private static final Image batmanFull = new Image("/images/batman.png");
    private static final Image batmanPortrait = new Image("/images/batman-portrait.png");
    private static final Image batmanSprite = new Image("/images/batmanSprite.png");

    // Pikachu
    private static final Image pikachuFull = new Image("/images/pikachu.png");
    private static final Image pikachuPortrait = new Image("/images/pikachu-portrait.png");
    private static final Image pikachuSprite = new Image("/images/pikachuSprite.png");

    // Skeleton
    private static final Image skeletonFull = new Image("/images/skeleton.png");
    private static final Image skeletonPortrait = new Image("/images/skeleton-portrait.png");
    private static final Image skeletonSprite = new Image("/images/skeletonSprite.png");

    // Wonder Woman
    private static final Image wonderWomanFull = new Image("/images/wonderWoman.png");
    private static final Image wonderWomanPortrait = new Image("/images/wonderWoman-portrait.png");
    private static final Image wonderWomanSprite = new Image("/images/wonderWomanSprite.png");

    // Yoshi
    private static final Image yoshiFull = new Image("/images/yoshi.png");
    private static final Image yoshiPortrait = new Image("/images/yoshi-portrait.png");
    private static final Image yoshiSprite = new Image("/images/yoshiSprite.png");


    // Default
    private static final Image error = new Image("/images/error.png");
    private final Image unavailable = new Image("/images/Unknown.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarFullBody.setPreserveRatio(true);

            peach.setFill(new ImagePattern(peachPortrait));
            batman.setFill(new ImagePattern(batmanPortrait));
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

        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> submit());

        peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("peach");
        });

        batman.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("batman");
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
                case "batman":
                    batman.setRadius(80.0);
                    batman.setEffect(null);
                    avatar = "batman";
                    avatarFullBody.setImage(batmanFull);
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

    public void submit() {
        // TODO: Send username and avatar back to the server for storage
        // What will we do in the case of avatar change
        // Platform.exit(); <<< Closes the whole JAVAFX program
        //openChat();
        // Close Avatar Window.
        // Open chat window.

        System.out.println("Submit has been activated");
    }

    /*public static void openChat() {
        MuckController.MuckController(uname);
    }*/


    //MOVE THESE TO TOP WHEN REST OF CODE IS DONE
    /**
     Returns an image object of the full bodied Avatar image.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getFullAvatar(String avatarID) {

        switch (avatarID) {
            case "peach":
                return peachFull;
            case "batman":
                return batmanFull;
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
            case "batman":
                return batmanPortrait;
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

    public static Image getSprite(String avatarID) {
        switch (avatarID) {
            case "peach":
                return peachSprite;
            case "batman":
                return batmanSprite;
            case "pikachu":
                return pikachuSprite;
            case "skeleton":
                return skeletonSprite;
            case "wonderWoman":
                return wonderWomanSprite;
            case "yoshi":
                return yoshiSprite;
            default:
                return error;
        }
    }

    // The below code is for formatting the changes to the avatar dashboard.

    private void restorePortraitSize() {
        // Can you set a slow change in size effect here?
        //ScaleTransition peachScale = new ScaleTransition(Duration.millis(3000).peach); << This does not accept circle elements
        avatarFullBody.setFitHeight(538.0);
        peach.setRadius(75.0);
        batman.setRadius(75.0);
        pikachu.setRadius(75.0);
        skeleton.setRadius(75.0);
        wonderWoman.setRadius(75.0);
        yoshi.setRadius(75.0);
    }

    private void blur() {
        GaussianBlur blur = new GaussianBlur(10);
        peach.setEffect(blur);
        batman.setEffect(blur);
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