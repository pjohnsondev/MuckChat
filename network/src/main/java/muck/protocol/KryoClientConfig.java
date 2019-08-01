package muck.protocol;

/**
 * Stores KryoNet configuration, such as which ports to bind to
 */
public class KryoClientConfig {

    private int tcpPort = 54555;

    private int udpPort = 54777;

    private String destinationIp = "127.0.0.1";

    private int timeOut = 5000;

    public int getTcpPort() {
        return tcpPort;
    }

    public KryoClientConfig setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
        return this;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public KryoClientConfig setUdpPort(int udpPort) {
        this.udpPort = udpPort;
        return this;
    }

    public String getDestinationIp() { return destinationIp; }

    public KryoClientConfig setIP(String ip) {
        this.destinationIp = ip;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public KryoClientConfig setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public KryoClientConfig() {
        // do nothing
    }


}
