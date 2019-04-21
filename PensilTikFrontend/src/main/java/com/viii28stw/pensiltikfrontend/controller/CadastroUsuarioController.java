package com.viii28stw.pensiltikfrontend.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.enumeration.SexoEnum;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.IUsuarioService;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.EmailValidator;
import com.viii28stw.pensiltikfrontend.util.PasswordValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@NoArgsConstructor
public class CadastroUsuarioController implements Initializable {
    @Getter @Setter private Stage cadastroUsuarioStage;
    @FXML private JFXTextField jtxNome;
    @FXML private JFXTextField jtxSobrenome;
    @FXML private JFXComboBox<SexoEnum> jcbxSexo;
    @FXML private JFXTextField jtxEmail;
    @FXML private JFXPasswordField jpwSenha;
    @FXML private JFXPasswordField jpwConfirmarSenha;
    @FXML private JFXButton jbtnSalvar;
    @FXML private Label lblSexoObrigatorio;
    @FXML private ImageView imgvwSexoObrigatorio;
    @FXML private Label lblEmailInvalido;
    @FXML private ImageView imgvwEmailInvalido;
    @FXML private Label lblSenhaInvalido;
    @FXML private ImageView imgvwSenhaInvalido;
    @FXML private Label lblConfirmarSenha;
    @FXML private ImageView imgvwConfirmarSenha;
    private RequiredFieldValidator confirmarSenhaValidator3 = new RequiredFieldValidator();
    @Setter private boolean modoEdicao;
    private final ObservableList<SexoEnum> obsListSexo = FXCollections.observableArrayList();
    private IUsuarioService usuarioService = UsuarioService.getInstance();
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

        lblSenhaInvalido.setVisible(false);
        imgvwSenhaInvalido.setVisible(false);
        lblSenhaInvalido.setStyle("-fx-text-fill: #c00d0d;");

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

        SexoEnum.getList().forEach(obsListSexo::add);
        jcbxSexo.setItems(obsListSexo);

        jtxNome.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jtxNome.validate();
                }
            }
        });

        jtxSobrenome.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jtxSobrenome.validate();
                }
            }
        });

        jcbxSexo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    if (jcbxSexo.getValue() == null) {
                        lblSexoObrigatorio.setVisible(true);
                        imgvwSexoObrigatorio.setVisible(true);
                    } else {
                        lblSexoObrigatorio.setVisible(false);
                        imgvwSexoObrigatorio.setVisible(false);
                    }
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
                    if (jpwSenha.validate()) {
                        if (PasswordValidator.isValidPassword(jpwSenha.getText())) {
                            lblSenhaInvalido.setVisible(false);
                            imgvwSenhaInvalido.setVisible(false);
                        } else {
                            lblSenhaInvalido.setText("Senha: Inválido");
                            lblSenhaInvalido.setVisible(true);
                            imgvwSenhaInvalido.setVisible(true);
                        }
                    } else {
                        lblSenhaInvalido.setVisible(false);
                        imgvwSenhaInvalido.setVisible(false);
                    }
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
        if(!validaTodosOsCampos()) {
            return;
        }
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(jtxNome.getText())
                .sobreNome(jtxSobrenome.getText())
                .sexoEnum(jcbxSexo.getValue())
                .email(jtxEmail.getText())
                .senha(jpwSenha.getText())
                .dataNascimento(LocalDate.now())
                .build();

        UsuarioDto usuarioSalvo = usuarioService.salvarUsuario(usuarioDto);
        boolean salvou = usuarioSalvo == null;

        jbtnSalvar.setText("SALVAR");

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Salvar Usuário"));
        content.setBody(new Text("Usuário salvo com sucesso"));

        content.setBody(new Text("Usuário salvo com sucesso!\n".concat(usuarioSalvo.toString())));

        JFXDialog dialog = new JFXDialog((StackPane) getCadastroUsuarioStage().getScene().getRoot(), content, JFXDialog.DialogTransition.CENTER);
        JFXButton btnOK = new JFXButton("OK");
        btnOK.setStyle("-fx-background-color: #0091EA;");
        btnOK.setButtonType(JFXButton.ButtonType.RAISED);
        btnOK.setTextFill(Paint.valueOf("WHITE"));
        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });
        content.setActions(btnOK);
        dialog.show();
    }

    private boolean validaTodosOsCampos() {
        int campoIndex = 0;

        if (!jpwSenha.getText().equals(jpwConfirmarSenha.getText())) {
            lblConfirmarSenha.setVisible(true);
            imgvwConfirmarSenha.setVisible(true);
            campoIndex = 6;
        }

        if(jpwSenha.validate()) {
            if (!PasswordValidator.isValidPassword(jpwSenha.getText())) {
                lblSenhaInvalido.setText("Senha: Inválido");
                lblSenhaInvalido.setVisible(true);
                imgvwSenhaInvalido.setVisible(true);
                campoIndex = 5;
            }
        } else {
            campoIndex = 5;
        }

        if(jtxEmail.validate()) {
            if (!EmailValidator.isValidEmail(jtxEmail.getText())) {
                lblEmailInvalido.setText("E-mail: Inválido");
                lblEmailInvalido.setVisible(true);
                imgvwEmailInvalido.setVisible(true);
                campoIndex = 4;
            }
        } else {
            campoIndex = 4;
        }

        if (jcbxSexo.getValue() == null) {
            lblSexoObrigatorio.setVisible(true);
            imgvwSexoObrigatorio.setVisible(true);
            campoIndex = 3;
        }

        campoIndex = jtxSobrenome.validate() ? campoIndex : 2;
        campoIndex = jtxNome.validate() ? campoIndex : 1;

        switch (campoIndex) {
            case 1: jtxNome.requestFocus(); return false;
            case 2: jtxSobrenome.requestFocus(); return false;
            case 3: jcbxSexo.requestFocus(); return false;
            case 4: jtxEmail.requestFocus(); return false;
            case 5: jpwSenha.requestFocus(); return false;
            case 6: jpwConfirmarSenha.requestFocus(); return false;
            default: return true;
        }
    }

    @FXML
    private void jbtnLimparAction() {
        jtxNome.resetValidation();
        jtxSobrenome.resetValidation();

        lblSexoObrigatorio.setVisible(false);
        imgvwSexoObrigatorio.setVisible(false);

        jtxEmail.resetValidation();
        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);

        jpwSenha.resetValidation();

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);

        jtxNome.clear();
        jtxSobrenome.clear();
        jcbxSexo.getSelectionModel().select(null);
        jtxEmail.clear();
        jpwSenha.clear();
        jpwConfirmarSenha.clear();

        jtxNome.requestFocus();

    }

    @FXML
    private void jbtnFecharAction() {
        cadastroUsuarioStage.close();
    }

}
