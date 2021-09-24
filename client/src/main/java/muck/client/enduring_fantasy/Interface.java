package muck.client.enduring_fantasy;

import java.util.Scanner;

/** Sets up the text reader input and creates a game instance **/
public class Interface {
    private Scanner gameReader;
    private EnduringFantasy playGame;


    /** Sets the game to an action state **/
    public Interface (){
        this.gameReader = new Scanner(System.in);
        this.playGame = new EnduringFantasy();
    }


    /** Starts the load screen **/
    public static void main(String[] args) throws Exception{
        Interface loadGame = new Interface();
        loadGame.startGame(args);
    }


    /** Sets up the origional game load screen and looks for the players input from gameMenu **/
    public void startGame(String[] args) throws Exception{
        this.playGame.gameMenu();
        String action = this.gameReader.nextLine();
        if(action.equals("1")){
            this.newGame();
        } else if (action.equals("2")){
            this.exitGame();
        } else { this.startGame(args);}
    }


    /** Sets up the game and loads the text to set the players name **/
    public void newGame(){
        this.playGame = new EnduringFantasy();
        this.playGame.startGreeting();
        String name = this.gameReader.nextLine();
        this.playGame.checkName(name);

        while(!this.playGame.getConfirmName()){
            System.out.print("Name: ");
            String newName = this.gameReader.nextLine();
            this.playGame.checkName(newName);
        }
        this.typeSelection();
        this.mainCommand();
    }


    /** Sets up the character named aboves class or job type **/
    public void typeSelection(){
        this.playGame.typeSelection();
        String type = this.gameReader.nextLine();
        this.playGame.checkType(type);
        while(!this.playGame.getConfirmType()) {
            System.out.print("Type: ");
            String newType = this.gameReader.nextLine();
            this.playGame.checkType(newType);
        }
    }


    /** Sets up the main command window **/
    public void mainCommand(){
        System.out.println();
        this.playGame.mainInteract();
        String command = this.gameReader.nextLine();
        this.playGame.mainCommand(command);
        while(!this.playGame.getCommand()) {
            this.playGame.mainInteract();
            command = this.gameReader.nextLine();
            this.playGame.mainCommand(command);
        }
        if (command.equalsIgnoreCase("check")) {
            this.intermission();
            this.mainCommand();
        } else if (command.equalsIgnoreCase("fight")) {
            this.battleCommand();
        }
    }


    /** Checks the text action for main command window above **/
    /** if your dead , you have no actions and the game ends **/
    public void battleCommand() {
        for(; this.playGame.mobHp() > 0 && !this.playGame.checkDeath(); System.out.println("")) {
            this.playGame.battleIntro();
            String newMove = this.gameReader.nextLine();
            this.playGame.battleCommand(newMove);
            if (!newMove.equalsIgnoreCase("atk") && !newMove.equalsIgnoreCase("check") &&
                    !newMove.equalsIgnoreCase("rest")) {
                if (newMove.equalsIgnoreCase("magic")) {
                    this.magicMenu();
                }
            } else {
                this.intermission();
            }
        }
        if (this.playGame.checkDeath()) {
            System.out.println(this.playGame.getName() + " has been slain....");
            this.intermission();
            this.gameOver();
        }
    }


    /** Set the command to kill the game instance **/
    public void exitGame() {
        System.out.println("Are you sure you want to exit the game? - Yes to confirm otherwise press any key");
        System.out.print("Command: ");
        String command = this.gameReader.nextLine();
        if (command.equalsIgnoreCase("yes")) {
            System.exit(0);
        }
    }


    /** Creates a pause state and looks to read the next input **/
    public void intermission() {
        System.out.print("Press any key to continue: ");
        this.gameReader.nextLine();
    }


    /** Sets up the macig command and listens for the input of the spell to cast **/
    public void magicMenu() {
        this.playGame.magicMenu();
        String command = this.gameReader.nextLine();
        if (!command.equalsIgnoreCase("Igni") && !command.equalsIgnoreCase("Ici") && !command.equalsIgnoreCase("Levin")&&
                !command.equalsIgnoreCase("Lumis") && !command.equalsIgnoreCase("Scourge")){
            if (command.equalsIgnoreCase("Exit")){ this.battleCommand();
            } else {
                this.magicMenu();
            }
        } else {
            this.playGame.useMagic(command);
            this.intermission();
            this.battleCommand();
        }
    }


    /** Ends and exits the game **/
    public void gameOver() {
        this.playGame.gameOver();
        String command = this.gameReader.nextLine();
        if (command.equals("1")) {
            this.newGame();
        } else {
            this.gameOver();
        }
    }
}
