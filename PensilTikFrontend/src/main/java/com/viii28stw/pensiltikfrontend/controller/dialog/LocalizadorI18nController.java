package com.viii28stw.pensiltikfrontend.controller.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXTextField;
import com.viii28stw.pensiltikfrontend.enumeration.NominatimCountryCodesEnum;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LocalizadorI18nController implements Initializable {

    @Getter
    @Setter
    private Stage localizadorI18nStage;
    @FXML
    private TableView<NominatimCountryCodesEnum> tvwI18n;
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
    private final ObservableList<NominatimCountryCodesEnum> obsI18n = FXCollections.observableArrayList();
    private static LocalizadorI18nController uniqueInstance;

    public static synchronized LocalizadorI18nController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LocalizadorI18nController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvwI18nColumnStyleProperty();

        NominatimCountryCodesEnum.getList().forEach(obsI18n::add);
        tvwI18n.setItems(obsI18n);

        atualizaLblQtdIdiomasDisponiveis();
    }

    private void setTvwI18nColumnStyleProperty() {
        clmLanguageNameEnglish.setCellValueFactory(new PropertyValueFactory<>("languageNameEnglish"));
        clmLanguageNameEnglish.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 10 0 0;");
        clmLanguageNameEnglish.getStyleClass().add("left-header");
        clmLanguageNameEnglish.setCellFactory(column -> new TableCell<NominatimCountryCodesEnum, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        }
                        if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.BLUEVIOLET);
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
        clmLanguageNameLocal.setCellFactory(column -> new TableCell<NominatimCountryCodesEnum, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        }
                        if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.BLUEVIOLET);
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
        clmCountryNameEnglish.setCellFactory(column -> new TableCell<NominatimCountryCodesEnum, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        }
                        if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.BLUEVIOLET);
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
        clmCountryNameLocal.setCellFactory(column -> new TableCell<NominatimCountryCodesEnum, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    return;
                } else {
                    try {
                        if (!getTableRow().getItem().isAvailable()) {
                            setTextFill(Color.SILVER);
                        }
                        if (getTableRow().getItem().getLanguageCode().concat("_").concat(getTableRow().getItem().getCountryCode())
                                .equals(I18nFactory.getInstance().getLocale().toString())) {
                            setTextFill(Color.BLUEVIOLET);
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
            for (NominatimCountryCodesEnum nominatimCountryCodesEnum : obsI18n) {
                if (nominatimCountryCodesEnum.isAvailable()) {
                    qtdIdiomasDisponiveis++;
                }
            }
            lblQtd.setText(qtdIdiomasDisponiveis == 0 ?
                    "Nenhuma lingua disponível" :
                    qtdIdiomasDisponiveis > 1 ?
                            qtdIdiomasDisponiveis + " linguas disponíveis" :
                            qtdIdiomasDisponiveis + " lingua disponível");
        } else {
            lblQtd.setText("Nenhuma lingua disponível");
        }
    }

    @FXML
    private void tvwI18nSelecionaLinhaMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            NominatimCountryCodesEnum nominatimCountryCodesEnum = tvwI18n.getSelectionModel().getSelectedItem();
            if (nominatimCountryCodesEnum == null || !nominatimCountryCodesEnum.isAvailable()) {
                return;
            }
            try {
                new ObjectMapper()
                        .writeValue(new File("include/nominatim.i18n"), nominatimCountryCodesEnum);
            } catch (IOException ex) {
            }
            I18nFactory.getInstance().setSystemLanguage(nominatimCountryCodesEnum);
            try {
                new ObjectMapper()
                        .readValue(new File("include/nominatim.i18n"), NominatimCountryCodesEnum.class);
            } catch (IOException ex) {
            }
            localizadorI18nStage.close();
        }
    }

    @FXML
    private void jtxLanguageFiltroKeyReleased() {
        obsI18n.clear();

        for (NominatimCountryCodesEnum ncce : NominatimCountryCodesEnum.getList()) {
            if (ncce.getLanguageNameLocal().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getLanguageNameEnglish().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getCountryNameLocal().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase()) ||
                    ncce.getCountryNameEnglish().toUpperCase().startsWith(jtxLanguageFilter.getText().toUpperCase())) {
                obsI18n.add(ncce);
            }
        }

        tvwI18n.setItems(obsI18n);
        setTvwI18nColumnStyleProperty();
        atualizaLblQtdIdiomasDisponiveis();
    }

}