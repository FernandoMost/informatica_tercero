package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {

    //───────────────────────────────────────

    Timeline sreenSaver;
    private ArrayList<ImageView> imagesScreenSaver;
    private int indexActualImage;

    @FXML private ImageView imageScreenSaver1;
    @FXML private ImageView imageScreenSaver2;
    @FXML private ImageView imageScreenSaver3;
    @FXML private ImageView imageScreenSaver4;

    //───────────────────────────────────────

    @FXML private Button btWheelchair;

    //───────────────────────────────────────

    @FXML
    public void initialize() {
        imagesScreenSaver = new ArrayList<>();

        imagesScreenSaver.add(imageScreenSaver1);
        imagesScreenSaver.add(imageScreenSaver2);
        imagesScreenSaver.add(imageScreenSaver3);
        imagesScreenSaver.add(imageScreenSaver4);

        indexActualImage = 0;

        for (ImageView im : imagesScreenSaver) im.setVisible(false);

        sreenSaver = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            indexActualImage++;
            indexActualImage = indexActualImage % imagesScreenSaver.size();

            for (ImageView im : imagesScreenSaver) im.setVisible(false);

            imagesScreenSaver.get(indexActualImage).setVisible(true);
        }));

        sreenSaver.setCycleCount(Timeline.INDEFINITE);

        arrancaScreenSaver();
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    private void arrancaScreenSaver() {
        sreenSaver.play();
    }

    private void paraScreenSaver() {
        sreenSaver.stop();
        for (ImageView im : imagesScreenSaver) im.setVisible(false);
    }

    // ───────────────────────────────────────────────────────────────────────────────────────────

    public void clickadoprueba() {
        paraScreenSaver();
    }
}