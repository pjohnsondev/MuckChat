package muck.server;

import muck.server.database.Database;
import muck.server.models.models.MessageModel;
import muck.server.models.models.UserModel;
import muck.server.services.ChatDBService;
import muck.server.structures.ChatMessageStructure;
import muck.server.testHelpers.TestDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.time.LocalDateTime;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatDatabaseTest {

    private static final Logger logger = LogManager.getLogger(DatabaseTest.class);

    private Database testDb;
    private ChatDBService chatService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        logger.info("This message prints BEFORE each test runs");
//         reset database using testDB
        testDb = TestDatabase.getINSTANCE();
        chatService = new ChatDBService(testDb);
    }

    /**
     * Close database connection after each test
     */
    @AfterEach
    public void afterEach() {
        logger.info("This message prints AFTER each test runs");
        chatService.closeConnections();
    }

    @Test
    public void injectMessage(){
        ChatMessageStructure msg = new ChatMessageStructure();
        msg.setChannelId(1);
        msg.setUserName("Peter Kiefel");
        msg.setMessage("This is a message to store");
        msg.setTimeStamp(new Date(System.currentTimeMillis()));
        chatService.storeMessage(msg);
        testDb.query("SELECT * from messages");
        ResultSet rs = null;
        try {
            rs = testDb.getResultSet();
            assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void retrieveGroupChatMessagesTest() {
        ChatMessageStructure msg = new ChatMessageStructure();
        msg.setChannelId(1);
        msg.setUserName("Peter Kiefel");
        msg.setMessage("This is a message to store");
        msg.setTimeStamp(new Date(System.currentTimeMillis()));
        chatService.storeMessage(msg);
        msg.setMessage("Another Message");
        chatService.storeMessage(msg);
        ArrayList<ChatMessageStructure> rs;
        rs = chatService.getMessages(1);
        assertTrue(rs.size()>0);
    }

}
