package com.viii28stw.pensiltikfrontend.controller;

import com.viii28stw.pensiltikfrontend.controller.form.cadastro.CadastroUsuarioController;
import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.form.ajuda.SobreController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuEnum;
import com.viii28stw.pensiltikfrontend.model.domain.FormMenu;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class MDIController implements Initializable {

    @Setter
    private Stage mdiStage;
    private static HashMap<MenuEnum, FormMenu> listFormsMenu;
    @FXML
    private Hyperlink hlkNomeUsuario;
    @FXML
    private ImageView imgvwLogoVergo;
    @FXML
    private Label lblDataHora;

    @FXML
    private Menu mnConfiguracoes;
    @FXML
    private Menu mnCadastro;

    private Sessao usuarioLogado;

    private static MDIController uniqueInstance;

    public static synchronized MDIController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MDIController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblDataHora.setText("");
        listFormsMenu = new HashMap<>();
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaDataHora());
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        imgvwLogoVergo.setX(180);
        imgvwLogoVergo.setY(50);
    }

    private void atualizaDataHora() {
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEE',' dd/MM/yyyy HH:mm:ss");
        String dataHora = sdf.format(Calendar.getInstance().getTime());
        lblDataHora.setText(dataHora.substring(0, 1).toUpperCase().concat(dataHora.substring(1)));
    }

    @FXML
    private void mnuConfiguracaoContaUsuarioAction() {
        abreForm(MenuEnum.CONFIGURACOES_CONTA_USUARIO);
    }

    @FXML
    private void mnuCadastroTipoRendaAction() {
        abreForm(MenuEnum.CADASTRO_TIPO_RENDA);
    }

    @FXML
    private void mnuCadastroRendaAction() {
        abreForm(MenuEnum.CADASTRO_RENDA);
    }

    @FXML
    private void mnuCadastroTipoDespesaAction() {
        abreForm(MenuEnum.CADASTRO_TIPO_DESPESA);
    }

    @FXML
    private void mnuCadastroDespesaAction() {
        abreForm(MenuEnum.CADASTRO_DESPESA);
    }

    @FXML
    private void mnuCadastroUsuarioAction() {
        abreForm(MenuEnum.CADASTRO_USUARIO);
    }

    @FXML
    private void mnuRelatorioRendaAction() {
        abreForm(MenuEnum.RELATORIO_RENDA);
    }

    @FXML
    private void mnuRelatorioDespesasAction() {
        abreForm(MenuEnum.RELATORIO_DESPESAS);
    }

    @FXML
    private void mnuAjudaSobreAction() {
        abreForm(MenuEnum.AJUDA_SOBRE);
    }

    //--- *** ----- ### ----- *** ---
    @FXML
    private void hlkSairOnAction() {
        mdiStage.close();
    }

    private void abreForm(MenuEnum menuEnum) {
        boolean aberto = false;
        try {
            if (listFormsMenu.containsKey(menuEnum)) {
                aberto = true;
                listFormsMenu.get(menuEnum).getStage().toFront();
            }

            if (!aberto) {
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource(menuEnum.getFxmlPath()));
                StackPane parent = loader.load();

                Stage formStage = new Stage();
                formStage.initOwner(hlkNomeUsuario.getScene().getWindow());
                formStage.setResizable(false);
                formStage.setMaximized(false);
                formStage.initModality(menuEnum.equals(MenuEnum.AJUDA_SOBRE) ? Modality.APPLICATION_MODAL : Modality.NONE);
                formStage.setTitle(menuEnum.getTitle());
                if (null != menuEnum.getIcon() && !menuEnum.getIcon().isEmpty()) {
                    formStage.getIcons().add(new Image(menuEnum.getIcon()));
                }
                formStage.setX(CentralizeLocationRelativeToScreen.getX(parent.getPrefWidth()));
                formStage.setY(CentralizeLocationRelativeToScreen.getY(parent.getPrefHeight()));

                Scene scene = new Scene(parent);
                formStage.setScene(scene);

                formStage.setOnCloseRequest((WindowEvent we) -> fechaJanela(menuEnum));

                listFormsMenu.put(menuEnum, new FormMenu(menuEnum, formStage));

                //Flexible zone begining
                switch (menuEnum) {
//
//                    case CONFIGURACOES_CONTA_USUARIO: {
//                        ConfiguracaoContaUsuarioController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//
//                    case CADASTRO_PECA: {
//                        CadastroPecaSimplesController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//                    case CADASTRO_MONTAGEM: {
//                        CadastroMontagemPecaController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//                    case CADASTRO_UNIDADE_MEDIDA: {
//                        CadastroUnidadeMedidaController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//
//                    case CADASTRO_MAO_OBRA: {
//                        CadastroMaoDeObraController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//
//                    case CADASTRO_GRUPO_USUARIO: {
//                        CadastroGrupoUsuarioController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//
                    case CADASTRO_USUARIO: {
                        CadastroUsuarioController controller = loader.getController();
                        controller.setCadastroUsuarioStage(formStage);
                        break;
                    }
//
//                    case RELATORIO_PECAS: {
//                        RelatorioPecaController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//                    case RELATORIO_MONTAGEM_PECA: {
//                        RelatorioMontagemPecaController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
//
                    case AJUDA_SOBRE: {
                        SobreController controller = loader.getController();
                        controller.setSobreStage(formStage);
                        break;
                    }
//
                    default:
                        break;
                }
                //Flexible zone final

                formStage.showAndWait();

                if (usuarioLogado.isRequerLogout()) {
                    hlkSairOnAction();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public static void fechaJanela(MenuEnum menuEnum) {
        if (listFormsMenu.containsKey(menuEnum)) {
            listFormsMenu.remove(menuEnum);
        }

    }

}
