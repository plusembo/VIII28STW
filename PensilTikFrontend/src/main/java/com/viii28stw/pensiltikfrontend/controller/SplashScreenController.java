package com.viii28stw.pensiltikfrontend.controller;

import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.form.LoginController;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import com.viii28stw.pensiltikfrontend.util.animations.FadeInLeftTransition;
import com.viii28stw.pensiltikfrontend.util.animations.FadeInRightTransition;
import com.viii28stw.pensiltikfrontend.util.animations.FadeInTransition;
import com.viii28stw.pensiltikfrontend.util.config.Config;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class of the FXML file for splash screen.
 * <p>
 * Handle all of the splash screen implementation.
 *
 * @version 1.0.0
 * @since August 06, 2019
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class SplashScreenController implements Initializable {

    @Setter
    private Stage splashScreenStage;
    @FXML
    private ImageView imvCecil;
    @FXML
    private Text txtCecil;
    @FXML
    private Text txtMistersoft;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        longStart();
    }

    /**
     * Load the ApplicationContext while splashing.
     * Check the database successful connection.
     * <p>
     * And launch the login screen.
     *
     * @return      none
     * @version 1.0
     * @since August 06, 2019
     */
    private void longStart() {
        Service<ApplicationContext> service = new Service<ApplicationContext>() {
            @Override
            protected Task<ApplicationContext> createTask() {
                return new Task<ApplicationContext>() {
                    @Override
                    protected ApplicationContext call() throws Exception {
                        ApplicationContext appContex = Config.getInstance().getApplicationContext();
                        int max = appContex.getBeanDefinitionCount();
                        updateProgress(0, max);
                        for (int k = 0; k < max; k++) {
                            Thread.sleep(50);
                            updateProgress(k + 1, max);
                        }
                        return appContex;
                    }
                };
            }
        };
        service.start();
        service.setOnRunning((WorkerStateEvent event) -> {
            new FadeInLeftTransition(txtCecil).play();
            new FadeInRightTransition(imvCecil).play();
            new FadeInTransition(txtMistersoft).play();
        });
        service.setOnSucceeded((WorkerStateEvent event) -> {
            try {
                Stage loginStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource("/fxml/form/login.fxml"));
                StackPane loginStackPane = loader.load();
                Scene loginScene = new Scene(loginStackPane);
                loginStage.setResizable(false);
                loginStage.setMaximized(false);
                loginStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
                loginStage.setX(CentralizeLocationRelativeToScreen.getX(loginStackPane.getPrefWidth()));
                loginStage.setY(CentralizeLocationRelativeToScreen.getY(loginStackPane.getPrefHeight()));

                loginStage.setScene(loginScene);

                LoginController loginController = loader.getController();
                loginController.setLoginStage(loginStage);

                splashScreenStage.close();
                loginStage.show();

            } catch (IOException ex) {
                Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        });
    }

}
