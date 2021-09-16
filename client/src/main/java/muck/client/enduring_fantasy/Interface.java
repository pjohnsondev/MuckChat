package muck.client.enduring_fantasy;

import java.util.Scanner;

public class Interface {
    private Scanner gameReader;
    private EnduringFantasy playGame;

    public Interface (){
        this.gameReader = new Scanner(System.in);
        this.playGame = new EnduringFantasy();
    }

    public static void main(String[] args) throws Exception{
        Interface loadGame = new Interface();
        loadGame.startGame();
    }

    public void startGame() throws Exception{
        this.playGame.gameMenu();
        String action = this.gameReader.nextLine();
        if(action.equals("1")){
            this.newGame();
        } else if (action.equals("2")){
            this.exitGame();
        } else { this.startGame();} //What is going on here? Is this recursive?
    }

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

    public void typeSelection(){
        this.playGame.typeSelection();
        String type = this.gameReader.nextLine();
        this.playGame.checkType(type);
        while(!this.playGame.getConfirmType()) {
            System.out.print("Type: ");
            String newType = this.gameReader.nextLine();
            this.playGame.checkName(newType);
        }
    }

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

    public void battleCommand() {
        for(; this.playGame.mobHp() > 0 && !this.playGame.checkDeath(); System.out.println()) {
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

    public void exitGame() {
        System.out.println("Are you sure you want to exit the game? - Yes to confirm otherwise press any key");
        System.out.print("Command: ");
        String command = this.gameReader.nextLine();
        if (command.equalsIgnoreCase("yes")) {
            System.exit(0);
        }
    }

    public void intermission() {
        System.out.print("Press any key to continue: ");
        this.gameReader.nextLine();
    }

    public void magicMenu() { /** Place holder **/ }

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