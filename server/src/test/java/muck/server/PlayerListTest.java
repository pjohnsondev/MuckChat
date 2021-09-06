package muck.server;

import com.esotericsoftware.kryonet.Client;
import muck.core.Login;
import muck.core.character.Player;
import muck.core.user.SignUpInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerListTest {

    @Test
    // Test that the server contains the playerlist and that we can add to it
    public void serverContainsPlayerList() {
        Player player = new Player("TestUser");
        Client client = new Client();
        MuckServer muckServer = MuckServer.getINSTANCE();
        muckServer.players.put(client.getID(), player.getUsername());

        assertAll(
                () -> assertTrue(muckServer.players.containsKey(client.getID())),
                () -> assertTrue(muckServer.players.containsValue(player.getUsername()))
        );
    }


}
