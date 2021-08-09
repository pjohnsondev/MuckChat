/**
 * Sample Skeleton for 'MuckWindow.fxml' Controller Class
 */

package muck.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import muck.client.space_invaders.LandingPage;
import muck.protocol.connection.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.checkerframework.common.reflection.qual.Invoke;

public class MuckController implements Initializable {

    @FXML // fx:id="menu"
    private MenuBar menu; // Value injected by FXMLLoader

    @FXML // fx:id="windowPane"
    private SplitPane windowPane; // Value injected by FXMLLoader

    @FXML // fx:id="windowPane"
    private SplitPane chatSplitPane; // Value injected by FXMLLoader

    @FXML // fx:id="chatSection"
    private VBox chatSection; // Value injected by FXMLLoader

    @FXML // fx:id="gamePane"
    private BorderPane gamePane1; // Value injected by FXMLLoader

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

    @FXML // fx:id="mapButton"
    private Button mapButton; // Value injected by FXMLLoader

    @FXML // fx:id="x3"
    private Font x3; // Value injected by FXMLLoader

    @FXML // fx:id="x4"
    private Color x4; // Value injected by FXMLLoader

    @FXML // fx:id="openFullChat"
    private Button openFullChat; // Value injected by FXMLLoader

    @FXML // fx:id="openChatOnly"
    private Button openChatOnly; // Value injected by FXMLLoader

    @FXML // fx:id="closeChat"
    private Button closeChat; // Value injected by FXMLLoader

    @FXML // fx:id="game1Button"
    private Button game1Button; // Value injected by FXMLLoader

    @FXML
    private ImageView openChatImage;

    String message;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeChat.setOnAction(this::toggleChatWindow);
        game1Button.setOnAction(this::launchGame);
        openChatOnly.setOnAction(this::openChatOnly);
        enter.setOnAction(this::sendMessage); // assigns function to button
        openFullChat.setOnAction(this::openFullChat);
        plus.setOnAction(this::addChatTab); // adds new tab
        GameMap gm = new GameMap(gameCanvas); // Adds GameMap animation to the game window
        Image chosenAvatar = new Image("images/peach-portrait2.png"); // Avatar pic
        circle.setFill(new ImagePattern(chosenAvatar)); //Makes avatar a circle
        chatSection.setFocusTraversable(true);
        chatSection.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> chatSection.isFocused());
    }

    @FXML
        //Function that sends message when user presses enter
    void onEnter(ActionEvent event) {
        displayAndSend();
    }

    //Function that sends message when user clicks on arrow
    private void sendMessage(ActionEvent event) {
        displayAndSend();
    }

    //Function that displays message in chat box
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
      **NOTE**: The following line should append whatever message is in the currentMessage field on the
                client. Not sure how we're going to implement checking for new messages, probably using
                a timer and an array.
                groupChatBox.appendText(MuckClient.getCurrentMessage().getMessage() + "\n")

                In a similair way,we can retrieve user ID's and timestampes, although we will have to
                implement those getters seperately.
     */
                userMessage currentMessage = new userMessage();
                currentMessage.setMessage(message);
                MuckClient.INSTANCE.send(currentMessage);
     /*         This is a wait to retrieve the current message from the server. It should be moved from here when message
                retrieval is implemented. This just helps to test current message retrieval implementation.

                try{
                  Thread.sleep(10);
                }
                catch(InterruptedException ex)
                {
                  Thread.currentThread().interrupt();
                }

                groupChatBox.appendText("UserName Here: "+ MuckClient.INSTANCE.getCurrentMessage()+ "\n");
                */
            } else {
                int num = chatPane1.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane1.lookup("#chatbox" + num);
                currentChatBox.appendText(message + "\n");
                messageBox.clear();
            }
        }
    }


    // Function that creates new chat tab
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

    @FXML
    //Function that opens chat window and list window
    private void openFullChat(ActionEvent event) {
        windowPane.setDividerPositions(0.43);
        chatSplitPane.setDividerPositions(0.6);
        openChatOnly.setVisible(false);
    }


    @FXML
    //Function that opens the chat window only
    private void openChatOnly(ActionEvent event) {
        windowPane.setDividerPositions(0.68);
        chatSplitPane.setDividerPositions(0.989);
        openChatOnly.setVisible(false);
    }

    @FXML
    private void toggleChatWindow(ActionEvent event) {
        double[] windowDivider = windowPane.getDividerPositions();
        double[] chatDiv = chatSplitPane.getDividerPositions();
        if (windowDivider[0] >= 0.40 && chatDiv[0] >= 0.6) {
            openChatOnly(event);
        }
        if (windowDivider[0] >= 0.68 && chatDiv[0] >= 0.9) {
            hideChatWindow(event);
        }
        if (!(windowDivider[0] == 0.99)) {
            hideChatWindow(event);
        }
    }

    @FXML
    //Function that hides both chat window and list window
    private void hideChatWindow(ActionEvent event) {
        windowPane.setDividerPositions(0.999);
        chatSplitPane.setDividerPositions(1.0);
        openChatOnly.setVisible(true);
    }


    @FXML
    private void launchGame(ActionEvent event) {
        gameCanvas.setDisable(true);
        gameCanvas.setVisible(false);
        Canvas SICanvas = new Canvas();
        SICanvas.setHeight(gameCanvas.getHeight());
        SICanvas.setWidth(gameCanvas.getWidth());
        gamePane1.setCenter(SICanvas);
        BorderPane.setAlignment(SICanvas, Pos.CENTER);
        LandingPage si = new LandingPage(gamePane1, SICanvas);
    }
}
