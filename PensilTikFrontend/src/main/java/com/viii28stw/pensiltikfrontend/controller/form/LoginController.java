package com.viii28stw.pensiltikfrontend.controller.form;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.MDIController;
import com.viii28stw.pensiltikfrontend.controller.dialog.LocalizadorI18nController;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.IUsuarioService;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.DialogBoxFactory;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
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
import javafx.scene.layout.HBox;
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
    @FXML
    private HBox hbxNotification;

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
        hbxNotification.setVisible(false);

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
            Stage localizadorI18nStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class.getResource("/fxml/dialog/localizador_i18n.fxml"));
            AnchorPane localizadorI18nAnchorPane = loader.load();
            Scene localizadorI18nScene = new Scene(localizadorI18nAnchorPane);
            localizadorI18nStage.setResizable(false);
            localizadorI18nStage.setMaximized(false);
            localizadorI18nStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("title.language.setup"));
            localizadorI18nStage.initModality(Modality.WINDOW_MODAL);
            localizadorI18nStage.initOwner(loginStage);
            localizadorI18nStage.setX(CentralizeLocationRelativeToScreen.getX(919));
            localizadorI18nStage.setY(CentralizeLocationRelativeToScreen.getY(567));
            localizadorI18nStage.setScene(localizadorI18nScene);
            LocalizadorI18nController localizadorI18nController = loader.getController();
            localizadorI18nController.setLocalizadorI18nStage(localizadorI18nStage);

            loginStage.close();

            jtxEmail.resetValidation();
            jpwSenha.resetValidation();

            localizadorI18nStage.showAndWait();

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
            hbxNotification.setVisible(true);
            return;
        } // Exibir mensagem de que não foi possível realizar o login

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
                        I18nFactory.getInstance().getResourceBundle().getString("dialog.contentText.are.you.sure.you.want.to.close.the.system"),
                        I18nFactory.getInstance().getResourceBundle().getString("button.close"))) {
                    we.consume();
                } else System.exit(0);
            });
            loginStage.close();
            mdiStage.showAndWait();

            //reload the fxml file to apply resource bundle
            reloadLogin();

            hbxNotification.setVisible(false);
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
            AnchorPane loginAnchorPane = loaderLogin.load();
            loginStage.getScene().setRoot(loginAnchorPane);
            loginStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
            LoginController loginController = loaderLogin.getController();
            loginController.setLoginStage(loginStage);
            loginStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
