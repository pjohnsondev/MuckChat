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

    public void gameMenu() {
        System.out.println("*-----* Enduring Fantasy *-----*\nNew Game - 1\nExit Game - 2\n");
        System.out.print("Please select: ");
    }

    public void startGreeting() {
        System.out.println("*-----* Welcome to Enduring Fantasy *-----*\n What shall we call you? \n");
        System.out.print("Your name: ");
    }

    public boolean getConfirmName() {
        return this.confirmName;
    }

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

    public String getName() {
        return this.player.getName();
    }

    public void typeSelection() {
        System.out.println("*-----* Path Selection *-----*\nNow please select " + this.getName() + "'s path.\n" + "You can be a  \n" + "Soldier - Adept with a sword\n" + "RedMage - A jack of all Paths\n" + "Wizard - Adept with Magic");
        System.out.print("Type: ");
    }

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

    public boolean getConfirmType() {
        return this.confirmType;
    }

    public int getPlayerLvl() {
        return this.player.getPlayerLvl();
    }

    public int getNextLvl() {
        return this.player.getNextLvl();
    }

    public void setNextLvl(int newLvl) {
        this.player.setNextLvl(newLvl);
    }

    public void mainInteract() {
        this.confirmAction = false;
        System.out.println("*-----* Welcome to Mucktopia *-----*\nThis world is a never ending onslaught of enemies\n" +
                "How long do you think you can survive?\n Will " + this.getName() + " -Fight- or -Flee- ?\n");
        System.out.print("Choice: ");
    }

    public boolean getCommand() {
        return this.confirmAction;
    }

    public void mainCommand(String command) {
        this.confirmAction = false;
        if (command.equalsIgnoreCase("Fight")) {
            this.confirmAction = true;
            this.mobBattle();
        } else if (!command.equalsIgnoreCase("Flee")) {
            System.out.println("Invalid input");
        }
    }

    public void mobBattle() {
        this.battle = new Battle(this.player);
        this.battle.mobGen();
    }

    public void battleIntro() {
        this.battle.battleInput("Attack");
    }

    public void battleCommand(String com) {
        this.battle.battleInput(com);
    }

    //public void displayStat() {
    //    System.out.println();
    //    this.player.displayStats();
    //}

    public int mobHp() {
        return this.battle.getMobHp();
    }

    public String mobName() {
        return this.battle.getMobName();
    }

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

    public void magicMenu() {
        this.battle.magicSelection();
    }

    public void useMagic(String magicName) {
        this.battle.useMagic(magicName);
    }
//    public void newPlayer(Player newPlayer) {
//        this.player = newPlayer;
//    }

    public boolean checkDeath() {
        return this.battle.pcDeath();
    }

    public void gameOver(){
        System.out.println("*-----*  Game Over  *-----*\nRestart - 1\nExit - 2\n");
        System.out.print("Please select: ");
    }
}