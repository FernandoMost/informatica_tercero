package graphics;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controller {
    @FXML private JFXButton minimizarButton;
    @FXML private JFXButton cerrarButton;
    @FXML private HBox topBar;
    @FXML private BorderPane primaryStageLogin;

    private double xOffset, yOffset;

    // ───────────────────────────────────────────────────────────────────────────────────────────

    @FXML public void initialize() {
    }

    public void minimizar(MouseEvent e) {
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void cerrar() {
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
