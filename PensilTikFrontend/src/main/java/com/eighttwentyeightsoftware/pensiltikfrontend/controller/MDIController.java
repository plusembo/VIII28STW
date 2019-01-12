package com.eighttwentyeightsoftware.pensiltikfrontend.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eighttwentyeightsoftware.pensiltikfrontend.MainApp;
import com.eighttwentyeightsoftware.pensiltikfrontend.controller.ajuda.SobreController;
import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.MenuEnum;
import com.eighttwentyeightsoftware.pensiltikfrontend.model.domain.FormMenu;
import com.eighttwentyeightsoftware.pensiltikfrontend.model.domain.UsuarioLogado;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class MDIController implements Initializable {

    private Stage mainLayoutStage;
    public static List<FormMenu> lstFormsMenu;
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

    @Autowired
    private UsuarioLogado usuarioLogado;

    private static MDIController uniqueInstance;

    public static synchronized MDIController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MDIController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        mnConfiguracoes.getItems().stream().filter((mi) -> (mi.getId().equals("CFG001")))
//                .forEachOrdered((mi) -> {
//                    mi.setVisible(false);
//                });
//        if (UsuarioLogado.getInstance().getUsuario().getGrupoUsuario().getCdGrupoUsuario() != 1) {
//
//            mnCadastro.getItems().stream().filter((mi) -> (mi.getId().equals("SPRT")
//                    || mi.getId().equals("CAD005") || mi.getId().equals("CAD006")))
//                    .forEachOrdered((mi) -> {
//                        mi.setVisible(false);
//                    });
//        }

//        hlkNomeUsuario.setText(String.format("%s (%s)", usuarioLogado.getUsuario().getNome(), usuarioLogado.getUsuario().getEmail()));

        lblDataHora.setText("");
        lstFormsMenu = new ArrayList();
        KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> atualizaHoras());
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        imgvwLogoVergo.setX(180);
        imgvwLogoVergo.setY(50);
    }

    private void atualizaHoras() {
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEE',' dd/MM/yyyy HH:mm:ss");
        lblDataHora.setText(sdf.format(Calendar.getInstance().getTime()));
    }

    private double calculaX(double w) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm = tk.getScreenSize();
        return (dm.width - w) / 2.0;
    }

    private double calculaY(double h) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dm = tk.getScreenSize();
        return (dm.height - h) / 2.0;
    }

    @FXML
    private void mnuConfiguracaoContaUsuarioAction() {
        abreForm(MenuEnum.CONFIGURACOES_CONTA_USUARIO, "configuracao-conta-usuario.fxml",
                null, calculaX(627), calculaY(485));
    }

    @FXML
    private void mnuCadastroTipoRendaAction() {
        abreForm(MenuEnum.CADASTRO_TIPO_RENDA, "cadastro-tipo-renda.fxml",
                null, calculaX(627), calculaY(562));
    }

    @FXML
    private void mnuCadastroRendaAction() {
        abreForm(MenuEnum.CADASTRO_RENDA, "cadastro-renda.fxml",
                null, calculaX(1207), calculaY(614));
    }

    @FXML
    private void mnuCadastroTipoDespesaAction() {
        abreForm(MenuEnum.CADASTRO_TIPO_DESPESA, "cadastro-tipo-despesa.fxml",
                null, calculaX(627), calculaY(533));
    }

    @FXML
    private void mnuCadastroDespesaAction() {
        abreForm(MenuEnum.CADASTRO_DESPESA, "cadastro-despesa.fxml",
                null, calculaX(627), calculaY(439));
    }

    @FXML
    private void mnuRelatorioRendaAction() {
        abreForm(MenuEnum.RELATORIO_RENDA, "relatorio-renda.fxml",
                null, calculaX(650), calculaY(600));
    }

    @FXML
    private void mnuRelatorioDespesasAction() {
        abreForm(MenuEnum.RELATORIO_DESPESAS, "relatorio-despesas.fxml",
                null, calculaX(670), calculaY(289));
    }

    @FXML
    private void mnuAjudaSobreAction() {
        this.abreForm(MenuEnum.AJUDA_SOBRE,
                "/view/ajuda/sobre.fxml",
                null,
                calculaX(670), calculaY(289));
    }

    //--- *** ----- ### ----- *** ---
    @FXML
    private void hlkSairOnAction() {
        try {
            Stage loginStage = new Stage();
            mainLayoutStage = (Stage) hlkNomeUsuario.getScene().getWindow();
            AnchorPane loginAnchorPane = FXMLLoader.load(MainApp.class.getResource("/view/login.fxml"));
            Scene scene = new Scene(loginAnchorPane);
            loginStage.setResizable(false);
            loginStage.setMaximized(false);
            loginStage.setTitle("Login");
            loginStage.setScene(scene);
            loginStage.show();
            mainLayoutStage.close();

        } catch (IOException ex) {
        }
    }

    private void abreForm(MenuEnum menum, String arquivofxml, String icone, double x, double y) {
        boolean aberto = false;

        try {
            for (FormMenu formMenu : lstFormsMenu) {
                if (formMenu.getMenum().equals(menum)) {
                    aberto = true;
                    formMenu.getStage().toFront();
                    break;
                }
            }

            if (!aberto) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource(arquivofxml));
                StackPane parent = (StackPane) loader.load();

                Stage formStage = new Stage();
                formStage.initOwner((Stage) hlkNomeUsuario.getScene().getWindow());
                formStage.setResizable(false);
                formStage.initModality(Modality.NONE);
                formStage.setTitle(menum.getTitulo());
                if (icone != null && !icone.equals("")) {
                    formStage.getIcons().add(new Image(icone));
                }
                if (x != 0) {
                    formStage.setX(x);
                }
                if (y != 0) {
                    formStage.setY(y);
                }

                Scene scene = new Scene(parent);
                formStage.setScene(scene);

                formStage.setOnCloseRequest((WindowEvent we) -> {
                    for (FormMenu frm : lstFormsMenu) {
                        if (frm.getMenum().equals(menum)) {
                            lstFormsMenu.remove(frm);
                            break;
                        }
                    }
                });

                lstFormsMenu.add(new FormMenu(menum, formStage));

                //Flexible zone begining
                switch (menum) {
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
//                    case CADASTRO_USUARIO: {
//                        CadastroUsuarioController controller = loader.getController();
//                        controller.setFormStage(formStage);
//                        break;
//                    }
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
                        controller.setFormStage(formStage);
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

        } catch (IOException e) { }

    }

}
