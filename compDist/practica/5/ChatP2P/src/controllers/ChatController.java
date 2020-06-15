package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import myRMIchatP2P.Client.ChatRoomClientImpl;
import myRMIchatP2P.Client.ChatRoomClientInterfaceForClients;
import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class ChatController extends CommonController {
    private ChatRoomClientImpl chatRoomClient;
    private ChatRoomServerInterface chatRoomServer;
    private Vector<ChatRoomClientInterfaceForClients> amigosConectados;
    private ChatRoomClientInterfaceForClients currentClientOnChat;
    private VBox currentVBoxOnChat;

    private HashMap<String, VBox> vBoxsChats;
    private HashMap<String, FadeTransition> ongoingBlinkings;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @FXML private JFXTextArea messageTextArea;
    @FXML private VBox chatVBox;
    @FXML private VBox amigosVBox;
    @FXML private ScrollPane chatScrollPane;
    @FXML private Label amigosEmpty;
    @FXML private Label chatsOwnerLabel;
    @FXML private Label chatsNameLabel;
    @FXML private JFXButton closeChatButton;

    public void createChatRoom(String nombre, String contrasena, ChatRoomServerInterface chatRoomServer) throws Exception {
        this.chatRoomServer = chatRoomServer;
        amigosConectados = new Vector<>();
        vBoxsChats = new HashMap<>();
        ongoingBlinkings = new HashMap<>();
        currentVBoxOnChat = chatVBox;

        if (!this.chatRoomServer.existeUsuario(nombre))
            throw new Exception("El nombre de usuario no existe. Regístrate!");

        if (this.chatRoomServer.estaConectado(nombre))
            throw new Exception("El usuario ya está conectado en otro cliente.");

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
                    String nombre = amigo.getNombre();

                    nombresConectados.add(nombre);
                    JFXButton botonAmigo = new JFXButton();
                    botonAmigo.getStyleClass().add("bloque_amigo_conectado");
                    botonAmigo.setMaxWidth(Double.MAX_VALUE);
                    botonAmigo.setText(nombre);
                    botonAmigo.setOnAction(event -> clickaAmigoConectado(event));

                    if (!vBoxsChats.containsKey(nombre)) {
                        VBox vBoxChat = new VBox();
                        vBoxChat.getStyleClass().add("section");
                        vBoxChat.setSpacing(10.0);
                        vBoxChat.setPadding(new Insets(20,20,20,20));

                        vBoxsChats.put(nombre, vBoxChat);
                    }

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

                        vBoxsChats.remove(nombre);

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
        String message = messageTextArea.getText();

        if (!message.isEmpty()) {
            try {
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

                Platform.runLater(() -> {
                    textFlow.getChildren().add(text);
                    hBox.getChildren().add(time);
                    hBox.getChildren().add(textFlow);
                    currentVBoxOnChat.getChildren().add(hBox);
                });

                currentClientOnChat.nuevoMensaje(chatRoomClient, message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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

    public void uncomingMessage(ChatRoomClientInterfaceForClients origen, String mensaje) {
        Text text = new Text(mensaje);
        Date date = new Date();
        Text time = new Text(dateFormat.format(date));
        TextFlow textFlow = new TextFlow();
        HBox hBox = new HBox();
        String nombreOrigen = null;
        try {
            nombreOrigen = origen.getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        hBox.setAlignment(Pos.BOTTOM_LEFT); hBox.setSpacing(8);
        textFlow.setTextAlignment(TextAlignment.LEFT);
        textFlow.setStyle("-fx-background-color: rgba(0,255,0,0.31)");
        textFlow.setPadding(new Insets(7,7,7,7));
        time.setStyle("-fx-font-size: 12px");
        text.setStyle("-fx-font-size: 20px");

        VBox vBoxDestino = vBoxsChats.get(nombreOrigen);

        Platform.runLater(() -> {
            textFlow.getChildren().add(text);
            hBox.getChildren().add(time);
            hBox.getChildren().add(textFlow);
            if (vBoxDestino != null) {
                vBoxDestino.getChildren().add(hBox);
            }
        });

        if (vBoxDestino != currentVBoxOnChat) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), getBotonAmigo(nombreOrigen));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(Animation.INDEFINITE);
            ft.setAutoReverse(true);

            ft.play();
            ongoingBlinkings.put(nombreOrigen, ft);
        }
    }

    private JFXButton getBotonAmigo(String nombre) {
        for (Node child : amigosVBox.getChildren()) {
            if (child instanceof JFXButton)
                if (((JFXButton) child).getText().equals(nombre))
                    return (JFXButton) child;
        }

        return null;
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

    public void clickaAmigoConectado(ActionEvent e) {
        JFXButton boton = (JFXButton) e.getSource();
        String nombre = boton.getText();

        chatsNameLabel.setText(nombre + "'s Chat");

        chatScrollPane.setContent(vBoxsChats.get(nombre));

        closeChatButton.setVisible(true);

        for (ChatRoomClientInterfaceForClients conectado: amigosConectados) {
            try {
                if (conectado.getNombre().equals(nombre)) {
                    currentClientOnChat = conectado;
                    currentVBoxOnChat = vBoxsChats.get(nombre);
                    if (ongoingBlinkings.containsKey(nombre)) {
                        ongoingBlinkings.get(nombre).stop();
                        ongoingBlinkings.remove(nombre);
                        Platform.runLater(() -> {
                            boton.setOpacity(1.0);
                        });
                    }
                    break;
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void cerrarChat(ActionEvent e) {
        chatsNameLabel.setText("Chat");
        closeChatButton.setVisible(false);
        currentVBoxOnChat = chatVBox;
        chatScrollPane.setContent(currentVBoxOnChat);
        currentClientOnChat = null;
    }
}
