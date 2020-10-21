package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerNotiRemoveWord {

    @FXML
    private Button cancelNoti;

    public void cancel() {
        Stage stage = (Stage) cancelNoti.getScene().getWindow();
        stage.close();
    }
}
