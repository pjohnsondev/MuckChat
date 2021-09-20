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
    boolean achievementName;
    String achievementTitle;
    String achievementDescription;

    // List of achievements that can be earned in-game
    public static boolean achievement1 = false;
    public static final String achievement1Title = "Hotel California";
    public static final String achievement1Description = "Player has visited the Inn";

    public static boolean achievement2 = false;
    public static final String achievement2Title = "Retail Therapy";
    public static final String achievement2Description = "Player has visited the Shop";

    public static boolean achievement3 = false;
    public static final String achievement3Title = "Hide your kids, Hide your wife";
    public static final String achievement3Description = "Player has invaded a home";

    public static boolean achievement4 = false;
    public static final String achievement4Title = "Indiana Stones";
    public static final String achievement4Description = "Player has visited the cave";

    public static boolean achievement5 = false;
    public static final String achievement5Title = "Would You Like Some Pie?";
    public static final String achievement5Description = "Player has entered the cottage";

    public static boolean achievement6 = false;
    public static final String achievement6Title = "I'm A Real Boy";
    public static final String achievement6Description = "Player has selected an avatar";

    public static boolean achievement7 = false;
    public static final String achievement7Title = "Indecisive";
    public static final String achievement7Description = "Player has selected another avatar";

    public static boolean achievement8 = false;
    public static final String achievement8Title = "The Skeleton King";
    public static final String achievement8Description = "Player has unlocked Skeleton";

    public static boolean achievement9 = false;
    public static final String achievement9Title = "Wonderful!";
    public static final String achievement9Description = "Player has unlocked Wonder Woman";

    public static boolean achievement10 = false;
    public static final String achievement10Title = "Better Than Luigi";
    public static final String achievement10Description = "Player has unlocked Yoshi";

    public static boolean achievement11 = false;
    public static final String achievement11Title = "Winner Winner Chicken Dinner";
    public static final String achievement11Description = "Player has won a game of Frogger";

    public static boolean achievement12 = false;
    public static final String achievement12Title = "Alien Exterminator";
    public static final String achievement12Description = "Player has won a game of Space Invaders";

    public static boolean achievement13 = false;
    public static final String achievement13Title = "All skill, No luck";
    public static final String achievement13Description = "Player has won a game of Tick-Tac-Toe";


    /**
     * Constructor to create an achievement object
     * @param aName The boolean status for the achievement (False = locked, True = unlocked)
     * @param aTitle The achievement title
     * @param aDescription The achievement description
     */
    public Achievements(boolean aName, String aTitle, String aDescription) {
        this.achievementName = aName;
        this.achievementTitle = aTitle;
        this.achievementDescription = aDescription;
    }


    /**
     * achievementUnlock Unlocks the achievement if it has not yet been unlocked and
     * then displays the achievement notification.
     * The achievement is then displayed on the achievement tab within the Player Dashboard
     * @param achievement The achievement object being triggered
     */
    public void achievementUnlock(Achievements achievement) {
        if (!this.achievementName) {
            this.achievementName = true;
            achievementPopUp(achievement);
            PlayerDashboardController.addAchievements(this.achievementTitle, this.achievementDescription);
        }
    }


    /**
     * achievementPopUp Displays a pop-up notification that notifies the player of
     * the achievement that was earned.
     * A notification sound is played as the achievement appears
     * @param achievement The achievement object being triggered
     */
    public void achievementPopUp(Achievements achievement) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/AchievementWindow.fxml"));
            Stage window = new Stage(StageStyle.DECORATED);
            window.setTitle("Achievement Window");
            Label titleLabel = (Label) parent.lookup("#achievementTitleLabel");
            titleLabel.setText(achievement.achievementTitle);
            Label descriptionLabel = (Label) parent.lookup("#achievementDescriptionLabel");
            descriptionLabel.setText(achievement.achievementDescription);
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
        Sound soundObject = new Sound("/sounds/tilegame.mp3");
        soundObject.music(soundObject);
    }

}
