package muck.client.enduring_fantasy;

public class Player extends Character {
    private String type = "";
    private int playerLevel;
    private int nextLevel;
    private int magicPoints;
    private int magicStr;

    public Player(){
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
            if (this.getType().equalsIgnoreCase("Soldier")){
                this.setHealth(this.getHealth() + 100);
                this.setDmg(this.getDamage() + 30);
                this.magicStr += 10;
                this.incMagPoints(10);
            } else if (this.getType().equalsIgnoreCase("RedMage")){
                this.setHealth(this.getHealth() + 75);
                this.setDmg(this.getDamage() + 15);
                this.magicStr += 15;
                this.incMagPoints(15);
            } else if (this.getType().equalsIgnoreCase("Wizard")){
                this.setHealth(this.getHealth() + 50);
                this.setDmg(this.getDamage() + 10);
                this.magicStr += 20;
                this.incMagPoints(20);
            }

        this.resetLvl();
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
        if (this.getType().equalsIgnoreCase("Soldier")){
            this.setHealth(200);
            this.setDmg(50);
            this.magicStr = 5;
            this.magicPoints = 10;
        } else if (this.getType().equalsIgnoreCase("RedMage")){
            this.setHealth(160);
            this.setDmg(40);
            this.magicStr = 15;
            this.magicPoints = 20;
        }else if (this.getType().equalsIgnoreCase("Wizard")) {
            this.setHealth(120);
            this.setDmg(25);
            this.magicStr = 20;
            this.magicPoints = 30;
        }
    }
}
