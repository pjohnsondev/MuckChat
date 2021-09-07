package muck.client.enduring_fantasy;

public class Battle {
    /** load the stats **/
    private int pcHp;
    private int pcMp;
    private int pcMag;
    private int pcStr;
    private int nextLvl;
    private Player player;

    private int monsterHp;
    private Monster monster = new Monster();

    public Battle(Player newHero){
        this.player = newHero;
        this.pcHp = this.player.getHealth();
        this.pcMp = this.player.getMP();
        this.pcMag = this.player.getMagicStr();
        this.pcStr = this.player.getDamage();
        this.nextLvl = this.player.getPlayerLvl();
    }


    public void battleInput(String input){
        /** Lets set up the action command window options  **/
        if (input.equalsIgnoreCase("Attack")){
            //this.pcAttack();
        } else if (input.equalsIgnoreCase("Rest")){
            //this.pcRest();
        } else if (input.equalsIgnoreCase("Flee")){
            //this.pcFlee();
        }
    }

}


