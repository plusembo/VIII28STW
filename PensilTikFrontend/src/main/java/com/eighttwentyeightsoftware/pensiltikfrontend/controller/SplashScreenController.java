package com.eighttwentyeightsoftware.pensiltikfrontend.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.eighttwentyeightsoftware.pensiltikfrontend.MainApp;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Plamedi L. Lusembo
 */

public class SplashScreenController implements Initializable {

    @FXML
    private Label lblVersao;

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
                        Thread.sleep(2750);
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((WorkerStateEvent event) -> {
            try {
                Stage loginStage = new Stage();
                Stage splashStage = (Stage) lblVersao.getScene().getWindow();
                AnchorPane loginAnchorPane = FXMLLoader.load(MainApp.class.getResource("/view/login.fxml"));
                Scene scene = new Scene(loginAnchorPane);
//                loginStage.getIcons().add(new Image(PathEnum.IMAGES_PATH + "mistersoftlogo.png"));
                loginStage.setResizable(false);
                loginStage.setMaximized(false);
                loginStage.setTitle("Login");
                loginStage.setScene(scene);
                splashStage.close();
                loginStage.show();
            } catch (IOException ex) {
            }
        });
    }
}
