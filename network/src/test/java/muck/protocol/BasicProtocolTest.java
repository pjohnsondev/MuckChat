package muck.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import muck.protocol.connection.Ping;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicProtocolTest {

    /** This test really just tests that we're able to compile and run the tests including creating some Kryo objects */
    @Test void pingCopyTest() {
        // Create a client. Don't start it for this test, though
        Client client = new Client();
        Kryo kryo = client.getKryo();
        Protocol.register(kryo);

        Ping p = new Ping();
        Ping copy = kryo.copy(p);

        //This test does not check anything useful
        assertEquals(p.getClass().getName(), copy.getClass().getName());
    }
}

