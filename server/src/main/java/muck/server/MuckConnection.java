package muck.server;

import muck.core.character.Character;
import com.esotericsoftware.kryonet.Connection;

/**
 * Connection class to store connection-specific states.
 * By providing our own connection implementation, we can store per-connection state without a connection Id to state lookup.
 */
public class MuckConnection extends Connection {
    private Character character;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
