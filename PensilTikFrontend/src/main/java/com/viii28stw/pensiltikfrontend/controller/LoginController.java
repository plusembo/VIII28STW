package com.viii28stw.pensiltikfrontend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.IUsuarioService;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.DialogBoxFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        Platform.runLater(() -> {
            lembraDeMim();
        });

        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);

        emailValidator.setMessage("Email: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");

        jtxEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jtxEmail.validate();
                }
            }
        });

        jpwSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jpwSenha.validate();
                }
            }
        });

        Image errorIcon = new Image(MainApp.class
                .getResource("/image/validator-error.png").toString());
        emailValidator.setIcon(new ImageView(errorIcon));
        senhaValidator.setIcon(new ImageView(errorIcon));
    }

    @FXML
    private void jtxEmailOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            this.jbtnEntrarOnAction();
        }
    }

    @FXML
    private void jpwSenhaOnKeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            this.jbtnEntrarOnAction();
        }
    }

    @FXML
    private void hlkAbrirUmaContaAction() {
        try {
            Stage cadastroUsuarioStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/cadastro/cadastro_usuario.fxml"));
            StackPane cadastroUsuarioStackPane = loader.load();
            Scene cadastroUsuarioScene = new Scene(cadastroUsuarioStackPane);
            cadastroUsuarioStage.setResizable(false);
            cadastroUsuarioStage.setMaximized(false);
            cadastroUsuarioStage.setTitle("Cadastro de usuário");
            cadastroUsuarioStage.setScene(cadastroUsuarioScene);
            CadastroUsuarioController cadastroUsuarioController = loader.getController();
            cadastroUsuarioController.setCadastroUsuarioStage(cadastroUsuarioStage);

            loginStage.close();
            limparCampos();
            cadastroUsuarioStage.showAndWait();
            loginStage.show();

            jtxEmail.requestFocus();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void limparCampos() {
        jtxEmail.clear();
        jpwSenha.clear();
        jchxLembrarDeMim.setSelected(false);
        jtxEmail.resetValidation();
        jpwSenha.resetValidation();
    }

    private void lembraDeMim() {
        try {
            Usuario usuario = new ObjectMapper()
                    .readValue(new File("texugo.ldm"), Usuario.class);
            jchxLembrarDeMim.setSelected(true);
            jtxEmail.setText(usuario.getEmail());
            jpwSenha.setText(usuario.getSenha());

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    private void jbtnEntrarOnAction() {
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
        //if (usuarioDto == null) return; // Exibir mensagem de que não foi possível realizar o login

        new Thread(() -> {
            try {
                if (jchxLembrarDeMim.isSelected()) {
                    Usuario usuario = Usuario.builder().id(usuarioDto.getId())
                            .nome(usuarioDto.getNome())
                            .sobreNome(usuarioDto.getSobreNome())
                            .email(usuarioDto.getEmail())
                            .senha(usuarioDto.getSenha())
                            .sexoEnum(usuarioDto.getSexoEnum())
                            .dataNascimento(usuarioDto.getDataNascimento())
                            .build();

                    new ObjectMapper()
                            .writeValue(new File("texugo.ldm"), usuario);
                } else {
                    new File("texugo.ldm").delete();
                }
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }).start();
        try {
            Stage mdiStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/mdi.fxml"));
            StackPane mdiStackPane = loader.load();
            Scene mdiScene = new Scene(mdiStackPane);
            mdiStage.setMaximized(true);
            mdiStage.setTitle("PENSIL TIK");
            mdiStage.setScene(mdiScene);
            MDIController mdiController = loader.getController();
            mdiController.setMdiStage(mdiStage);
            mdiStage.setOnCloseRequest((WindowEvent we) -> {

                if (!DialogBoxFactory.getInstance().questiona("/image/exit.png",
                        "Fechar o sistema", "Você está prestes a fechar o sistema Mistersoft",
                        "Tem certeza que deseja fechar o sistema ?", "FECHAR")) {
                    we.consume();
                } else System.exit(0);


                /*Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType btnFechar = new ButtonType("FECHAR", ButtonBar.ButtonData.YES);
                ButtonType btnCancelar = new ButtonType("CANCELAR", ButtonBar.ButtonData.CANCEL_CLOSE);

                dialogoExe.setTitle("Fechar o sistema");
                dialogoExe.setHeaderText("Você está prestes a fechar o sistema");
                dialogoExe.setContentText("Tem certeza que deseja fechar o Pensil Tik?\n");
                dialogoExe.getButtonTypes().setAll(btnFechar, btnCancelar);

                DialogPane dialogPane = dialogoExe.getDialogPane();

                dialogPane.getStylesheets().add(MainApp.class.getResource("/stylesheet/dialog.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");

                Stage stage = (Stage) dialogPane.getScene().getWindow();
                stage.getIcons().add(new Image(MainApp.class.getResource("/image/exit.png").toString()));

                Optional<ButtonType> result = dialogoExe.showAndWait();
                if (!result.isPresent() || result.get() != btnFechar) {
                    we.consume();
                } else {
                    System.exit(0);
                }
*/
            });

            loginStage.close();
            limparCampos();
            mdiStage.showAndWait();
            loginStage.show();
            lembraDeMim();
            jtxEmail.requestFocus();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
