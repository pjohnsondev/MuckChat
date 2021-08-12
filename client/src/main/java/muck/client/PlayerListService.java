package muck.client;

import javafx.scene.control.TextArea;
import javafx.concurrent.ScheduledService;
import javafx.application.Platform;
import javafx.concurrent.Task;

/*
PlayerListService is used to create a async task that can be run off the javafx thread allowing
the functionality needed to consistently update and display the list of current players.
 */
class PlayerListService extends ScheduledService<Void> {

    private TextArea textBox;

    // The constructor needed to pass the playerTextArea from the Muck controller into the service
    public PlayerListService(TextArea someTextBox) {
        this.textBox = someTextBox;
    }

    // Creates the async task and assign it
    @Override
    protected Task<Void> createTask(){
        return new Task<>() {
            @Override
            protected Void call() {
                // Updates the Player list in the GUI
                Platform.runLater(() -> {
                    fillPlayerList(textBox);
                });
                return null;
            }
        };
    }

    /* fillPlayerList takes a textArea and clears it, then reads from the current instance
    of the client to iterate over the stored playerlist to add each player to the text area
    */
    public void fillPlayerList(TextArea someTextBox) {
        someTextBox.clear();
        for (String player: MuckClient.INSTANCE.players) {
            someTextBox.appendText("Player " + player + "\n");
        }
    }
}