package muck.client.utilities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import muck.client.App;
import muck.protocol.connection.userMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// INTERACT WITH GAME UI TO INITIATE INTERACTION

// IDENTIFY USER BEING INTERACTED WITH

// GENERATE INTERACTION TYPE

// SEND MESSAGE TO CHAT UI

public class InteractionController {

    private static final Logger logger = LogManager.getLogger(InteractionController.class);

    private userMessage um = new userMessage();
    private TextArea guiChatBox;
    private String defaultMessage = "Default Interaction Controller Message";

    InteractionController(Scene scene, TextArea guiChatBox){
        logger.info("Interaction controller Instantiated");
        um.setMessage(defaultMessage);
        this.guiChatBox = guiChatBox;

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getText() == "m"){
                    sendToChat();
                }
            }
        });
    };

    public void sendToChat(){
        guiChatBox.appendText(um.getMessage());
    }



    //receive reference to chat GUI chat box

    //TextArea guiChatBox = getChatBox();









}
