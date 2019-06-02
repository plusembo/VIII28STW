package com.viii28stw.pensiltikfrontend.controller.dialog;

import com.jfoenix.controls.JFXTextField;
import com.viii28stw.pensiltikfrontend.enumeration.NominatimCountryCodesEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LocalizadorI18nController implements Initializable {

    @Getter
    @Setter
    private Stage localizadorI18nStage;
    @FXML private TableView<NominatimCountryCodesEnum> tvwI18n;
    @FXML private TableColumn clmLanguageNameEnglish;
    @FXML private TableColumn clmLanguageNameLocal;
    @FXML private TableColumn clmCountryNameEnglish;
    @FXML private TableColumn clmCountryNameLocal;
    @FXML private JFXTextField jtxLanguage;
    @FXML private Label lblQtd;
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
        setColumnStyleProperty();

        NominatimCountryCodesEnum.getList().forEach(obsI18n::add);
        tvwI18n.setItems(obsI18n);

        atualizaQtd();
    }

    private void setColumnStyleProperty() {
        clmLanguageNameEnglish.setCellValueFactory(new PropertyValueFactory<>("languageNameEnglish"));
        clmLanguageNameEnglish.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 10 0 0;");
        clmLanguageNameEnglish.getStyleClass().add("right-header");

        clmLanguageNameLocal.setCellValueFactory(new PropertyValueFactory<>("languageNameLocal"));
        clmLanguageNameLocal.setStyle("-fx-alignment: CENTER-LEFT;");
        clmLanguageNameLocal.getStyleClass().add("left-header");

        clmCountryNameEnglish.setCellValueFactory(new PropertyValueFactory<>("countryNameEnglish"));
        clmCountryNameEnglish.setStyle("-fx-alignment: CENTER-LEFT;");
        clmCountryNameEnglish.getStyleClass().add("left-header");

        clmCountryNameLocal.setCellValueFactory(new PropertyValueFactory<>("contryNameLocal"));
        clmCountryNameLocal.setStyle("-fx-alignment: CENTER-LEFT;");
        clmCountryNameLocal.getStyleClass().add("left-header");
    }

    private void atualizaQtd() {
        if (!obsI18n.isEmpty()) {
            lblQtd.setText(obsI18n.size() > 1 ? obsI18n.size()
                    + " usuários encontrados" : obsI18n.size() + " usuário encontrado");
        } else {
            lblQtd.setText("");
        }
    }

    @FXML
    private void tvwI18nSelecionarMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            NominatimCountryCodesEnum nominatimCountryCodesEnum = (NominatimCountryCodesEnum) tvwI18n.getSelectionModel().getSelectedItem();
            if (nominatimCountryCodesEnum == null) {
                return;
            }
            localizadorI18nStage.close();
        }
    }

    @FXML
    private void jtxNomeFiltroKeyReleased() {
//        List<I18nDto> lstI18ns = usuarioService.buscarTodosOsI18ns();
//
//        obsI18n.clear();
//        lstI18ns.stream().filter((usuario1) -> (usuario1.getNome().toUpperCase()
//                .startsWith(jtxNomeFiltro.getText().toUpperCase())))
//                .forEachOrdered((usuario1) -> {
//                    obsI18n.add(null);
//                });
//
//        tvwI18n.setItems(obsI18n);

        atualizaQtd();

    }

}