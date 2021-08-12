package muck.client;

import javafx.scene.control.TextArea;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.*;
import javafx.application.Platform;
import javafx.event.EventTarget;
import javafx.concurrent.Worker.State;
import muck.client.MuckController;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

class PlayerListService extends ScheduledService<Void> {

    private TextArea textBox;

    public PlayerListService(TextArea someTextBox) {
        this.textBox = someTextBox;
    }

    @Override
    protected Task<Void> createTask(){
        return new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    fillPlayerList(textBox);
                });
                return null;
            }
        };
    }

    public void fillPlayerList(TextArea someTextBox) {
        someTextBox.clear();
        for (String player: MuckClient.INSTANCE.players) {
            someTextBox.appendText("Player " + player + "\n");
        }
    }
}