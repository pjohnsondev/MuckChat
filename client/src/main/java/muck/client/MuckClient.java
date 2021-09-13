package muck.client;

import muck.client.components.ActiveUser;
import muck.core.Login;
import muck.core.UpdatePlayerRequest;
import muck.core.Id;
import muck.core.Location;
import muck.core.LocationRequest;
import muck.core.LocationResponse;
import muck.core.ClientId;
import muck.core.character.AddCharacter;
import muck.core.structures.UserStructure;
import muck.core.user.SignUpInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esotericsoftware.kryonet.Client;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;
import java.util.LinkedList;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Handles network communication with the server. It's created as an enum to
 * ensure there is only one instance.
 */
public enum MuckClient {

	INSTANCE;

	String currentMessage;
	List<String> inMessages = new ArrayList<String>();

	ArrayList<String> messageBuffer = new ArrayList<String>();
	HashMap<Integer, String> players = new HashMap<Integer, String>();
	List<Sprite> playerSprites = new ArrayList<Sprite>();

	public static MuckClient getINSTANCE() {
		return INSTANCE;
	}

	/** A logger for logging output */
	private static final Logger logger = LogManager.getLogger(MuckClient.class);

	public static void logError(Exception message) {
		logger.error(message);
	}

	public static void logError(String message) {
		logger.error(message);
	}

	public static void logInfo(String info) {
		logger.info(info);
	}

	/** The KryoNet client */
	Client client;
	public static final Id<ClientId> clientId = new Id<ClientId>();

	public List<Sprite> getPlayerSprites() {
		client.sendTCP(new LocationRequest(clientId));
		return this.playerSprites;
	}

	public Client getClient() {
		return client;
	}

	public void updatePlayerLocation(String avatar, Location location) {
		var req = new UpdatePlayerRequest(clientId, avatar, location);
		// logger.info("Updating my location in the gamemap..."); //Commented this out
		// because it was spamming the client logger.
		client.sendTCP(req);
	}

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

		// // Create random dummy credentials
		// String username = "TestUser_" + Calendar.getInstance().get(Calendar.SECOND) %
		// 100;
		//
		// // Create a new user account
		// signUp(username, "TestPassword", "Test User");
		//
		// // Login an existing user
		// login(username, "TestPassword");

		// Add a Ping listener. Still being used for debugging.
		client.addListener(ListenerBuilder.forClass(Ping.class)
				.onReceive((conn, ping) -> logger.info("Ping received from {}", conn.getID())));

		/*
		 * // Listener for the message sent back from the server.
		 * client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive(
		 * (connID, serverMessage) -> logger.info("Message from the server was: {}",
		 * serverMessage.getMessage())));
		 */

		client.addListener(ListenerBuilder.forClass(AddCharacter.class).onReceive((connection, addCharacter) -> {
			logger.info("Received new character from the server: {}", addCharacter.getCharacter().getIdentifier());

			logger.debug("Initial location of new character is: X:{}, Y:{}", addCharacter.getLocation().getX(),
					addCharacter.getLocation().getY());

			// TODO: Notify of the new character and its location so that it can be placed
			// on the map.
		}));

		// Add a listener to listen for an arraylist message from the server, this is
		// then stored on the client instance as the player array
		client.addListener(ListenerBuilder.forClass(HashMap.class).onReceive((conn, playerList) -> {
			logger.info("Player list: {} received from {}", playerList, conn.getID());
			players.clear();
			players.putAll(playerList);
			logger.info("Clients playerlist is {}", players);
		}));

		client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, clientMessage) -> {
			logger.info("Message recieved was: {}", clientMessage.getMessage());
			currentMessage = clientMessage.getMessage();
			inMessages.add(clientMessage.getMessage());
		}));

		// When a chatlog object is detected, add it to the queue.
		client.addListener(ListenerBuilder.forClass(chatLog.class).onReceive((connID, chatLog) -> {
			List<String> newChatLog = chatLog.getChatLog();
			for (int i = 0; i < newChatLog.size(); i++) {
				inMessages.add(newChatLog.get(i));
			}
		}));

		client.addListener(ListenerBuilder.forClass(LocationResponse.class).onReceive((connID, response) -> {
			logger.info("List of locations receieved, building sprites");
			var data = response.data;
			this.playerSprites = data.stream().map(p -> new Sprite(p.x, p.y)).collect(Collectors.toList());
		}));

		// setActiveUser
		client.addListener(ListenerBuilder.forClass(UserStructure.class).onReceive((connID, response) -> {
			ActiveUser.getInstance().setUserStructure(response);
		}));
	}

	/**
	 * Login user to the game.
	 *
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		Login login = new Login(username, password, clientId);

		client.sendTCP(login);
	}

	/**
	 * Create a new account on the server
	 *
	 * @param username    should not be null or empty
	 * @param password    should not be null or empty
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

	public synchronized void requestPlayerLocations() {
		client.sendTCP(clientId);

	}

	public synchronized void send(Object message) {
		client.sendTCP(message);
	}

	public synchronized void getChatHistroy() {
		chatLog newChat = new chatLog();
		client.sendTCP(newChat);
	}

	/*
	 * Simple getter for the currentMessage stored in the client. TODO: Ensure that
	 * message buffer is cleared after it has been printed to the chatui to avoid
	 * old messages coming through again at the next timer. Note: Probably should
	 * add ways to get timestamps/etc.
	 *
	 */
	public synchronized List<String> getCurrentMessage() {
		List<String> outMessages = inMessages;
		inMessages.clear();

		if (inMessages.size() > 0) {
			return outMessages;
		} else {
			return inMessages;
		}
	}

}
