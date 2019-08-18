package com.viii28stw.pensiltikfrontend.controller.form.registration;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.MainController;
import com.viii28stw.pensiltikfrontend.controller.dialog.LocalizadorUsuarioController;
import com.viii28stw.pensiltikfrontend.enumeration.MenuMatch;
import com.viii28stw.pensiltikfrontend.enumeration.Sexo;
import com.viii28stw.pensiltikfrontend.model.domain.Usuario;
import com.viii28stw.pensiltikfrontend.model.dto.UsuarioDto;
import com.viii28stw.pensiltikfrontend.service.UsuarioService;
import com.viii28stw.pensiltikfrontend.util.EmailValidator;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import com.viii28stw.pensiltikfrontend.util.PasswordValidator;
import com.viii28stw.pensiltikfrontend.util.dialogbox.DialogBoxFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
public class CadastroDespesaController implements Initializable {
    @Getter
    @Setter
    private Stage cadastroDespesaStage;
    @FXML
    private TabPane tbpDespesa;
    @FXML
    private JFXTextField jmskCodigo;
    @FXML
    private JFXTextField jtxNome;
    @FXML
    private JFXTextField jtxSobrenome;
    @FXML
    private JFXComboBox<Sexo> jcbxSexo;
    @FXML
    private JFXTextField jtxEmail;
    @FXML
    private JFXPasswordField jpwSenha;
    @FXML
    private JFXPasswordField jpwConfirmarSenha;
    @FXML
    private JFXButton jbtnLocalizarUsuario;
    @FXML
    private JFXButton jbtnSalvar;
    @FXML
    private JFXButton jbtnExcluir;
    @FXML
    private JFXButton jbtnFechar;
    @FXML
    private JFXButton jbtnLimpar;
    @FXML
    private Label lblSexoObrigatorio;
    @FXML
    private ImageView imgvwSexoObrigatorio;
    @FXML
    private Label lblEmailInvalido;
    @FXML
    private ImageView imgvwEmailInvalido;
    @FXML
    private Label lblSenhaInvalida;
    @FXML
    private ImageView imgvwSenhaInvalida;
    @FXML
    private Label lblConfirmarSenha;
    @FXML
    private ImageView imgvwConfirmarSenha;
    private RequiredFieldValidator confirmarSenhaValidator3 = new RequiredFieldValidator();
    @Setter
    private boolean modoEdicao;
    private final ObservableList<Sexo> obsListSexo = FXCollections.observableArrayList();
    private UsuarioService usuarioService = null;
    private static CadastroDespesaController uniqueInstance;

    public static synchronized CadastroDespesaController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CadastroDespesaController();
        }
        return uniqueInstance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Les tabPanes sont les suivants: "+tbpDespesa.getTabs());

        try {
        Tab tab = new Tab();


        Label label = new Label("Nova tab");
        tab.setGraphic(label);
        tab.setClosable(true);

        FXMLLoader loader = new FXMLLoader();
        loader.setResources(I18nFactory.getInstance().getResourceBundle());
        loader.setLocation(MainApp.class.getResource("/fxml/form/cadastros/cadastro_teste.fxml"));
        AnchorPane anchorPane = loader.load();
        tab.setContent(anchorPane);

        tbpDespesa.getTabs().add(tab);

        } catch (IOException e) {
            e.printStackTrace();
        }


        lblSexoObrigatorio.setVisible(false);
        imgvwSexoObrigatorio.setVisible(false);
        lblSexoObrigatorio.setStyle("-fx-text-fill: #c00d0d;");

        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);
        lblEmailInvalido.setStyle("-fx-text-fill: #c00d0d;");

        lblSenhaInvalida.setVisible(false);
        imgvwSenhaInvalida.setVisible(false);
        lblSenhaInvalida.setStyle("-fx-text-fill: #c00d0d;");

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);
        lblConfirmarSenha.setStyle("-fx-text-fill: #c00d0d;");

        RequiredFieldValidator codigoValidator = new RequiredFieldValidator();
        RequiredFieldValidator nomeValidator = new RequiredFieldValidator();
        RequiredFieldValidator sobrenomeValidator = new RequiredFieldValidator();
        RequiredFieldValidator emailValidator = new RequiredFieldValidator();
        RequiredFieldValidator senhaValidator = new RequiredFieldValidator();

        confirmarSenhaValidator3.setMessage("Confirmar nhase: Campo obrigatório");
        jpwConfirmarSenha.getValidators().add(confirmarSenhaValidator3);

        jmskCodigo.getValidators().add(nomeValidator);
        jtxNome.getValidators().add(nomeValidator);
        jtxSobrenome.getValidators().add(sobrenomeValidator);
        jtxEmail.getValidators().add(emailValidator);
        jpwSenha.getValidators().add(senhaValidator);

        codigoValidator.setMessage("Código: Campo obrigatório");
        nomeValidator.setMessage("Nome: Campo obrigatório");
        sobrenomeValidator.setMessage("Sobrenome: Campo obrigatório");
        emailValidator.setMessage("E-mail: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");

        jbtnExcluir.setDisable(true);

        Sexo.getList().forEach(obsListSexo::add);
        jcbxSexo.setItems(obsListSexo);

        jmskCodigo.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                  Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                if (jmskCodigo.validate()) {
                    jmskCodigoFocusLost();
                } else {
                    if (jbtnLocalizarUsuario.isFocused() || jbtnLimpar.isFocused() || jbtnFechar.isFocused()) {
                        jmskCodigo.resetValidation();
                        return;
                    }
                    jmskCodigo.requestFocus();
                }
            }
        });

        jbtnLocalizarUsuario.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                            Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                if (!jmskCodigo.validate()) {
                    jmskCodigo.requestFocus();
                }
            }
        });


        jtxNome.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                               Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                jtxNome.validate();
            }
        });

        jtxSobrenome.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                    Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                jtxSobrenome.validate();
            }
        });

        jcbxSexo.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                if (jcbxSexo.getValue() == null) {
                    lblSexoObrigatorio.setVisible(true);
                    imgvwSexoObrigatorio.setVisible(true);
                } else {
                    lblSexoObrigatorio.setVisible(false);
                    imgvwSexoObrigatorio.setVisible(false);
                }
            }
        });

        jtxEmail.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
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
        });

        jpwSenha.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0,
                                                Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue) {
                if (jpwSenha.validate()) {
                    if (PasswordValidator.isValidPassword(jpwSenha.getText())) {
                        lblSenhaInvalida.setVisible(false);
                        imgvwSenhaInvalida.setVisible(false);
                    } else {
                        lblSenhaInvalida.setText("Senha: Inválido");
                        lblSenhaInvalida.setVisible(true);
                        imgvwSenhaInvalida.setVisible(true);
                    }
                } else {
                    lblSenhaInvalida.setVisible(false);
                    imgvwSenhaInvalida.setVisible(false);
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

    private void jmskCodigoFocusLost() {
        if (!jmskCodigo.isEditable()) {
            return;
        }
        UsuarioDto usuarioDto = usuarioService.buscarUsuarioPorId(jmskCodigo.getText());
        jmskCodigo.setEditable(false);
        if (usuarioDto == null) {
            return;
        }

        Usuario usuario = Usuario.builder()
                .codigo(usuarioDto.getCodigo())
                .nome(usuarioDto.getNome())
                .sobreNome(usuarioDto.getSobreNome())
                .email(usuarioDto.getEmail())
                .usuarioNivelAcesso(usuarioDto.getUsuarioNivelAcesso())
                .senha(usuarioDto.getSenha())
                .sexo(usuarioDto.getSexo())
                .dataNascimento(usuarioDto.getDataNascimento())
                .build();

        preencheUsuario(usuario);

    }

    @FXML
    private void jbtnSalvarAction() {
        if (!validaTodosOsCampos()) {
            return;
        }
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .nome(jtxNome.getText())
                .sobreNome(jtxSobrenome.getText())
                .sexo(jcbxSexo.getValue())
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

        JFXDialog dialog = new JFXDialog((StackPane) getCadastroDespesaStage().getScene().getRoot(), content, JFXDialog.DialogTransition.CENTER);
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

    @FXML
    private void jbtnExcluirAction() {
        try {
            if (!DialogBoxFactory.getInstance().confirm("Excluir usuário", "Este usuário será excluido permanentemente",
                    "Tem certeza que deseja excluir este usuário ?")) {
                return;
            }
            usuarioService.deletarUsuarioPorId(jmskCodigo.getText());
            //notificacoes.notificaExcluido();
            limpaForm();
            jmskCodigo.requestFocus();
        } catch (IOException ex) {
            Logger.getLogger(CadastroDespesaController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @FXML
    public void jmskCodigoUsuarioKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (jmskCodigo.validate()) {
                jtxNome.requestFocus();
            } else {
                jmskCodigo.requestFocus();
            }
        }
    }

    @FXML
    public void jmskCodigoUsuarioKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F1) {
//            Integer csb = UsuarioService.getInstance().selecionaCodigoUsuarioSubsequente();
//            jmskCodigo.setText(csb.toString());
//            jmskCodigo.positionCaret(csb.toString().length());
        }
    }

    @FXML
    private void jbtnLocalizarUsuarioAction() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18nFactory.getInstance().getResourceBundle());
            loader.setLocation(MainApp.class
                    .getResource("/fxml/dialog/localiza_usuario.fxml"));
            StackPane localizadorUsuarioStackPane = loader.load();

            Stage localizadorUsuarioStage = new Stage();
            localizadorUsuarioStage.setTitle("Usuarios");
            localizadorUsuarioStage.setResizable(false);
            localizadorUsuarioStage.initModality(Modality.WINDOW_MODAL);
            localizadorUsuarioStage.initOwner(cadastroDespesaStage);
            Scene localizadorScene = new Scene(localizadorUsuarioStackPane);
            localizadorUsuarioStage.setScene(localizadorScene);
            LocalizadorUsuarioController localizadorUsuarioController = loader.getController();
            localizadorUsuarioController.setLocalizadorUsuarioStage(localizadorUsuarioStage);

            localizadorUsuarioStage.showAndWait();
//
//            Usuario usuario = controller.getUsuario();
//
//            if (usuario != null) {
//                usuario = usuarioService.buscarUsuarioPorId(usuario.getCodigo());
//                jmskCodigoUsuario.setEditable(false);
//                preencheUsuario(usuario);
//            }

            jmskCodigo.resetValidation();
            jtxNome.resetValidation();
            jtxEmail.resetValidation();
            jpwSenha.resetValidation();
            jpwConfirmarSenha.resetValidation();

            lblEmailInvalido.setVisible(false);
            imgvwEmailInvalido.setVisible(false);

            lblSexoObrigatorio.setVisible(false);
            imgvwSexoObrigatorio.setVisible(false);

            lblConfirmarSenha.setVisible(false);
            imgvwConfirmarSenha.setVisible(false);

        } catch (IOException ex) {
        }
    }

    private void preencheUsuario(Usuario usuario) {
        jbtnExcluir.setDisable(false);
        setModoEdicao(true);

        jmskCodigo.setText(usuario.getCodigo());
        jtxNome.setText(usuario.getNome());
        jcbxSexo.getSelectionModel().select(usuario.getSexo());
        jtxEmail.setText(usuario.getEmail());
        jpwSenha.setText(usuario.getSenha());
        jpwConfirmarSenha.setText(usuario.getSenha());
        jbtnSalvar.setText("ATUALIZAR");
    }

    private boolean validaTodosOsCampos() {
        int campoIndex = 0;

        if (!jpwSenha.getText().equals(jpwConfirmarSenha.getText())) {
            lblConfirmarSenha.setVisible(true);
            imgvwConfirmarSenha.setVisible(true);
            campoIndex = 6;
        }

        if (jpwSenha.validate()) {
            if (!PasswordValidator.isValidPassword(jpwSenha.getText())) {
                lblSenhaInvalida.setText("Senha: Inválido");
                lblSenhaInvalida.setVisible(true);
                imgvwSenhaInvalida.setVisible(true);
                campoIndex = 5;
            }
        } else {
            campoIndex = 5;
        }

        if (jtxEmail.validate()) {
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
            case 1:
                jtxNome.requestFocus();
                return false;
            case 2:
                jtxSobrenome.requestFocus();
                return false;
            case 3:
                jcbxSexo.requestFocus();
                return false;
            case 4:
                jtxEmail.requestFocus();
                return false;
            case 5:
                jpwSenha.requestFocus();
                return false;
            case 6:
                jpwConfirmarSenha.requestFocus();
                return false;
            default:
                return true;
        }
    }

    @FXML
    private void jbtnLimparAction() {
        jmskCodigo.resetValidation();
        jtxNome.resetValidation();
        jtxSobrenome.resetValidation();

        lblSexoObrigatorio.setVisible(false);
        imgvwSexoObrigatorio.setVisible(false);

        jtxEmail.resetValidation();
        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);

        jpwSenha.resetValidation();
        lblSenhaInvalida.setVisible(false);
        imgvwSenhaInvalida.setVisible(false);

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);

        limpaForm();

        jtxNome.clear();
        jtxSobrenome.clear();
        jcbxSexo.getSelectionModel().select(null);
        jtxEmail.clear();
        jpwSenha.clear();
        jpwConfirmarSenha.clear();

        jmskCodigo.requestFocus();

    }

    private void limpaForm() {
        jmskCodigo.setEditable(true);
        jbtnExcluir.setDisable(true);
        setModoEdicao(false);

        jmskCodigo.setText("");
        jtxNome.clear();
        jtxSobrenome.clear();
        jcbxSexo.getSelectionModel().select(null);
        jtxEmail.clear();
        jpwSenha.clear();
        jpwConfirmarSenha.clear();

        jbtnSalvar.setText("SALVAR");
    }

    @FXML
    private void jbtnFecharAction() {
        cadastroDespesaStage.close();
        MainController.fechaJanela(MenuMatch.CADASTRO_USUARIO);
    }

}
