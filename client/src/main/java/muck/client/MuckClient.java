package muck.client;

import muck.core.Login;
import muck.core.character.AddCharacter;
import muck.core.user.SignUpInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esotericsoftware.kryonet.Client;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles network communication with the server. It's created as an enum to
 * ensure there is only one instance.
 */
public enum MuckClient {

	INSTANCE;

	userMessage currentMessage;
	ArrayList<String> players = new ArrayList<String>();

	public static MuckClient getINSTANCE() {
		return INSTANCE;
	}

	/** A logger for logging output */
	private static final Logger logger = LogManager.getLogger(MuckClient.class);

	/** The KryoNet client */
	Client client;
	public static final muck.core.Id<muck.core.character.Character> clientId = new muck.core.Id<muck.core.character.Character>();

	public synchronized void connect(KryoClientConfig config) throws IOException {
		if (client != null) {
			throw new IllegalStateException("Starting connection when already started");
		}

		client = new Client();
		client.start();

		// Register the protocol classes with Kryo
		Protocol.register(client.getKryo());

		// Connect to the server
		client.connect(config.getTimeOut(), config.getDestinationIp(), config.getTcpPort(), config.getUdpPort());

        // Create random dummy credentials
        String username = "TestUser_" + Calendar.getInstance().get(Calendar.SECOND) % 100;

        // Create a new user account
        signUp(username, "TestPassword", "Test User");

        // Login an existing user
        login(username, "TestPassword");

		// Add a Ping listener. Still being used for debugging.
		client.addListener(ListenerBuilder.forClass(Ping.class)
				.onReceive((conn, ping) -> logger.info("Ping received from {}", conn.getID())));

		// Listener for the message sent back from the server.
		client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive(
				(connID, serverMessage) -> logger.info("Message from the server was: {}", serverMessage.getMessage())));

		// A listener for an interaction from the server (added 27/8 by Arlo Taylor)
		client.addListener(ListenerBuilder.forClass(Interaction.class).onReceive(
				(connID, serverInteraction) -> logger.info("Interaction from the server was: {}", serverInteraction.getCommand())));

		client.addListener(ListenerBuilder.forClass(AddCharacter.class).onReceive((connection, addCharacter) -> {
			logger.info("Received new character from the server: {}", addCharacter.getCharacter().getIdentifier());

			logger.debug("Initial location of new character is: X:{}, Y:{}", addCharacter.getLocation().getX(),
					addCharacter.getLocation().getY());

			// TODO: Notify of the new character and its location so that it can be placed
			// on the map.
		}));

		// Add a listener to listen for an arraylist message from the server, this is
		// then stored on the client instance as the player array
		client.addListener(ListenerBuilder.forClass(ArrayList.class).onReceive((conn, playerList) -> {
			logger.info("Player list: {} received from {}", playerList, conn.getID());
			players = playerList;
			logger.info("Clients playerlist is {}", players);
		}));

		client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, clientMessage) -> {
			logger.info("Message recieved was: {}", clientMessage.getMessage());
			currentMessage = clientMessage;
		}));
	}

    /**
     * Login user to the game.
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        Login login = new Login(username, password);

		client.sendTCP(login);
	}

	/**
	 * Create a new account on the server
	 *
	 * @param username should not be null or empty
	 * @param password should not be null or empty
	 * @param displayName should not be null or empty
	 *
	 */
	public void signUp(String username, String password, String displayName) {
		logger.info("Send new account details to server {}", username);

		SignUpInfo signup = new SignUpInfo(username, password, displayName);

		client.sendTCP(signup);
	}

	public synchronized void disconnect() throws IOException {
		if (client == null) {
			throw new IllegalStateException("Closing connection when not started");
		}

		client.sendTCP(new Disconnect());
		client.dispose();
		client = null;
	}

	public synchronized void send(Object message) {
		client.sendTCP(message);
	}

	// Simple getter for the currentMessage stored in the client.
	// Note: Probably should add wayus to get timestamps/etc.
	public synchronized Object getCurrentMessage() {
		return currentMessage.getMessage();
	}

}
