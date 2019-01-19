package com.eighttwentyeightsoftware.pensiltikfrontend.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.SexoEnum;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CadastroUsuarioController implements Initializable {

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
    private JFXButton jbtnLimpar;
    @FXML
    private JFXButton jbtnFechar;

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
    private Stage formStage;
    @Setter
    private boolean modoEdicao;
    private static CadastroUsuarioController uniqueInstance;

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
        RequiredFieldValidator confirmarSenhaValidator = new RequiredFieldValidator();

        confirmarSenhaValidator3.setMessage("Confirmar nhase: Campo obrigatório");
        jpwConfirmarSenha.getValidators().add(confirmarSenhaValidator3);

        jtxNome.getValidators().add(nomeValidator);
        jtxSobrenome.getValidators().add(sobrenomeValidator);
        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);
        jpwConfirmarSenha.getValidators().add(confirmarSenhaValidator);

        nomeValidator.setMessage("Nome: Campo obrigatório");
        sobrenomeValidator.setMessage("Sobrenome: Campo obrigatório");
        emailValidator.setMessage("E-mail: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");
        confirmarSenhaValidator.setMessage("Confirmar Senha: Campo obrigatório");




    }

    @FXML
    private void jbtnSalvarAction() {

        jpwConfirmarSenha.getValidators();


        jpwConfirmarSenha.validate();
    }

    @FXML
    private void jbtnLimparAction() {

    }

    @FXML
    private void jbtnFecharAction() {

    }

}
