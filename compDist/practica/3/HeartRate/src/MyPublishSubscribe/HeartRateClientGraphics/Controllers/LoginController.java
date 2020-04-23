package MyPublishSubscribe.HeartRateClientGraphics.Controllers;

import MyPublishSubscribe.HeartRateClientImpl;
import MyPublishSubscribe.HeartRateServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class LoginController extends CommonController {
    @FXML private TextField ipTextField, portTextField;
    @FXML private Label exceptionLabel;


    public void accessAction(ActionEvent e) {
        String registryURL = "rmi://" + ipTextField.getText() + ":" + portTextField.getText() + "/HeartRate";

        try {
            HeartRateServerInterface serverInterface = (HeartRateServerInterface) Naming.lookup(registryURL);

            System.out.println("Objeto remoto encontrado!\nEl servidor te saluda: " + serverInterface.sayHello());

            Stage transmissionStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../sample.fxml"));
            transmissionStage.initStyle(StageStyle.UNDECORATED);
            transmissionStage.setScene(new Scene(fxmlLoader.load()));

            TransmissionController transmissionController = fxmlLoader.getController();
            HeartRateClientImpl clientInterface =  new HeartRateClientImpl(transmissionController);
            transmissionController.setHeartRateObjects(serverInterface, clientInterface);

            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            transmissionStage.show();
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
}
