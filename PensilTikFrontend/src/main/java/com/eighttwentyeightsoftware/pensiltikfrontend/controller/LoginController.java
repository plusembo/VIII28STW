package com.eighttwentyeightsoftware.pensiltikfrontend.controller;


import com.eighttwentyeightsoftware.pensiltikfrontend.MainApp;
import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.MenuEnum;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.NoArgsConstructor;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class LoginController implements Initializable {

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
            Stage loginStage = (Stage) jtxEmail.getScene().getWindow();
            StackPane cadastroUsuarioStackPane = FXMLLoader.load(MainApp.class.getResource("/view/cadastro-usuario.fxml"));
            Scene scene = new Scene(cadastroUsuarioStackPane);
//                cadastroUsuarioStage.getIcons().add(new Image(PathEnum.IMAGES_PATH + "mistersoftlogo.png"));
            cadastroUsuarioStage.setResizable(false);
            cadastroUsuarioStage.setMaximized(false);
            cadastroUsuarioStage.setTitle("Cadastro de usuário");
            cadastroUsuarioStage.setScene(scene);
            loginStage.close();
            cadastroUsuarioStage.show();
        } catch (IOException ex) {
            System.out.println(ex);
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
        try {
//            Usuario usuario = new Usuario();
//            usuario.setEmail(jtxEmail.getText());
//            usuario.setSenha(jpwSenha.getText());
//
//            if (LoginService.getInstance().entrar(usuario, jchxLembrarDeMim.isSelected())) {
                Stage mainLayoutStage = new Stage();
                Stage loginStage = (Stage) jchxLembrarDeMim.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/MDI.fxml"));
                StackPane mainStackPane = loader.load();
                Scene scene = new Scene(mainStackPane);
                mainLayoutStage.setMaximized(true);
//                mainLayoutStage.getIcons().add(new Image("/image/mistersoft-logo.png"));
                mainLayoutStage.setTitle("Mistersoft");
                mainLayoutStage.setScene(scene);
                MDIController mainController = loader.getController();

                mainLayoutStage.setOnCloseRequest((WindowEvent we) -> {
//                    if (!DialogFactory.getInstance().questiona("exit.png",
//                            "Fechar o sistema", "Você está prestes a fechar o sistema Mistersoft",
//                            "Tem certeza que deseja fechar o sistema ?", "FECHAR")) {
//                        we.consume();
//                    }
                });

                mainLayoutStage.show();

                loginStage.close();
//            } else {
//                NotifierPigeon.getInstance().notificaErro("Senha ou e-mail errado!");
//            }
        } catch (IOException ex) {
        }
    }

}
