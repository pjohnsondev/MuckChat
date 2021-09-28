package muck.client;

import muck.client.components.ActiveUser;
import muck.core.Login;
import muck.core.MapId;
import muck.core.UpdatePlayerRequest;
import muck.core.Id;
import muck.core.Location;
import muck.core.LocationRequest;
import muck.core.LocationResponse;
import muck.core.AvatarLocation;
import muck.core.ClientId;
import muck.core.character.AddCharacter;
import muck.core.character.Player;
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

	HashMap<Integer, String> players = new HashMap<Integer, String>();
	List<Sprite> playerSprites = new ArrayList<Sprite>();

	Player currentPlayer;

	public static MuckClient getINSTANCE() {
		return INSTANCE;
	}

	/**
	 * A logger for logging output
	 */
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

	/**
	 * The KryoNet client
	 */
	Client client;
	public static final Id<ClientId> clientId = new Id<ClientId>();

	public List<Sprite> getPlayerSprites() {
		client.sendTCP(new LocationRequest(clientId));
		return this.playerSprites;
	}

	public Client getClient() {
		return client;
	}

	public void updatePlayerLocation(String avatar, Integer mapId, Location location) {
		var req = new UpdatePlayerRequest(clientId, new AvatarLocation(avatar), new MapId(mapId), location);
		client.sendTCP(req);
	}

	// Set current player
	public void setCurrentPlayer(String username) {
		currentPlayer = new Player(username);
	}

	public synchronized void connect(KryoClientConfig config) throws IOException {
		//inMessages.add("hello");
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

		/**
		 * Listens for a userMessage coming from the server.
		 * Adds the message to local buffer.
		 * Author: Low Expectations.
		 */
		client.addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, serverMessage) -> {
			logger.info("Message recieved was: {}", serverMessage.getMessage());
			currentMessage = serverMessage.getMessage();
			inMessages.add(serverMessage.getMessage());
			logger.info("inMessages size is: {}", inMessages.size());
			checkLoginMessages(serverMessage.getMessage());
		}));

		/**
		 * Listens for incoming chatLogs as a signal to accept a chat history coming in.
		 * Author: Low Expectations.
		 */
		client.addListener(ListenerBuilder.forClass(chatLog.class).onReceive((connID, chatLog) -> {
			List<String> newChatLog = chatLog.getChatLog();
			for (int i = 0; i < newChatLog.size(); i++) {
				inMessages.add(newChatLog.get(i));
			}
		}));

		client.addListener(ListenerBuilder.forClass(LocationResponse.class).onReceive((connID, response) -> {
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
	 */
	public void signUp(String username, String password, String displayName) {
		logger.info("Send new account details to server {}", username);

		SignUpInfo signup = new SignUpInfo(username, password, displayName);

		client.sendTCP(signup);
	}

	public void checkLoginMessages(String message) {
		for (String expectedMessage : ActiveUser.getInstance().serverResponses) {
			if (message.equals(expectedMessage)) {
				ActiveUser.getInstance().setServerMessage(message);
				logger.info(expectedMessage);
			}
		}
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

	/**
	 * getCurrentMessage() is the method called by MuckController ot get the oldest message coming through to the client.
	 * If there are no messages at that point in time, empty is returned and MuckControlller deals with that, otherwise, oldest
	 * message is returned and cleared from buffer.
	 * Author: Low Expectations  - latest revision by Ryan Birch (rbirch4@myune.edu.au).
	 * @return
	 */
	public synchronized List<String> getCurrentMessage() {
		if (inMessages.size() > 0) {
			String outMessage = inMessages.get(0);
			List<String> outMessages = new ArrayList<String>();
			outMessages.add(outMessage);
			inMessages.remove(0);
			return outMessages;
		} else {
			return inMessages;
		}
	}
}
