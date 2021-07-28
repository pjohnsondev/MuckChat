package muck.client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import muck.protocol.*;
import muck.protocol.connection.*;
 //For userMessage class;

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
    @FXML
    Button newGameTab;
    @FXML
    Pane achievements;
    @FXML
    Pane collectibles;
    @FXML
    Pane mapPane;
    @FXML
    ImageView map;
    @FXML
    Button achievementButton;
    @FXML
    Button collectibleButton;

    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enter.setOnAction(this::buttonActionGroup); // assigns function to button
        plus.setOnAction(this::addChatTab); // adds new tab
        //newGameTab.setOnAction(this::addGameTab); //

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
    private void addChatTab(ActionEvent event) {
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
        chatX.setPrefWidth(308);
        chatX.setPrefHeight(363);
        chatX.setLayoutX(9);
        chatX.setLayoutY(11);
        newAnc.getChildren().add(chatX);
        chatPane.getTabs().add(newTab);
        chatX.isFocused();
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

      /* **********************************************************************
      Code edited for sending functionality.
      Last updated: Harrison Liddell, utilising Ryan Birch development serverside, 27/07/2021
      Adding Sending functionality by first creating a userMessage object and
      then sending it to the server.
      **NOTE**: No functionality for ChatId has been implemented serverside yet.
                Also, this hasnt been tested extensively. Let me know if it causes
                problems!
      TODO: Create multiple chat groups serverside to filter messages. .
      */
                userMessage currentMessage = new userMessage();
                currentMessage.setMessage(message);
                MuckClient.INSTANCE.send(currentMessage);
      /********************************************************************** */
            } else {
                int num = chatPane.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane.lookup("#chatbox" + num);
                currentChatBox.appendText(message + "\n");
                messageBox.clear();

            }
        }
    }

    /**
     * Adds new game tab for multiple games running at once (if we need it)
     */

/*
    @FXML
    private void addGameTab(ActionEvent event) {
        int numTabs = gamePane.getTabs().size();
        int tabNum = numTabs + 1;
        Tab newTab = new Tab("Game Name Here");
        newTab.setId("Game" + tabNum);
        AnchorPane newAnc = new AnchorPane();
        newAnc.setStyle("-fx-background-color: lightgrey");
        newTab.setContent(newAnc);
        newTab.setClosable(true);
        newAnc.setId("gameWindow1"+tabNum);
        gamePane.getTabs().add(newTab);
    }
*/

/**
 * Add user name to the top text
 * TODO: Create function to update user name
 */

    private void updateUserName() {

    }
}
