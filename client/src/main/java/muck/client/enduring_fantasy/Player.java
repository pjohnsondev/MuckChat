package muck.client.enduring_fantasy;

public class Player extends Character {

    private String type = "";
    private int playerLevel;
    private int nextLevel;
    private int magicPoints;
    private int magicStr;

    public Player(String name){
        super(name);
        /** Setting base stats **/
        this.playerLevel = 1;
        this.nextLevel = 200;
        this.magicPoints = 0;
        this.magicStr = 0;
    }

    /** Lets set Magic stats **/
    public int getMagicStr() { return this.magicStr;}
    public void setMagicStr(int mag) { this.magicStr = mag;}
    public int getMP() { return this.magicPoints;}
    public void setMP(int newMP) { this.magicPoints = newMP;}
    public void incMagPoints(int newPoints) { this.magicPoints = this.magicPoints += newPoints;}

    /** Lets set level stats **/
    public void setNextLvl(int nextLevel) { this.nextLevel = nextLevel;}
    public int getNextLvl(){ return this.nextLevel;}
    public void decNextLvl(int newLvl){ this.nextLevel -= newLvl;}
    public void resetLvl(){ this.nextLevel = this.playerLevel * 150;}
    public int getPlayerLvl(){ return this.playerLevel;}
    public void setPlayerLvl(int newLvl){ this.playerLevel = newLvl;}

    public void incPcLvl() {
        if (this.nextLevel <= 0) {
            ++this.playerLevel;
            System.out.println("You have increased your level");
            if (getType().equalsIgnoreCase("Soldier")){
                super.setHealth(super.getHealth() + 100);
                super.setDmg(super.getDamage() + 30);
                this.magicStr += 10;
                incMagPoints(10);
            } else if (getType().equalsIgnoreCase("RedMage")){
                super.setHealth(super.getHealth() + 75);
                super.setDmg(super.getDamage() + 15);
                this.magicStr += 15;
                incMagPoints(15);
            } else if (getType().equalsIgnoreCase("Wizard")){
                super.setHealth(super.getHealth() + 50);
                super.setDmg(super.getDamage() + 10);
                this.magicStr += 20;
                incMagPoints(20);
            }

        resetLvl();
    } else { System.out.println("Your have gained experience");}
    }



    /** Lets set up player classes **/
    public String getType() {return this.type;}
    public void setType(String newType) {
        /** SET Job Classes **/
        if (!newType.equalsIgnoreCase("Soldier") &&
                !newType.equalsIgnoreCase("RedMage") &&
                !newType.equalsIgnoreCase("Wizard")){
            System.out.println("Not a job class");
        } else { this.type = newType; }
    }

    public void setTypeStats() {
        /** set job base stats here **/
        if (getType().equalsIgnoreCase("Soldier")){
            super.setHealth(200);
            super.setDmg(50);
            this.magicStr = 5;
            this.magicPoints = 10;
        } else if (getType().equalsIgnoreCase("RedMage")){
            super.setHealth(160);
            super.setDmg(40);
            this.magicStr = 15;
            this.magicPoints = 20;
        }else if (getType().equalsIgnoreCase("Wizard")) {
            super.setHealth(120);
            super.setDmg(25);
            this.magicStr = 20;
            this.magicPoints = 30;
        }
    }
}
