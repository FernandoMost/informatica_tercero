package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myMulticast.MulticastSession;

import java.io.IOException;
import java.net.DatagramPacket;

public class Controller {
    private MulticastSession multicastSession;

    @FXML private JFXButton minimizarButton;
    @FXML private JFXButton cerrarButton;
    @FXML private HBox topBar;
    @FXML private BorderPane primaryStageLogin;

    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;

    @FXML private JFXTextArea messageTextArea;
    @FXML private VBox chatVBox;

    private double xOffset, yOffset;

    // ───────────────────────────────────────────────────────────────────────────────────────────

    @FXML public void initialize() {
    }

    public void minimizar(MouseEvent e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void cerrar() {
        System.exit(0);
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    public void takeWindowOffset(MouseEvent e) {
        Stage primaryStage = (Stage) ((HBox) e.getSource()).getScene().getWindow();

        xOffset = primaryStage.getX() - e.getScreenX();
        yOffset = primaryStage.getY() - e.getScreenY();
    }

    public void moveWindow(MouseEvent e) {
        Stage primaryStage = (Stage) ((HBox) e.getSource()).getScene().getWindow();

        primaryStage.setX(e.getScreenX() + xOffset);
        primaryStage.setY(e.getScreenY() + yOffset);
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    public void accessAction(ActionEvent e) {
        Stage chatStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat.fxml"));
            chatStage.initStyle(StageStyle.UNDECORATED);
            chatStage.setScene(new Scene(fxmlLoader.load()));
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            chatStage.show();

            multicastSession = new MulticastSession(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void sendAction(ActionEvent e) {
        byte[] m = messageTextArea.getText().getBytes();
        DatagramPacket messageOut = new DatagramPacket(m, m.length, multicastSession.getInetAddress(), multicastSession.getPort());

        try {
            multicastSession.getMulticastSocket().send(messageOut);

            messageTextArea.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void receive() {
        byte[] m = messageTextArea.getText().getBytes();
        DatagramPacket messageOut = new DatagramPacket(m, m.length, multicastSession.getInetAddress(), multicastSession.getPort());

        try {
            multicastSession.getMulticastSocket().send(messageOut);

            messageTextArea.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
