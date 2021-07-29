package muck.client;

import java.util.Random;

public class GenRandom  {


    /**
     * returns a random dice roll between min and max (inclusive)
     * @param min smallest possible dice roll
     * @param max largest possible dice roll
     * @return random int (min,max)
     */
    public int GetRandomDice(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    //is the above function GetRandomDice what this is meant to be doing?
    public static void main(String[] args){
        int min = 1;
        int max = 10;

        Random random = new Random();

        int spotCheck = random.nextInt(max + min) + min;
        /* spotCheck is a random variable [1-10] for our dice rolls */

    }
}