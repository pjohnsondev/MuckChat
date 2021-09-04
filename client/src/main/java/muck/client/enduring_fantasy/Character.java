package muck.client.enduring_fantasy;

public abstract class Character {
    private String charName;
    private int maxHp = 0;
    private int maxDmg = 0;

    public Character(){ }

    public String getCharName() { return this.charName; }
    public int getHealth() { return this.maxHp; }
    public int getDamage() { return this.maxDmg; }
    public void setDmg(int newDmg){ this.maxDmg = newDmg; }
    public void setHealth(int newHealth) { this.maxHp = newHealth; }
    public void setName(String newName) { this.charName = newName; }
}
