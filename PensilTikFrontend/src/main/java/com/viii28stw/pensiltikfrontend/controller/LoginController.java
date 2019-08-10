package com.viii28stw.pensiltikfrontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.form.settings.LanguageSettingController;
import com.viii28stw.pensiltikfrontend.enumeration.LanguagesSetting;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.IUsuarioService;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInLeftTransition;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInOtherLeftTransition;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInRightTransition;
import com.viii28stw.pensiltikfrontend.util.animation.tray_animation.Animations;
import com.viii28stw.pensiltikfrontend.util.dialogbox.DialogBoxFactory;
import com.viii28stw.pensiltikfrontend.enumeration.Notifications;
import com.viii28stw.pensiltikfrontend.util.notification.TrayNotification;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class of the FXML file for login screen.
 * <p>
 * Handle all of the login implementation.
 * </p>
 *
 * @version 1.0.0
 * @since August 06, 2019
 * @author Plamedi L. Lusembo
 */
@NoArgsConstructor
public class LoginController implements Initializable {

    private static LoginController uniqueInstance;
    @Setter
    private Stage loginStage;
    @FXML
    private Text txtWelcome;
    @FXML
    private Text txtUserLogin;
    @FXML
    private Label lblEmail;
    @FXML
    private TextField tfdEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private PasswordField pwfPassword;
    @FXML
    private CheckBox ckbRememberMe;
    @FXML
    private Button btnLogin;
    private static final String FX_BORDER_COLOR = "-fx-border-color: ";
    private static final String REMEMBER_MIM_FILE_PATH = "remember_me.txg";
    private IUsuarioService usuarioService = UsuarioService.getInstance();

    public static synchronized LoginController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LoginController();
        }
        return uniqueInstance;
    }

    /**
     * Initializes the controller class.
     *
     * @param location
     * @param resources
     *
     *  @version 1.0.0
     *  @since August 06, 2019
     *  @author Plamedi L. Lusembo
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            new FadeInRightTransition(txtUserLogin).play();
            new FadeInLeftTransition(txtWelcome).play();
            new FadeInOtherLeftTransition(lblPassword).play();
            new FadeInOtherLeftTransition(lblEmail).play();
            new FadeInOtherLeftTransition(tfdEmail).play();
            new FadeInOtherLeftTransition(pwfPassword).play();
            new FadeInRightTransition(btnLogin).play();
            rememberMe();
        });

        tfdEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (Boolean.TRUE.equals(oldPropertyValue)) {
                tfdEmail.setStyle(tfdEmail.getText().isEmpty() ?
                        FX_BORDER_COLOR.concat(Notifications.ERROR.getPaintHex()).concat(";") :
                        FX_BORDER_COLOR.concat("#A9A9A9;"));
            }
        });

        pwfPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (Boolean.TRUE.equals(oldPropertyValue)) {
                pwfPassword.setStyle(pwfPassword.getText().isEmpty() ?
                        FX_BORDER_COLOR.concat(Notifications.ERROR.getPaintHex()).concat(";") :
                        FX_BORDER_COLOR.concat("#A9A9A9;"));
            }
        });
    }

    /**
     * Check saved user (email and password) to be load from a .txg extension file.
     * In case of existing saved user, his email and password will be loaded to fill login fields.
     *
     * @return      none
     * @version 1.0.0
     * @since August 06, 2019
     */
    private void rememberMe() {
        try {
            Usuario usuario = new ObjectMapper()
                    .readValue(new File(REMEMBER_MIM_FILE_PATH), Usuario.class);
            ckbRememberMe.setSelected(true);
            tfdEmail.setText(usuario.getEmail());
            pwfPassword.setText(usuario.getSenha());
            tfdEmail.selectAll();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Handle the On Key Pressed action of the email login field.
     * <p>
     * Perform action such as Enter key pressed and execute btnLoginOnAction() method.
     * </p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 06, 2019
     */
    @FXML
    private void tfdEmailOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            this.btnLoginOnAction();
        }
    }

    /**
     * Handle the On Key Pressed action of the email password field.
     * <p>
     * Perform action such as Enter key pressed and execute btnLoginOnAction() method.
     * </p>
     *
     * @param evt
     *
     * @return      none
     * @version 1.0.0
     * @since August 06, 2019
     */
    @FXML
    private void pwfSPasswordOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            btnLoginOnAction();
        }
    }

    /**
     * Launch the system language set up screen.
     *
     * @return      none
     * @version 1.0.0
     * @since August 06, 2019
     */
    @FXML
    private void hlkSetUpSystemLanguageOnAction() {
        try {
            Stage configuracaoIdiomaStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class.getResource("/fxml/form/settings/language_setting.fxml"));
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
            LanguageSettingController configuracaoIdiomaController = loader.getController();
            configuracaoIdiomaController.setLanguageSettingStage(configuracaoIdiomaStage);

            loginStage.close();
            configuracaoIdiomaStage.showAndWait();

            reloadLogin();
            tfdEmail.requestFocus();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Check authenticity of user credentials to the database.
     * <p>
     * If it's all Okay, the system will be launched
     * and synchronously the user will be saved in a .txg extension file
     * if 'Remember me' checkbox is checked.
     * </p>
     *
     * @return      none
     * @version 1.0.0
     * @since August 06, 2019
     */
    @FXML
    private void btnLoginOnAction() {
        UsuarioDto usuarioDto = usuarioService.fazerLogin(tfdEmail.getText(), pwfPassword.getText());
        if (usuarioDto == null) {

//            try {
//                DialogBoxFactory.getInstance().inform(I18nFactory.getInstance().getResourceBundle().getString("dialog.title.login.failure"),
//                        "",
//                        I18nFactory.getInstance().getResourceBundle().getString("dialog.login.failure.contenttext"));
//            } catch (IOException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }

            tfdEmail.setStyle(FX_BORDER_COLOR.concat(Notifications.ERROR.getPaintHex()).concat(";"));
            pwfPassword.setStyle(FX_BORDER_COLOR.concat(Notifications.ERROR.getPaintHex()).concat(";"));

            String title = I18nFactory.getInstance().getResourceBundle().getString("dialog.title.login.failure");
            String message = I18nFactory.getInstance().getResourceBundle().getString("dialog.login.failure.contenttext");
            TrayNotification tray = TrayNotification.getInstance();
            tray.setTitle(title);
            tray.setMessage(message);
            tray.setNotification(Notifications.ERROR);
            tray.setAnimation(Animations.POPUP);
            tray.showAndWait();

            return;
        }

        new Thread(() -> {
            try {
                if (ckbRememberMe.isSelected()) {
                    Usuario usuario = Usuario.builder()
                            .codigo(usuarioDto.getCodigo())
                            .nome(usuarioDto.getNome())
                            .sobreNome(usuarioDto.getSobreNome())
                            .email(usuarioDto.getEmail())
                            .senha(usuarioDto.getSenha())
                            .sexo(usuarioDto.getSexo())
                            .dataNascimento(usuarioDto.getDataNascimento())
                            .build();

                    new ObjectMapper()
                            .writeValue(new File(REMEMBER_MIM_FILE_PATH), usuario);
                } else {
                    Files.deleteIfExists(Paths.get(REMEMBER_MIM_FILE_PATH));
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
            BorderPane bdpMDI = loader.load();
            Scene mdiScene = new Scene(bdpMDI);
            mdiStage.setMaximized(true);
            mdiStage.setTitle("PENSIL TIK");
            mdiStage.setScene(mdiScene);
            MDIController mdiController = loader.getController();
            mdiController.setMdiStage(mdiStage);
            mdiStage.setOnCloseRequest((WindowEvent we) -> {
                try {
                    if (!DialogBoxFactory.getInstance().confirm(I18nFactory.getInstance().getResourceBundle().getString("dialog.title.close.the.system"),
                            I18nFactory.getInstance().getResourceBundle().getString("dialog.you.are.about.to.close.the.system"),
                            I18nFactory.getInstance().getResourceBundle().getString("dialog.contenttext.are.you.sure.you.want.to.close.the.system"))) {
                        we.consume();
                    } else System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            });
            loginStage.close();
            mdiStage.showAndWait();

            reloadLogin();

            tfdEmail.requestFocus();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Reload the login stage in order to apply eventual modification, such as language setting.
     * The FXML file is reloaded to apply resource bundle properties.
     * <p>
     * This method must be called after displaying login screen.
     * </p>
     *
     * @return      none
     * @version 1.0
     * @since August 06, 2019
     */
    private void reloadLogin() {
        try {
            LanguagesSetting languagesSetting = new ObjectMapper()
                    .readValue(new File("language-setting.i18n"), LanguagesSetting.class);
            I18nFactory.getInstance().setSystemLanguage(languagesSetting);

            FXMLLoader loaderLogin = new FXMLLoader();
            loaderLogin.setLocation(MainApp.class.getResource("/fxml/login.fxml"));
            loaderLogin.setResources(I18nFactory.getInstance().getResourceBundle());
            BorderPane loginBorderPane = loaderLogin.load();
            loginStage.getScene().setRoot(loginBorderPane);
            loginStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
            LoginController loginController = loaderLogin.getController();
            loginController.setLoginStage(loginStage);
            loginStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


}
