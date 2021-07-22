package muck.client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatControl implements Initializable {

    @FXML
    TextArea groupChatBox;

    @FXML
    TextField messageBox;

    @FXML
    Button enter;

    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enter.setOnAction(this::buttonActionGroup);

    }

    private void buttonActionGroup(ActionEvent event) {
        message = messageBox.getText();
        if (message.length() != 0) {
            groupChatBox.appendText(message + "\n");
            messageBox.clear();
        }
    }
        @FXML
        private void onEnter(ActionEvent event) {
            message = messageBox.getText();
            if (message.length() != 0) {
                groupChatBox.appendText(message + "\n");
                messageBox.clear();

            }
        }

    }


