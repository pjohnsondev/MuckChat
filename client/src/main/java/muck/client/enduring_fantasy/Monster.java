package muck.client.enduring_fantasy;
//import muck.client.Dice;
import java.util.Random;

public class Monster extends Character {
    //private Dice monster = new Dice(1);
    private Random monster = new Random();
    private String mobDets = " ";
    private int dmgtotal = 0;

    public Monster() { }

    public void genMonster(int num) {
        int monsGen = this.monster.nextInt(4);
        int monsHealth = num * 100;
        int monsDmg = num * 10;
        if (monsGen <= 1) {
            /** Fodder Mob, everyone starts on rats **/
            this.setName("Rat");
            this.setHealth(monsHealth + 1);
            this.setDmg(monsDmg);
            this.mobDets = "A large rat";
        } else if (monsGen == 3) {
            /** Basic DD **/
            this.setName("Wolf");
            this.setHealth(monsHealth + 50);
            this.setDmg(monsDmg + 20);
            this.mobDets = "A lone feral wolf";
        } else {
            /** Basic ´Tank / Punisher´ Mob **/
            this.setName("Bear");
            this.setHealth(monsHealth + 200);
            this.setDmg(monsDmg + 150);
            this.mobDets = "A large Bear";
        }
    }
    public String getMonsDesc() { return this.monsDesc; }
}

