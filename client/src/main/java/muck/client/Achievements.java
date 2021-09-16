package muck.client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;


// This class stores and triggers achievements when specific in-game requirements from other classes are met.

public class Achievements {
    boolean achievementName;
    String achievementTitle;
    String achievementDescription;

    // List of achievements that can be earned in-game
    // TODO: NEED TO ADD ACHIEVEMENTS HERE
    // TODO: NEED TO CODE IN THE ACHIEVEMENT TRIGGERS TO OTHER THE CLASSES

    public boolean achievement1 = false;
    public String achievement1Title = "Hotel California";
    public String achievement1Description = "Player has visited the Inn";

    public boolean achievement2 = false;
    public String achievement2Title = "Retail Therapy";
    public String achievement2Description = "Player has visited the Shops";

    public boolean achievement3 = false;
    public String achievement3Title = "Monkey See, Monkey Do";
    public String achievement3Description = "Player has 'climbed' the tree";

    public boolean achievement4 = false;
    public String achievement4Title = "The Explorer";
    public String achievement4Description = "Player has visited the cave";

    public boolean achievement5 = false;
    public String achievement5Title = "Don't look down";
    public String achievement5Description = "Player has walked the bridge";

    public boolean achievement6 = false;
    public String achievement6Title = "Smooth Talker";
    public String achievement6Description = "Player has sent a message on chat";

    public boolean achievement7 = false;
    public String achievement7Title = "Alien Exterminator";
    public String achievement7Description = "Player has won a game of Space Invaders";

    public boolean achievement8 = false;
    public String achievement8Title = "Winner Winner Chicken Dinner";
    public String achievement8Description = "Player has won a card game";

    public boolean achievement9 = false;
    public String achievement9Title = "I'm A Real Boy";
    public String achievement9Description = "Player has selected an avatar";

    public boolean achievement10 = false;
    public String achievement10Title = "The Skeleton King";
    public String achievement10Description = "Player has unlocked Skeleton";

    public boolean achievement11 = false;
    public String achievement11Title = "Wonderful!";
    public String achievement11Description = "Player has unlocked Wonder Woman";

    public boolean achievement12 = false;
    public String achievement12Title = "Better Than Luigi";
    public String achievement12Description = "Player has unlocked Yoshi";




    public Achievements(boolean aName, String aTitle, String aDescription) {
        this.achievementName = aName;
        this.achievementTitle = aTitle;
        this.achievementDescription = aDescription;
    }


    /**
     * achievementUnlock Flips achievement boolean from false to true and then displays the achievement notification
     */
    public void achievementUnlock(Achievements achievement) {
        if (!this.achievementName) {
            this.achievementName = true;
            achievementPopUp(achievement);
        }
    }


    public void achievementPopUp(Achievements achievement) {
        Stage window = new Stage();
        window.setTitle("Achievement Unlocked!");

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/AchievementWindow.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Achievement Notification");
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
