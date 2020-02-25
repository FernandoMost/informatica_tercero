package graphics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController extends CommonController {
    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;

    public void accessAction(ActionEvent e) {
        Stage chatStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/chat.fxml"));
            chatStage.initStyle(StageStyle.UNDECORATED);
            chatStage.setScene(new Scene(fxmlLoader.load()));

            ChatController chatController = fxmlLoader.getController();
            chatController.createMulticastSession(ipTextField.getText(), portTextField.getText());

            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();

            chatStage.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
