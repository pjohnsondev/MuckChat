package muck.client;

import javax.swing.*;

// This class stores and triggers achievements when specific in-game requirements from other classes are met.

public class Achievements {

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
