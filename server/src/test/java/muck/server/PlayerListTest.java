package muck.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import muck.core.ClientId;
import muck.core.Id;
import muck.protocol.KryoClientConfig;
import muck.protocol.KryoServerConfig;
import muck.protocol.Protocol;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerListTest {
    static KryoServerConfig ServConfig = new KryoServerConfig();
    static KryoClientConfig config = new KryoClientConfig();

    @Test
    // Test that the server contains the playerlist and that we can add to it
    public void playerList() {
        MuckServer muckServer = MuckServer.getINSTANCE();
        muckServer.players.add("1");
        assertTrue(muckServer.players.contains("1"));
    }


}
