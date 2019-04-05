package com.viii28stw.pensiltikfrontend.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.viii28stw.pensiltikfrontend.enumeration.SexoEnum;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.util.EmailValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CadastroUsuarioController implements Initializable {


    @Setter
    private Stage cadastroUsuarioStage;
    @FXML
    private JFXTextField jtxNome;
    @FXML
    private JFXTextField jtxSobrenome;
    @FXML
    private JFXComboBox<SexoEnum> jcbxSexo;
    @FXML
    private JFXTextField jtxEmail;
    @FXML
    private JFXPasswordField jpwSenha;
    @FXML
    private JFXPasswordField jpwConfirmarSenha;

    @FXML
    private JFXButton jbtnSalvar;

    @FXML
    private Label lblSexoObrigatorio;
    @FXML
    private ImageView imgvwSexoObrigatorio;
    @FXML
    private Label lblEmailInvalido;
    @FXML
    private ImageView imgvwEmailInvalido;

    @FXML
    private Label lblConfirmarSenha;
    @FXML
    private ImageView imgvwConfirmarSenha;
    private RequiredFieldValidator confirmarSenhaValidator3 = new RequiredFieldValidator();

    @Setter
    private boolean modoEdicao;
    private static CadastroUsuarioController uniqueInstance;

    private final ObservableList<SexoEnum> obsLstSexo = FXCollections.observableArrayList();

    public static synchronized CadastroUsuarioController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CadastroUsuarioController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblSexoObrigatorio.setVisible(false);
        imgvwSexoObrigatorio.setVisible(false);
        lblSexoObrigatorio.setStyle("-fx-text-fill: #c00d0d;");

        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);
        lblEmailInvalido.setStyle("-fx-text-fill: #c00d0d;");

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);
        lblConfirmarSenha.setStyle("-fx-text-fill: #c00d0d;");

        RequiredFieldValidator nomeValidator = new RequiredFieldValidator();
        RequiredFieldValidator sobrenomeValidator = new RequiredFieldValidator();
        RequiredFieldValidator emailValidator = new RequiredFieldValidator();
        RequiredFieldValidator senhaValidator = new RequiredFieldValidator();

        confirmarSenhaValidator3.setMessage("Confirmar nhase: Campo obrigatório");
        jpwConfirmarSenha.getValidators().add(confirmarSenhaValidator3);

        jtxNome.getValidators().add(nomeValidator);
        jtxSobrenome.getValidators().add(sobrenomeValidator);
        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);

        nomeValidator.setMessage("Nome: Campo obrigatório");
        sobrenomeValidator.setMessage("Sobrenome: Campo obrigatório");
        emailValidator.setMessage("E-mail: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");

        Arrays.asList(SexoEnum.values()).forEach(obsLstSexo::add);
        jcbxSexo.setItems(obsLstSexo);

        jtxNome.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jtxNome.validate();
                }
            }
        });

        jtxEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    if (jtxEmail.validate()) {
                        if (EmailValidator.isValidEmail(jtxEmail.getText())) {
                            lblEmailInvalido.setVisible(false);
                            imgvwEmailInvalido.setVisible(false);
                        } else {
                            lblEmailInvalido.setText("E-mail: Inválido");
                            lblEmailInvalido.setVisible(true);
                            imgvwEmailInvalido.setVisible(true);
                        }
                    } else {
                        lblEmailInvalido.setVisible(false);
                        imgvwEmailInvalido.setVisible(false);
                    }
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

        jpwConfirmarSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    if (jpwSenha.getText().equals(jpwConfirmarSenha.getText())) {
                        lblConfirmarSenha.setVisible(false);
                        imgvwConfirmarSenha.setVisible(false);
                    } else {
                        lblConfirmarSenha.setVisible(true);
                        imgvwConfirmarSenha.setVisible(true);
                    }
                }
            }
        });

    }

    @FXML
    private void jbtnSalvarAction() {

        jpwConfirmarSenha.getValidators();


        jpwConfirmarSenha.validate();

        jbtnSalvar.setText("SALVAR");

    }

    @FXML
    private void jbtnLimparAction() {
        jtxNome.resetValidation();
        jtxSobrenome.resetValidation();
        jtxEmail.resetValidation();
        jpwSenha.resetValidation();

        jtxNome.clear();
        jtxSobrenome.clear();
        jtxEmail.clear();
        jpwSenha.clear();
        jpwConfirmarSenha.clear();




    }

    @FXML
    private void jbtnFecharAction() {
        cadastroUsuarioStage.close();
    }

}
