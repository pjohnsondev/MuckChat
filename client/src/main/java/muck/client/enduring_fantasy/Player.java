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

    public void setNextLvl(int nextLevel) { this.nextLevel = nextLevel;}
    public int getMagicStr() { return this.magicStr;}
    public void setMagicStr(int mag) { this.magicStr = mag;}
    public int getMP() { return this.magicPoints;}
    public void setMP(int newMP) { this.magicPoints = newMP;}
    public void incMagPoints(int newPoints) { this.magicPoints = this.magicPoints += newPoints;}

    public String getType() {return this.type;}
    public void setType(String newType){
        /** FINISH ME TOMARROW - SET MY Job Classes**/
    }

}
