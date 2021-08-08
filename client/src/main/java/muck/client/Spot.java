package muck.client;

/**
 * Spot check interacts closely with Dice class to generate random results,
 * with consideration given to character stats.
 */

public class Spot{

    /**
     * returns the odds of spot check of an action passing
     * @return Boolean of fail(1) or pass(2-10)
     */

    public boolean Check(boolean set){

        boolean gate = false;

        Dice Dice = new Dice(10);
        int chance = Dice.Roll(1);

        if (chance == 1){ gate = false;}
        else {gate = true;}

        return gate;
    }

    /**
     * returns the severity of the attack IE glancing, standard, Critical
     * @return double of the damage modifiers to be multiplied by (int str mind?)
     */

    public double Attack(){

        double atkDegree = 0.0;

        Dice Dice = new Dice(5);
        int mod = Dice.Roll(1);

        if (mod == 1){ atkDegree = 0.50; }
        if (mod == 2){ atkDegree = 0.75; }
        if (mod == 3){ atkDegree = 1.00; }
        if (mod == 4){ atkDegree = 1.25; }
        if (mod == 5){ atkDegree = 1.50; }

        return atkDegree;
    }

    /**
     * returns the defensive multiplier of the character. Can be used to negate damage IE glancing strike is negated
     * by a full defend / parry.
     * @return double of the defence modifiers to be multiplied by (int str mind?)
     */

    public double Defend(){

        double defDegree = 0.0;

        Dice Dice = new Dice(5);
        int mod = Dice.Roll(1);

        if (mod == 1){ defDegree = 0.50; }
        if (mod == 2){ defDegree = 0.75; }
        if (mod == 3){ defDegree = 1.00; }
        if (mod == 4){ defDegree = 1.25; }
        if (mod == 5){ defDegree = 1.50; }

        return defDegree;
    }

    /**
     * Returns total damage dealt, taking in the parameters atkDegree, defDegree, primaryStatAtk and primaryStatDef.
     * atkDamageDealt is calculated by the attacks primary stat (str, int, mind?) * atkDegree.
     * atkDamageNegated is calculated by the defensive primary stat (str, int, mind?) * defDegree.
     * damageDealt is calculated by atkDamageDealt - atkDamageNegated. If damageDealt returns a negative double
     * damageDealt is set to output 0.
     *
     */

    public double DamageOutput(double atkDegree, double defDegree, double primaryStatAtk, double primaryStatDef){

        double damageDealt = 0.0;
        double atkDamageDealt = 0.0;
        double atkDamageNegated = 0.0;

        atkDamageDealt = (atkDegree * primaryStatAtk);
        atkDamageNegated = (defDegree * primaryStatDef);
        damageDealt = (atkDamageDealt - atkDamageNegated);

        if (damageDealt <= 0.0) {
            damageDealt = 0.0;
            return damageDealt;
        } else {
            return damageDealt;
        }

    }
    
}