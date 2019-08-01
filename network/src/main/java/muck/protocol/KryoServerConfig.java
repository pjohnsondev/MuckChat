package muck.protocol;

/**
 * Stores KryoNet configuration, such as which ports to bind to
 */
public class KryoServerConfig {

    private int tcpPort = 54555;

    private int udpPort = 54777;

    public int getTcpPort() {
        return tcpPort;
    }

    public KryoServerConfig setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
        return this;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public KryoServerConfig setUdpPort(int udpPort) {
        this.udpPort = udpPort;
        return this;
    }

    public KryoServerConfig() {
        // do nothing
    }


}
