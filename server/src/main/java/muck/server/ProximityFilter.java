package muck.server;

//import muck.server.CharacterLocationTracker;

//import muck.core.ClientId;

import java.lang.Math;

/**
 * determines the location of the user who sent the command, determines
 * the locations of other users within the app and determines which user(s) are closest,
 * then passes this data to the interaction controller
 */
public class ProximityFilter {
    //CharacterLocationTracker<ClientId> CLT = new CharacterLocationTracker<ClientId>();
    
    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }


}

/*
package muck.client.utilities;

import java.util.Random;

public class RandomNameGenerator { 

    private static String[] Adjectives = {"Ancient", "Bashful", "Brave", "Calm", "Capable", 
    "Clever", "Crazy", "Dazzling", "Delightful", "Elegant", "Fancy", "Gentle", "Gigantic", "Happy", "Helpful", 
    "Magnificent", "Melodic", "Minature", "Polite", "Silly", "Super", "Thrilling", "Witty", "Wonderful"};

    private static String[] Nouns = {"Centaur", "Dinosour", "Dwarf", "Elf", "Fairy", "Flower", "Fox", 
    "Gnome", "Goblin", "Halfling", "Horse", "King", "Monkey", "Orc", "Owl", "Parrot", "Penguin", "Planet", "Queen", 
    "Rabbit", "Racecar", "Ship", "Turtle", "Unicorn", "Zebra"};
    // putting in all the "s was extremely fun

    public String generateName() {
        int rnd1 = new Random().nextInt(Adjectives.length);
        int rnd2 = new Random().nextInt(Nouns.length);
        return Adjectives[rnd1] + " " + Nouns[rnd2];
    }
}
*/