package muck.client;

import java.util.Random;

public class Dice  {

    int sides;
    //constructor to make a dice with 's' sides
    public Dice(int s){
        sides = s;
    }

    /**
     * returns the sum of an input amount of rolls
     * @param numberOfRolls amount of times to roll
     * @return sum of total rolls
     */
    public int Roll(int numberOfRolls){
        Random random = new Random();
        int sum = 0;
        for(int i = 0; i < numberOfRolls; i++){
            sum += random.nextInt(sides - 1) + 1;
        }
        return sum;
    }

    //is the above code what this is meant to be doing?
//    public static void main(String[] args){
//        int min = 1;
//        int max = 10;
//
//        Random random = new Random();
//
//        int spotCheck = random.nextInt(max + min) + min;
//        /* spotCheck is a random variable [1-10] for our dice rolls */
//
//    }
}