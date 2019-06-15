package com.viii28stw.pensiltikfrontend.controller.form;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.MDIController;
import com.viii28stw.pensiltikfrontend.controller.SplashScreenController;
import com.viii28stw.pensiltikfrontend.controller.form.configuracoes.ConfiguracaoIdiomaController;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.IUsuarioService;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.DialogBoxFactory;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import com.viii28stw.pensiltikfrontend.util.Notification.DialogBoxController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class LoginController implements Initializable {

    @Setter
    private Stage loginStage;
    @FXML
    private JFXTextField jtxEmail;
    @FXML
    private JFXPasswordField jpwSenha;
    @FXML
    private JFXCheckBox jchxLembrarDeMim;

    private RequiredFieldValidator emailValidator = new RequiredFieldValidator();
    private RequiredFieldValidator senhaValidator = new RequiredFieldValidator();

    private IUsuarioService usuarioService = UsuarioService.getInstance();
    private static LoginController uniqueInstance;

    public static synchronized LoginController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LoginController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> lembraDeMim());

        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);

        emailValidator.setMessage(I18nFactory.getInstance().getResourceBundle().getString("notification.email.required.field"));
        senhaValidator.setMessage(I18nFactory.getInstance().getResourceBundle().getString("notification.password.required.field"));

        jtxEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                jtxEmail.validate();
            }
        });

        jpwSenha.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                jpwSenha.validate();
            }
        });

        Image errorIcon = new Image(MainApp.class
                .getResource("/img/validator_error.png").toString());
        emailValidator.setIcon(new ImageView(errorIcon));
        senhaValidator.setIcon(new ImageView(errorIcon));
    }

    @FXML
    private void jtxEmailOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            this.jbtnLoginOnAction();
        }
    }

    @FXML
    private void jpwSenhaOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            this.jbtnLoginOnAction();
        }
    }

    @FXML
    private void hlkSetUpSystemLanguage() {
        try {
            Stage configuracaoIdiomaStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class.getResource("/fxml/form/cofiguracoes/configuracao_idioma.fxml"));
            StackPane configuracaoIdiomaStackPane = loader.load();
            Scene localizadorI18nScene = new Scene(configuracaoIdiomaStackPane);
            configuracaoIdiomaStage.setResizable(false);
            configuracaoIdiomaStage.setMaximized(false);
            configuracaoIdiomaStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("title.language.setup"));
            configuracaoIdiomaStage.initModality(Modality.WINDOW_MODAL);
            configuracaoIdiomaStage.initOwner(loginStage);
            configuracaoIdiomaStage.setX(CentralizeLocationRelativeToScreen.getX(configuracaoIdiomaStackPane.getPrefWidth()));
            configuracaoIdiomaStage.setY(CentralizeLocationRelativeToScreen.getY(configuracaoIdiomaStackPane.getPrefHeight()));
            configuracaoIdiomaStage.setScene(localizadorI18nScene);
            ConfiguracaoIdiomaController configuracaoIdiomaController = loader.getController();
            configuracaoIdiomaController.setConfiguracaoIdiomaStage(configuracaoIdiomaStage);

            loginStage.close();

            jtxEmail.resetValidation();
            jpwSenha.resetValidation();

            configuracaoIdiomaStage.showAndWait();

            //reload the fxml file to apply resource bundle
            reloadLogin();
            jtxEmail.requestFocus();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void lembraDeMim() {
        try {
            Usuario usuario = new ObjectMapper()
                    .readValue(new File("include/caca_trufas.txg"), Usuario.class);
            jchxLembrarDeMim.setSelected(true);
            jtxEmail.setText(usuario.getEmail());
            jpwSenha.setText(usuario.getSenha());
            jtxEmail.selectAll();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void jbtnLoginOnAction() {
        /*jtxEmail.requestFocus();
        NotificacaoCRUDFactory.getInstance().notificaSucesso("Atualização bem sucedida!");
    }

    private void jbtnLginOnAction() {*/
        if (!jtxEmail.validate() && !jpwSenha.validate()) {
            jtxEmail.requestFocus();
            return;
        } else if (!jtxEmail.validate()) {
            jtxEmail.requestFocus();
            return;
        } else if (!jpwSenha.validate()) {
            jpwSenha.requestFocus();
            return;
        }
        UsuarioDto usuarioDto = usuarioService.fazerLogin(jtxEmail.getText(), jpwSenha.getText());
        if (usuarioDto == null) {
            try {
                Stage dialogBoxStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource("/fxml/util/notification/dialog_box.fxml"));
                StackPane dialogBoxStackPane = loader.load();
                Scene dialogBoxScene = new Scene(dialogBoxStackPane);
                dialogBoxStage.setResizable(false);
                dialogBoxStage.setMaximized(false);
                dialogBoxStage.initModality(Modality.APPLICATION_MODAL);
                dialogBoxStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
                dialogBoxStage.setX(CentralizeLocationRelativeToScreen.getX(dialogBoxStackPane.getPrefWidth()));
                dialogBoxStage.setY(CentralizeLocationRelativeToScreen.getY(dialogBoxStackPane.getPrefHeight()));

                dialogBoxStage.setScene(dialogBoxScene);

                DialogBoxController dialogBoxController = loader.getController();
                dialogBoxController.setDialogBoxStage(dialogBoxStage);
                dialogBoxStage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }


//            DialogBoxFactory.getInstance().informa1(I18nFactory.getInstance().getResourceBundle().getString("dialog.title.login.failure"),
//                    I18nFactory.getInstance().getResourceBundle().getString("dialog.login.failure.contenttext"));
            return;
        }

        new Thread(() -> {
            try {
                if (jchxLembrarDeMim.isSelected()) {
                    Usuario usuario = Usuario.builder()
                            .codigo(usuarioDto.getCodigo())
                            .nome(usuarioDto.getNome())
                            .sobreNome(usuarioDto.getSobreNome())
                            .email(usuarioDto.getEmail())
                            .senha(usuarioDto.getSenha())
                            .sexoEnum(usuarioDto.getSexoEnum())
                            .dataNascimento(usuarioDto.getDataNascimento())
                            .build();

                    new ObjectMapper()
                            .writeValue(new File("include/caca_trufas.txg"), usuario);
                } else {
                    new File("include/caca_trufas.txg").delete();
                }
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }).start();
        try {
            Stage mdiStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class.getResource("/fxml/mdi.fxml"));
            StackPane mdiStackPane = loader.load();
            Scene mdiScene = new Scene(mdiStackPane);
            mdiStage.setMaximized(true);
            mdiStage.setTitle("PENSIL TIK");
            mdiStage.setScene(mdiScene);
            MDIController mdiController = loader.getController();
            mdiController.setMdiStage(mdiStage);
            mdiStage.setOnCloseRequest((WindowEvent we) -> {
                if (!DialogBoxFactory.getInstance().questiona("/img/exit.png",
                        I18nFactory.getInstance().getResourceBundle().getString("dialog.title.close.the.system"),
                        I18nFactory.getInstance().getResourceBundle().getString("dialog.you.are.about.to.close.the.system"),
                        I18nFactory.getInstance().getResourceBundle().getString("dialog.contenttext.are.you.sure.you.want.to.close.the.system"),
                        I18nFactory.getInstance().getResourceBundle().getString("button.close"))) {
                    we.consume();
                } else System.exit(0);
            });
            loginStage.close();
            mdiStage.showAndWait();

            //reload the fxml file to apply resource bundle
            reloadLogin();

            jtxEmail.requestFocus();
            jtxEmail.resetValidation();
            jpwSenha.resetValidation();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void reloadLogin() {
        try {
            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(MainApp.class.getResource("/fxml/form/login.fxml"));
            loaderLogin.setResources(I18nFactory.getInstance().getResourceBundle());
            StackPane loginStackPane = loaderLogin.load();
            loginStage.getScene().setRoot(loginStackPane);
            loginStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
            LoginController loginController = loaderLogin.getController();
            loginController.setLoginStage(loginStage);
            loginStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
