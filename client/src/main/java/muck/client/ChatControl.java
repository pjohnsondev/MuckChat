package muck.client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/** This class is where the functionality of the ChatUI lives. */

public class ChatControl implements Initializable {


    /** The fields of the chat */
    @FXML
    TextArea groupChatBox;

    @FXML
    TextField messageBox;

    @FXML
    Button enter;

    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enter.setOnAction(this::buttonActionGroup); // assigns function to button

    }
    /** Event listener for enter (Submit) button  */
    private void buttonActionGroup(ActionEvent event) {
        message = messageBox.getText();
        if (message.length() != 0) {
            groupChatBox.appendText(message + "\n");
            messageBox.clear();
        }
    }

    /** Event listener for Enter on keyboard  */
        @FXML
        private void onEnter(ActionEvent event) {
            message = messageBox.getText();
            if (message.length() != 0) {
                groupChatBox.appendText(message + "\n");
                messageBox.clear();

            }
        }

    }


