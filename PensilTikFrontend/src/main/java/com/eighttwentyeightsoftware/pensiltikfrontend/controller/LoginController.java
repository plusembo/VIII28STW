package com.eighttwentyeightsoftware.pensiltikfrontend.controller;


import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * @author Plamedi L. Lusembo
 */
public class LoginController implements Initializable {

    private Stage loginStage;
    @FXML
    private JFXTextField jtxEmail;
    @FXML
    private JFXPasswordField jpwSenha;
    @FXML
    private JFXCheckBox jchxLembrarDeMim;

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

//        Image errorIcon = new Image(MainApp.class
//                .getResource(PathEnum.IMAGES_PATH + "error.png").toString());
//        emailValidator.setIcon(new ImageView(errorIcon));
//        senhaValidator.setIcon(new ImageView(errorIcon));

    }

    @FXML
    private void jbtnEntrarOnAction() {
//        if (!jtxEmail.validate()) {
//            return;
//        }
//        if (!jpwSenha.validate()) {
//            return;
//        }
//        try {
//            Usuario usuario = new Usuario();
//            usuario.setEmail(jtxEmail.getText());
//            usuario.setSenha(jpwSenha.getText());
//
//            if (LoginService.getInstance().entrar(usuario, jchxLembrarDeMim.isSelected())) {
//                Stage mainLayoutStage = new Stage();
//                loginStage = (Stage) jchxLembrarDeMim.getScene().getWindow();
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass()
//                        .getResource(PathEnum.VIEW_PATH + "MainLayout.fxml"));
//                StackPane mainStackPane = loader.load();
//                Scene scene = new Scene(mainStackPane);
//                mainLayoutStage.setMaximized(true);
//                mainLayoutStage.getIcons().add(new Image(PathEnum.IMAGES_PATH + "mistersoftlogo.png"));
//                mainLayoutStage.setTitle("Mistersoft");
//                mainLayoutStage.setScene(scene);
//                MainController mainController = loader.getController();
//
//                mainLayoutStage.setOnCloseRequest((WindowEvent we) -> {
//                    if (!DialogFactory.getInstance().questiona("exit.png",
//                            "Fechar o sistema", "Você está prestes a fechar o sistema Mistersoft",
//                            "Tem certeza que deseja fechar o sistema ?", "FECHAR")) {
//                        we.consume();
//                    }
//                });
//
//                mainLayoutStage.show();
//
//                loginStage.close();
//            } else {
//                NotifierPigeon.getInstance().notificaErro("Senha ou e-mail errado!");
//            }
//        } catch (ClassNotFoundException | SQLException | IOException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
