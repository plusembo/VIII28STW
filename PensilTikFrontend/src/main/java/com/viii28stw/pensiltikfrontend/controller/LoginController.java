package com.viii28stw.pensiltikfrontend.controller;


import com.jfoenix.controls.*;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.jfoenix.validation.RequiredFieldValidator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

    private static LoginController uniqueInstance;

    public static synchronized LoginController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LoginController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator emailValidator = new RequiredFieldValidator();
        RequiredFieldValidator senhaValidator = new RequiredFieldValidator();

        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);

        emailValidator.setMessage("Email: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");

        jtxEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    jtxEmail.validate();
                }
            }
        });
        jpwSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
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
        if(evt.getCode() == KeyCode.ENTER) {
            this.jbtnEntrarOnAction();
        }
    }

    @FXML
    private void jpwSenhaOnKeyPressed(KeyEvent evt) {
        if(evt.getCode() == KeyCode.ENTER) {
            this.jbtnEntrarOnAction();
        }
    }


    @FXML
    private void hlkAbrirUmaContaAction() {
        try {
            Stage cadastroUsuarioStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/cadastro_usuario.fxml"));
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
            System.out.println(ex);
        }
    }

    private void limparCampos() {
        jtxEmail.clear();
        jpwSenha.clear();
        jchxLembrarDeMim.setSelected(false);
        jtxEmail.resetValidation();
        jpwSenha.resetValidation();
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
                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Fechar o sistema"));
                content.setBody(new Text("Você está prestes a fechar o sistema\n"
                        .concat("Tem certeza que deseja fechar o Pensil Tik?")));

                JFXDialog dialog = new JFXDialog(mdiStackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton btnFechar = new JFXButton("Fechar");
                btnFechar.setStyle("-fx-background-color: #0091EA;");
                btnFechar.setButtonType(JFXButton.ButtonType.RAISED);
                btnFechar.setTextFill(Paint.valueOf("WHITE"));
                btnFechar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.exit(0);
                    }
                });

                JFXButton btnCancelar = new JFXButton("Cancelar");
                btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        we.consume();
                        dialog.close();
                    }
                });
                content.setActions(btnFechar, btnCancelar);
                dialog.show();
                try {
                    we.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Teste");

            });

            loginStage.close();
            limparCampos();
            mdiStage.showAndWait();
            loginStage.show();

            jtxEmail.requestFocus();

        } catch (IOException ex) {
        }
    }

}
