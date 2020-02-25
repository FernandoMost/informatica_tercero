package graphics.controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import myMulticast.MulticastSession;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatController extends CommonController {
    private MulticastSession multicastSession;

    @FXML private JFXTextArea messageTextArea;
    @FXML private VBox chatVBox;
    @FXML private ScrollPane chatScrollPane;

    public void createMulticastSession(String ip, String port) {
        multicastSession = new MulticastSession(ip, Integer.parseInt(port), this);
    }

    @Override public void cerrar(ActionEvent e) {
        multicastSession.endSession();
        super.cerrar(e);
    }

    public void sendAction() {
        String message = messageTextArea.getText();

        if (!message.isEmpty()) {
            byte[] m = message.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, multicastSession.getInetAddress(), multicastSession.getPort());

            try {
                multicastSession.getMulticastSocket().send(messageOut);

                messageTextArea.setText("");

                Text text = new Text(message);
                TextFlow textFlow = new TextFlow();
                HBox hBox = new HBox();

                hBox.setAlignment(Pos.CENTER_RIGHT);
                textFlow.setTextAlignment(TextAlignment.RIGHT);
                textFlow.setStyle("-fx-background-color: rgba(0,183,255,0.31)");

                hBox.getChildren().add(textFlow);
                textFlow.getChildren().add(text);
                chatVBox.getChildren().add(textFlow);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void onEnterPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER){
            sendAction();
        }
    }

    public void uncomingMessage(DatagramPacket packet) {
        String host, port, message;

        host = packet.getAddress().getHostName();
        port = String.valueOf(packet.getPort());
        message = new String(packet.getData(), 0, packet.getLength());

        System.out.println(host + ":" + port + " --> " + message);

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow();
        HBox hBox = new HBox();

        hBox.setAlignment(Pos.CENTER_LEFT);
        textFlow.setTextAlignment(TextAlignment.LEFT);
        textFlow.setStyle("-fx-background-color: rgba(0,255,0,0.31)");

        hBox.getChildren().add(textFlow);
        textFlow.getChildren().add(text);
        chatVBox.getChildren().add(textFlow);
    }
}
