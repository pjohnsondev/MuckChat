package muck.client.utilities;

import java.util.Random;

/**
 * This class contains two arrays and a function.
 * One array has adjectives and the other array has nouns.
 * contains the function generateName().
 */
public class RandomNameGenerator { 

    private static String[] Adjectives = {"Ancient", "Bashful", "Brave", "Calm", "Capable", 
    "Clever", "Crazy", "Dazzling", "Delightful", "Elegant", "Fancy", "Gentle", "Gigantic", "Happy", "Helpful", 
    "Magnificent", "Melodic", "Minature", "Polite", "Silly", "Super", "Thrilling", "Witty", "Wonderful"};

    private static String[] Nouns = {"Centaur", "Dinosour", "Dwarf", "Elf", "Fairy", "Flower", "Fox", 
    "Gnome", "Goblin", "Halfling", "Horse", "King", "Monkey", "Orc", "Owl", "Parrot", "Penguin", "Planet", "Queen", 
    "Rabbit", "Racecar", "Ship", "Turtle", "Unicorn", "Zebra"};
    // putting in all the "s was extremely fun

    /**
    * returns a string made from a random adjective
    * and a random noun from two preset arrays separated by a space
    */
    public String generateName() {
        int rnd1 = new Random().nextInt(Adjectives.length);
        int rnd2 = new Random().nextInt(Nouns.length);
        return Adjectives[rnd1] + " " + Nouns[rnd2];
    }
}
