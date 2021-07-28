package muck.client;

import java.util.Random;

public class GenRandom  {
    public static void main(String[] args){
        int min = 1;
        int max = 10;

        Random random = new Random();

        int spotCheck = random.nextInt(max + min) + min;
        /* spotCheck is a random variable [1-10] for our dice rolls */
    }
}