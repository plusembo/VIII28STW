package com.viii28stw.pensiltikfrontend.controller.form.help;

import com.viii28stw.pensiltikfrontend.controller.MDIController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class AboutController implements Initializable {

    @FXML private Label lblVersao;
    @Setter private Stage aboutStage;
    private static AboutController uniqueInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblVersao.setText("Versao");
    }

    public static synchronized AboutController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AboutController();
        }
        return uniqueInstance;
    }

    @FXML
    private void jbtnFecharAction() {
        aboutStage.close();
        MDIController.fechaJanela(MenuMatch.AJUDA_SOBRE);
    }

}
