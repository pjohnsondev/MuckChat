package muck.protocol;

import muck.core.Login;
import muck.core.Pair;
import muck.core.LocationRequest;
import muck.core.LocationResponse;
import muck.core.Location;
import muck.core.LocationResponseData;
import muck.core.UpdatePlayerRequest;
import muck.core.character.AddCharacter;
import muck.core.character.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import muck.core.Id;
import muck.core.structures.UserStructure;
import muck.core.user.SignUpInfo;
import muck.protocol.connection.*;
import muck.core.AvatarLocation;
/**
 * A common Protocol registration class, shared by the client and server, to
 * ensure that both ends of the connection register the same classes in the same
 * order (which is an assumption Kryonet requires in order to serialise and
 * deserialise classes correctly)
 */
public class Protocol {

	/**
	 * Classes that need to be serialised across the network should be registered in
	 * this method, as for the provided examples
	 *
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
		kryo.register(SignUpInfo.class);
		kryo.register(Pair.class);
		kryo.register(AddCharacter.class);
		kryo.register(ArrayList.class);
		kryo.register(HashMap.class);
		kryo.register(List.class);
		kryo.register(Location.class);
		kryo.register(LocationRequest.class);
		kryo.register(LocationResponse.class);
		kryo.register(LocationResponseData.class);
		kryo.register(UpdatePlayerRequest.class);
		kryo.register(AvatarLocation.class);
		kryo.register(byte[].class);
		kryo.register(UserStructure.class);

	}
}
