package com.viii28stw.pensiltikfrontend.controller.dialog;

import com.jfoenix.controls.JFXTextField;
import com.viii28stw.pensiltikfrontend.model.domain.I18n;
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
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LocalizaI18nController implements Initializable {

    @FXML private TableView<I18n> tvwI18n;
    @FXML private TableColumn clmLanguage;
    @FXML private TableColumn clmCountry;
    @FXML private TableColumn clmFlag;
    @FXML private JFXTextField jtxLanguage;
    @FXML private Label lblQtd;
    private final ObservableList<I18n> obsI18n = FXCollections.observableArrayList();
    private static LocalizaI18nController uniqueInstance;

    public static synchronized LocalizaI18nController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LocalizaI18nController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnStyleProperty();

        //Coloca lista de usuários no ObservableList
        //obsI18n.addAll(I18nService.getInstance().listaI18n());
        //tvwI18n.setItems(obsI18n);

        atualizaQtd();

    }

    private void setColumnStyleProperty() {
        clmLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
        clmLanguage.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 10 0 0;");
        clmLanguage.getStyleClass().add("right-header");

        clmCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        clmCountry.setStyle("-fx-alignment: CENTER-LEFT;");
        clmCountry.getStyleClass().add("left-header");

        clmFlag.setCellValueFactory(new PropertyValueFactory<>("flag"));
        clmFlag.setStyle("-fx-alignment: CENTER-LEFT;");
        clmFlag.getStyleClass().add("left-header");
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
            I18n usr = (I18n) tvwI18n.getSelectionModel().getSelectedItem();
            if (usr == null) {
                return;
            }
            Stage stage = (Stage) lblQtd.getScene().getWindow();
            stage.close();
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