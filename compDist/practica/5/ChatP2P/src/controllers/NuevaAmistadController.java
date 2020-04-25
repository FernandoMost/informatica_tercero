package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import myRMIchatP2P.Client.ChatRoomClientImpl;
import myRMIchatP2P.Server.ChatRoomServerInterface;

import java.rmi.RemoteException;

public class NuevaAmistadController extends CommonController {
    private ChatRoomServerInterface serverInterface;
    private ChatRoomClientImpl clientImpl;

    @FXML private JFXTextField nuevoAmigoTextField;
    @FXML private Label exceptionLabel;

    @Override
    public void cerrar(ActionEvent e) {
        // cerrar aquí solo cierra el pop up
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }

    public void setServerInterface(ChatRoomServerInterface chatRoomServer) {
        this.serverInterface = chatRoomServer;
    }

    public void setClient(ChatRoomClientImpl chatRoomClient) {
        this.clientImpl = chatRoomClient;
    }

    public void pressedAnhadir(ActionEvent e) {
        String nombreNuevoAmigo = nuevoAmigoTextField.getText();

        try {
            if (clientImpl.getNombre().equals(nombreNuevoAmigo)) {
                exceptionLabel.setText("No puedes ser amigo de tí mismo!");
                exceptionLabel.setVisible(true);
            } else {
                if (serverInterface.existeUsuario(nombreNuevoAmigo)) {
                    if (serverInterface.existeAmistad(clientImpl, nombreNuevoAmigo)) {
                        exceptionLabel.setText("El usuario ya es tu amigo");
                        exceptionLabel.setVisible(true);
                    } else { // la amistad no existe ya
                        if (serverInterface.existeYaUnaSolicitud(clientImpl, nombreNuevoAmigo)) {
                            exceptionLabel.setText("Existe ya una solicitud entre tú y el usuario");
                            exceptionLabel.setVisible(true);
                        } else {    // no existe la solicitud
                            serverInterface.guardarsolicitudDeAmistad(clientImpl, nombreNuevoAmigo);
                            cerrar(e);
                        }
                    }
                } else { // el nombre no está almacenado en el server
                    exceptionLabel.setText("El nombre de usuario no existe");
                    exceptionLabel.setVisible(true);
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }


}
