package muck.client;

import Dice.*;

public class Spot{

    /**
     * returns the odds of spot check of an action passing
     * @return Boolean of fail(1) or pass(2-10)
     */

    public boolean Check(boolean set){

        boolean gate = false;

        Dice Dice = new Dice(10);
        int chance = Dice.Roll(1);

        if (chance = 1){ gate = false;}
        else {gate = true;}

        return gate;
    }

    /**
     * returns the severity of the attack IE glancing, standard, Critical
     * @return double of the damage modifiers to be multiplied by (int str mind?)
     */

    public double Damage(double degree){

        double degree = 0.0;

        Dice Dice = new Dice(5);
        int mod = Dice.Roll(1);

        if (mod == 1){ degree = 0.50; }
        if (mod == 2){ degree = 0.75; }
        if (mod == 3){ degree = 1.00; }
        if (mod == 4){ degree = 1.25; }
        if (mod == 5){ degree = 1.50; }

        return degree;
    }
    
}