package muck.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.Array;

import java.util.Arrays;

/**
 * Interaction Listener will listen to the chat feed for valid 'interaction commands' and pass them to the
 * Interaction Controller when heard
 */

//TODO: link to MuckServer to pass chat lines from the listener to the class
//TODO: analyse chat strings and identify valid interactions
//TODO: identify the user the message has come from
//TODO: pass user and interaction type to the Interaction Controller

    

public class InteractionListener {

    private static final Logger logger = LogManager.getLogger(InteractionListener.class);
    private static final String[] COMMANDS = new String[]{"wave"} ;
    
    
    
    InteractionListener(){};
    
    public static void handle(int id, String message){

        //just log what is heard for now
        logger.info("Heard message from " + Integer.toString(id) + ". Message: " + message);

    }

    public static boolean isValidCommand(String input){

        char[] data = input.toCharArray();

        if (data[0] != '/') {
            return false;
        }
        else{
            return Arrays.stream(COMMANDS).anyMatch(input.substring(1)::equals);
        }
    }
    

}
