package muck.client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/** This class is where the functionality of the ChatUI lives. */

public class ChatControl implements Initializable {


    /**
     * The fields of the UI
     */

    @FXML
    Tab groupChat;
    @FXML
    TextArea groupChatBox;
    @FXML
    TextField messageBox;
    @FXML
    Button enter;
    @FXML
    AnchorPane channelsList;
    @FXML
    AnchorPane playerList;
    @FXML
    TabPane chatPane;
    @FXML
    Button plus;


    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enter.setOnAction(this::buttonActionGroup); // assigns function to button
        plus.setOnAction(this::addTab); // adds new tab

    }

    /**
     * Event listener for enter (Submit) button
     */
    private void buttonActionGroup(ActionEvent event) {
        displayAndSend();
    }

    /**
     * Event listener for Enter on keyboard
     */
    @FXML
    private void onEnter(ActionEvent event) {
        displayAndSend();
    }

    /**
     * Function for adding new chat channel (aka a tab)
     */

    @FXML
    private void addTab(ActionEvent event) {
        int numTabs = chatPane.getTabs().size();
        int tabNum = numTabs + 1;
        Tab newTab = new Tab("Chat Name Here");
        newTab.setId("Chat" + tabNum);
        AnchorPane newAnc = new AnchorPane();
        newAnc.setStyle("-fx-background-color: lightgreen");
        newTab.setContent(newAnc);
        newTab.setClosable(true);
        TextArea chatX = new TextArea();
        chatX.setId("chatbox" + tabNum);
        chatX.setEditable(false);
        chatX.setPrefWidth(220);
        chatX.setPrefHeight(399);
        chatX.setLayoutX(12);
        chatX.setLayoutY(12);
        newAnc.getChildren().add(chatX);
        chatPane.getTabs().add(newTab);
    }

    /**
     * Displays the user message to the chat depending on which chat (aka tab) is active
     * TODO: Send the user message to the server
     */
    private void displayAndSend() {
        message = messageBox.getText();
        if ((message.length() != 0)) {
            Tab currentTab = chatPane.getSelectionModel().getSelectedItem();
            String currentID = currentTab.getId();
            if (currentID.equals("groupChat")) {
                groupChatBox.appendText(message + "\n");
                messageBox.clear();
            } else {
                int num = chatPane.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane.lookup("#chatbox" + num);
                currentChatBox.appendText(message + "\n");
                messageBox.clear();
            }
        }
    }
}

