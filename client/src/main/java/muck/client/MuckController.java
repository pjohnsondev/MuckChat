/**
 * Sample Skeleton for 'MuckWindow.fxml' Controller Class
 */

package muck.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import muck.client.space_invaders.LandingPage;
import muck.protocol.connection.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.checkerframework.common.reflection.qual.Invoke;
import javafx.util.Duration;

public class MuckController implements Initializable {

    @FXML // fx:id="menu" The menu bar at the top
    private MenuBar menu; // Value injected by FXMLLoader

    @FXML // fx:id="windowPane" The pane that seperated the game area and the chat area
    private SplitPane windowPane; // Value injected by FXMLLoader

    @FXML // The pane that separates the chat area and list area
    private SplitPane chatSplitPane; // Value injected by FXMLLoader

    @FXML // fx:id="chatSection" The chat area part of the second pane
    private VBox chatSection; // Value injected by FXMLLoader

    @FXML // fx:id="gamePane" The game window
    private BorderPane gamePane1; // Value injected by FXMLLoader

    @FXML // fx:id="gameCanvas" the original game canvas with the Map. Map displays in here
    private Canvas gameCanvas; // Value injected by FXMLLoader

    @FXML // fx:id="circle" Circle image area for avatar
    private Circle circle; // Value injected by FXMLLoader

    @FXML // fx:id="avatar" Actual avatar picture
    private ImageView avatar; // Value injected by FXMLLoader

    @FXML // fx:id="chatPane1" The tab pane for the chat - tabs sit in this. Despite the name there is only 1
    private TabPane chatPane1; // Value injected by FXMLLoader

    @FXML // fx:id="channelTextArea" The pane for the channel list
    private TextArea channelTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="playerTextArea" The pane for the player list
    private TextArea playerTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="groupChat" The tab for the group chat. It sits in chatPane1
    private Tab groupChat; // Value injected by FXMLLoader

    @FXML // fx:id="chatAnchor" Makes a border for the chat text area
    private AnchorPane chatAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="groupChatBox" The text area for the group chat
    private TextArea groupChatBox; // Value injected by FXMLLoader

    @FXML // fx:id="messageBox" The box you type in to send a pessage
    private TextField messageBox; // Value injected by FXMLLoader

    @FXML // fx:id="plus"   The menu item to add another tab
    private MenuItem plus; // Value injected by FXMLLoader

    @FXML
    private MenuItem quitMuck; // Value injected by FXMLLoader

    @FXML // fx:id="enter" The button to submit your text
    private Button enter; // Value injected by FXMLLoader


    @FXML // fx:id="x3"
    private Font x3; // Value injected by FXMLLoader

    @FXML // fx:id="x4"
    private Color x4; // Value injected by FXMLLoader

    @FXML // fx:id="openFullChat" The image button to open chat in the corner
    private Button openFullChat; // Value injected by FXMLLoader

    @FXML // fx:id="openChatOnly" The side tab button to open the chat window
    private Button openChatOnly; // Value injected by FXMLLoader

    @FXML // fx:id="closeChat" The x button when the chat is open
    private Button closeChat; // Value injected by FXMLLoader

    @FXML // fx:id="game1Button" The space invaders button. This is supposed to be temporary
    private Button game1Button; // Value injected by FXMLLoader

    @FXML //fx:id="userNameDisplay"
    private Text userNameDisplay;

    String message;
    private static String userName;
    private static String avatarID;

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeChat.setOnAction(this::toggleChatWindow);
        game1Button.setOnAction(this::launchSpaceInvaders);
        openChatOnly.setOnAction(this::openChatOnly);
        enter.setOnAction(this::sendMessage);
        openFullChat.setOnAction(this::openFullChat);
        plus.setOnAction(this::addChatTab); // adds new tab
        GameMap gm = new GameMap(gameCanvas); // Adds GameMap animation to the game window
        Image chosenAvatar = AvatarController.getPortrait(avatarID); // Updates avatar portrait based on selection from Avatar class
        userNameDisplay.setText(userName);// // Sets username that has been passed in from Avatar class
        circle.setFill(new ImagePattern(chosenAvatar)); //Makes avatar a circle
        chatSection.setFocusTraversable(true);
        chatSection.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> chatSection.isFocused());
        quitMuck.setOnAction(this::quitMuck);
        // Creates and sets the player list service to be called every second, to update the current player list
        PlayerListService service = new PlayerListService(playerTextArea);
        service.setPeriod(Duration.seconds(1));
        service.start();
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

    // Use this method from external classes to open the gameplay window. Added by CA 14 Aug
    public static void constructor(MouseEvent event, String name, String avatar) {
        userName = name;
        avatarID = avatar;
        try {
            Parent root = FXMLLoader.load(MuckController.class.getResource("/fxml/MuckWindow.fxml"));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(MuckController.class.getResource("/css/style.css").toExternalForm());
            //This line gets the Stage Information
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> stage.close());
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        windowPane.setDividerPositions(0.70);
        chatSplitPane.setDividerPositions(0.989);
        openChatOnly.setVisible(false);
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
        windowPane.setDividerPositions(0.70);
        chatSplitPane.setDividerPositions(0.989);
        openChatOnly.setVisible(false);
    }

    @FXML
    // Function that opens and closes the chat area depening on how far open it is
    private void toggleChatWindow(ActionEvent event) {
        double[] windowDivider = windowPane.getDividerPositions();
        double[] chatDiv = chatSplitPane.getDividerPositions();

        if (windowDivider[0] >= 0.68 && chatDiv[0] >= 0.9) { //if only the chat is open then is closes the chat area
            hideChatWindow(event);
        }
        if (!(windowDivider[0] == 0.99)) { // if the chat is open slightly it also closes the entire chat area
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
    /** Function to launch the game. The game exists in the LandingPage.class.
    Basically what this is doing is disabling and hiding the map canvas and creating a new canvas
    for the new game. It then adds this canvas as a child of the pane.

     When the user presses exit, it essentially removes the content and adds the game map back
     */
    private void launchSpaceInvaders (ActionEvent event) {
        gameCanvas.setDisable(true);
        gameCanvas.setVisible(false);
        Canvas SICanvas = new Canvas();
        SICanvas.setHeight(gameCanvas.getHeight());
        SICanvas.setWidth(gameCanvas.getWidth());
        gamePane1.setCenter(SICanvas);
        BorderPane.setAlignment(SICanvas, Pos.CENTER);
        LandingPage si = new LandingPage(gamePane1, SICanvas);
    }

    private void quitMuck(ActionEvent event) {
        Platform.exit();
    }
}
