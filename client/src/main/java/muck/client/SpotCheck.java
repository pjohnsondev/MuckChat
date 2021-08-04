package muck.client;

import Dice.*;

public class Spot{

    /**
     * returns the odds of spot check of an action passing
     * @return Boolean of fail(1) or pass(2-5)
     */

    public int Check(boolean gate){

        private int chance;
        private boolean gate;

        Dice Dice = new Dice(5);
        chance = Dice.Roll(1);

        if (chance = 1){ gate = false;}
        else {gate = true;}

        return gate;
    }

    /**
     * returns the severity of the attack IE glancing, standard, Critical
     * @return float of the damage modifiers to be multiplied by (int str mind?)
     */

    public int Damage(int degree){

        private int mod;
        private float degree;

        Dice Dice = new Dice(5);
        mod = Dice.Roll(1);

        if (mod = 1){ degree = 0.50; }
        if (mod = 2){ degree = 0.75; }
        if (mod = 3){ degree = 1.00; }
        if (mod = 4){ degree = 1.25; }
        if (mod = 5){ degree = 1.50; }

        return degree;
    }
    
}