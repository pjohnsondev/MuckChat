package muck.client;

import com.esotericsoftware.kryonet.Client;
import muck.client.PlayerListService;
import muck.client.MuckClient;
import muck.core.character.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientPlayerListTest {

    @Test
    public void clientContainsPlayerList() {
        Player player = new Player("TestUser");
        Client client = new Client();
        MuckClient muckClient = MuckClient.getINSTANCE();
        muckClient.players.put(client.getID(), player.getUsername());

        assertAll(
                () -> assertTrue(muckClient.players.containsKey(client.getID())),
                () -> assertTrue(muckClient.players.containsValue(player.getUsername()))
        );
    }
}
