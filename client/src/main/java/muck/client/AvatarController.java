package muck.client;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import muck.client.components.ActiveUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static muck.client.Achievements.*;

public class AvatarController implements Initializable  {

    private static final Logger LOGGER = LogManager.getLogger(AvatarController.class);

    // These will be the associated attributes of the user
    private static String uname;//Will be updated when constructing AvatarController
    private static String displayName; //Will be updated when constructing AvatarController
    private static int muckPoints = 0; //Default value. PlayerDashboard will pass in applicable muck value for avatar change
    private static String avatar = "notSet"; //Default. No image.
    private static String previous = "login"; //Previous screen. Will determine where the submit button leads.
    private final int OPEN_SKELETON = 20; // Muck points required to activate skeleton avatar
    private final int OPEN_WW = 30; // Muck points required to activate Wonder Woman avatar
    private final int OPEN_YOSHI = 50; // Muck points required to activate Yoshi avatar

    @FXML
    private GridPane gridPane;

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
    // NB:
    // Peach
    private static Image peach_full;// = new Image("/images/peach.png");
    private static Image peach_portrait;// = new Image("/images/peach-portrait.png");
    private static Image peach_sprite;// = new Image("/images/peachSprite.png");

    // Batman
    private static Image batman_full;// = new Image("/images/batman.png");
    private static Image batman_portrait;// = new Image("/images/batman-portrait.png");
    private static Image batman_sprite;// = new Image("/images/batmanSprite.png");

    // Pikachu
    private static Image pikachu_full;// = new Image("/images/pikachu.png");
    private static Image pikachu_portrait;// = new Image("/images/pikachu-portrait.png");
    private static Image pikachu_sprite;// = new Image("/images/pikachuSprite.png");

    // Skeleton
    private static Image skeleton_full;// = new Image("/images/skeleton.png");
    private static Image skeleton_portrait;// = new Image("/images/skeleton-portrait.png");
    private static Image skeleton_sprite;// = new Image("/images/skeletonSprite.png");

    // Wonder Woman
    private static Image wonder_woman_full;// = new Image("/images/wonderWoman.png");
    private static Image wonder_woman_portrait;// = new Image("/images/wonderWoman-portrait.png");
    private static Image wonder_woman_sprite;// = new Image("/images/wonderWomanSprite.png");

    // Yoshi
    private static Image yoshi_full;// = new Image("/images/yoshi.png");
    private static Image yoshi_portrait;// = new Image("/images/yoshi-portrait.png");
    private static Image yoshi_sprite;// = new Image("/images/yoshiSprite.png");


    // Default
    private static Image error;// = new Image("/images/error.png");
    private Image unavailable;// = new Image("/images/Unknown.png");
    private BackgroundImage background;// = new BackgroundImage(new Image("/images/BackgroundAvSelection.png"), null, null, null, null);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //NB: Moved image initialisation in here due to initialisation errors in a few tests
        // Peach
        peach_full = new Image("/images/peach.png");
        peach_portrait = new Image("/images/peach-portrait.png");
        peach_sprite = new Image("/images/peachSprite.png");

        // Batman
        batman_full = new Image("/images/batman.png");
        batman_portrait = new Image("/images/batman-portrait.png");
        batman_sprite = new Image("/images/batmanSprite.png");

        // Pikachu
        pikachu_full = new Image("/images/pikachu.png");
        pikachu_portrait = new Image("/images/pikachu-portrait.png");
        pikachu_sprite = new Image("/images/pikachuSprite.png");

        // Skeleton
        skeleton_full = new Image("/images/skeleton.png");
        skeleton_portrait = new Image("/images/skeleton-portrait.png");
        skeleton_sprite = new Image("/images/skeletonSprite.png");

        // Wonder Woman
        wonder_woman_full = new Image("/images/wonderWoman.png");
        wonder_woman_portrait = new Image("/images/wonderWoman-portrait.png");
        wonder_woman_sprite = new Image("/images/wonderWomanSprite.png");

        // Yoshi
        yoshi_full = new Image("/images/yoshi.png");
        yoshi_portrait = new Image("/images/yoshi-portrait.png");
        yoshi_sprite = new Image("/images/yoshiSprite.png");

        // Default
        error = new Image("/images/error.png");
        unavailable = new Image("/images/Unknown.png");
        background = new BackgroundImage(new Image("/images/BackgroundAvSelection.png"), null, null, null, null);

        try {
            gridPane.setBackground(new Background(background));
        } catch (Exception e) {
            gridPane.setStyle("-fx-background-color: steelgray");
            LOGGER.error("Error with background image");
            e.printStackTrace();
        }

        try {
            avatarFullBody.setPreserveRatio(true);

            peach.setFill(new ImagePattern(peach_portrait));
            batman.setFill(new ImagePattern(batman_portrait));
            pikachu.setFill(new ImagePattern(pikachu_portrait));

            // The below three avatars are only available once the user achieves a certain number of muck points
            lockedAvatars(OPEN_SKELETON, skeleton, skeleton_portrait, skeletonAlert, "skeleton");
            lockedAvatars(OPEN_WW, wonderWoman, wonder_woman_portrait, wonderWomanAlert, "wonderWoman");
            lockedAvatars(OPEN_YOSHI, yoshi, yoshi_portrait, yoshiAlert, "yoshi");

            username.setText(displayName);

            submit.addEventHandler(MouseEvent.MOUSE_CLICKED, this::submit);
            peach.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selection("peach"));
            batman.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selection("batman"));
            pikachu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selection("pikachu"));

            // If there is already an avatar associated with a user, display the avatar
            // Will be used in the case of an avatar change

            try {
                if (!avatar.equals("notSet")) {
                    selection(avatar);
                }
            } catch (NullPointerException e) {
                LOGGER.error("Avatar is null");
            }
        } catch (Exception e) {
            LOGGER.error("Error with image initialisation");
            e.printStackTrace();
        }
    }

    /**
     * AvatarCreation method
     * From the user creation screen
     * This function will be used to access and initialise the Avatar Selection screen from another window.
     * @param event: The mouse event that has prompted the opening of the window.
     * @param username: The username of the current player
     * @param display: The display name of the current player
     */
    public static void avatarCreation(MouseEvent event, String username, String display) {
        previous = "login";
        uname = username;
        displayName = display;
        avatar = "notSet";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(AvatarController.class.getResource("/fxml/Avatar.fxml")));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(Objects.requireNonNull(AvatarController.class.getResource("/css/style.css")).toExternalForm());
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            LOGGER.error("Unable to open avatar window");
            ex.printStackTrace();
        }
    }

    /**
     * Overloaded AvatarCreation method
     * From the player dashboard screen
     * This is used purely to set the variables for the avatar creation screen.  The window
     * is launched from the PlayerDashboardController
     * @param username: The player's username
     * @param display: The player's display name
     * @param avID: The player's avatarID
     * @param muck: The player's current muck point total
     */
    public static void avatarCreation(String username, String display, String avID, int muck){
        previous = "playerDashboard";
        uname = username;
        displayName = display;
        avatar = avID;
        muckPoints = muck;
    }

    /**
     * For testing purposes.  Allows the MuckPoints to be updated to check unlocked avatars during the testing process
     * @param username: The player's username
     * @param display: The player's display name
     * @param avID: The player's avatarID
     */
    public static void avatarCreation(String username, String display, String avID){
        previous = "playerDashboard";
        uname = username;
        displayName = display;
        avatar = avID;
    }

    //TODO: Remove this method once the SignIn Screen sends the window to Muck
    public static void avatarCreation(MouseEvent event, String username) {
        ActiveUser player = ActiveUser.getInstance();
        displayName = player.getUser().displayName;
        previous = "login";
        uname = username;
        avatar = "notSet";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(AvatarController.class.getResource("/fxml/Avatar.fxml")));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(Objects.requireNonNull(AvatarController.class.getResource("/css/style.css")).toExternalForm());
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            App.hideStage();
            stage.show();
        } catch (IOException ex) {
            LOGGER.error("Unable to open avatar window");
            ex.printStackTrace();
        }
    }

    /**
     Returns an image object of the full bodied Avatar image.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getFullAvatar(String avatarID) {

        switch (avatarID) {
            case "peach":
                return peach_full;
            case "batman":
                return batman_full;
            case "pikachu":
                return pikachu_full;
            case "skeleton":
                return skeleton_full;
            case "wonderWoman":
                return wonder_woman_full;
            case "yoshi":
                return yoshi_full;
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
                return peach_portrait;
            case "batman":
                return batman_portrait;
            case "pikachu":
                return pikachu_portrait;
            case "skeleton":
                return skeleton_portrait;
            case "wonderWoman":
                return wonder_woman_portrait;
            case "yoshi":
                return yoshi_portrait;
            default:
                return error;
        }
    }

    /**
     Returns an image object of the sprite sheet of their avatar.
     @param: avatarID. This will be passed into the method from the server
     */
    public static Image getSprite(String avatarID) {
        switch (avatarID) {
            case "peach":
                return peach_sprite;
            case "batman":
                return batman_sprite;
            case "pikachu":
                return pikachu_sprite;
            case "skeleton":
                return skeleton_sprite;
            case "wonderWoman":
                return wonder_woman_sprite;
            case "yoshi":
                return yoshi_sprite;
            default:
                return error;
        }
    }

    /**
     * Get method for AvatarID
     * @return The avatarID
     */
    public static String getAvatarId() { return avatar;}

    /**
     * LockedAvatars method.
     * Updated the appearance and actions associated with a locked Avatar depending on MuckPoint value
     * @param open_muck: the MuckPoint total at which the avatar becomes unlocked
     * @param portrait: the circle object within which each avatar portrait is displayed
     * @param avatarPortrait: the portrait image of the avatar
     * @param avatarAlert: the Text object within which the MuckPoint total required to unlock the avatar is displayed
     * @param avatarID: the current player's avatar ID
     */
    private void lockedAvatars(int open_muck, Circle portrait, Image avatarPortrait, Text avatarAlert, String avatarID) {
        if (muckPoints >= open_muck) {
            portrait.setFill(new ImagePattern(avatarPortrait));
            portrait.setCursor(Cursor.HAND);
            portrait.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> selection(avatarID));
        } else {
            portrait.setFill(new ImagePattern(unavailable));
            portrait.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> hoverEvent(avatarAlert, ("Earn " + open_muck + " MuckPoints to unlock!")));
            portrait.addEventHandler(MouseEvent.MOUSE_EXITED, event -> hoverEvent(avatarAlert, ""));
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
                    peach.setRadius(65.0);
                    peach.setEffect(null);
                    avatar = "peach";
                    avatarFullBody.setImage(peach_full);
                    avatarFullBody.setLayoutY(90.0);
                    avatarFullBody.setFitHeight(390);
                    break;
                case "batman":
                    batman.setRadius(65.0);
                    batman.setEffect(null);
                    avatar = "batman";
                    avatarFullBody.setImage(batman_full);
                    avatarFullBody.setLayoutY(40.0);
                    break;
                case "pikachu":
                    pikachu.setRadius(65.0);
                    pikachu.setEffect(null);
                    avatar = "pikachu";
                    avatarFullBody.setImage(pikachu_full);
                    avatarFullBody.setLayoutY(210.0);
                    avatarFullBody.setFitHeight(250); // Changes the height of the image so pikachu is a more realistic
                    break;
                case "skeleton":
                    if (muckPoints >= OPEN_SKELETON) {
                        skeleton.setRadius(65.0);
                        skeleton.setEffect(null);
                        avatar = "skeleton";
                        avatarFullBody.setImage(skeleton_full);
                        avatarFullBody.setLayoutY(120.0);
                        avatarFullBody.setFitHeight(410);
                        // Unlocks achievement 8 when the player selects Skeleton.
                        if (Achievements.achievement8_instance == null) {
                            Achievements.achievement8_instance = new Achievements(achievement8,
                                    ACHIEVEMENT8TITLE, ACHIEVEMENT8DESCRIPTION);
                            achievement8_instance.achievementUnlock();
                            achievement8_instance.achievementPopUp();
                        }
                        break;
                    }
                case "wonderWoman":
                    if (muckPoints >= OPEN_WW) {
                        wonderWoman.setRadius(65.0);
                        wonderWoman.setEffect(null);
                        avatar = "wonderWoman";
                        avatarFullBody.setImage(wonder_woman_full);
                        avatarFullBody.setLayoutY(70.0);
                        avatarFullBody.setFitHeight(400);
                        // Unlocks achievement 9 when the player selects Wonder Woman.
                        if (Achievements.achievement9_instance == null) {
                            Achievements.achievement9_instance = new Achievements(achievement9,
                                    ACHIEVEMENT9TITLE, ACHIEVEMENT9DESCRIPTION);
                            achievement9_instance.achievementUnlock();
                            achievement9_instance.achievementPopUp();
                        }
                        break;
                    }
                case "yoshi":
                    if (muckPoints >= OPEN_YOSHI) {
                        yoshi.setRadius(65.0);
                        yoshi.setEffect(null);
                        avatar = "yoshi";
                        avatarFullBody.setImage(yoshi_full);
                        avatarFullBody.setLayoutY(180.0);
                        avatarFullBody.setFitHeight(300);
                        // Unlocks achievement 10 when the player selects Yoshi.
                        if (Achievements.achievement10_instance == null) {
                            Achievements.achievement10_instance = new Achievements(achievement10,
                                    ACHIEVEMENT10TITLE, ACHIEVEMENT10DESCRIPTION);
                            achievement10_instance.achievementUnlock();
                            achievement10_instance.achievementPopUp();
                        }
                        break;
                    }
                case "notSet":
                    break;
                default:
                    break;
            }
            centreImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Submit event.
     * If the avatar screen was opened from the Sign Up page the submit event will send to map.
     * Otherwise if the avatar screen was opened from the Player Dashboard, the submit event will send back to the dashboard.
     * @param event: The mouse click event
     */
    private void submit(MouseEvent event) {
        try {
            if (previous.equals("playerDashboard")) { //If the user previously came from player dashboard return there
                // Unlocks achievement 7 when the player changes avatar.
                if (Achievements.achievement7_instance == null) {
                    Achievements.achievement7_instance = new Achievements(achievement7,
                            ACHIEVEMENT7TITLE, ACHIEVEMENT7DESCRIPTION);
                    achievement7_instance.achievementUnlock();
                    achievement7_instance.achievementPopUp();
                }
                submitToDashboard(event);
            } else {
                submitToMap(event); // Otherwise send them to the map
                // Unlocks achievement 6 when the player initially selects an avatar.
                if (Achievements.achievement6_instance == null) {
                    Achievements.achievement6_instance = new Achievements(achievement6,
                            ACHIEVEMENT6TITLE, ACHIEVEMENT6DESCRIPTION);
                    achievement6_instance.achievementUnlock();
                    achievement6_instance.achievementPopUp();
                }
            }
        } catch (IOException e) {
                LOGGER.error("Unable to open dashboard window");
                e.printStackTrace();
        }
    }

    /**
     * submitToMap method
     * Will be used when moving from User SignUp >> Avatar Selection Screen >> Map
     * @param event: The click event that is generated when the submit button is pressed
     */
    private void submitToMap(MouseEvent event) {
        // TODO: Send avatar back to the server for storage
        MuckController.constructor(event, uname, displayName, avatar);
        }

    /**
     * submitToDashboard method
     * Will be used when moving from Dashboard >> Avatar Selection Screen >> Dashboard
     * Will have the player dashboard take over the avatar selection screen
     * @param event: The click event that is generated when the submit button is pressed
     */
    private void submitToDashboard(MouseEvent event) throws IOException{
        // TODO: Send avatar back to server for storage
        try {
            PlayerDashboardController.playerDashboard(uname, displayName, avatar);
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/PlayerDashboard.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            scene.getStylesheets().add("/css/style.css");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("Unable to open dashboard window");
            e.printStackTrace();
        }
    }

    /**
     * Hover Event Method.
     * Will update the associated text boxes when a player hovers over a locked avatar.
     * @param alertBox: The Text element associated with a locked Avatar
     * @param message: The message to be displayed in the text element upon hover
     */
    private void hoverEvent(Text alertBox, String message) { alertBox.setText(message); }

    // ************  For testing purposes *************
    public static String getUserName() { return uname; }
    public static void setMuck(int points) { muckPoints = points; }
    // ************************************************

    // The below code is for formatting the changes to the avatar dashboard.
    private void restorePortraitSize() {
        avatarFullBody.setFitHeight(470.0);
        avatarFullBody.setLayoutY(58.0);
        peach.setRadius(60.0);
        batman.setRadius(60.0);
        pikachu.setRadius(60.0);
        skeleton.setRadius(60.0);
        wonderWoman.setRadius(60.0);
        yoshi.setRadius(60.0);
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

            double reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            avatarFullBody.setX((avatarFullBody.getFitWidth() - w) / 2);
            avatarFullBody.setY((avatarFullBody.getFitHeight() - h) / 2);

        }
    }
}