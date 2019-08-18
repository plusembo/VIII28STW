package com.viii28stw.pensiltikfrontend.util.dialogbox;

import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.enumeration.DialogType;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @author Plamedi L. Lusembo
 */
@NoArgsConstructor
public class DialogBoxFactory {
    private static DialogBoxFactory uniqueInstance;

    public static synchronized DialogBoxFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DialogBoxFactory();
        }
        return uniqueInstance;
    }

    public void inform(String title, String headerText, String contenText) throws IOException {
        show(DialogType.INFORMATION, title, headerText, contenText);
    }

    public void warn(String title, String headerText, String contenText) throws IOException {
        show(DialogType.WARNING, title, headerText, contenText);
    }

    public void miss(String title, String headerText, String contenText) throws IOException {
        show(DialogType.ERROR, title, headerText, contenText);
    }

    public boolean confirm(String title, String headerText, String contenText) throws IOException {
        return show(DialogType.CONFIRMATION, title, headerText, contenText);
    }


    private boolean show(DialogType dialogType, String title, String headerText, String contenText) throws IOException {
        Stage dialogBoxStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(I18nFactory.getInstance().getResourceBundle());
        loader.setLocation(MainApp.class.getResource("/fxml/util/dialogbox/dialog_box.fxml"));
        StackPane dialogBoxStackPane = loader.load();
        Scene dialogBoxScene = new Scene(dialogBoxStackPane);
        dialogBoxStage.setResizable(false);
        dialogBoxStage.setMaximized(false);
        dialogBoxStage.initModality(Modality.APPLICATION_MODAL);
        dialogBoxStage.setTitle(title.trim().isEmpty() ?
                dialogType.getDescription() :
                dialogType.getDescription().concat(" - ").concat(title));
        dialogBoxStage.setScene(dialogBoxScene);
        DialogBoxController dialogBoxController = loader.getController();
        dialogBoxController.setDialogBoxStage(dialogBoxStage);
        dialogBoxController.setDialogType(dialogType);
        dialogBoxController.getLblHeaderText().setText(headerText);
        dialogBoxController.getLblContentText().setText(contenText);
        dialogBoxStage.showAndWait();
        return dialogBoxController.isResultOkay();
    }

}