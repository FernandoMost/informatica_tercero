package myMulticast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSession {
    private MulticastSocket multicastSocket;
    private InetAddress inetAddress;
    private int port;

    public MulticastSession(String ip, int port) {
        this.port = port;

        try {
            this.inetAddress = InetAddress.getByName(ip);
            this.multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(inetAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public int getPort() {
        return port;
    }
}
