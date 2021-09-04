package muck.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerListTest {

    @Test
    // Test that the server contains the playerlist and that we can add to it
    public void playerList() {
        MuckServer muckServer = MuckServer.getINSTANCE();
        muckServer.players.add("1");
        assertTrue(muckServer.players.contains("1"));
    }


}
