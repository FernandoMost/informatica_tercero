package controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import myRMIchatP2P.Client.ChatRoomClientImpl;
import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController extends CommonController {
    private ChatRoomClientImpl chatRoomClient;
    private ChatRoomServerInterface chatRoomServer;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @FXML private JFXTextArea messageTextArea;
    @FXML private VBox chatVBox;
    @FXML private ScrollPane chatScrollPane;

    public void createChatRoom(String nombre, String contrasena, ChatRoomServerInterface chatRoomServer) throws Exception {
        this.chatRoomServer = chatRoomServer;

        if (!this.chatRoomServer.existeUsuario(nombre))
            throw new Exception("El nombre de usuario no existe. Regístrate!");

        if (!this.chatRoomServer.logInCorrecto(nombre, contrasena))
            throw new Exception("Contraseña incorrecta.");

        try {
            chatRoomClient = new ChatRoomClientImpl(nombre, contrasena, this);
            this.chatRoomServer = chatRoomServer;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        messageTextArea.requestFocus();
    }

    @Override public void cerrar(ActionEvent e) {
        try {
            chatRoomServer.logOutChatRoom(chatRoomClient);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        super.cerrar(e);
    }

    public void sendAction() {
        /*String message = messageTextArea.getText();

        if (!message.isEmpty()) {
            byte[] m = message.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, multicastSession.getInetAddress(), multicastSession.getPort());

            try {
                multicastSession.getMulticastSocket().send(messageOut);
                messageTextArea.setText("");

                Text text = new Text(message);
                Date date = new Date();
                Text time = new Text(dateFormat.format(date));
                TextFlow textFlow = new TextFlow();
                HBox hBox = new HBox();

                hBox.setAlignment(Pos.BOTTOM_RIGHT); hBox.setSpacing(8);
                textFlow.setTextAlignment(TextAlignment.RIGHT);
                textFlow.setStyle("-fx-background-color: rgba(0,183,255,0.31)");
                textFlow.setPadding(new Insets(7,7,7,7));
                time.setStyle("-fx-font-size: 12px");
                text.setStyle("-fx-font-size: 20px");

                textFlow.getChildren().add(text);
                hBox.getChildren().add(time);
                hBox.getChildren().add(textFlow);
                chatVBox.getChildren().add(hBox);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
    }

    public void onEnterPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER){
            e.consume();
            if (e.isShiftDown())
                messageTextArea.appendText(System.getProperty("line.separator"));
            else
                sendAction();
        }
    }

    public void uncomingMessage(DatagramPacket packet) {
        /*String host, port, message;

        host = packet.getAddress().getHostName();
        port = String.valueOf(packet.getPort());
        message = new String(packet.getData(), 0, packet.getLength());

        Text source = new Text(host + ":" + port);
        Text text = new Text(message);
        Date date = new Date();
        Text time = new Text(dateFormat.format(date));
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        hBox.setAlignment(Pos.BOTTOM_LEFT); hBox.setSpacing(8);
        vBox.setAlignment(Pos.TOP_LEFT); vBox.setSpacing(10);
        vBox.setPadding(new Insets(7,7,7,7));
        vBox.setStyle("-fx-background-color: rgba(0,255,0,0.31)");
        time.setStyle("-fx-font-size: 12px");
        source.setStyle("-fx-font-weight: bold");
        source.setStyle("-fx-font-size: 10");
        text.setStyle("-fx-font-size: 20px");

        vBox.getChildren().add(source);
        vBox.getChildren().add(text);
        hBox.getChildren().add(vBox);
        hBox.getChildren().add(time);
        chatVBox.getChildren().add(hBox);*/
    }
}
