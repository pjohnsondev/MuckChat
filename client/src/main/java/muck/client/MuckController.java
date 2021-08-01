/**
 * Sample Skeleton for 'MuckWindow.fxml' Controller Class
 */

package muck.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;
//import muck.protocol.connection.*;

public class MuckController implements Initializable {

    @FXML // fx:id="menu"
    private MenuBar menu; // Value injected by FXMLLoader

    @FXML // fx:id="windowPane"
    private SplitPane windowPane; // Value injected by FXMLLoader

    @FXML // fx:id="chatSection"
    private VBox chatSection; // Value injected by FXMLLoader

    @FXML // fx:id="gamePane"
    private AnchorPane gamePane; // Value injected by FXMLLoader

    @FXML // fx:id="gameCanvas"
    private Canvas gameCanvas; // Value injected by FXMLLoader

    @FXML // fx:id="circle"
    private Circle circle; // Value injected by FXMLLoader

    @FXML // fx:id="avatar"
    private ImageView avatar; // Value injected by FXMLLoader

    @FXML // fx:id="chatPane1"
    private TabPane chatPane1; // Value injected by FXMLLoader

    @FXML // fx:id="groupChat"
    private Tab groupChat; // Value injected by FXMLLoader

    @FXML // fx:id="chatAnchor"
    private AnchorPane chatAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="groupChatBox"
    private TextArea groupChatBox; // Value injected by FXMLLoader

    @FXML // fx:id="messageBox"
    private TextField messageBox; // Value injected by FXMLLoader

    @FXML // fx:id="plus"
    private MenuItem plus; // Value injected by FXMLLoader

    @FXML // fx:id="enter"
    private Button enter; // Value injected by FXMLLoader

    @FXML
    private TitledPane channelPane;

    @FXML
    private AnchorPane playerPane;


    @FXML // fx:id="x3"
    private Font x3; // Value injected by FXMLLoader

    @FXML // fx:id="x4"
    private Color x4; // Value injected by FXMLLoader

    @FXML
    private Accordion ChannelsAndPlayersPane;

    String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enter.setOnAction(this::sendMessage); // assigns function to button
        plus.setOnAction(this::addChatTab); // adds new tab
        //GameMap gm = new GameMap(gameCanvas); // Adds GameMap animation to the game window
        Image chosenAvatar = new Image("images/peach-portrait2.png"); // Avatar pic
        circle.setFill(new ImagePattern(chosenAvatar)); //Makes avatar a circle
        chatSection.setFocusTraversable(true);
        chatSection.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> chatSection.isFocused());
    }

    @FXML
    void onEnter(ActionEvent event) {
        displayAndSend();
    }

    private void sendMessage(ActionEvent event) {
        displayAndSend();
    }

    private void displayAndSend() {
        message = messageBox.getText();
        if ((message.length() != 0)) {
            Tab currentTab = chatPane1.getSelectionModel().getSelectedItem();
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
               /* userMessage currentMessage = new userMessage();
                currentMessage.setMessage(message);
                MuckClient.INSTANCE.send(currentMessage);*/
            } else {
                int num = chatPane1.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane1.lookup("#chatbox" + num);
                currentChatBox.appendText(message + "\n");
                messageBox.clear();

            }
        }
    }

    @FXML
    private void addChatTab(ActionEvent event) {
        int numTabs = chatPane1.getTabs().size();
        int tabNum = numTabs + 1;
        Tab newTab = new Tab("Chat Name Here");
        newTab.setId("Chat" + tabNum);
        AnchorPane newAnc = new AnchorPane();
        newAnc.setStyle("-fx-background-color: lightgrey");
        newTab.setContent(newAnc);
        newTab.setClosable(true);
        TextArea chatX = new TextArea();
        chatX.setId("chatbox" + tabNum);
        chatX.setEditable(false);
        chatX.setPrefWidth(246);
        chatX.setPrefHeight(473);
        chatX.setWrapText(true);
        AnchorPane.setLeftAnchor(chatX, 11.0);
        AnchorPane.setRightAnchor(chatX, 11.0);
        AnchorPane.setTopAnchor(chatX, 22.0);
        AnchorPane.setBottomAnchor(chatX, 22.0);
        chatX.setLayoutX(14);
        chatX.setLayoutY(17);
        newAnc.getChildren().add(chatX);
        chatPane1.getTabs().add(newTab);
        chatPane1.getSelectionModel().select(newTab);
    }

}