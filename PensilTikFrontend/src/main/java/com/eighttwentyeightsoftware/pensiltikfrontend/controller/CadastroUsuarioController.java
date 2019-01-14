package com.eighttwentyeightsoftware.pensiltikfrontend.controller;

import com.eighttwentyeightsoftware.pensiltikfrontend.enumeration.SexoEnum;
import com.eighttwentyeightsoftware.pensiltikfrontend.model.entity.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    private JFXPasswordField jpwSenhaUsuario;
    @FXML
    private JFXPasswordField jpwConfirmarSenhaUsuario;

    @FXML
    private JFXButton jbtnSalvar;
    @FXML
    private JFXButton jbtnLimpar;
    @FXML
    private JFXButton jbtnFechar;

    @FXML private Label lblNotificacao;
    @FXML private ImageView imgvwNotificacao;
    @FXML private Label lblEmailInvalido;
    @FXML private ImageView imgvwEmailInvalido;
    @FXML private Label lblGrupoUsuarioObrig;
    @FXML private ImageView imgvwGrupoUsuarioObrig;
    @FXML private Label lblConfirmarSenha;
    @FXML private ImageView imgvwConfirmarSenha;

    private Stage formStage;
    private boolean modoEdicao;
    private static CadastroUsuarioController uniqueInstance;

    public CadastroUsuarioController() {
    }

    public static synchronized CadastroUsuarioController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CadastroUsuarioController();
        }
        return uniqueInstance;
    }

    public void setFormStage(Stage formStage) {
        this.formStage = formStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNotificacao.setVisible(false);
        imgvwNotificacao.setVisible(false);

        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);
        lblEmailInvalido.setStyle("-fx-text-fill: #c00d0d;");

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);
        lblConfirmarSenha.setStyle("-fx-text-fill: #c00d0d;");

        RequiredFieldValidator nomeValidator = new RequiredFieldValidator();
        RequiredFieldValidator emailValidator = new RequiredFieldValidator();
        RequiredFieldValidator senhaValidator = new RequiredFieldValidator();
        RequiredFieldValidator confirmarSenhaValidator = new RequiredFieldValidator();

        jtxNome.getValidators().add(nomeValidator);
        jtxEmail.getValidators().add(emailValidator);
        jpwSenhaUsuario.getValidators().add(senhaValidator);
        jpwConfirmarSenhaUsuario.getValidators().add(confirmarSenhaValidator);

        nomeValidator.setMessage("Nome: Campo obrigatório");
        emailValidator.setMessage("E-mail: Campo obrigatório");
        senhaValidator.setMessage("Senha: Campo obrigatório");
        confirmarSenhaValidator.setMessage("Confirmar Senha: Campo obrigatório");

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
//                        if (EmailValidator.getInstance().validaEmail(jtxEmailUsuario.getText())) {
//                            lblEmailInvalido.setVisible(false);
//                            imgvwEmailInvalido.setVisible(false);
//                        } else {
//                            lblEmailInvalido.setText("E-mail: Inválido");
//                            lblEmailInvalido.setVisible(true);
//                            imgvwEmailInvalido.setVisible(true);
//                        }
                    } else {
                        lblEmailInvalido.setVisible(false);
                        imgvwEmailInvalido.setVisible(false);
                    }
                }
            }
        });

         jpwSenhaUsuario.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    jpwSenhaUsuario.validate();
                }
            }
        });

        jpwConfirmarSenhaUsuario.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue) {
                    if (jpwConfirmarSenhaUsuario.validate()) {
                        if (jpwSenhaUsuario.getText().equals(jpwConfirmarSenhaUsuario.getText())) {
                            lblConfirmarSenha.setVisible(false);
                            imgvwConfirmarSenha.setVisible(false);
                        } else {
                            lblConfirmarSenha.setVisible(true);
                            imgvwConfirmarSenha.setVisible(true);
                        }
                    } else {
                        lblConfirmarSenha.setVisible(false);
                        imgvwConfirmarSenha.setVisible(false);
                    }
                }
            }
        });

//        Image errorIcon = new Image(MainApp.class
//                .getResource(PathEnum.IMAGES_PATH + "error.png").toString());
//        codigoValidator.setIcon(new ImageView(errorIcon));
//        nomeValidator.setIcon(new ImageView(errorIcon));
//        emailValidator.setIcon(new ImageView(errorIcon));
//        senhaValidator.setIcon(new ImageView(errorIcon));
//        confirmarSenhaValidator.setIcon(new ImageView(errorIcon));
    }

    private void preencheUsuario(Usuario usuario) {
        setModoEdicao(true);

        jtxNome.setText(usuario.getNome());
        jtxEmail.setText(usuario.getEmail());
        jpwSenhaUsuario.setText(usuario.getSenha());
        jpwConfirmarSenhaUsuario.setText(usuario.getSenha());
        jbtnSalvar.setText("ATUALIZAR");
    }

    @FXML
    private void jbtnConsultarUsuarioAction() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class
//                    .getResource(PathEnum.VIEW_PATH + "ConsultaUsuario.fxml"));
//            AnchorPane page = (AnchorPane) loader.load();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Usuarios");
//            dialogStage.setResizable(false);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(formStage);
//            dialogStage.setX(414);
//            dialogStage.setY(85);
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//
//            ConsultaUsuarioController controller = loader.getController();
//            dialogStage.showAndWait();
//
//            Usuario usuario = controller.getUsuario();
//
//            if (usuario != null) {
//                usuario = UsuarioService.getInstance()
//                        .selecionaUsuario(usuario.getCdUsuario());
//                jmskCodigoUsuario.setEditable(false);
//                preencheUsuario(usuario);
//            }
//
//            jmskCodigoUsuario.resetValidation();
//            jtxNomeUsuario.resetValidation();
//            jtxEmailUsuario.resetValidation();
//            jpwSenhaUsuario.resetValidation();
//            jpwConfirmarSenhaUsuario.resetValidation();
//
//            lblEmailInvalido.setVisible(false);
//            imgvwEmailInvalido.setVisible(false);
//
//            lblGrupoUsuarioObrig.setVisible(false);
//            imgvwGrupoUsuarioObrig.setVisible(false);
//
//            lblConfirmarSenha.setVisible(false);
//            imgvwConfirmarSenha.setVisible(false);
//
//        } catch (IOException | ClassNotFoundException | SQLException ex) {
//            DialogFactory.getInstance().erro(ex.getMessage());
//        }
    }

    @FXML
    private void jbtnSalvarAction() {
//        if (!jmskCodigoUsuario.validate()) {
//            jmskCodigoUsuario.requestFocus();
//            return;
//        }
//        if (!jtxNomeUsuario.validate()) {
//            jtxNomeUsuario.requestFocus();
//            return;
//        }
//        if (!jtxEmailUsuario.validate()) {
//            jtxEmailUsuario.requestFocus();
//            return;
//        }
//        if (!EmailValidator.getInstance().validaEmail(jtxEmailUsuario.getText())) {
//            lblEmailInvalido.setText("E-mail: Inválido");
//            lblEmailInvalido.setVisible(true);
//            imgvwEmailInvalido.setVisible(true);
//            jtxEmailUsuario.requestFocus();
//            return;
//        }
//        if (jcbxGrupoUsuario.getValue() == null) {
//            lblGrupoUsuarioObrig.setVisible(true);
//            imgvwGrupoUsuarioObrig.setVisible(true);
//            jcbxGrupoUsuario.requestFocus();
//            return;
//        }
//        if (!jpwSenhaUsuario.validate()) {
//            jpwSenhaUsuario.requestFocus();
//            return;
//        }
//        if (!jpwConfirmarSenhaUsuario.validate()) {
//            jpwConfirmarSenhaUsuario.requestFocus();
//            return;
//        }
//        if (!jpwSenhaUsuario.getText().equals(jpwConfirmarSenhaUsuario.getText())) {
//            lblConfirmarSenha.setVisible(true);
//            imgvwConfirmarSenha.setVisible(true);
//            jpwConfirmarSenhaUsuario.requestFocus();
//            return;
//        }
//
//        Usuario usuario = new Usuario();
//        usuario.setCdUsuario(Integer.parseInt(jmskCodigoUsuario.getText()));
//        usuario.setNome(jtxNomeUsuario.getText().trim());
//        usuario.setEmail(jtxEmailUsuario.getText().trim());
//        usuario.setGrupoUsuario(jcbxGrupoUsuario.getValue());
//        usuario.setSenha(jpwSenhaUsuario.getText().trim());
//
//        try {
//            if (UsuarioService.getInstance().isEmailJaUtilizado(usuario, isModoEdicao())) {
//                lblEmailInvalido.setText("E-mail: Já utilizado por outro usuário");
//                lblEmailInvalido.setVisible(true);
//                imgvwEmailInvalido.setVisible(true);
//                jtxEmailUsuario.requestFocus();
//                return;
//            }
//
//            if (UsuarioService.getInstance().salvaUsuario(usuario, isModoEdicao())) {
//                NotifierPigeon.getInstance().notificaSucesso(!isModoEdicao()
//                        ? "Cadastro bem sucedido!" : "Atualização bem sucedida!");
//            }
//            limpaForm();
//
//        } catch (SQLException | ClassNotFoundException ex) {
//            DialogFactory.getInstance().erro(ex.getMessage());
//        }
//        jmskCodigoUsuario.requestFocus();

    }

    @FXML
    private void jbtnExcluirAction() {
//        if (DialogFactory.getInstance().adverte("trash.png",
//                "Excluir usuário", "Este usuário será excluido permanentemente",
//                "Tem certeza que deseja excluir este usuário ?", "EXCLUIR")) {
//            try {
//                UsuarioService.getInstance().excluiUsuario(Integer.parseInt(jmskCodigoUsuario.getText()));
//                notificacoes.notificaExcluido();
//                limpaForm();
//                jmskCodigoUsuario.requestFocus();
//            } catch (SQLException | ClassNotFoundException ex) {
//                DialogFactory.getInstance().erro(ex.getMessage());
//            }
//        }
    }

    @FXML
    private void jbtnLimparAction() {
        limpaForm();

        jtxNome.resetValidation();
        jtxEmail.resetValidation();
        jpwSenhaUsuario.resetValidation();
        jpwConfirmarSenhaUsuario.resetValidation();

        lblEmailInvalido.setVisible(false);
        imgvwEmailInvalido.setVisible(false);

        lblGrupoUsuarioObrig.setVisible(false);
        imgvwGrupoUsuarioObrig.setVisible(false);

        lblConfirmarSenha.setVisible(false);
        imgvwConfirmarSenha.setVisible(false);

    }

    private void limpaForm() {
        setModoEdicao(false);

        jtxNome.setText("");
        jtxEmail.setText("");
        jpwSenhaUsuario.setText("");
        jpwConfirmarSenhaUsuario.setText("");

        jbtnSalvar.setText("SALVAR");
    }

    @FXML
    private void jbtnFecharAction() {
//        formStage.close();
//        for (FormMenu fm : MainController.lstFormsMenu) {
//            if (fm.getMenum().equals(MenuEnum.CADASTRO_USUARIO)) {
//                MainController.lstFormsMenu.remove(fm);
//                break;
//            }
//        }
    }

    public boolean isModoEdicao() {
        return modoEdicao;
    }

    public void setModoEdicao(boolean modoEdicao) {
        this.modoEdicao = modoEdicao;
    }

}
