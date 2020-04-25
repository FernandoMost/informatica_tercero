package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.rmi.RemoteException;

public class RegistroController extends CommonController {
    private ChatRoomServerInterface serverInterface;
    private LoginController loginController;

    @FXML private TextField nombreTextField;
    @FXML private PasswordField contrasenaTextField;
    @FXML private Label exceptionLabel;

    public void setServerInterface(ChatRoomServerInterface chatRoomServerInterface) {
        this.serverInterface = chatRoomServerInterface;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void cerrar(ActionEvent e) {
        // cerrar aquí solo cierra el pop up
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }

    public void registerAction(ActionEvent e) {
        if (nombreTextField.getText().isEmpty() || contrasenaTextField.getText().isEmpty()) {
            exceptionLabel.setText("Ambos campos son obligatorios!");
            exceptionLabel.setVisible(true);
        } else {
            try {
                if (serverInterface.existeUsuario(nombreTextField.getText())) {
                    exceptionLabel.setText("El nombre de usuario está ocupado!");
                    exceptionLabel.setVisible(true);
                } else {
                    serverInterface.guardarNuevoUsuario(nombreTextField.getText(), contrasenaTextField.getText());
                    cerrar(e);
                    loginController.fakeLogInFromSignUp(nombreTextField.getText(), contrasenaTextField.getText());
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }


}
