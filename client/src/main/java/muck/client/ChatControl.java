package muck.client;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import muck.protocol.connection.*;

 //For userMessage class;

/** This class is where the functionality of the ChatUI lives. */

public class ChatControl implements Initializable {


    /**
     * The fields of the UI
     */
    @FXML
    GridPane grid;
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
    Canvas gameWindow;
    @FXML
    Pane achievements;
    @FXML
    Pane collectibles;
    @FXML
    Button achievementButton;
    @FXML
    Button collectibleButton;
    @FXML
    Button hideCollectiblesAchievements;
    @FXML
    ImageView avatar;

    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enter.setOnAction(this::buttonActionGroup); // assigns function to button
        plus.setOnAction(this::addChatTab); // adds new tab
        collectibleButton.setOnAction(this::showCollectables); // shows collectible pane
        achievementButton.setOnAction(this::showAchievements); // shows collectible pane
        hideCollectiblesAchievements.setOnAction(this::hideCollectiblesAchievements); // hides both pane
        GameMap gm = new GameMap(gameWindow); // Adds GameMap animation to the game window
        Image chosenAvatar = new Image("images/goku-portrait.png");
        avatar.setImage(chosenAvatar);

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
        chatX.setPrefWidth(255);
        chatX.setPrefHeight(382);
        AnchorPane.setTopAnchor(chatX, 12.0);
        AnchorPane.setLeftAnchor(chatX, 12.0);
        AnchorPane.setRightAnchor(chatX, 12.0);
        AnchorPane.setBottomAnchor(chatX, 12.0);
        chatX.setLayoutX(12);
        chatX.setLayoutY(12);
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
                Also, this hasn't been tested extensively. Let me know if it causes
                problems!
      TODO: Create multiple chat groups serverside to filter messages. .
      */
                userMessage currentMessage = new userMessage();
                currentMessage.setMessage(message);
                MuckClient.INSTANCE.send(currentMessage);
            } else {
                int num = chatPane.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane.lookup("#chatbox" + num);
                currentChatBox.appendText(message + "\n");
                messageBox.clear();

            }
        }
    }

    /**
     * Displays collectibles if button clicked
     */


    @FXML
    private void showCollectables(ActionEvent event) {
        achievements.setVisible(false);
        collectibles.setVisible(true);
        hideCollectiblesAchievements.setVisible(true);

    }

    @FXML
    private void showAchievements(ActionEvent event) {
        achievements.setVisible(true);
        collectibles.setVisible(false);
        hideCollectiblesAchievements.setVisible(true);

    }


    private void hideCollectiblesAchievements(ActionEvent event) {
        achievements.setVisible(false);
        collectibles.setVisible(false);
        hideCollectiblesAchievements.setVisible(false);


    }

/*
 * Add user name to the top text
 * TODO: Create function to update user name
 */

/*
    private void updateUserName() {

    }
*/
    /*
     * Sets the avatar pic
     * TODO: Create function to update the user's avatar
     */

/*
    private void setAvatar() {

    }
*/

}
