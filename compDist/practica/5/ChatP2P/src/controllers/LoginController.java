package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginController extends CommonController {
    @FXML private TextField ipTextField, portTextField;
    @FXML private Label exceptionLabel;

    private ChatRoomServerInterface lookup() {
        try {
            return (ChatRoomServerInterface) Naming.lookup("rmi://localhost:1099/ChatRoom");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void accessAction(ActionEvent e) {
        try {
            ChatRoomServerInterface serverInterface = lookup();

            Stage chatStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/chat.fxml"));
            chatStage.initStyle(StageStyle.UNDECORATED);
            chatStage.setScene(new Scene(fxmlLoader.load()));

            ChatController chatController = fxmlLoader.getController();

            chatController.createChatRoom(
                    ipTextField.getText(),
                    portTextField.getText(),
                    serverInterface);

            ((Stage) ipTextField.getScene().getWindow()).close();
            chatStage.show();
        } catch (NotBoundException ex) {
            exceptionLabel.setText("Lookup in the registry a name that has no associated binding.");
        } catch (MalformedURLException ex) {
            exceptionLabel.setText("Malformed URL, either no legal protocol or the string could not be parsed.");
        } catch (Exception ex) {
            exceptionLabel.setText(ex.getMessage());
        } finally {
            exceptionLabel.setVisible(true);
        }
    }

    public void registrarsePressed() {
        try {
            Stage nuevoAmigoStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/registro.fxml"));
            nuevoAmigoStage.initStyle(StageStyle.UNDECORATED);
            nuevoAmigoStage.setScene(new Scene(fxmlLoader.load()));
            nuevoAmigoStage.show();

            RegistroController registroController = fxmlLoader.getController();
            registroController.setServerInterface(lookup());
            registroController.setLoginController(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void fakeLogInFromSignUp(String usuario, String contrasena) {
        ipTextField.setText(usuario);
        portTextField.setText(contrasena);
        this.accessAction(null);
    }
}
