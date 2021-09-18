package muck.client;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;


// This class stores and triggers achievements when specific in-game requirements from other classes are met.

public class Achievements {
    boolean achievementName;
    String achievementTitle;
    String achievementDescription;

    // List of achievements that can be earned in-game
    // TODO: NEED TO ADD ACHIEVEMENTS HERE
    // TODO: NEED TO CODE IN THE ACHIEVEMENT TRIGGERS TO OTHER THE CLASSES


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
    public static final String achievement4Title = "The Explorer";
    public static final String achievement4Description = "Player has visited the cave";

    public static boolean achievement5 = false;
    public static final String achievement5Title = "Would you like some pie?";
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






    public Achievements(boolean aName, String aTitle, String aDescription) {
        this.achievementName = aName;
        this.achievementTitle = aTitle;
        this.achievementDescription = aDescription;
    }


    /**
     * achievementUnlock Flips achievement boolean from false to true and then displays the achievement notification
     */
    public void achievementUnlock(Achievements achievement) {
        if (this.achievementName == false) {
            this.achievementName = true;
            achievementPopUp(achievement);
        }
    }


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
            window.toFront();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    public static void createAchievements() {
        Achievements achieve1 = new Achievements(achievement1, achievement1Title, achievement1Description);
        Achievements achieve2 = new Achievements(achievement2, achievement2Title, achievement2Description);
        Achievements achieve3 = new Achievements(achievement3, achievement3Title, achievement3Description);
        Achievements achieve4 = new Achievements(achievement4, achievement4Title, achievement4Description);
        Achievements achieve5 = new Achievements(achievement5, achievement5Title, achievement5Description);
        Achievements achieve6 = new Achievements(achievement6, achievement6Title, achievement6Description);
        Achievements achieve7 = new Achievements(achievement7, achievement7Title, achievement7Description);
        Achievements achieve8 = new Achievements(achievement8, achievement8Title, achievement8Description);
        Achievements achieve9 = new Achievements(achievement9, achievement9Title, achievement9Description);
        Achievements achieve10 = new Achievements(achievement10, achievement10Title, achievement10Description);
        Achievements achieve11 = new Achievements(achievement11, achievement11Title, achievement11Description);
        Achievements achieve12 = new Achievements(achievement12, achievement12Title, achievement12Description);
    }


    public String getAchievementTitle(Achievements achievement) {
        return achievement.achievementTitle;
    }


    public String getAchievementDescription(Achievements achievement) {
        return achievement.achievementDescription;
    }
 */

}
