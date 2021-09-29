package muck.server;

import muck.core.*;
import muck.core.character.AddCharacter;
import muck.core.character.CharacterDoesNotExistException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import muck.core.character.Player;
import muck.core.user.SignUpInfo;
import muck.server.Exceptions.UserNameAlreadyTakenException;
import muck.server.models.ModelRegister;
import muck.server.services.UserService;
import muck.core.structures.UserStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;

import java.sql.SQLException;
import java.util.*;

import java.util.HashMap;

/**
 * The central body of the server. This should avoid getting too large, but
 * should have references to any of the necessary components. It is a singleton.
 */
public enum MuckServer {

    /**
     * The single instance of the server that exists
     */
    INSTANCE;

    /**
     * Gets the MuckServer object
     */
    public static MuckServer getINSTANCE() {
        return INSTANCE;
    }

    /**
     * A logger for logging output
     */
    private static final Logger logger = LogManager.getLogger(MuckServer.class);

    // Create a new KryoNet server
    Server kryoServer;

    // Tries to make handling background tasks easier
    WorkerManager workerManager = new WorkerManager();

    ICharacterLocationTracker<ClientId> tracker = new CharacterLocationTracker<ClientId>();

    HashMap<Integer, String> players = new HashMap<Integer, String>();

    //A temporary queue to store chatlogs.
    List<String> chatQueue = new ArrayList<String>();
	//Listens to the chat stream to handle interaction commands - added 26/8  - mhay23@myune.edu.au
	InteractionListener interactionListener = new InteractionListener();

    /**
     * Sets up the KryoNet server that will handle communication
     */
    synchronized void startKryo(KryoServerConfig config) throws IOException {

        if (kryoServer != null) {
            throw new IllegalStateException("Attempted to start KryoServer when it was already started");
        }

        // Create a new KryoNet server
        kryoServer = new Server() {
            protected Connection newConnection() {
                return new MuckConnection();
            }
        };
        kryoServer.start();

        // Register the protocol classes with Kryo
        Protocol.register(kryoServer.getKryo());
        // Make necessary migrations to set up database (their not really migrations but what can you do)
        new ModelRegister().makeMigrations();
        // Bind the server to the configured ports
        kryoServer.bind(config.getTcpPort(), config.getUdpPort());

        /**
         * This is a temporary call to help populate the server with an initial test user
         * If you are seeing errors in playermanagertest or from the server regarding COLUMN not
         * existing, please delete the muckdb folder and the testdb folder from the server
         * The next time you start the server it will automatically create a new database with
         * the latest structure
         */

        addTestUser("Test", "Testdisplay", "password");
        // Adds a listener to listen for clients disconnecting from the server, then
        // removes them from the players hashmap and sends to all connected clients.
        addListener(ListenerBuilder.forClass(Disconnect.class).onReceive((conn, disconnect) -> {
            logger.info("Disconnect has been called");
            players.remove(conn.getID()); // This will obtain the index of the player
            logger.info("Player connection id's are: {} disconnected: {}", players, disconnect);
            kryoServer.sendToAllExceptTCP(conn.getID(), players);
        }));

        // Add a Ping listener. Still being used for debugging.
        addListener(ListenerBuilder.forClass(Ping.class).onReceive((conn, ping) -> {

            logger.info("Ping received from {}", conn.getID());

            // Let's just demonstrate how to send messages to worker actors, by sending this message to one.
            workerManager.schedule(ping, reply -> {
                logger.info("I sent my ping to a background worker, and all I got in return was this lousy {}", reply);
            });
        }));
        /**
         * Listens for a userMessage class coming from the client.
         * Adds the message to the buffer in the server.
         * Send message to all other clients.
         * Author: Low Expectations.
         */
        addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, clientMessage) -> {
            logger.info("Recieved a message!");
            logger.info("Message received from {}", connID.getID());
            logger.info("Message is: {}", clientMessage.getMessage());

            //Send to interaction listener - added 26/8  - mhay23@myune.edu.au
            InteractionListener.handle(connID.getID(), clientMessage.getMessage());

            if (InteractionListener.isValidCommand(clientMessage.getMessage())) {
            	 logger.info("The interaction was correct.");
                kryoServer.sendToAllTCP(new Interaction("A " + clientMessage.getMessage().substring(1) + " was sent!", connID.getID()));
            }
            else {
            logger.info(clientMessage);
            chatQueue.add(clientMessage.getMessage());
            kryoServer.sendToAllExceptTCP(connID.getID(), clientMessage); //Send to all clients connected except sending client.
            }
        }));
        /**
         * Listens for a newChatLog class coming from the client (or another class).
         * Acts as a signal to either create a new table in the database or could be used as chat history.
         * Author: Low Expectations.
         */
        addListener(ListenerBuilder.forClass(chatLog.class).onReceive((connID, recievedLog) -> {
                  connID.sendTCP(recievedLog);
                }
        ));

		addListener(ListenerBuilder.forClass(muck.core.LocationRequest.class).onReceive((connection, lr) -> {
			List<Triple<AvatarLocation, MapId, Location>> locs = tracker.getAllLocationsExceptId(lr.id);
			kryoServer.sendToTCP(connection.getID(), new LocationResponse(locs));
		}));

		// listen for signup requests from the server
		addListener(ListenerBuilder.forClass(SignUpInfo.class).onReceive((connection, signup) -> {
			createAccount(signup, (MuckConnection) connection);
		}));

		// listen for login requests from the server
		addListener(ListenerBuilder.forClass(Login.class).onReceive((connection, login) -> {
			loginPlayer(login, (MuckConnection) connection);
		}));

        addListener(ListenerBuilder.forClass(UserStructure.class).onReceive((connection, userStructure) -> {
            try {
                new UserService().updateUser(userStructure);
            } catch (SQLException exception) {
                logger.info(exception.getMessage());
            }
        }));

        addListener(ListenerBuilder.forClass(UpdatePlayerRequest.class).onReceive((connection, req) -> {
			    tracker.updateLocationById(req.id, req.avatar, req.mapId, req.location);
		}));

		addListener(ListenerBuilder.forClass(ClientLocationsRequest.class).onReceive((connection, req) -> {
			var locs = tracker.getAllClientLocationsExcept(req.clientId);

			kryoServer.sendToTCP(connection.getID(), locs);

		}));

	}

	/**
	 * Creates an account based on data received from a signup request Sends the
	 * signup response from the server to the client
	 *
	 * @param signUpInfo
	 * @param connection
	 */
	public void createAccount(SignUpInfo signUpInfo, MuckConnection connection) {
		logger.info("Attempting to create account {}.", signUpInfo.getUsername());

		PlayerManager playerManager = new PlayerManager(new UserService());
		userMessage userMessage = new userMessage();
		UserStructure userStructure = new UserStructure();
		userStructure.username = signUpInfo.getUsername();
		userStructure.password = signUpInfo.getPassword();
		userStructure.displayName = signUpInfo.getDisplayName();

		SignupResponse signupResponse = new SignupResponse();
		signupResponse.setUsername(userStructure.username);

		try {
			Player player = playerManager.signupPlayer(userStructure);
			logger.info("Sign up successful for {}", player.getUsername());

			players.put(connection.getID(), player.getUsername());
			kryoServer.sendToAllTCP(players);
			logger.info("Players are {}", players.values());
			UserStructure returnedUser = playerManager.getUser(userStructure);
			userMessage.setMessage("Signup successful", signUpInfo.getUsername());
			kryoServer.sendToTCP((connection.getID()), userMessage);
			kryoServer.sendToTCP((connection.getID()), returnedUser);

			signupResponse.setMessage("Your account has been created successfully.");
			signupResponse.setResultCode(200);
		} catch (UserNameAlreadyTakenException ex) {
			userMessage.setMessage(ex.getMessage(), signUpInfo.getUsername());
			logger.info(ex.getMessage());
			kryoServer.sendToTCP(connection.getID(), userMessage);

			signupResponse.setMessage("This username has already been taken.");
			signupResponse.setResultCode(409);
		} catch (BadRequestException ex) {
			logger.info("error in muckServer signup badrequestexception catch");
			userMessage.setMessage(ex.getMessage(), signUpInfo.getUsername());
			kryoServer.sendToTCP(connection.getID(), userMessage);

			signupResponse.setMessage("Invalid input provided.");
			signupResponse.setResultCode(400);
		} catch (Exception ex) {
			userMessage.setMessage("Error setting user to database", signUpInfo.getUsername());

			kryoServer.sendToTCP(connection.getID(), userMessage);
			ex.printStackTrace();

			signupResponse.setMessage("Something went wrong.");
			signupResponse.setResultCode(500);
		}

		kryoServer.sendToTCP(connection.getID(), signupResponse);
	}

	/**
	 * Log in player/user based on data received from a login request Sends the
	 * login response from the server to the client
	 *
	 * @param login
	 * @param muckConnection
	 */
	public void loginPlayer(Login login, MuckConnection muckConnection) {
		logger.info("Attempting to log in");
		logger.debug("{} is trying to log in", login.getUsername());

		PlayerManager playerManager = new PlayerManager(new UserService());

		UserStructure userStructure = new UserStructure();
		userStructure.username = login.getUsername();
		userStructure.password = login.getPassword();

		UserStructure returnedUser = new UserStructure();

		Player player = null;

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername(userStructure.username);

		try {
			player = playerManager.loginPlayer(userStructure);
			// set user as active user
			returnedUser = playerManager.getUser(userStructure);
			userMessage testMessage = new userMessage(); // Create new message to send back.
			testMessage.setMessage("Login Successful", login.getUsername());
			kryoServer.sendToTCP((muckConnection.getID()), testMessage);
			kryoServer.sendToTCP((muckConnection.getID()), returnedUser);

			muckConnection.setCharacter(player);

			logger.info("Login successful for {}", login.getUsername());

			loginResponse.setMessage("You have been logged in successfully.");
			loginResponse.setResultCode(ResponseCodes.OK);

			if (!players.containsKey(muckConnection.getID())) {
				players.put(muckConnection.getID(), login.getUsername());
				kryoServer.sendToAllTCP(players);
				logger.info("Players are {}", players.values());
			}
			/**
			 * This has been commented out as there is an issue deserialising AddCharacter
			 * on the client the error is causing the client to disconnect from the server
			 * so I have commented it out AddCharacter addCharacter =
			 * addCharacter(login.getClientId(), player);
			 *
			 * kryoServer.sendToAllTCP(addCharacter);
			 */

		} catch (DuplicateLoginException ex) {
			userMessage testMessage = new userMessage(); // Create new message to send back.
			testMessage.setMessage("Duplicate login", login.getUsername());
			kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client

			loginResponse.setMessage("You are already logged in.");
			loginResponse.setResultCode(ResponseCodes.DUPLICATE_LOGIN);
		} catch (CharacterDoesNotExistException ex) {
			userMessage testMessage = new userMessage(); // Create new message to send back.
			testMessage.setMessage("Character does not exist. Please register.", login.getUsername());
			kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client

			loginResponse.setMessage("Your account does not exist. Please sign up before logging in.");
			loginResponse.setResultCode(ResponseCodes.ACCOUNT_DOES_NOT_EXIST_CODE);
		} catch (AuthenticationFailedException ex) {
			userMessage testMessage = new userMessage(); // Create new message to send back.
			testMessage.setMessage("Supplied credentials are invalid.", login.getUsername());
			kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client

			loginResponse.setMessage("Invalid username and/or password.");
			loginResponse.setResultCode(ResponseCodes.UNAUTHORISED);
		} catch (Exception ex) {
			userMessage testMessage = new userMessage(); // Create new message to send back.
			testMessage.setMessage("Error setting user to database", login.getUsername());
			kryoServer.sendToTCP(muckConnection.getID(), testMessage);
			ex.printStackTrace();

			loginResponse.setMessage("Something went wrong.");
			loginResponse.setResultCode(500);
		}

		kryoServer.sendToTCP(muckConnection.getID(), loginResponse);
	}

	public AddCharacter addCharacter(Id<ClientId> id, Player character) {
		Location location = new muck.core.Location(0, 0);

		AddCharacter addCharacter = new AddCharacter(character, location);

		tracker.addClient(id, null, null, new muck.core.Location(location.getX(), location.getY()));

		logger.info("Character added successfully {}", character.getIdentifier());

		return addCharacter;
	}

	/**
	 * Stops the KryoNet server.
	 */
	synchronized void stopKryo() {
		kryoServer.stop();
		kryoServer = null;
	}

	/**
	 * Registers a listener with the KryoNet server.
	 *
	 * @param l
	 */
	public void addListener(Listener l) {
		if (kryoServer == null) {
			throw new IllegalStateException("Attempted to register listener when stopped.");
		}

		kryoServer.addListener(l);
	}

	public void addTestUser(String username, String displayName, String password) {
		logger.info("Attempting to create account {}.", username);

		PlayerManager playerManager = new PlayerManager(new UserService());
		userMessage userMessage = new userMessage();
		UserStructure userStructure = new UserStructure();
		userStructure.username = username;
		userStructure.password = password;
		userStructure.displayName = displayName;

		try {
			Player player = playerManager.signupPlayer(userStructure);
			logger.info("Sign up successful for {}", player.getUsername());
		} catch (UserNameAlreadyTakenException ex) {
			logger.info(ex.getMessage());
		} catch (BadRequestException ex) {
			logger.info("error in muckServer signup badrequestexception catch");
		} catch (RuntimeException ex) {
			userMessage.setMessage("Error setting user to database", username);
			ex.printStackTrace();
		} catch (Exception ex) {
			logger.info("error in playermanager signup exception catch");
		}
	}

}
