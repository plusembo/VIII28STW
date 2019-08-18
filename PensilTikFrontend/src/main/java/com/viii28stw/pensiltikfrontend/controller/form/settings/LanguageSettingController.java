package com.viii28stw.pensiltikfrontend.controller.form.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.controller.MainController;
import com.viii28stw.pensiltikfrontend.enumeration.LanguagesSetting;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.util.dialogbox.DialogBoxFactory;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class LanguageSettingController implements Initializable {
    @Setter
    private Stage languageSettingStage;
    @FXML
    private TableView<LanguagesSetting> tvwLanguages;
    @FXML
    private TableColumn clmLanguageNameEnglish;
    @FXML
    private TableColumn clmLanguageNameLocal;
    @FXML
    private TableColumn clmCountryNameEnglish;
    @FXML
    private TableColumn clmCountryNameLocal;
    @FXML
    private TextField tfdLanguage;
    @FXML
    private Label lblQtt;
    private static final String LEFT_HEADER_COLUMN_STYLE = "left-header";
    private static final String FX_ALIGNMENT_CENTER_LEFT_COLUMN_STYLE = "-fx-alignment: CENTER-LEFT;";
    private final ObservableList<LanguagesSetting> obsI18n = FXCollections.observableArrayList();
    private static LanguageSettingController uniqueInstance;

    public static synchronized LanguageSettingController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LanguageSettingController();
        }
        return uniqueInstance;
    }

    /**
     * Initializes the controller class.
     *
     * @param location
     * @param resources
     *
     *  @version 1.0.0
     *  @since August 06, 2019
     *  @author Plamedi L. Lusembo
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvwLanguages.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                    Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (Boolean.TRUE.equals(newPropertyValue)) {
                tvwLanguages.getSelectionModel().selectFirst();
            }
        });
        setTvwLanguagesColumnStyleProperty();
        LanguagesSetting.getList().forEach(obsI18n::add);
        tvwLanguages.setItems(obsI18n);
        lblQttUpdate();
    }

    /**
     * Set the style property of each row of the table.
     * <p>
     * The purpose is to set the color of a row of the table
     * according if the language is currently set by default, or if is available or not.
     * </p>
     *<p>
     * Set the GREEN color for the default language, BLACK for the available one
     * and unavailable language set SILVER color.
     *</p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    private void setTvwLanguagesColumnStyleProperty() {
        clmLanguageNameEnglish.setCellValueFactory(new PropertyValueFactory<>("languageNameEnglish"));
        clmLanguageNameEnglish.setStyle(FX_ALIGNMENT_CENTER_LEFT_COLUMN_STYLE.concat(" -fx-padding: 0 10 0 0;"));
        clmLanguageNameEnglish.getStyleClass().add(LEFT_HEADER_COLUMN_STYLE);
        clmLanguageNameEnglish.setCellFactory(column -> new TableCell<LanguagesSetting, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        } else if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                        setText(item);
                    } catch (NullPointerException ex) {
                        Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }
        });

        clmLanguageNameLocal.setCellValueFactory(new PropertyValueFactory<>("languageNameLocal"));
        clmLanguageNameLocal.setStyle(FX_ALIGNMENT_CENTER_LEFT_COLUMN_STYLE);
        clmLanguageNameLocal.getStyleClass().add(LEFT_HEADER_COLUMN_STYLE);
        clmLanguageNameLocal.setCellFactory(column -> new TableCell<LanguagesSetting, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        } else if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                        setText(item);
                    } catch (NullPointerException ex) {
                        Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }
        });

        clmCountryNameEnglish.setCellValueFactory(new PropertyValueFactory<>("countryNameEnglish"));
        clmCountryNameEnglish.setStyle(FX_ALIGNMENT_CENTER_LEFT_COLUMN_STYLE);
        clmCountryNameEnglish.getStyleClass().add(LEFT_HEADER_COLUMN_STYLE);
        clmCountryNameEnglish.setCellFactory(column -> new TableCell<LanguagesSetting, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        } else if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                        setText(item);
                    } catch (NullPointerException ex) {
                        Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }
        });

        clmCountryNameLocal.setCellValueFactory(new PropertyValueFactory<>("countryNameLocal"));
        clmCountryNameLocal.setStyle(FX_ALIGNMENT_CENTER_LEFT_COLUMN_STYLE);
        clmCountryNameLocal.getStyleClass().add(LEFT_HEADER_COLUMN_STYLE);
        clmCountryNameLocal.setCellFactory(column -> new TableCell<LanguagesSetting, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        } else if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.BLACK);
                        }
                        setText(item);
                    } catch (NullPointerException ex) {
                        Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }
        });

    }

    /**
     * Calculate the quantity of available languages on the table.
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    private int calculateQttAvailableLanguages() {
        int qttAvailableLanguages = 0;
        for (LanguagesSetting languagesSetting : obsI18n) {
            if (languagesSetting.isAvailable()) {
                qttAvailableLanguages++;
            }
        }
        return qttAvailableLanguages;
    }

    /**
     * Update the lblQtt label with the quantity of available languages on the table.
     * <p>
     * Call the calculateQttAvailableLanguages() method
     * to get the quantity of available languages on the table.
     * </p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    private void lblQttUpdate() {
        int qttAvailableLanguages = calculateQttAvailableLanguages();
        if (qttAvailableLanguages > 0) {
            String pattern = I18nFactory.getInstance().getResourceBundle().getString("label.available.language");
            String message = MessageFormat.format(pattern, qttAvailableLanguages, qttAvailableLanguages > 1 ? "s" : "");
            lblQtt.setText(message);
        } else {
            lblQtt.setText(I18nFactory.getInstance()
                    .getResourceBundle().getString("label.no.language.available"));
        }
    }

    /**
     * Handle the On Key Released action of the table selected row.
     * <p>
     * Perform the overwrite of the content of 'language-setting.i18n' file,
     * by calling of setLanguageRowSelected() method..
     * </p>
     *
     * @param evt
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    @FXML
    private void tvwLanguagesOnKeyReleased(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            setLanguageRowSelected();
        }
    }

    /**
     * Handle the On Mouse Clicked action of the table selected row.
     * <p>
     * Perform the overwrite of the content of 'language-setting.i18n' file,
     * by calling of setLanguageRowSelected() method.
     * </p>
     *
     * @param mouseEvent
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    @FXML
    private void tvwLanguagesOnMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            setLanguageRowSelected();
        }
    }

    /**
     * Perform the overwrite of the content of 'language-setting.i18n' file,
     * in order to be reloaded by applying the selected row language change.
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    private void setLanguageRowSelected() {
        LanguagesSetting languagesSetting = tvwLanguages.getSelectionModel().getSelectedItem();
        if (languagesSetting == null || !languagesSetting.isAvailable()) {
            return;
        }
        if (languagesSetting.getLanguageCode().concat("_").concat(languagesSetting.getCountryCode())
                .equals(I18nFactory.getInstance().getLocale().toString())) {
            Sessao.getInstance().setLogoutRequest(false);
        } else {
            try {
                new ObjectMapper()
                        .writeValue(new File("language-setting.i18n"), languagesSetting);
            } catch (IOException ex) {
                Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            if (Sessao.getInstance().isLogoutRequest()) {
                try {
                    if (!DialogBoxFactory.getInstance().confirm(I18nFactory.getInstance().getResourceBundle().getString("dialog.title.close.the.system"),
                            I18nFactory.getInstance().getResourceBundle().getString("dialog.you.are.about.to.close.the.system"),
                            I18nFactory.getInstance().getResourceBundle().getString("dialog.contenttext.are.you.sure.you.want.to.close.the.system"))) {
                        Sessao.getInstance().setLogoutRequest(false);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LanguageSettingController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
        MainController.fechaJanela(MenuMatch.SETUP_SYSTEM_LANGUAGE);
        languageSettingStage.close();
    }

    /**
     * Handle the On Key Typed action of the language search field.
     * <p>
     * Capitalize the first letter of language search field input.
     * And move the cursor on the last position.
     * </p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    @FXML
    private void tfdLanguageOnKeyTyped() {
        if (tfdLanguage.getText().length() == 1) {
            tfdLanguage.setText(tfdLanguage.getText().substring(0, 1).toUpperCase());
            tfdLanguage.positionCaret(tfdLanguage.getCaretPosition() + 1);
        }
    }

    /**
     * Handle the On Key Released action of the language search field.
     * <p>
     * Performs in the table, the search of the word entered in the field.
     * </p>
     * <p>
     * At the end of processing, call the calculateQttAvailableLanguages() method
     * to get the quantity of available languages on the table.
     * </p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 10, 2019
     */
    @FXML
    private void tfdLanguageOnKeyReleased() {
        obsI18n.clear();
        for (LanguagesSetting ncce : LanguagesSetting.getList()) {
            if (ncce.getLanguageNameLocal().toUpperCase().startsWith(tfdLanguage.getText().toUpperCase()) ||
                    ncce.getLanguageNameEnglish().toUpperCase().startsWith(tfdLanguage.getText().toUpperCase()) ||
                    ncce.getCountryNameLocal().toUpperCase().startsWith(tfdLanguage.getText().toUpperCase()) ||
                    ncce.getCountryNameEnglish().toUpperCase().startsWith(tfdLanguage.getText().toUpperCase())) {
                obsI18n.add(ncce);
            }
        }
        tvwLanguages.setItems(obsI18n);
        setTvwLanguagesColumnStyleProperty();
        lblQttUpdate();
    }

}