package com.viii28stw.pensiltikfrontend.controller.dialog;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LocalizaUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tvwUsuario;
    @FXML
    private TableColumn clmCodigo;
    @FXML
    private TableColumn clmNome;
    @FXML
    private JFXTextField jtxNomeFiltro;
    @FXML
    private Label lblQtd;
    private final ObservableList<Usuario> obsUsuario = FXCollections.observableArrayList();
    private Usuario usuario;
    private static ConsultaUsuarioController uniqueInstance;

    public ConsultaUsuarioController() {
    }

    public static synchronized ConsultaUsuarioController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ConsultaUsuarioController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmCodigo.setCellValueFactory(new PropertyValueFactory<>("cdUsuario"));
        clmCodigo.setStyle("-fx-alignment: CENTER-RIGHT; -fx-padding: 0 10 0 0;");
        clmCodigo.getStyleClass().add("right-header");

        clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clmNome.setStyle("-fx-alignment: CENTER-LEFT;");
        clmNome.getStyleClass().add("left-header");

        try {
            //Coloca lista de usuários no ObservableList
            obsUsuario.addAll(UsuarioService.getInstance().listaUsuario());
            tvwUsuario.setItems(obsUsuario);

            atualizaQtd();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConsultaUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            this.setUsuario(usr);
            Stage stage = (Stage) lblQtd.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void jtxNomeFiltroKeyReleased() {
        try {
            List<Usuario> lstUsuarios = UsuarioService.getInstance().listaUsuario();

            obsUsuario.clear();
            lstUsuarios.stream().filter((usuario1) -> (usuario1.getNome().toUpperCase()
                    .startsWith(jtxNomeFiltro.getText().toUpperCase())))
                    .forEachOrdered((usuario1) -> {
                        obsUsuario.add(usuario1);
                    });

            tvwUsuario.setItems(obsUsuario);

            atualizaQtd();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConsultaUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}