package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myRMIchatP2P.Client.ChatRoomClientImpl;
import myRMIchatP2P.Client.ChatRoomClientInterfaceForClients;
import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.io.IOException;
import java.net.DatagramPacket;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Vector;

public class ChatController extends CommonController {
    private ChatRoomClientImpl chatRoomClient;
    private ChatRoomServerInterface chatRoomServer;
    private Vector<ChatRoomClientInterfaceForClients> amigosConectados;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @FXML private JFXTextArea messageTextArea;
    @FXML private VBox chatVBox;
    @FXML private VBox amigosVBox;
    @FXML private ScrollPane chatScrollPane;
    @FXML private Label amigosEmpty;
    @FXML private Label chatsOwnerLabel;

    public void createChatRoom(String nombre, String contrasena, ChatRoomServerInterface chatRoomServer) throws Exception {
        this.chatRoomServer = chatRoomServer;
        amigosConectados = new Vector<>();

        if (!this.chatRoomServer.existeUsuario(nombre))
            throw new Exception("El nombre de usuario no existe. Regístrate!");

        if (!this.chatRoomServer.logInCorrecto(nombre, contrasena))
            throw new Exception("Contraseña incorrecta.");

        try {
            chatRoomClient = new ChatRoomClientImpl(nombre, contrasena, this);
            chatRoomServer.logInChatRoom(chatRoomClient, chatRoomClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        actualizarSolicitudesAmistad();
        chatsOwnerLabel.setText(nombre);
        messageTextArea.requestFocus();
    }

    public void actualizarListaAmigos() {
        HashSet<String> nombresAmigos;

        try {
            nombresAmigos = chatRoomServer.getAmigos(chatRoomClient);

            Platform.runLater(() -> {
                amigosVBox.getChildren().removeAll(amigosVBox.getChildren());
            });

            if (nombresAmigos == null || nombresAmigos.isEmpty()) {
                Platform.runLater(() -> {
                    amigosVBox.getChildren().add(amigosEmpty);
                });
            } else {
                Platform.runLater(() -> {
                    amigosVBox.getChildren().remove(amigosEmpty);
                });

                amigosConectados = chatRoomServer.getAmigosConectados(chatRoomClient);

                HashSet<String> nombresConectados = new HashSet<>();

                for (ChatRoomClientInterfaceForClients amigo : amigosConectados) {
                    nombresConectados.add(amigo.getNombre());
                    JFXButton botonAmigo = new JFXButton();
                    botonAmigo.getStyleClass().add("bloque_amigo_conectado");
                    botonAmigo.setMaxWidth(Double.MAX_VALUE);
                    botonAmigo.setText(amigo.getNombre());

                    Platform.runLater(() -> {
                        amigosVBox.getChildren().add(botonAmigo);
                    });
                }

                for (String nombre : nombresAmigos) {
                    if (!nombresConectados.contains(nombre)) {
                        JFXButton botonAmigo = new JFXButton();
                        botonAmigo.getStyleClass().add("bloque_amigo_desconectado");
                        botonAmigo.setMaxWidth(Double.MAX_VALUE);
                        botonAmigo.setText(nombre);
                        botonAmigo.setDisable(true);

                        Platform.runLater(() -> {
                            amigosVBox.getChildren().add(botonAmigo);
                        });
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
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

    public void nuevoAmigoPressed(ActionEvent e) {
        try {
            Stage nuevoAmigoStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/nuevaAmistad.fxml"));
            nuevoAmigoStage.initStyle(StageStyle.UNDECORATED);
            nuevoAmigoStage.setScene(new Scene(fxmlLoader.load()));
            nuevoAmigoStage.show();

            NuevaAmistadController nuevaAmistadController = fxmlLoader.getController();
            nuevaAmistadController.setServerInterface(chatRoomServer);
            nuevaAmistadController.setClient(chatRoomClient);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void printSolicitudAmistad(String usuario) {
        VBox divSolicitud = new VBox();
        divSolicitud.getStyleClass().add("bloque_solicitud_amistad");
        divSolicitud.setAlignment(Pos.CENTER);
        divSolicitud.setSpacing(15.0);
        divSolicitud.setPadding(new Insets(15,15,15,15));

        Label titulo = new Label("Solicitud de amistad");
        titulo.getStyleClass().add("titulo_solicitud_amistad");

        Label nombre = new Label(usuario);
        nombre.getStyleClass().add("nombre_solicitud_amistad");

        HBox divBotones = new HBox();
        divBotones.setAlignment(Pos.CENTER);
        divBotones.setSpacing(30.0);

        JFXButton botonAceptar = new JFXButton();
        JFXButton botonRechazar = new JFXButton();

        botonAceptar.setMinWidth(60.0);
        botonRechazar.setMinWidth(60.0);
        botonAceptar.setMinHeight(35.0);
        botonRechazar.setMinHeight(35.0);

        botonAceptar.getStyleClass().add("botonAceptar_solicitud_amistad");
        botonAceptar.setOnAction(event -> respondeSolicitud(event, true));
        botonRechazar.getStyleClass().add("botonRechazar_solicitud_amistad");
        botonRechazar.setOnAction(event -> respondeSolicitud(event, false));

        Platform.runLater(() -> {
            divBotones.getChildren().addAll(botonAceptar, botonRechazar);
            divSolicitud.getChildren().addAll(titulo, nombre, divBotones);
            amigosVBox.getChildren().add(0, divSolicitud);
        });
    }

    public void actualizarSolicitudesAmistad() {
        actualizarListaAmigos();

        try {
            if (chatRoomServer.tieneSolicitudes(chatRoomClient)) {
                for (String solicita : chatRoomServer.getSolicitudes(chatRoomClient)) {
                    Platform.runLater(() -> {
                        printSolicitudAmistad(solicita);
                    });
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void respondeSolicitud(ActionEvent e, boolean respuesta) {
        String nombre = ((Label) ((VBox) ((JFXButton) e.getSource()).getParent().getParent()).getChildren().get(1)).getText();
        try {
            chatRoomServer.responderSolicitudDeAmistad(chatRoomClient, nombre, respuesta);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        Platform.runLater(this::actualizarSolicitudesAmistad);
    }
}
