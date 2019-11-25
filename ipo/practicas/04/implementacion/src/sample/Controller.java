package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sample.teatro.Teatro;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    //───────────────────────────────────────

    @FXML private Pane paneScreenSaver;
    private Timeline sreenSaver;
    private ImageView[] imagesScreenSaver = new ImageView[6];
    private int indexActualImage;

    @FXML private ImageView fondoCortinas;
    @FXML private ImageView marco;

    @FXML private ImageView imageScreenSaver1;
    @FXML private ImageView imageScreenSaver2;
    @FXML private ImageView imageScreenSaver3;
    @FXML private ImageView imageScreenSaver4;
    @FXML private ImageView imageScreenSaver5;
    @FXML private ImageView imageScreenSaver6;

    private Media[] players = new Media[5];

    private double[] tamanosPanelContenido = new double[4];
    private int currentTamanoPanelContenido;
    @FXML private Pane panelContenido;
    @FXML private ScrollPane scrollContenido;

    @FXML private ToolBar toolbar;

    @FXML private Button btWheelchair;
    @FXML private Button btLanguage;

    @FXML private HBox hardware;
    @FXML private Button btNarratorMode;

    @FXML private Pane paneLanguage;
    @FXML private Pane paneHome;
    @FXML private Pane paneFunciones;
    @FXML private Pane paneButacas;
    @FXML private Pane paneResumen;
    @FXML private Pane panePago;
    @FXML private Pane panePin;
    @FXML private Pane paneCargando;
    @FXML private Pane paneExito;

    private boolean[][] ocupadas = new boolean[Teatro.NUM_FILAS][Teatro.NUM_COLUMNAS];
    private boolean[][] seleccion = new boolean[Teatro.NUM_FILAS][Teatro.NUM_COLUMNAS];

    @FXML private GridPane butacas;
    private int numSeleccionadas;
    private double precioSeleccionadas;

    @FXML private TextField numEntradas;
    @FXML private TextField precioEntradas;
    @FXML private Button btConfirmarEntradas;

    @FXML private TextArea resumenPago;
    @FXML private ImageView flechaTarjeta;
    private Timeline flechaNFC;

    private boolean pagoConTarjeta;
    @FXML private Button btNFC;
    @FXML private PasswordField campoPIN;
    private Timeline tramitando;
    @FXML private Label tramitandoLabel;
    @FXML private TextField correoField;
    @FXML private Label enviadoLabel;

    private Timer volverAHome;
    private Image butacaNotSelect;
    private Image butacaSelect;

    private String obraActual;
    private String fechaObra;
    private String horaObra;

    //───────────────────────────────────────

    @FXML
    public void initialize() {
        imagesScreenSaver[0] = imageScreenSaver1;
        imagesScreenSaver[1] = imageScreenSaver2;
        imagesScreenSaver[2] = imageScreenSaver5;
        imagesScreenSaver[3] = imageScreenSaver4;
        imagesScreenSaver[4] = imageScreenSaver3;
        imagesScreenSaver[5] = imageScreenSaver6;

        indexActualImage = 0;

        for (ImageView im : imagesScreenSaver) im.setVisible(false);

        sreenSaver = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            indexActualImage++;
            indexActualImage = indexActualImage % imagesScreenSaver.length;

            for (ImageView im : imagesScreenSaver) im.setVisible(false);

            imagesScreenSaver[indexActualImage].setVisible(true);
        }));

        sreenSaver.setCycleCount(Timeline.INDEFINITE);

        // ───────────────────────────────────

        double portionScreen = panelContenido.getPrefHeight() / 4;

        for (int i = 0; i < 4; i++) tamanosPanelContenido[i] = portionScreen * (i+1);

        currentTamanoPanelContenido = 3;

        // ───────────────────────────────────

        String[] audioFiles = {
            "sample/audios/01.mp3",
            "sample/audios/02.mp3",
            "sample/audios/03.mp3",
            "sample/audios/04.mp3",
            "sample/audios/05.mp3"
        };

        for (int i = 0; i < 5; i++) {
            players[i] = new Media(new File(audioFiles[i]).toURI().toString());
        }

        // ───────────────────────────────────

        for (boolean[] fila : ocupadas)
            for (boolean columna : fila)
                columna = false;

        numSeleccionadas = 0;
        precioSeleccionadas = 0.0;

        pagoConTarjeta = false;
        btNFC.setDisable(true);

        enviadoLabel.setVisible(false);

        butacaSelect = new Image(new File("src/sample/imagenes/seat2.png").toURI().toString());
        butacaNotSelect = new Image(new File("src/sample/imagenes/seat.png").toURI().toString());

        arrancaScreenSaver();
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    public void arrancaScreenSaver() {
        fondoCortinas.toFront();
        paneScreenSaver.toFront();
        fondoCortinas.setVisible(true);
        paneScreenSaver.setVisible(true);
        for (ImageView im : imagesScreenSaver) im.setVisible(true);
        sreenSaver.play();
    }

    public void paraScreenSaver() {
        sreenSaver.stop();
        for (ImageView im : imagesScreenSaver) im.setVisible(false);
        paneScreenSaver.toBack();
        fondoCortinas.toBack();
        clickHome();
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    public void clickWheelchair() {
        currentTamanoPanelContenido++;
        currentTamanoPanelContenido = currentTamanoPanelContenido%4;

        double nuevoTamano = tamanosPanelContenido[currentTamanoPanelContenido];

        panelContenido.setPrefHeight(nuevoTamano);
        scrollContenido.setPrefHeight(nuevoTamano);

        panelContenido.setLayoutY(814 - nuevoTamano);
    }

    public void clickLanguage() {
        paneLanguage.setVisible(true);
        paneLanguage.toFront();
        marco.toFront();
        hardware.toFront();
    }

    public void clickHome() {
        paneHome.setVisible(true);
        paneHome.toFront();
        marco.toFront();
        hardware.toFront();

        if (volverAHome != null) {
            volverAHome.cancel();
            volverAHome.purge();
            volverAHome = null;
        }
    }

    public void goHome() {
        resetButacasSeleccionadas();
        clickHome();
    }

    public void clickAudioNavigation() {
        paraScreenSaver();
        clickHome();

        ObservableList<Media> listaAudios = FXCollections.observableArrayList();
        listaAudios.addAll(Arrays.asList(players));

        btNarratorMode.setDisable(true);
        reproduceAudio(listaAudios);
    }

    private void reproduceAudio(ObservableList<Media> lista) {
        if (lista.size() == 0) {
            btNarratorMode.setDisable(false);
            return;
        }

        MediaPlayer mediaplayer = new MediaPlayer(lista.remove(0));
        mediaplayer.play();

        mediaplayer.setOnEndOfMedia(() -> reproduceAudio(lista));
    }

    public void verFunciones(MouseEvent e) {
        Button b = (Button) e.getSource();
        obraActual = ((Label) ((BorderPane) b.getParent()).getTop()).getText();

        paneHome.setVisible(false);
        paneFunciones.setVisible(true);
        paneFunciones.toFront();
        marco.toFront();
        hardware.toFront();
    }

    private void resetButacasSeleccionadas() {
        for (Node n : butacas.getChildren()) {
            if (n != null) {
                if (GridPane.getRowIndex(n) != null && GridPane.getColumnIndex(n) != null ){
                    //System.out.println("[" + GridPane.getRowIndex(n) + "],[" + GridPane.getColumnIndex(n) + "]");
                    ((ImageView) n).setImage(butacaNotSelect);

                    if (ocupadas[GridPane.getRowIndex(n) - 1][GridPane.getColumnIndex(n) - 1]) {
                        ((ImageView) n).setStyle("-fx-opacity: 0.5");
                    } else {
                        ((ImageView) n).setStyle("-fx-opacity: 1.0");
                    }
                }
            }
        }

        for (boolean[] fila : seleccion)
            for (boolean columna : fila)
                columna = false;

        numSeleccionadas = 0;
        precioSeleccionadas = 0.0;
    }

    public void verButacas(MouseEvent e) {
        Button b = (Button) e.getSource();
        horaObra = b.getText();

        //resetButacasSeleccionadas();

        numEntradas.setText(String.valueOf(numSeleccionadas));
        precioEntradas.setText(String.format("%.2f", precioSeleccionadas));

        paneButacas.setVisible(true);
        paneButacas.toFront();
        marco.toFront();
        hardware.toFront();
        btConfirmarEntradas.setDisable(true);
    }

    public void clickEnButaca(MouseEvent e) {
        ImageView source = (ImageView) e.getSource();
        int butaca = GridPane.getColumnIndex(source);
        int fila = GridPane.getRowIndex(source);
        //System.out.println(butaca + " " + fila + " " + ocupadas[fila-1][butaca-1]);
        double precio;

        if (ocupadas[fila-1][butaca-1]) {

        } else {
            if (fila == 7 && (butaca < 6 || butaca > 9)) precio = 12.5;
            else precio = 20.0;

            if (seleccion[fila-1][butaca-1]) {
                source.setImage(butacaNotSelect);
                seleccion[fila-1][butaca-1] = false;
                numSeleccionadas--;
                precioSeleccionadas -= precio;
            } else {
                source.setImage(butacaSelect);
                seleccion[fila-1][butaca-1] = true;
                numSeleccionadas++;
                precioSeleccionadas += precio;
            }

            if (numSeleccionadas == 0) btConfirmarEntradas.setDisable(true);
            else btConfirmarEntradas.setDisable(false);

            numEntradas.setText(String.valueOf(numSeleccionadas));
            precioEntradas.setText(String.format("%.2f", precioSeleccionadas));
        }
    }

    public void clickFecha(MouseEvent e) {
        TitledPane tp = (TitledPane) e.getSource();

        fechaObra = tp.getText();

        System.out.println(fechaObra);
    }

    public void irAResumenPago() {
        paneResumen.setVisible(true);
        paneResumen.toFront();
        marco.toFront();
        hardware.toFront();

        String resumen = "";
        int numNormal = 0, numEspecial = 0, i = 1, j = 1;

        for (boolean[] fila : seleccion) {
            for (boolean butaca : fila) {
                if (butaca) {
                    if (i == 7 && (j < 6 || j > 9)) numEspecial++;
                    else numNormal++;
                }

                j++;
            }

            i++; j = 1;
        }

        resumen += obraActual + "\n" + fechaObra + "\t" + horaObra + "\n\n";

        if (numNormal > 0)
            resumen += "Entrada Tipo Normal \t x" + numNormal + "\t" + String.format("%.2f", numNormal*20.0) + "\n\n";

        if (numEspecial > 0)
            resumen += "Entrada Tipo Especial \t x" + numEspecial + "\t" + String.format("%.2f", numEspecial*12.5) + "\n\n";

        resumenPago.setText(resumen);
    }

    public void pagoTarjeta() {
        pagoConTarjeta = true;
        irAPago();
    }

    public void pagoClubPremium() {
        pagoConTarjeta = false;
        irAPago();
    }

    private void irAPago() {
        panePago.setVisible(true);
        panePago.toFront();
        marco.toFront();
        hardware.toFront();

        flechaNFC = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            flechaTarjeta.setVisible(!flechaTarjeta.isVisible());
        }));

        flechaNFC.setCycleCount(Timeline.INDEFINITE);

        flechaNFC.play();

        btNFC.setDisable(false);
    }

    public void pagoEfectuado() {
        flechaNFC.stop();
        btNFC.setDisable(true);

        // cambiar las butacas
        for (int i = 0; i < Teatro.NUM_FILAS; i++) {
            for (int j = 0; j < Teatro.NUM_COLUMNAS; j++) {
                if (seleccion[i][j])
                    ocupadas[i][j] = true;
            }
        }

        if (pagoConTarjeta) {
            irAPreguntarPIN();
        } else {
            irAExito();
        }
    }

    private void irAPreguntarPIN() {
        panePin.setVisible(true);
        panePin.toFront();
        marco.toFront();
        hardware.toFront();
    }

    public void addPinNumber() {
        String nuevo = campoPIN.getText() + "1";

        if (nuevo.length() == 4){
            irAExito();
        } else
            campoPIN.setText(nuevo);
    }

    public void irAExito() {
        paneExito.setVisible(true);
        paneExito.toFront();
        marco.toFront();
        hardware.toFront();

        volverAHome = new Timer();
        volverAHome.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> clickHome());
                volverAHome.cancel();
                volverAHome.purge();
            }
        }, 50000,50000);
    }

    public void enviaCorreo() {
        enviadoLabel.setVisible(true);
        correoField.setText(null);
    }
}