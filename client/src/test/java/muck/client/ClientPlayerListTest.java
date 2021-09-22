package muck.client;

import com.esotericsoftware.kryonet.Client;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import muck.core.character.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientPlayerListTest {

    private static final Logger logger = LogManager.getLogger(ClientPlayerListTest.class);
    private Player player;
    private Client client;
    private MuckClient muckClient;
    private TextArea textBox;

    @BeforeEach
    public void beforeEach() {
        Player player = new Player("TestUser");
        Client client = new Client();
        muckClient = MuckClient.getINSTANCE();
        muckClient.players.put(client.getID(), player.getUsername());
        ArrayList textBox = new ArrayList();
    }

    @Test
    @Disabled
    public void clientContainsPlayerList() {
        assertAll(
                () -> assertTrue(muckClient.players.containsKey(client.getID())),
                () -> assertTrue(muckClient.players.containsValue(player.getUsername()))
        );
    }

    @Test
    @Disabled
    public void fillPlayerListTest() {
        logger.info("Playerlist contains: " + muckClient.players);
        PlayerListService service = new PlayerListService(textBox);
        service.fillPlayerList(textBox);
        assertTrue(textBox.getText().contains("TestUser"));
    }

    @Test
    @Disabled
    public void playerListServiceAddTest() {
        logger.info("Playerlist contains: " + muckClient.players);
        PlayerListService service = new PlayerListService(textBox);
        service.setPeriod(Duration.seconds(1));
        service.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("Unable to make thread sleep " + e);
        }
        logger.info("The textbox contains: " + textBox.getText());
        assertTrue(textBox.getText().contains("TestUser"));
    }

    @Test
    @Disabled
    public void playerListServiceRemoveTest() {
        logger.info("Playerlist contains: " + muckClient.players);
        PlayerListService service = new PlayerListService(textBox);
        service.setPeriod(Duration.seconds(1));
        service.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("Unable to make thread sleep " + e);
        }
        logger.info("The textbox contains: " + textBox.getText());
        muckClient.players.remove(client.getID());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("Unable to make thread sleep " + e);
        }
        logger.info("The textbox contains: " + textBox.getText());
        assertTrue(!textBox.getText().contains("TestUser"));
    }
}
