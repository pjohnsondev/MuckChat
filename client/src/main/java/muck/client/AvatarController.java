package muck.client;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AvatarController implements Initializable  {

    // This will be the associated attributes of the user
    private static String uname; //Will be updated when constructing AvatarController
    private static int muckPoints = 15; //Dummy value for testing purposes TODO: Remove
    public static String avatar = "error"; //Default. No image.
    private static String previous = "login"; //Previous screen. Will determine where the submit button leads.
    private final int OPEN_SKELETON = 20; // Muck points required to activate skeleton avatar
    private final int OPEN_WW = 30; // Muck points required to activate Wonder Woman avatar
    private final int OPEN_YOSHI = 50; // Muck points required to activate Yoshi avatar

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
    private Text skeletonAlert;

    @FXML
    private Circle wonderWoman;

    @FXML
    private Text wonderWomanAlert;

    @FXML
    private Circle yoshi;

    @FXML
    private Text yoshiAlert;

    // IMAGE INITIALISATION
    // Peach
    public static final Image PEACH_FULL = new Image("/images/peach.png");
    private static final Image PEACH_PORTRAIT = new Image("/images/peach-portrait.png");
    private static final Image PEACH_SPRITE = new Image("/images/peachSprite.png");

    // Batman
    private static final Image BATMAN_FULL = new Image("/images/batman.png");
    private static final Image BATMAN_PORTRAIT = new Image("/images/batman-portrait.png");
    private static final Image BATMAN_SPRITE = new Image("/images/batmanSprite.png");

    // Pikachu
    private static final Image PIKACHU_FULL = new Image("/images/pikachu.png");
    private static final Image PIKACHU_PORTRAIT = new Image("/images/pikachu-portrait.png");
    private static final Image PIKACHU_SPRITE = new Image("/images/pikachuSprite.png");

    // Skeleton
    private static final Image SKELETON_FULL = new Image("/images/skeleton.png");
    private static final Image SKELETON_PORTRAIT = new Image("/images/skeleton-portrait.png");
    private static final Image SKELETON_SPRITE = new Image("/images/skeletonSprite.png");

    // Wonder Woman
    private static final Image WONDER_WOMAN_FULL = new Image("/images/wonderWoman.png");
    private static final Image WONDER_WOMAN_PORTRAIT = new Image("/images/wonderWoman-portrait.png");
    private static final Image WONDER_WOMAN_SPRITE = new Image("/images/wonderWomanSprite.png");

    // Yoshi
    private static final Image YOSHI_FULL = new Image("/images/yoshi.png");
    private static final Image YOSHI_PORTRAIT = new Image("/images/yoshi-portrait.png");
    private static final Image YOSHI_SPRITE = new Image("/images/yoshiSprite.png");


    // Default
    private static final Image ERROR = new Image("/images/error.png");
    private final Image UNAVAILABLE = new Image("/images/Unknown.png");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarFullBody.setPreserveRatio(true);

            peach.setFill(new ImagePattern(PEACH_PORTRAIT));
            batman.setFill(new ImagePattern(BATMAN_PORTRAIT));
            pikachu.setFill(new ImagePattern(PIKACHU_PORTRAIT));

            // The below three avatars are only available once the user achieves a certain number of muck points
            lockedAvatars(OPEN_SKELETON, skeleton, SKELETON_PORTRAIT, skeletonAlert, "skeleton");

            lockedAvatars(OPEN_WW, wonderWoman, WONDER_WOMAN_PORTRAIT, wonderWomanAlert, "wonderWoman");

            lockedAvatars(OPEN_YOSHI, yoshi, YOSHI_PORTRAIT, yoshiAlert, "yoshi");

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
            if (previous.equals("playerDashboard")) { //If the user previously came from player dashboard return there
                submitToDashboard(event);
            } else { //Else return to the map
                submitToMap(event);
            }
        });

        peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("peach");
        });

        batman.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("batman");
        });

        pikachu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selection("pikachu");
        });
    }

     /** NB: This method will become void once we have linked the player sign up page!!
     * Sets the username for the avatar interaction so as to store the selection later.
     * Calls the database to determine if there is an existing avatar selected for the user and the user's current muck
     * point value.
     * Opens the Avatar selection window
     * @param username: the player's username.
     */
    public static void avatarCreation(String username) {
        uname = username;
            // TODO: Need to call the database for current avatar and muck point values
        try {
            FXMLLoader loader = new FXMLLoader(AvatarController.class.getResource("/fxml/Avatar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setRoot(root);
            Stage stage = new Stage();
            stage.setTitle("Muck 2021");
            stage.setMaxWidth(1200);
            stage.setMaxHeight(1100);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * AvatarCreation method
     * From the user creation screen
     * This function will be used to access and initialise the Avatar Selection screen from another window.
     * @param event: The mouse event that has prompted the opening of the window.
     * @param username: The username of the current player
     */
    public static void avatarCreation(MouseEvent event, String username) {
        previous = "login";
        uname = username;
        avatar = "error";
        try {
            Parent root = FXMLLoader.load(AvatarController.class.getResource("/fxml/Avatar.fxml"));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            //This line gets the Stage Information
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Overloaded Avatar creation method.
     * From the player dashboard screen
     * @param event
     * @param username
     */
    public static void avatarCreation(MouseEvent event, String username, String avID) {
        //TODO: Call server for muck point value
        previous = "playerDashboard";
        uname = username;
        avatar = avID;
        try {
            Parent root = FXMLLoader.load(AvatarController.class.getResource("/fxml/Avatar.fxml"));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            //This line gets the Stage Information
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * LockedAvatars method.
     * Updated the appearance and actions associated with a locked Avatar depending on MuckPoint value
     * @param open_muck: the MuckPoint total at which the avatar becomes unlocked
     * @param portrait: the circle object within which each avatar portrait is displayed
     * @param avatarPortrait: the portrait image of the avatar
     * @param avatarAlert: the Text object within which the MuckPoint total required to unlock the avatar is displayed
     */
    private void lockedAvatars(int open_muck, Circle portrait, Image avatarPortrait, Text avatarAlert, String avatarID) {
        if (muckPoints >= open_muck) {
            portrait.setFill(new ImagePattern(avatarPortrait));
            portrait.setCursor(Cursor.HAND);
            portrait.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                selection(avatarID);
            });
        } else {
            portrait.setFill(new ImagePattern(UNAVAILABLE));
            portrait.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                hoverEvent(avatarAlert, ("Earn " + open_muck + " MuckPoints to unlock!"));
            });
            portrait.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                hoverEvent(avatarAlert, "");
            });
        }
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
                    avatarFullBody.setImage(PEACH_FULL);
                    break;
                case "batman":
                    batman.setRadius(80.0);
                    batman.setEffect(null);
                    avatar = "batman";
                    avatarFullBody.setImage(BATMAN_FULL);
                    break;
                case "pikachu":
                    pikachu.setRadius(80.0);
                    pikachu.setEffect(null);
                    avatar = "pikachu";
                    avatarFullBody.setImage(PIKACHU_FULL);
                    avatarFullBody.setFitHeight(250); // Changes the height of the image so pikachu is a more realistic
                    // How do I set pikachu in the centre of the screen (by height)
                    break;
                case "skeleton":
                    if (muckPoints >= OPEN_SKELETON) {
                        skeleton.setRadius(80.0);
                        skeleton.setEffect(null);
                        avatar = "skeleton";
                        avatarFullBody.setImage(SKELETON_FULL);
                        break;
                    }
                case "wonderWoman":
                    if (muckPoints >= OPEN_WW) {
                        wonderWoman.setRadius(80.0);
                        wonderWoman.setEffect(null);
                        avatar = "wonderWoman";
                        avatarFullBody.setImage(WONDER_WOMAN_FULL);
                        break;
                    }
                case "yoshi":
                    if (muckPoints >= OPEN_YOSHI) {
                        yoshi.setRadius(80.0);
                        yoshi.setEffect(null);
                        avatar = "yoshi";
                        avatarFullBody.setImage(YOSHI_FULL);
                        avatarFullBody.setFitHeight(300);
                        break;
                    }
                case "error":
                    break;
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
    private void onEnter(KeyEvent event) {
        if (!avatar.equals("error")) {
            submit();
        }
    }*/

    private void submitToMap(MouseEvent event) {
        // TODO: Send username and avatar back to the server for storage
        MuckController.constructor(event, uname, avatar);
        //AvatarController.avatarCreation(event, "Test");
        }

    private void submitToDashboard(MouseEvent event) {
        // TODO: Send username and avatar back to server for storage
        PlayerDashboardController.playerDashboard(uname, event, avatar);
    }

    private void hoverEvent(Text alertBox, String message) {
        alertBox.setText(message);
    }


    //MOVE THESE TO TOP WHEN REST OF CODE IS DONE
    /**
     Returns an image object of the full bodied Avatar image.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getFullAvatar(String avatarID) {

        switch (avatarID) {
            case "peach":
                return PEACH_FULL;
            case "batman":
                return BATMAN_FULL;
            case "pikachu":
                return PIKACHU_FULL;
            case "skeleton":
                return SKELETON_FULL;
            case "wonderWoman":
                return WONDER_WOMAN_FULL;
            case "yoshi":
                return YOSHI_FULL;
            default:
                return ERROR;
        }
    }

    /**
     Returns an image object of the portrait of their avatar.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getPortrait(String avatarID) {
        switch (avatarID) {
            case "peach":
                return PEACH_PORTRAIT;
            case "batman":
                return BATMAN_PORTRAIT;
            case "pikachu":
                return PIKACHU_PORTRAIT;
            case "skeleton":
                return SKELETON_PORTRAIT;
            case "wonderWoman":
                return WONDER_WOMAN_PORTRAIT;
            case "yoshi":
                return YOSHI_PORTRAIT;
            default:
                return ERROR;
        }
    }

    public static Image getSprite(String avatarID) {
        switch (avatarID) {
            case "peach":
                return PEACH_SPRITE;
            case "batman":
                return BATMAN_SPRITE;
            case "pikachu":
                return PIKACHU_SPRITE;
            case "skeleton":
                return SKELETON_SPRITE;
            case "wonderWoman":
                return WONDER_WOMAN_SPRITE;
            case "yoshi":
                return YOSHI_SPRITE;
            default:
                return ERROR;
        }
    }

    // For testing purposes
    public static String getUserName() {
        return uname;
    }
    public static String getAvatarId() { return avatar;}
    public static void setMuck(int points) { muckPoints = points;}

    // The below code is for formatting the changes to the avatar dashboard.

    private void restorePortraitSize() {
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

    private void centreImage() { //TODO: This is not centring vertically???
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