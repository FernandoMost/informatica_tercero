package graphics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CommonController {
    private double xOffset, yOffset;

    // ───────────────────────────────────────────────────────────────────────────────────────────

    @FXML public void initialize() {
    }

    public void minimizar(ActionEvent e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void cerrar(ActionEvent e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
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
}
