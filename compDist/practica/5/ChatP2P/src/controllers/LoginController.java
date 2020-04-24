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

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class LoginController extends CommonController {
    @FXML private TextField ipTextField, portTextField;
    @FXML private Label exceptionLabel;

    public void accessAction(ActionEvent e) {
        String registryURL = "rmi://localhost:1099/ChatRoom";

        try {
            ChatRoomServerInterface serverInterface = (ChatRoomServerInterface) Naming.lookup(registryURL);

            System.out.println("Objeto remoto encontrado!\nEl servidor te saluda: " + serverInterface.sayHello());

            Stage chatStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/chat.fxml"));
            chatStage.initStyle(StageStyle.UNDECORATED);
            chatStage.setScene(new Scene(fxmlLoader.load()));

            ChatController chatController = fxmlLoader.getController();

            chatController.createChatRoom(
                    ipTextField.getText(),
                    portTextField.getText(),
                    serverInterface);

            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            chatStage.show();
        } catch (NotBoundException ex) {
            exceptionLabel.setText("Lookup in the registry a name that has no associated binding.");
        } catch (MalformedURLException ex) {
            exceptionLabel.setText("Malformed URL, either no legal protocol or the string could not be parsed.");
        } catch (Exception ex) {
            exceptionLabel.setText(ex.getMessage());
        } finally {
            System.out.println(exceptionLabel.getText());
            exceptionLabel.setVisible(true);
        }
    }
}
