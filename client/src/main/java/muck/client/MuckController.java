
package muck.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.logging.Level;

import javafx.scene.text.Font;
import muck.core.Location;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import muck.client.enduring_fantasy.LandingPageEf;
import muck.client.space_invaders.LandingPage;
import muck.client.frogger.LandingPageFrogger;
import muck.protocol.connection.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import javafx.util.Duration;

public class MuckController implements Initializable {

    public Font x3;
    public Color x4;
    @FXML // fx:id="windowPane" The pane that seperated the game area and the chat area
    private SplitPane windowPane; // Value injected by FXMLLoader

    @FXML // The pane that separates the chat area and list area
    private SplitPane chatSplitPane; // Value injected by FXMLLoader

    @FXML // fx:id="chatSection" The chat area part of the second pane
    private VBox chatSection; // Value injected by FXMLLoader

    @FXML // fx:id="gamePane" The game window
    private BorderPane gamePane1; // Value injected by FXMLLoader

    @FXML // fx:id="gameCanvas" the original game canvas with the Map. Map displays in here
    public Canvas gameCanvas; // Value injected by FXMLLoader

    @FXML // fx:id="circle" Circle image area for avatar
    public Circle circle; // Value injected by FXMLLoader

    @FXML // fx:id="chatPane1" The tab pane for the chat - tabs sit in this. Despite the name there is only 1
    private TabPane chatPane1; // Value injected by FXMLLoader

    @FXML // fx:id="playerTextArea" The pane for the player list
    private TextArea playerTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="groupChatBox" The text area for the group chat
    private TextArea groupChatBox; // Value injected by FXMLLoader

    @FXML // fx:id="messageBox" The box you type in to send a message
    private TextField messageBox; // Value injected by FXMLLoader

    @FXML // fx:id="plus"   The menu item to add another tab
    private MenuItem plus; // Value injected by FXMLLoader

    @FXML // fx:id="playerDashboardMenu"   The menu item to add another tab
    private MenuItem playerDashboardMenu; // Value injected by FXMLLoader

    @FXML // fx:id="chatMenuOpen"   The menu item to add another tab
    private MenuItem chatMenuOpen; // Value injected by FXMLLoader

    @FXML // fx:id="enduringFantasyMenu"   The menu item to add another tab
    private MenuItem enduringFantasyMenu; // Value injected by FXMLLoader

    @FXML // fx:id="spaceInvadersMenu"   The menu item to add another tab
    private MenuItem spaceInvadersMenu; // Value injected by FXMLLoader

    @FXML // fx:id="froggerMenu"   The menu item to add another tab
    private MenuItem froggerMenu; // Value injected by FXMLLoader

    @FXML
    private MenuItem quitMuck; // Value injected by FXMLLoader

    @FXML // fx:id="enter" The button to submit your text
    private Button enter; // Value injected by FXMLLoader

    @FXML // fx:id="openFullChat" The image button to open chat in the corner
    private Button openFullChat; // Value injected by FXMLLoader

    @FXML // fx:id="closeChat" The x button when the chat is open
    private Button closeChat; // Value injected by FXMLLoader

    @FXML // fx:id="game1Button" The space invaders button. This is supposed to be temporary
    private Button game1Button; // Value injected by FXMLLoader

    @FXML
    private Button game2Button;

    @FXML
    private Button game3Button;

    @FXML //fx:id="userNameDisplay"
    private Text userNameDisplay;

    private static final Image goToDashboard = new Image("/images/PlayerDashboardHover.png");
    private Image chosenAvatar;

    String message;
    private static String userName;
    private static String displayName;
    private static String avatarID;

    //static final Logger logger = LogManager.getLogger();
    static Supplier<List<Sprite>> getPlayersfn = MuckClient.INSTANCE::getPlayerSprites;
    static BiConsumer<String, Location> updatePlayerfn = MuckClient.INSTANCE::updatePlayerLocation;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
        //Button and menu functionality
        closeChat.setOnAction(this::hideChatWindow);
        spaceInvadersMenu.setOnAction(this::launchSpaceInvaders);
        enduringFantasyMenu.setOnAction(this::launchEnduringFantasy);
        froggerMenu.setOnAction(this::launchFrogger);
        game1Button.setOnAction(this::launchSpaceInvaders);
        game2Button.setOnAction(this::launchEnduringFantasy);
        game3Button.setOnAction(this::launchFrogger);
        enter.setOnAction(this::sendMessage);
        openFullChat.setOnAction(this::openFullChat);
        chatMenuOpen.setOnAction(this::openFullChat);
        plus.setOnAction(this::addChatTab); // adds new tab
        new GameMap(gameCanvas,updatePlayerfn, getPlayersfn); // Adds GameMap animation to the game window
        chosenAvatar = AvatarController.getPortrait(avatarID); // Updates avatar portrait based on selection from Avatar class
        userNameDisplay.setText(displayName);// // Sets username that has been passed in from Avatar class
        circle.setFill(new ImagePattern(chosenAvatar)); //Makes avatar a circle
        circle.setOnMouseEntered(event -> {circle.setFill(new ImagePattern(goToDashboard));});
        circle.setOnMouseExited(event -> { circle.setFill(new ImagePattern(chosenAvatar)); });
        circle.setOnMouseClicked(this::openPlayerDashboardMenu);
        chatSection.setFocusTraversable(true);
        chatSection.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> chatSection.isFocused());
        Timer messageChecker = new Timer();
        messageChecker.scheduleAtFixedRate(new getMessagesTask(), 0, 200);
        quitMuck.setOnAction(this::quitMuck);
        playerDashboardMenu.setOnAction(this::openPlayerDashboardMenu); //Opens player Dashboard
        // Creates and sets the player list service to be called every second, to update the current player list
        PlayerListService service = new PlayerListService(playerTextArea);
        service.setPeriod(Duration.seconds(1));
        service.start();
    }

    @FXML
        //Method that sends message when user presses enter
    void onEnter() {
        displayAndSend();
    }

    //Function that sends message when user clicks on arrow
    private void sendMessage(ActionEvent event) {
        displayAndSend();
    }

    // Use this method from external classes to open the gameplay window. Added by CA 14 Aug
    public static void constructor(MouseEvent event, String name, String display, String avatar) {
        userName = name;
        avatarID = avatar;
        displayName = display;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MuckController.class.getResource("/fxml/MuckWindow.fxml")));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(Objects.requireNonNull(MuckController.class.getResource("/css/style.css")).toExternalForm());
            //This line gets the Stage Information
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> stage.close());
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void constructor(String name, String avatar) {
        userName = name;
        avatarID = avatar;
    }

    public void openPlayerDashboardMenu(Event event) {
        try {
            PlayerDashboardController.playerDashboard(userName, displayName, avatarID);
            circle.setDisable(true);
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/PlayerDashboard.fxml")));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Muck2021");
            Scene scene = new Scene(parent);
            scene.getStylesheets().add("/css/style.css");
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnHiding(avatarEvent -> {
                try {
                    chosenAvatar = AvatarController.getPortrait(avatarID); // Updates avatar portrait based on selection from Avatar class
                    circle.setFill(new ImagePattern(chosenAvatar)); //Makes avatar a circle
                    circle.setDisable(false);
                    int x = gamePane1.getChildren().size();
                    Canvas currentCanvas = (Canvas) gamePane1.getChildren().get(x-1); //Finds the current canvas
                    new GameMap(currentCanvas, updatePlayerfn, getPlayersfn);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //Method that displays message in chat box
    private void displayAndSend() {
        message = messageBox.getText();
        if ((message.length() != 0)) {
            Tab currentTab = chatPane1.getSelectionModel().getSelectedItem();
            String currentID = currentTab.getId();
            if (currentID.equals("groupChat")) {
                groupChatBox.appendText(userName + ": " + message + "\n");
                messageBox.clear();


                userMessage currentMessage = new userMessage();
                currentMessage.setMessage(message);
                MuckClient.INSTANCE.send(currentMessage);

                //groupChatBox.appendText("UserName Here: "+ MuckClient.INSTANCE.getCurrentMessage()+ "\n");
            } else {
                int num = chatPane1.getTabs().indexOf(currentTab) + 1;
                TextArea currentChatBox = (TextArea) chatPane1.lookup("#chatbox" + num);
                currentChatBox.appendText(userName + ": " + message + "\n");
                messageBox.clear();
            }
        }
    }

    // Method that creates new chat tab
    @FXML
    private void addChatTab(ActionEvent event) {
        int numTabs = chatPane1.getTabs().size();
        int tabNum = numTabs + 1;
        Tab newTab = new Tab("Chat Name Here");
        newTab.setId("Chat" + tabNum);
        AnchorPane newAnc = new AnchorPane();
        newAnc.setStyle("-fx-background-color: #847f7f");
        newTab.setContent(newAnc);
        newTab.setClosable(true);
        TextArea chatX = new TextArea();
        chatX.setId("chatbox" + tabNum);
        chatX.setEditable(false);
        chatX.setPrefWidth(groupChatBox.getWidth());
        chatX.setPrefHeight(groupChatBox.getHeight());
        chatX.setWrapText(true);
        AnchorPane.setLeftAnchor(chatX, AnchorPane.getLeftAnchor(groupChatBox));
        AnchorPane.setRightAnchor(chatX, AnchorPane.getRightAnchor(groupChatBox));
        AnchorPane.setTopAnchor(chatX, AnchorPane.getTopAnchor(groupChatBox));
        AnchorPane.setBottomAnchor(chatX, AnchorPane.getBottomAnchor(groupChatBox));
        chatX.setLayoutX(groupChatBox.getLayoutX());
        chatX.setLayoutY(groupChatBox.getLayoutX());
        newAnc.getChildren().add(chatX);
        chatPane1.getTabs().add(newTab);
        chatPane1.getSelectionModel().select(newTab);
    }

    @FXML
    //Method that opens chat window and list window
    //The size of the chat depends on the size of the window. If the window is below under 1175px then only the chat opens
    //If the window is greater than the lists open as well
    private void openFullChat(ActionEvent event) {
        if (chatPane1.getScene().getWindow().getWidth() < 1175.0) {
            windowPane.setDividerPositions(0.7600);
            chatSplitPane.setDividerPositions(1.000);
        } else {
            windowPane.setDividerPositions(0.7300);
            chatSplitPane.setDividerPositions(0.6056);
        }
    }


    @FXML
    //Method that hides both chat window and list window
    private void hideChatWindow(ActionEvent event) {
        windowPane.setDividerPositions(0.999);
        chatSplitPane.setDividerPositions(1.0);
    }


    @FXML
    /* Function to launch the game. The game exists in the LandingPage.class.
    Basically what this is doing is removing the existing canvas and creating a new canvas
    for the new game. It then adds this canvas as a child of the pane.

     When the user presses exit, it essentially removes the canvas and adds the game map back in a new canvas
     */
    private void launchSpaceInvaders (ActionEvent event) {
        gamePane1.getChildren().clear();
        Canvas SICanvas = new Canvas();
        SICanvas.setHeight(gameCanvas.getHeight());
        SICanvas.setWidth(gameCanvas.getWidth());
        gamePane1.setCenter(SICanvas);
        BorderPane.setAlignment(SICanvas, Pos.CENTER);
        new LandingPage(gamePane1, SICanvas);
    }
    @FXML
    private void launchEnduringFantasy (ActionEvent event){
        gamePane1.getChildren().clear();
        Canvas EFCanvas = new Canvas();
        EFCanvas.setHeight(gameCanvas.getHeight());
        EFCanvas.setWidth(gameCanvas.getWidth());
        gamePane1.setCenter(EFCanvas);
        BorderPane.setAlignment(EFCanvas, Pos.CENTER);
        new LandingPageEf(gamePane1, EFCanvas);
    }

    @FXML
    private void launchFrogger (ActionEvent event){
        gamePane1.getChildren().clear();
        Canvas EFCanvas = new Canvas();
        EFCanvas.setHeight(gameCanvas.getHeight());
        EFCanvas.setWidth(gameCanvas.getWidth());
        gamePane1.setCenter(EFCanvas);
        BorderPane.setAlignment(EFCanvas, Pos.CENTER);
        new LandingPageFrogger(gamePane1, EFCanvas);
    }

    //Exits Muck consistent with the 'X' button when you choose exit from menu
    private void quitMuck(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Quit Muck?");
        alert.setContentText("Are you sure you want to quit Muck?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent()) {
            if (res.get().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                System.exit(0);
            }
        }
    }

    public class getMessagesTask extends TimerTask {
        public void run() {
            if (MuckClient.INSTANCE.getCurrentMessage() == null){
                
            }else {
                groupChatBox.appendText("UserName Here: "+ MuckClient.INSTANCE.getCurrentMessage()+ "\n");
            }

        }
    }
}
