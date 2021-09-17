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
    private Magic magic;

    /** Player Variables **/
    public int getPcHp(){ return this.pcHp; }
    public void setPcHp(int newHp){ this.pcHp = newHp;}
    public int getPcMp(){ return this.pcMp;}
    public void setPcMp(int newMp){ this.pcMp = newMp; }
    public int getPcStr(){ return this.pcStr; }
    public int getPcMag(){ return this.pcMp; }


    /** Load the monster stats **/
    private int mobHp;

    private Monster monster = new Monster();

    /** Load the monster variables**/
    public int getMobHp(){ return this.mobHp; }
    public void decMobHp(int dmg){ this.mobHp -= dmg;}
    public String getMobName(){ return this.monster.getName(); }
    public int getMobDmg(){ return this.monster.getDamage(); }

    public void mobGen(){
        this.monster.genMonster(this.pcLvl);
        this.mobHp = this.monster.getHealth();
    }


    /** Set up the battle class **/
    public Battle(Player newPc) {
        this.player = newPc;
        this.pcHp = this.player.getHealth();
        this.pcMp = this.player.getMP();
        this.pcMag = this.player.getMagicStr();
        this.pcStr = this.player.getDamage();
        this.pcLvl = this.player.getPlayerLvl();
        this.nextLvl = this.player.getNextLvl();
        this.magic = new Magic(this.player, this.pcMp);
    }


    /** Set up the command box **/
    public void battleActions() {
        System.out.println("*----------*");
        System.out.println("Name: " + this.player.getName() + " HP: " + this.pcHp + " MP: " + this.pcMp +
                " STR: " + this.pcStr + "\nMana: " + this.pcMag + " Lvl: " + this.pcLvl +
                " Next Lvl: " + this.nextLvl);
        System.out.println("*-----* Commands *-----*\nChoose your move! \nObserve the Monster - Check\n" +
                "Attack - Atk\nUse Magic - Magic\nRecover MP - Rest");
        System.out.print("Your Action: ");
    }

    public void battleInput(String input){
        /** Lets set up the action command window options **/
        if (input.equalsIgnoreCase("Attack")){ this.pcAtk();
        //} else if (input.equalsIgnoreCase("Magic")){ this.magTree();
        } else if (input.equalsIgnoreCase("Rest")){ this.pcRest();
        } else if (input.equalsIgnoreCase("Check")){ this.pcCheck();
        }// else if (input.equalsIgnoreCase("Item")){ this.itemSelector();
        //} else if (input.equalsIgnoreCase("Flee")){ this.pcFlee();}
    }

    public void pcCheck(){ this.monster.getMobDets(); }

    public boolean pcDeath(){
        boolean dead = false;
        if (this.pcHp <= 0){ dead = true;}
        return dead;
    }

    public void pcAtk(){
        this.decMobHp(this.pcStr);
        System.out.println("*----------*");
        System.out.println(this.player.getName() + " lands a blow for " + this.pcStr + " on the " + this.monster.getName());
    }

    public void mobAtk() {
        if (this.getMobName().equalsIgnoreCase("Rat")) {
            System.out.println("the Rat bites " + this.player.getName());
        } else if (this.getMobName().equalsIgnoreCase("Wolf")) {
            System.out.println("The lone Wolf lunges at " + this.player.getName());
        } else if (this.getMobName().equalsIgnoreCase("Bear")) {
            System.out.println("The feral Bear mauls " + this.player.getName()); }
        this.pcHp -= this.getMobDmg();
    }

    public void pcRest(){
        int mpRegen = this.pcLvl * 7;
        this.pcMp += mpRegen;
        if (this.pcMp > this.player.getMP()){
            this.pcMp = this.player.getMP();
        }
    }

    public void magicSelection(){
        this.magic = new Magic(this.player, this.pcMp);
        this.magic.magicMenu();
        System.out.print("Select the magic: ");
    }

    public void useMagic(String magicName){
        this.magic = new Magic(this.player, this.pcMp);
        this.magic.useMagic(magicName);
        if (this.magic.getConfirmDmg()){
            this.mobHp -= this.magic.getMagicDmg();
            this.pcMp = this.magic.getPlayerMp();
            System.out.println(+ this.magic.getMagicDmg() + this.monster.getName());
            if (this.mobHp> 0){
                this.mobAtk();
            }
        }
    }

    //public void itemSelector(){ Place holder }
 }