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
    public int getNextLevel(){ return this.nextLevel;}
    public void decNextLvl(int newLvl){ this.nextLevel -= newLvl;}
    public void resetLvl(){ this.nextLevel = this.playerLevel * 150;}
    public int getPlayerLvl(){ return this.playerLevel;}
    public void setPlayerLvl(int newLvl){ this.playerLevel = newLvl;}

    public void incPlayLvl() {
        if (this.nextLevel <= 0) {
            ++this.playerLevel;
            System.out.println("You have increased your level");
        }
        this.resetLvl();

    } else { System.out.println("Your have gained experience");}



    /** Lets set up player classes **/
    public String getType() {return this.type;}
    public void setType(String newType){
        /** SET Job Classes **/
    }


    public void setTypeStats(){
        /** set job base stats here **/
    }


}
