package muck.protocol;

import aw.character.Player;
import com.esotericsoftware.kryo.Kryo;
import muck.core.Id;
import muck.protocol.connection.*;



/**
 * A common Protocol registration class, shared by the client and server, to ensure that both
 * ends of the connection register the same classes in the same order (which is an assumption
 * Kryonet requires in order to serialise and deserialise classes correctly)
 */
public class Protocol {

    /**
     * Classes that need to be serialised across the network should be registered in this
     * method, as for the provided examples
     * @param kryo
     */
    public static void register(Kryo kryo) {

        // Register connection messages
        kryo.register(Ping.class);
        kryo.register(Connected.class);
        kryo.register(Disconnect.class);
        kryo.register(userMessage.class);
        kryo.register(java.util.Date.class);
        kryo.register(Id.class);
        kryo.register(Login.class);
        kryo.register(AddCharacter.class);
        kryo.register(Player.class);
        kryo.register(Location.class);
    }
}
