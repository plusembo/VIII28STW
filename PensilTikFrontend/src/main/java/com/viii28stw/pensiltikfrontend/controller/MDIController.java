package com.viii28stw.pensiltikfrontend.controller;

import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.form.registration.CadastroUsuarioController;
import com.viii28stw.pensiltikfrontend.controller.form.help.AboutController;
import com.viii28stw.pensiltikfrontend.controller.form.registration.CadastroDespesaController;
import com.viii28stw.pensiltikfrontend.controller.form.settings.LanguageSettingController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMDI;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
import com.viii28stw.pensiltikfrontend.model.domain.FormMDI;
import com.viii28stw.pensiltikfrontend.model.domain.FormMenu;
import com.viii28stw.pensiltikfrontend.model.domain.Sessao;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class MDIController implements Initializable {

    private static HashMap<MenuMatch, FormMenu> listFormsMenu;
    private static  HashMap<MenuMDI, FormMDI> listFormsMDI;
    private static MDIController uniqueInstance;
    @Setter
    private Stage mdiStage;
    @FXML
    private TabPane tbpMDI;
    @FXML
    private Hyperlink hlkNomeUsuario;
    @FXML
    private Label lblDataHora;
    @FXML
    private Menu mnConfiguracoes;
    @FXML
    private VBox vbxTest;
    @FXML
    private Menu mnCadastro;

    public static synchronized MDIController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MDIController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        responsiveTbpMDI();

        lblDataHora.setText("");
        listFormsMenu = new HashMap<>();
        listFormsMDI = new HashMap<>();
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaDataHora());
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        hlkNomeUsuario.setText(Sessao.getInstance().getUsuario().getNome()
                .concat(" (")
                .concat(Sessao.getInstance().getUsuario().getEmail())
                .concat(")"));
    }

    private void responsiveTbpMDI() {
        double swtHeight = 0;
        double defaultScreenHeight = 1440;
        double defaultSwtHeight = 621;

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double screenHeight = gd.getDisplayMode().getHeight();

        if(screenHeight == defaultScreenHeight) {
            swtHeight = defaultSwtHeight;
            tbpMDI.setPrefHeight(swtHeight);
        } else {
            double percentage = (screenHeight * 100)/defaultScreenHeight;
            double swtHeightDiff = (defaultSwtHeight * percentage)/100;
            swtHeight = screenHeight < defaultScreenHeight ? defaultSwtHeight - swtHeightDiff : defaultSwtHeight + swtHeightDiff;
            tbpMDI.setPrefHeight(swtHeight);
        }
    }

    private void atualizaDataHora() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, I18nFactory.getInstance().getLocale());
        String data = df.format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        lblDataHora.setText(data.substring(0, 1).toUpperCase().concat(data.substring(1)).concat(" ").concat(hora));
    }

    public static void fechaJanela(MenuMatch menuMatch) {
        try {
            if (listFormsMenu.containsKey(menuMatch)) {
                listFormsMenu.remove(menuMatch);
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void closeMDI(MenuMDI menumenuMDI) {
        try {
            if (listFormsMDI.containsKey(menumenuMDI)) {
                listFormsMDI.remove(menumenuMDI);
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void mnuConfiguracaoContaUsuarioAction() {
        abreForm(MenuMatch.CONFIGURACOES_CONTA_USUARIO);
    }

    @FXML
    private void mnuSetUpSystemLanguageAction() {
        abreForm(MenuMatch.SETUP_SYSTEM_LANGUAGE);
    }

    @FXML
    private void mnuCadastroTipoRendaAction() {
        abreForm(MenuMatch.CADASTRO_TIPO_RENDA);
    }

    @FXML
    private void mnuCadastroRendaAction() {
        abreForm(MenuMatch.CADASTRO_RENDA);
    }

    @FXML
    private void mnuCadastroTipoDespesaAction() {
        abreForm(MenuMatch.CADASTRO_TIPO_DESPESA);
    }

    @FXML
    private void mnuCadastroDespesaAction() {
        openWindowForm();
    }

    @FXML
    private void mnuCadastroUsuarioAction() {
        openMDI(MenuMDI.CADASTRO_USUARIO);
    }

    @FXML
    private void mnuRelatorioRendaAction() {
        abreForm(MenuMatch.RELATORIO_RENDA);
    }

    @FXML
    private void mnuRelatorioDespesasAction() {
        abreForm(MenuMatch.RELATORIO_DESPESAS);
    }

    @FXML
    private void mnuAjudaSobreAction() {
        abreForm(MenuMatch.AJUDA_SOBRE);
    }

    //--- *** ----- ### ----- *** ---
    @FXML
    private void hlkSairOnAction() {
        mdiStage.close();
    }

    private void openWindowForm() {
        try {
            Tab tab = new Tab();
            Label label = new Label("Nova tab");
            tab.setGraphic(label);
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class.getResource("/fxml/form/cadastros/cadastro_teste.fxml"));
            BorderPane borderPane = loader.load();
            tab.setContent(borderPane);
            tbpMDI.getTabs().add(tab);
            tbpMDI.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMDI(MenuMDI menuMDI) {
        try {
            if (listFormsMDI.containsKey(menuMDI)) {
                tbpMDI.getSelectionModel().select(listFormsMDI.get(menuMDI).getTab());
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource(menuMDI.getFxmlPath()));
                StackPane parent = loader.load();

//                formStage.initModality(menuMDI.equals(MenuMatch.AJUDA_SOBRE) ? Modality.APPLICATION_MODAL : Modality.NONE);
//                formStage.setTitle(menuMDI.getTitle());
                if (null != menuMDI.getIcon() && !menuMDI.getIcon().isEmpty()) {
//                    formStage.getIcons().add(new Image(menuMDI.getIcon()));
                }
                Tab tab = new Tab();
                tab.setContent(parent);
                tbpMDI.getTabs().add(tab);
                tbpMDI.getSelectionModel().select(tab);
                tab.setOnCloseRequest((Event we) -> closeMDI(menuMDI));
                listFormsMDI.put(menuMDI, new FormMDI(menuMDI, tab));
            }

        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    private void abreForm(MenuMatch menuMatch) {
        boolean aberto = false;
        try {
            if (listFormsMenu.containsKey(menuMatch)) {
                aberto = true;
                listFormsMenu.get(menuMatch).getStage().toFront();
            }

            if (!aberto) {
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource(menuMatch.getFxmlPath()));
                StackPane parent = loader.load();

                Stage formStage = new Stage();
                formStage.initOwner(hlkNomeUsuario.getScene().getWindow());
                formStage.setResizable(false);
                formStage.setMaximized(false);
                formStage.initModality(menuMatch.equals(MenuMatch.AJUDA_SOBRE) ? Modality.APPLICATION_MODAL : Modality.NONE);
                formStage.setTitle(menuMatch.getTitle());
                if (null != menuMatch.getIcon() && !menuMatch.getIcon().isEmpty()) {
                    formStage.getIcons().add(new Image(menuMatch.getIcon()));
                }
                Scene scene = new Scene(parent);
                formStage.setScene(scene);
                formStage.setOnCloseRequest((WindowEvent we) -> fechaJanela(menuMatch));
                listFormsMenu.put(menuMatch, new FormMenu(menuMatch, formStage));

                //Flexible zone begining
                switch (menuMatch) {
//
//                    case CONFIGURACOES_CONTA_USUARIO: {
//                        ConfiguracaoContaUsuarioController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
                    case SETUP_SYSTEM_LANGUAGE: {
                        LanguageSettingController controller = loader.getController();
                        controller.setLanguageSettingStage(formStage);
                        Sessao.getInstance().setLogoutRequest(true);
                        break;
                    }
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

                    case CADASTRO_DESPESA: {
                        CadastroDespesaController controller = loader.getController();
                        controller.setCadastroDespesaStage(formStage);
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
                        AboutController controller = loader.getController();
                        controller.setAboutStage(formStage);
                        break;
                    }
//
                    default:
                        break;
                }
                //Flexible zone final

                formStage.showAndWait();

                if (Sessao.getInstance().isLogoutRequest()) {
                    hlkSairOnAction();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(MDIController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

}
