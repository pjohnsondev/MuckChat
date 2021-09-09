package muck.client.enduring_fantasy;

public class Battle {
    /** load the player stats **/
    private int pcHp;
    private int pcMp;
    private int pcMag;
    private int pcStr;
    private int nextLvl;
    private int pcLvl;
    private Player player;

    /** Load the monster stats **/
    private int monsterHp;
    private Monster monster = new Monster();

    /** Set up the battle class **/
    public Battle(Player newHero){
        this.player = newHero;
        this.pcHp = this.player.getHealth();
        this.pcMp = this.player.getMP();
        this.pcMag = this.player.getMagicStr();
        this.pcStr = this.player.getDamage();
        this.pcLvl = this.player.getPlayerLvl();
        this.nextLvl = this.player.nextLevel();
    }

    /** Set up the command box **/
    public void battleActions() {
        System.out.println("*----------*");
        System.out.println("Name: " + this.player.getCharName()" HP: " + this.pcHp" MP: " + this.pcMp
                " STR: " + this.pcStr + "\nMana: " + this.pcMag + " Lvl: " + this.pcLvl
                " Next Lvl: " + this.nextLvl);
        System.out.println("*-----* Commands *-----*\nChoose your move! \nObserve the Monster - Check\n" +
                "Attack - Atk\nUse Magic - Magic\nRecover MP - Rest");
        System.out.print("Your Action: ");
    }


    //public void battleInput(String input){
        /** Lets set up the action command window options
        if (input.equalsIgnoreCase("Attack")){ this.pcAttack();
        } else if (input.equalsIgnoreCase("Rest")){ this.pcRest();
        } else if (input.equalsIgnoreCase("Check")){ this.pcCheckMon();
        }// else if (input.equalsIgnoreCase("Item")){ this.itemSelector();
        //} else if (input.equalsIgnoreCase("Flee")){ this.pcFlee();}
    }

    /**
    public void pcAttack(){
        this.decMonsHealth(this.pcStr);
        System.out.println("*----------*");
        System.out.println(this.player.getCharName());
        if (this.monsterHp > 0){ this.monsAttack()}
    }

    public void pcRest(){ Place holder }

    public void itemSelector(){ Place holder }

    public void pcCheckMon(){ Place holder }
 }

**/

