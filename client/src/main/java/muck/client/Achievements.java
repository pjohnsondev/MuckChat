package muck.client;

import javax.swing.*;

// This class stores and triggers achievements when specific in-game requirements from other classes are met.

public class Achievements {

    // List of achievements that can be earned in-game
    // TODO: NEED TO ADD ACHIEVEMENTS HERE
    // TODO: NEED TO CODE IN THE ACHIEVEMENT TRIGGERS TO OTHER THE CLASSES

    public boolean achievement1 = false;
    public String achievement1Title = "The Wanderer";
    public String achievement1Description = "Player has navigated the map";

    public boolean achievement2 = false;
    public String achievement2Title = "Smooth Talker";
    public String achievement2Description = "Player has sent a message on chat";

    public boolean achievement3 = false;
    public String achievement3Title = "Winner Winner Chicken Dinner";
    public String achievement3Description = "Player has won a game";

    public boolean achievement4 = false;
    public String achievement4Title = "";
    public String achievement4Description = "";


    /**
     * achievementUnlock Flips achievement boolean from false to true and then displays the achievement notification
     * @param achievementName Name of the boolean being converted to true
     * @param achievementTitle Title of the achievement
     * @param achievementDescription Description of the achievement
     */
    public void achievementUnlock(boolean achievementName, String achievementTitle, String achievementDescription) {
        if (!achievementName) {
            achievementName = true;
            achievementNotification(achievementTitle, achievementDescription);
        }
    }


    /**
     * achievementNotification Displays a pop-up notification
     * @param achievementTitle Name of the achievement
     * @param achievementDescription Description for the achievement
     */
    // TODO: NOT SURE IF THIS WORKS. NEED TO TEST.
    public static void achievementNotification(String achievementTitle, String achievementDescription) {
        JOptionPane.showMessageDialog(null, "Achievement Unlocked! \n" + achievementTitle + ": " + achievementDescription);
    }
}
