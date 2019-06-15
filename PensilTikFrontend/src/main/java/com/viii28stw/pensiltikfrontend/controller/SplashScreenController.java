package com.viii28stw.pensiltikfrontend.controller;

import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.controller.form.LoginController;
import com.viii28stw.pensiltikfrontend.util.CentralizeLocationRelativeToScreen;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Plamedi L. Lusembo
 */

@NoArgsConstructor
public class SplashScreenController implements Initializable {

    @Setter
    private Stage splashScreenStage;

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

    private void longStart() {
        Service<String> service = new Service() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                        Thread.sleep(50);//2750
                        return null;
                    }
                };
            }
        };
        service.start();
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
