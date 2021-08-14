package muck.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
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
import javafx.application.Application;
import javafx.stage.Stage;

public class AvatarController implements Initializable  {

    // This will be the associated attributes of the user
    private static String uname;
    private static int muckPoints = 50; //Dummy value for testing purposes TODO: Remove
    private static String avatar = "error";  //Dummy value for testing purposes TODO: Remove
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
    private Circle wonderWoman;

    @FXML
    private Circle yoshi;

    // IMAGE INITIALISATION
    // Peach
    private static final Image PEACH_FULL = new Image("/images/peach.png");
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
            if (muckPoints >= OPEN_SKELETON) {
                skeleton.setFill(new ImagePattern(SKELETON_PORTRAIT));
                skeleton.setCursor(Cursor.HAND);
            } else {
                skeleton.setFill(new ImagePattern(UNAVAILABLE));
            }

            if (muckPoints >= OPEN_WW) {
                wonderWoman.setFill(new ImagePattern(WONDER_WOMAN_PORTRAIT));
                wonderWoman.setCursor(Cursor.HAND);
            } else {
                wonderWoman.setFill(new ImagePattern(UNAVAILABLE));
            }

            if (muckPoints >= OPEN_YOSHI) {
                yoshi.setFill(new ImagePattern(YOSHI_PORTRAIT));
                yoshi.setCursor(Cursor.HAND);
            } else {
                yoshi.setFill(new ImagePattern(UNAVAILABLE));
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
            submit(event);
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
            // TODO: Need to call the database for current avatar and muck point values
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
    private void onEnter(ActionEvent event) {
        if (!avatar.equals("error")) {
            submit();
        }
    }*/

    public void submit(MouseEvent event) {
        // TODO: Send username and avatar back to the server for storage
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/MuckWindow.fxml"));
                Scene dashboard=new Scene(root);
                //This line gets the Stage Information
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(dashboard);
                //MuckController.constructor(event, uname, avatar);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public String getUserName() {
        return uname;
    }

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