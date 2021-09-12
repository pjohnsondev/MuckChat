package muck.server;

import muck.core.Id;
import muck.core.Location;
import muck.core.ClientId;
import muck.core.Login;
import muck.core.Pair;
import muck.core.UpdatePlayerRequest;
import muck.core.character.AddCharacter;
import muck.core.character.CharacterDoesNotExistException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import muck.core.character.Player;
import muck.core.structures.PlayerStructure;
import muck.core.user.SignUpInfo;
import muck.server.models.ModelRegister;
import muck.server.services.PlayerService;
import muck.server.services.UserService;
import muck.core.structures.UserStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.protocol.*;
import muck.protocol.connection.*;

import java.io.IOException;

import java.sql.SQLException;
import java.util.*;


import muck.core.LocationResponse;

import java.util.HashMap;

/**
 * The central body of the server. This should avoid getting too large, but should have references to
 * any of the necessary components. It is a singleton.
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

        PlayerService playerService = new PlayerService();
        try {
            PlayerStructure playerStructure = playerService.findByUserId(1);
            System.out.println(playerStructure.attack);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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
         * Calls a worker to handle storing the message in the chat log (not done yet).
         */
        addListener(ListenerBuilder.forClass(userMessage.class).onReceive((connID, clientMessage) -> {
            logger.info("Recieved a message!");
            logger.info("Message received from {}", connID.getID());
            logger.info("Message is: {}", clientMessage.getMessage());
            logger.info(clientMessage);
            chatQueue.add(clientMessage.getMessage());
            kryoServer.sendToAllTCP(clientMessage); //Send to all clients connected. Can be switched to send only to one client.
        }));
        /**
         * Listens for a newChatLog class coming from the client (or another class).
         * Acts as a signal to tell chatCreateTable to create a new chat log with specified name.
         */
        addListener(ListenerBuilder.forClass(chatLog.class).onReceive((connID, recievedLog) -> {
                  connID.sendTCP(recievedLog);
                }
        ));

        addListener(ListenerBuilder.forClass(Login.class).onReceive((connection, login) -> {
            loginPlayer(login, (MuckConnection) connection);
        }));

        addListener(ListenerBuilder.forClass(muck.core.LocationRequest.class).onReceive((connection, lr) -> {
            logger.info(String.format("Recieved a request for player locations from clientId: %s", lr.id));
            List<Pair<String, Location>> locs = tracker.getAllLocationsExceptId(lr.id);
            logger.info(String.format("Sending back: %s", locs.toString()));
            kryoServer.sendToTCP(connection.getID(), new LocationResponse(locs));
        }));

        addListener(ListenerBuilder.forClass(SignUpInfo.class).onReceive((connection, signup) -> {
            createAccount(signup, (MuckConnection) connection);
        }));

        addListener(ListenerBuilder.forClass(UpdatePlayerRequest.class).onReceive((connection, req) -> {
            logger.info(String.format("Recieved a request to update player for clientId: %s", req.id));
            tracker.updateLocationById(req.id, req.avatar, req.location);
        }));

    }

    public void createAccount(SignUpInfo signUpInfo, MuckConnection connection) {
        logger.info("Attempting to create account {}.", signUpInfo.getUsername());

        PlayerManager playerManager = new PlayerManager(new UserService());
        userMessage userMessage = new userMessage();
        UserStructure userStructure = new UserStructure();
        userStructure.username = signUpInfo.getUsername();
        userStructure.password = signUpInfo.getPassword();
        userStructure.displayName = signUpInfo.getDisplayName();

        try {
            // Insert player into database
            Player player = playerManager.signupPlayer(userStructure);
            logger.info("Sign up successful for {}", player.getUsername());

            // add player to set of all plauers
            players.put(connection.getID(), player.getUsername());
            kryoServer.sendToAllTCP(players);
            logger.info("Players are {}", players.values());

            // send user signup success message
            userMessage.setMessage("Your account has been created successfully. Username: " + player.getUsername());
            kryoServer.sendToTCP((connection.getID()), userMessage);

            // set new user as active user
            kryoServer.sendToTCP((connection.getID()), userStructure);

        } catch (BadRequestException ex) {
            ex.printStackTrace();

            userMessage.setMessage("Invalid sign up details provided. Please provide valid details. Username: "
                    + signUpInfo.getUsername());
            kryoServer.sendToTCP(connection.getID(), userMessage);
        }
    }

    public void loginPlayer(Login login, MuckConnection muckConnection) {
        logger.info("Attempting to log in");
        logger.debug("{} is trying to log in", login.getUsername());

        PlayerManager playerManager = new PlayerManager(new UserService());

        UserStructure userStructure = new UserStructure();
        userStructure.username = login.getUsername();
        userStructure.password = login.getPassword();

        Player player = null;

        try {
            player = playerManager.loginPlayer(userStructure);
            // set user as active user
            kryoServer.sendToTCP((muckConnection.getID()), userStructure);

        } catch (DuplicateLoginException ex) {
            userMessage testMessage = new userMessage(); // Create new message to send back.
            testMessage.setMessage("Duplicate login");
            kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client
        } catch (CharacterDoesNotExistException ex) {
            userMessage testMessage = new userMessage(); // Create new message to send back.
            testMessage.setMessage("Character does not exist. Please register.");
            kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client
        } catch (AuthenticationFailedException ex) {
            userMessage testMessage = new userMessage(); // Create new message to send back.
            testMessage.setMessage("Supplied credentials are invalid.");
            kryoServer.sendToTCP(muckConnection.getID(), testMessage); // send message back to client
        }

        muckConnection.setCharacter(player);

        logger.info("Login successful for {}", login.getUsername());
        if (!players.containsKey(muckConnection.getID())) {
            players.put(muckConnection.getID(), login.getUsername());
            kryoServer.sendToAllTCP(players);
            logger.info("Players are {}", players.values());
        }

        AddCharacter addCharacter = addCharacter(login.getClientId(), player);

        kryoServer.sendToAllTCP(addCharacter);
    }

    public AddCharacter addCharacter(Id<ClientId> id, Player character) {
        Location location = new muck.core.Location(0, 0);

        AddCharacter addCharacter = new AddCharacter(character, location);

        tracker.addClient(id, null, new muck.core.Location(location.getX(), location.getY()));

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

}
