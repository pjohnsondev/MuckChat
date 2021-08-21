package muck.client;

import javax.swing.*;

/*
This class stores and triggers achievements when specific in-game requirements are met.
*/

public class Achievements {

    // List of achievements that can be earned in-game
    // TODO: NEED TO ADD ACHIEVEMENTS HERE
    public boolean achievement1 = false;
    public String achievement1Title = "The Wanderer";
    public String achievement1Description = "Player has navigated the map.";

    public boolean achievement2 = false;
    public String achievement2Title = "Smooth Talker";
    public String achievement2Description = "Player has sent a message on chat.";

    public boolean achievement3 = false;
    public String achievement3Title = "Winner Winner Chicken Dinner";
    public String achievement3Description = "Player has won a game";

    public boolean achievement4 = false;
    public String achievement4Title = "";
    public String achievement4Description = "";





    //
    public void achievementUnlock(boolean achievementName) {
        if (achievementName == false) {
            achievementName = true;
        }
    }

    public void achievementQuery(boolean achievementName) {
        if (achievementName == true) {
            System.out.print("The achievement " + achievementName + " has already been unlocked!");
        }
        else if (achievementName == false) {
            System.out.print("The achievement " + achievementName + " has not yet been unlocked!");
        }
    }
}
