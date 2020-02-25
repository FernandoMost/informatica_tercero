package myMulticast;

import graphics.controllers.ChatController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSession {
    private MulticastSocket multicastSocket;
    private InetAddress inetAddress;
    private int port;
    private ReceiverThread receiverThread;
    private ChatController chatController;

    public MulticastSession(String ip, int port, ChatController chatController) {
        this.port = port;
        this.chatController = chatController;

        try {
            this.inetAddress = InetAddress.getByName(ip);
            this.multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(inetAddress);

            multicastSocket.setLoopbackMode(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.receiverThread = new ReceiverThread(this);
        receiverThread.start();
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
    ChatController getChatController() {
        return chatController;
    }

    public void endSession() {
        try {
            receiverThread.interrupt();
            multicastSocket.leaveGroup(inetAddress);
            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
