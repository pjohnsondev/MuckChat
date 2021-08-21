import javax.swing.*;

/*
This class stores and triggers achievements when specific in-game requirements are met.
*/

public class Achievements {

    // List of achievements that can be earned in-game
    // NEED TO ADD ACHIEVEMENTS HERE
    public boolean achievement1 = false;
    public String achievement1Title = "Achievement 1";
    public String achievement1Description = "This is the description for Achievement 1";



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
