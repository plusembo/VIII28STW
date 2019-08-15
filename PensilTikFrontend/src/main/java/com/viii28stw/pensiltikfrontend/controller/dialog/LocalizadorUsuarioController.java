package com.viii28stw.pensiltikfrontend.controller.dialog;

import com.jfoenix.controls.JFXTextField;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
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
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor
public class LocalizadorUsuarioController implements Initializable {

    @Setter
    private Stage localizadorUsuarioStage;
    private Stage configuracaoIdiomaStage;
    @FXML private TableView<Usuario> tvwUsuario;
    @FXML private TableColumn clmCodigo;
    @FXML private TableColumn clmNome;
    @FXML private TableColumn clmEmail;
    @FXML private TableColumn clmSexo;
    @FXML private JFXTextField jtxNomeFiltro;
    @FXML private Label lblQtd;
    private final ObservableList<Usuario> obsUsuario = FXCollections.observableArrayList();
    private static LocalizadorUsuarioController uniqueInstance;
    private UsuarioService usuarioService = null;

    public static synchronized LocalizadorUsuarioController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LocalizadorUsuarioController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumnStyleProperty();

        //Coloca lista de usuários no ObservableList
        //obsUsuario.addAll(UsuarioService.getInstance().listaUsuario());
        //tvwUsuario.setItems(obsUsuario);

        atualizaQtd();

    }

    private void setColumnStyleProperty() {
        clmCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clmCodigo.setStyle("-fx-alignment: CENTER-RIGHT; -fx-padding: 0 10 0 0;");
        clmCodigo.getStyleClass().add("right-header");

        clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clmNome.setStyle("-fx-alignment: CENTER-LEFT;");
        clmNome.getStyleClass().add("left-header");

        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmEmail.setStyle("-fx-alignment: CENTER-LEFT;");
        clmEmail.getStyleClass().add("left-header");

        clmSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        clmSexo.setStyle("-fx-alignment: CENTER;");
        clmSexo.getStyleClass().add("center-header");
    }

    private void atualizaQtd() {
        if (!obsUsuario.isEmpty()) {
            lblQtd.setText(obsUsuario.size() > 1 ? obsUsuario.size()
                    + " usuários encontrados" : obsUsuario.size() + " usuário encontrado");
        } else {
            lblQtd.setText("");
        }
    }

    @FXML
    private void tvwUsuarioSelecionarMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Usuario usr = (Usuario) tvwUsuario.getSelectionModel().getSelectedItem();
            if (usr == null) {
                return;
            }
            Stage stage = (Stage) lblQtd.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void jtxNomeFiltroKeyReleased() {
        List<UsuarioDto> lstUsuarios = usuarioService.buscarTodosOsUsuarios();

        obsUsuario.clear();
        lstUsuarios.stream().filter((usuario1) -> (usuario1.getNome().toUpperCase()
                .startsWith(jtxNomeFiltro.getText().toUpperCase())))
                .forEachOrdered((usuario1) -> {
                    obsUsuario.add(null);
                });

        tvwUsuario.setItems(obsUsuario);

        atualizaQtd();

    }

}