package muck.client.enduring_fantasy;
//import muck.client.Dice;
import java.util.Random;

public class Monster extends Character {
    //private Dice monster = new Dice(1);
    private Random monster = new Random();
    private String monsDesc = " ";
    private int dmgtotal = 0;

    public Monster() { }

    public void genMonster(int num) {
        int monsGen = this.monster.nextInt(4);
        int monsHealth = num * 100;
        int monsDmg = num * 10;
        if (monsGen <= 1) {
            this.setName("Rat");
            this.setHealth(monsHealth + 1);
            this.setDmg(monsDmg);
            this.monsDesc = "A large rat";
        } else if (monsGen == 3) {
            this.setName("Wolf");
            this.setHealth(monsHealth + 50);
            this.setDmg(monsDmg + 20);
            this.monsDesc = "A lone feral wolf";
        } else {
            this.setName("Bear");
            this.setHealth(monsHealth + 100);
            this.setDmg(monsDmg + 150);
            this.monsDesc = "A large Bear";
        }
    }
    public String getMonsDesc() { return this.monsDesc; }
}

