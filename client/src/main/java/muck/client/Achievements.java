package muck.client;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;


/**
 * The Achievements class creates achievement objects that trigger a pop-up notification
 * when specific in-game actions or milestones are met.
 */
public class Achievements {
    boolean achievementStatus;
    String achievementTitle;
    String achievementDescription;

    // List of achievements that can be earned in-game
    public static Achievements achievement1_instance = null;
    public static boolean achievement1 = false;
    public static final String ACHIEVEMENT1TITLE = "Hotel California";
    public static final String ACHIEVEMENT1DESCRIPTION = "Player has visited the Inn";

    public static Achievements achievement2_instance = null;
    public static boolean achievement2 = false;
    public static final String ACHIEVEMENT2TITLE = "Retail Therapy";
    public static final String ACHIEVEMENT2DESCRIPTION = "Player has visited the Shop";

    public static Achievements achievement3_instance = null;
    public static boolean achievement3 = false;
    public static final String ACHIEVEMENT3TITLE = "Hide your kids, Hide your wife";
    public static final String ACHIEVEMENT3DESCRIPTION = "Player has invaded a home";

    public static Achievements achievement4_instance = null;
    public static boolean achievement4 = false;
    public static final String ACHIEVEMENT4TITLE = "Indiana Stones";
    public static final String ACHIEVEMENT4DESCRIPTION = "Player has visited the cave";

    public static Achievements achievement5_instance = null;
    public static boolean achievement5 = false;
    public static final String ACHIEVEMENT5TITLE = "Would You Like Some Pie?";
    public static final String ACHIEVEMENT5DESCRIPTION = "Player has entered the cottage";

    public static Achievements achievement6_instance = null;
    public static boolean achievement6 = false;
    public static final String ACHIEVEMENT6TITLE = "I'm A Real Boy";
    public static final String ACHIEVEMENT6DESCRIPTION = "Player has selected an avatar";

    public static Achievements achievement7_instance = null;
    public static boolean achievement7 = false;
    public static final String ACHIEVEMENT7TITLE = "Indecisive";
    public static final String ACHIEVEMENT7DESCRIPTION = "Player has selected another avatar";

    public static Achievements achievement8_instance = null;
    public static boolean achievement8 = false;
    public static final String ACHIEVEMENT8TITLE = "The Skeleton King";
    public static final String ACHIEVEMENT8DESCRIPTION = "Player has unlocked Skeleton";

    public static Achievements achievement9_instance = null;
    public static boolean achievement9 = false;
    public static final String ACHIEVEMENT9TITLE = "Wonderful!";
    public static final String ACHIEVEMENT9DESCRIPTION = "Player has unlocked Wonder Woman";

    public static Achievements achievement10_instance = null;
    public static boolean achievement10 = false;
    public static final String ACHIEVEMENT10TITLE = "Better Than Luigi";
    public static final String ACHIEVEMENT10DESCRIPTION = "Player has unlocked Yoshi";

    public static Achievements achievement11_instance = null;
    public static boolean achievement11 = false;
    public static final String ACHIEVEMENT11TITLE = "Winner Winner Chicken Dinner";
    public static final String ACHIEVEMENT11DESCRIPTION = "Player has won a game of Frogger";

    public static Achievements achievement12_instance = null;
    public static boolean achievement12 = false;
    public static final String ACHIEVEMENT12TITLE = "Alien Exterminator";
    public static final String ACHIEVEMENT12DESCRIPTION = "Player has won a game of Space Invaders";

    public static Achievements achievement13_instance = null;
    public static boolean achievement13 = false;
    public static final String ACHIEVEMENT13TITLE = "All skill, No luck";
    public static final String ACHIEVEMENT13DESCRIPTION = "Player has won a game of Tick-Tac-Toe";


    /**
     * Constructor to create an achievement object
     * @param aStatus The boolean status for the achievement (False = locked, True = unlocked)
     * @param aTitle The achievement title
     * @param aDescription The achievement description
     */
    public Achievements(boolean aStatus, String aTitle, String aDescription) {
        this.achievementStatus = aStatus;
        this.achievementTitle = aTitle;
        this.achievementDescription = aDescription;
    }


    /**
     * achievementUnlock Unlocks the achievement if it has not yet been unlocked.
     * The achievement is then displayed on the achievement tab within the Player Dashboard
     */
    public void achievementUnlock() {
        if (!this.achievementStatus) {
            this.achievementStatus = true;
            PlayerDashboardController.addAchievements(this.achievementTitle, this.achievementDescription);
        }
    }


    /**
     * achievementPopUp Displays a pop-up notification that notifies the player of
     * the achievement that was earned.
     * A notification sound is played as the achievement appears
     */
    public void achievementPopUp() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/AchievementWindow.fxml"));
            Stage window = new Stage(StageStyle.DECORATED);
            window.setTitle("Achievement Window");
            Label titleLabel = (Label) parent.lookup("#achievementTitleLabel");
            titleLabel.setText(this.achievementTitle);
            Label descriptionLabel = (Label) parent.lookup("#achievementDescriptionLabel");
            descriptionLabel.setText(this.achievementDescription);
            window.setScene(new Scene(parent));
            window.show();
            window.setAlwaysOnTop(true);
            achievementSounds();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * achievementSounds plays a ringing sound. Used in the achievementPopUp method
     * as an achievement notification appears
     */
    public void achievementSounds() {
        Sound achievementSound = new Sound("/sounds/tilegame.mp3");
        achievementSound.music();
    }

}
