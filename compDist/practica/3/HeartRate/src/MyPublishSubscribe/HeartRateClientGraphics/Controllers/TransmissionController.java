package MyPublishSubscribe.HeartRateClientGraphics.Controllers;

import MyPublishSubscribe.HeartRateClientImpl;
import MyPublishSubscribe.HeartRateServerInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class TransmissionController extends CommonController {
    private final Object monitor = new Object();

    private HeartRateServerInterface serverInterface;
    private HeartRateClientImpl clientInterface;

    private int segundosSuscripcion, numDatosRecibidos;
    private final int NUM_MAX_DATOS = 60;

    @FXML private Button suscripcionButton;
    @FXML private TextField segundosField;

    @FXML private LineChart<Number, Number> grafica;
    @FXML private NumberAxis ejeX;
    @FXML private NumberAxis ejeY;

    @FXML private Label estadoLabel;

    private XYChart.Series datosGrafica;

    // ───────────────────────────────────────────────

    void setHeartRateObjects(HeartRateServerInterface serverInterface, HeartRateClientImpl clientInterface) {
        this.serverInterface = serverInterface;
        this.clientInterface = clientInterface;

        segundosSuscripcion = 0;

        ejeX.setLabel("Tiempo (s)");
        ejeY.setAutoRanging(true);
        ejeX.setAnimated(false);

        ejeY.setLabel("RR");
        ejeY.setAutoRanging(true);
        ejeY.setAnimated(false);

        datosGrafica = new XYChart.Series();

        grafica.getData().add(datosGrafica);
    }

    @Override
    public void cerrar(ActionEvent e) {
        try {
            serverInterface.unregisterForHeartRateTransmission(clientInterface);
            System.out.println("---------------------------------- Conexión terminada ----------------------------------");
            super.cerrar(e);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

// ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    public void nuevaOrenovar() {
        if (suscripcionButton.getText().equals("Suscripción"))
            nuevaSuscripcion();
        else
            renovarSuscripcion();
    }

    private void nuevaSuscripcion() {
        new Thread(() -> {
            try {
                serverInterface.registerForHeartRateTransmission(clientInterface);
                segundosSuscripcion = Integer.parseInt(segundosField.getText());
                Platform.runLater(() -> {
                    suscripcionButton.setText("Renovar");
                    suscripcionButton.setStyle("-fx-background-color: rgba(255,0,131,0.64)");
                    grafica.setDisable(false);
                    estadoLabel.setText("Estado de la conexion: " + segundosSuscripcion + " segundos restantes");
                });
                System.out.println("------------------------- Suscrito y listo para la transmisión -------------------------");


                while (segundosSuscripcion > -1) {
                    synchronized (monitor) {
                        try { monitor.wait(1000);
                        } catch (InterruptedException e) { e.printStackTrace(); }
                        segundosSuscripcion--;

                        Platform.runLater(() -> {
                            estadoLabel.setText("Estado de la conexion: " + segundosSuscripcion + " segundos restantes");
                        });
                    }
                }

                serverInterface.unregisterForHeartRateTransmission(clientInterface);
                Platform.runLater(() -> {
                    suscripcionButton.setText("Suscripción");
                    suscripcionButton.setStyle("-fx-background-color: rgba(0,0,255,0.63);");
                    grafica.setDisable(true);
                    estadoLabel.setText("Estado de la conexion: finalizada");
                });
                System.out.println("---------------------------------- Conexión terminada ----------------------------------");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void renovarSuscripcion() {
        synchronized (monitor) {
            segundosSuscripcion += Integer.parseInt(segundosField.getText());
            monitor.notifyAll();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    public void addDataToChart(float RR) {
        Platform.runLater(() -> {
            datosGrafica.getData().add(new XYChart.Data(numDatosRecibidos++, RR));
            // remove points to keep us at no more than MAX_DATA_POINTS
            if (datosGrafica.getData().size() > NUM_MAX_DATOS)
                datosGrafica.getData().remove(0, datosGrafica.getData().size() - NUM_MAX_DATOS);
/*
            // update
            ejeX.setUpperBound(numDatosRecibidos - NUM_MAX_DATOS);
            ejeX.setLowerBound(numDatosRecibidos - 1);*/
        });
    }

}
