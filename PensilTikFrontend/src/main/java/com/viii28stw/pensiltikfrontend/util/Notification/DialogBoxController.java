package com.viii28stw.pensiltikfrontend.util.Notification;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class DialogBoxController implements Initializable {

    @Setter private Stage dialogBoxStage;
    @FXML private Label lblHeaderText;
    @FXML private Label lblContentText;
    private static DialogBoxController uniqueInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public static synchronized DialogBoxController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DialogBoxController();
        }
        return uniqueInstance;
    }

    @FXML
    private void jbtnOKAction() {
        dialogBoxStage.close();
    }

}
