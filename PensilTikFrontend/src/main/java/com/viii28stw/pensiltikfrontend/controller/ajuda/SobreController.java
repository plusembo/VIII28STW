package com.viii28stw.pensiltikfrontend.controller.ajuda;

import com.viii28stw.pensiltikfrontend.controller.MDIController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuEnum;
import com.viii28stw.pensiltikfrontend.model.domain.FormMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class SobreController implements Initializable {

    @FXML
    private Label lblVersao;
    @Setter
    private Stage formStage;
    private static SobreController uniqueInstance;

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

    public static synchronized SobreController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SobreController();
        }
        return uniqueInstance;
    }

    @FXML
    private void jbtnFecharAction() {
        formStage.close();
        for (FormMenu fm : MDIController.lstFormsMenu) {
            if (fm.getMenum().equals(MenuEnum.AJUDA_SOBRE)) {
                MDIController.lstFormsMenu.remove(fm);
                break;
            }
        }
    }

}
