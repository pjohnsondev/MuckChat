package muck.protocol;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.function.BiConsumer;

/**
 * This helps to build KryoNet listeners that use lambdas.
 *
 * For example:
 * ListenerBuilder.forClass(String.class).onReceive((conn, str) -> System.out.println(str.toUpperCase());
 */
public class ListenerBuilder<T> {

    Class<T> clazz;

    public ListenerBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Listener onReceive(BiConsumer<Connection, T> handler) {
        return new Listener() {
            @Override public void received(Connection conn, Object message) {
                if (clazz.isAssignableFrom(message.getClass())) {
                    handler.accept(conn, (T)message);
                }
            }
        };
    }

    public static <T> ListenerBuilder<T> forClass(Class<T> clazz) {
        return new ListenerBuilder<T>(clazz);
    }
}
