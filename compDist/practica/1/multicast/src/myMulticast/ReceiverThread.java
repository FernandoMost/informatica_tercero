package myMulticast;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;

public class ReceiverThread extends Thread {
    private MulticastSession multicastSession;
    private byte[] messageIn = new byte[1000];

    ReceiverThread(MulticastSession multicastSession) {
        this.multicastSession = multicastSession;
    }

    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(messageIn, messageIn.length);
                multicastSession.getMulticastSocket().receive(packet);
                Platform.runLater(() -> {
                    multicastSession.getChatController().uncomingMessage(packet);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}