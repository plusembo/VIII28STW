package com.viii28stw.pensiltikfrontend.controller.form.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import com.viii28stw.pensiltikfrontend.controller.MDIController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
import com.viii28stw.pensiltikfrontend.enumeration.NominatimCountryCodes;
import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.util.dialogbox.DialogBoxFactory;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class LanguageSettingController implements Initializable {

    @Setter
    private Stage languageSettingStage;
    @FXML
    private TableView<NominatimCountryCodes> tvwLanguages;
    @FXML
    private TableColumn clmLanguageNameEnglish;
    @FXML
    private TableColumn clmLanguageNameLocal;
    @FXML
    private TableColumn clmCountryNameEnglish;
    @FXML
    private TableColumn clmCountryNameLocal;
    @FXML
    private JFXTextField jtxLanguageFilter;
    @FXML
    private Label lblQtd;
    private final ObservableList<NominatimCountryCodes> obsI18n = FXCollections.observableArrayList();
    private static LanguageSettingController uniqueInstance;

    public static synchronized LanguageSettingController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LanguageSettingController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvwLanguagesColumnStyleProperty();

        NominatimCountryCodes.getList().forEach(obsI18n::add);
        tvwLanguages.setItems(obsI18n);

        atualizaLblQtdIdiomasDisponiveis();
    }

    private void setTvwLanguagesColumnStyleProperty() {
        clmLanguageNameEnglish.setCellValueFactory(new PropertyValueFactory<>("languageNameEnglish"));
        clmLanguageNameEnglish.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 10 0 0;");
        clmLanguageNameEnglish.getStyleClass().add("left-header");
        clmLanguageNameEnglish.setCellFactory(column -> new TableCell<NominatimCountryCodes, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
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
                    } catch (NullPointerException e) {
                    }
                }
            }
        });

        clmLanguageNameLocal.setCellValueFactory(new PropertyValueFactory<>("languageNameLocal"));
        clmLanguageNameLocal.setStyle("-fx-alignment: CENTER-LEFT;");
        clmLanguageNameLocal.getStyleClass().add("left-header");
        clmLanguageNameLocal.setCellFactory(column -> new TableCell<NominatimCountryCodes, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
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
                    } catch (NullPointerException e) {
                    }
                }
            }
        });

        clmCountryNameEnglish.setCellValueFactory(new PropertyValueFactory<>("countryNameEnglish"));
        clmCountryNameEnglish.setStyle("-fx-alignment: CENTER-LEFT;");
        clmCountryNameEnglish.getStyleClass().add("left-header");
        clmCountryNameEnglish.setCellFactory(column -> new TableCell<NominatimCountryCodes, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
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
                    } catch (NullPointerException e) {
                    }
                }
            }
        });

        clmCountryNameLocal.setCellValueFactory(new PropertyValueFactory<>("countryNameLocal"));
        clmCountryNameLocal.setStyle("-fx-alignment: CENTER-LEFT;");
        clmCountryNameLocal.getStyleClass().add("left-header");
        clmCountryNameLocal.setCellFactory(column -> new TableCell<NominatimCountryCodes, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
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
                    } catch (NullPointerException e) {
                    }
                }
            }
        });

    }

    private void atualizaLblQtdIdiomasDisponiveis() {
        if (!obsI18n.isEmpty()) {
            int qtdIdiomasDisponiveis = 0;
            for (NominatimCountryCodes nominatimCountryCodes : obsI18n) {
                if (nominatimCountryCodes.isAvailable()) {
                    qtdIdiomasDisponiveis++;
                }
            }
            lblQtd.setText(qtdIdiomasDisponiveis == 0 ?
                    I18nFactory.getInstance().getResourceBundle().getString("label.no.language.available") :
                    qtdIdiomasDisponiveis > 1 ?
                            qtdIdiomasDisponiveis + " ".concat(I18nFactory.getInstance()
                                    .getResourceBundle().getString("label.available.languages")) :
                            qtdIdiomasDisponiveis + " ".concat(I18nFactory.getInstance()
                                    .getResourceBundle().getString("label.available.language")));
        } else {
            lblQtd.setText(I18nFactory.getInstance().getResourceBundle().getString("label.no.language.available"));
        }
    }

    @FXML
    private void tvwLanguagesSelecionaLinhaMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            NominatimCountryCodes nominatimCountryCodes = tvwLanguages.getSelectionModel().getSelectedItem();
            if (nominatimCountryCodes == null || !nominatimCountryCodes.isAvailable()) {
                return;
            }
            if (nominatimCountryCodes.getLanguageCode().concat("_").concat(nominatimCountryCodes.getCountryCode())
                    .equals(I18nFactory.getInstance().getLocale().toString())) {
                Sessao.getInstance().setLogoutRequest(false);
            } else {
                try {
                    new ObjectMapper()
                            .writeValue(new File("nominatim.i18n"), nominatimCountryCodes);
                } catch (IOException ex) {
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
            MDIController.fechaJanela(MenuMatch.SETUP_SYSTEM_LANGUAGE);
            languageSettingStage.close();
        }
    }

    @FXML
    private void jtxLanguageFiltroKeyReleased() {
        obsI18n.clear();
        for (NominatimCountryCodes ncce : NominatimCountryCodes.getList()) {
            if (ncce.getLanguageNameLocal().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getLanguageNameEnglish().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getCountryNameLocal().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getCountryNameEnglish().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase())) {
                obsI18n.add(ncce);
            }
        }

        tvwLanguages.setItems(obsI18n);
        setTvwLanguagesColumnStyleProperty();
        atualizaLblQtdIdiomasDisponiveis();
    }

}