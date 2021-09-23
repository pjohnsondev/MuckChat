package muck.client.enduring_fantasy;

public class EnduringFantasy {
    private Player player = new Player("name_of_character");
    private Battle battle;
    private Boolean confirmName;
    private Boolean confirmType;
    private Boolean confirmAction;

    public void FinalFantasyRPG() {
        this.confirmName = false;
        this.confirmType = false;
        this.confirmAction = false;
    }


    /** Sets the dialog intro **/
    public void gameMenu() {
        System.out.println("*-----* Enduring Fantasy *-----*\nNew Game - 1\nExit Game - 2\n");
        System.out.print("Please select: ");
    }

    /** Asks you to name the character **/
    public void startGreeting() {
        System.out.println("*-----* Welcome to Enduring Fantasy *-----*\n What shall we call you? \n");
        System.out.print("Your name: ");
    }


    /** confirms the name **/
    public boolean getConfirmName() {
        return this.confirmName;
    }


    /** makes sure the name is valid before saving **/
    public void checkName(String name) throws NullPointerException {
        if (!name.matches("^[\\w]+$")) {
            System.out.println("Invalid name. Please use letters or numbers");
        } else if (name.length() < 2) {
            System.out.println("Your name must be longer than 2 characters... ");
        } else {
            this.player.setName(name);
            this.confirmName = true;
            System.out.println("Hello "+ this.getName() +" !\n");
        }

    }


    /** Displays the character name **/
    public String getName() {
        return this.player.getName();
    }


    /** dialog for asking the character job type **/
    public void typeSelection() {
        System.out.println("*-----* Path Selection *-----*\nNow please select " + this.getName() + "'s path.\n" + "You can be a  \n" + "Soldier - Adept with a sword\n" + "RedMage - A jack of all Paths\n" + "Wizard - Adept with Magic");
        System.out.print("Type: ");
    }


    /** Checks for exceptions before saving the job type **/
    public void checkType(String type) throws NullPointerException {
        if (!type.equalsIgnoreCase("Soldier") && !type.equalsIgnoreCase("RedMage") && !type.equalsIgnoreCase("Wizard")) {
            System.out.println("Invalid Path - Try Again");
            this.confirmType = false;
        } else {
            player.setType(type);
            this.player.setTypeStats();
            this.confirmType = true;
        }
        System.out.println();
    }


    /** Confirms the job type **/
    public boolean getConfirmType() {
        return this.confirmType;
    }


    /** returns the players current level **/
    public int getPlayerLvl() {
        return this.player.getPlayerLvl();
    }


    /** returns the total exp required for the next level **/
    public int getNextLvl() {
        return this.player.getNextLvl();
    }


    /** Method for increasing the players level **/
    public void setNextLvl(int newLvl) {
        this.player.setNextLvl(newLvl);
    }


    /** Sets the intro and gives the player a chance to escape the game **/
    public void mainInteract() {
        this.confirmAction = false;
        System.out.println("*-----* Welcome to Mucktopia *-----*\nThis world is a never ending onslaught of enemies\n" +
                "How long do you think you can survive?\n Will " + this.getName() + " -Fight- or -Flee- ?\n");
        System.out.print("Choice: ");
    }


    /** Returns the current action flag for above **/
    public boolean getCommand() {
        return this.confirmAction;
    }


    /** Sets the action for the answer to the last two classes **/
    public void mainCommand(String command) {
        this.confirmAction = false;
        if (command.equalsIgnoreCase("Fight")) {
            this.confirmAction = true;
            this.mobBattle();
        } else if (!command.equalsIgnoreCase("Flee")) {
            System.out.println("Invalid input");
        }
    }


    /** generates a monster from battle class **/
    public void mobBattle() {
        this.battle = new Battle(this.player);
        this.battle.mobGen();
    }


    public void battleIntro() {
        this.battle.battleInput("Attack");
    }


    /** checks the current command  **/
    public void battleCommand(String com) {
        this.battle.battleInput(com);
    }


    //public void displayStat() {
    //    System.out.println();
    //    this.player.displayStats();
    //}


    /** Sets the monsters HP **/
    public int mobHp() {
        return this.battle.getMobHp();
    }


    /** Sets the monsters name **/
    public String mobName() {
        return this.battle.getMobName();
    }


    /** Sets the outcome of the current battle and increases the experiance gained **/
    public void endBattle() {
        boolean obtainItem = false;
        System.out.println("*-----*");
        System.out.println("You have defeated " + this.battle.getMobName());
        if (this.player.getPlayerLvl() < 3) {
            this.player.decNextLvl(100);
        } else {
            this.player.decNextLvl(250);
        }
        this.player.incPcLvl();
    }


    /** Sets the menu for selecting a spell **/
    public void magicMenu() {
        this.battle.magicSelection();
    }


    /** actions the last spell mentioned **/
    public void useMagic(String magicName) {
        this.battle.useMagic(magicName);
    }
//    public void newPlayer(Player newPlayer) {
//        this.player = newPlayer;
//    }

    /** checks to see if the players alive **/
    public boolean checkDeath() {
        return this.battle.pcDeath();
    }


    /** Guess he wasnt quite so alive afterall.  Pick an endgame option **/
    public void gameOver(){
        System.out.println("*-----*  Game Over  *-----*\nRestart - 1\nExit - 2\n");
        System.out.print("Please select: ");
    }
}