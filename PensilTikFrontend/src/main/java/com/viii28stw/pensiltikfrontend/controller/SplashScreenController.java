package com.viii28stw.pensiltikfrontend.controller;

import com.viii28stw.pensiltikfrontend.MainApp;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInLeftTransition;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInRightTransition;
import com.viii28stw.pensiltikfrontend.util.animation.FadeInTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class of the FXML file for splash screen.
 * <p>
 * Handle all of the splash screen implementation.
 * </p>
 *
 * @author Plamedi L. Lusembo
 * @version 1.0.0
 * @since August 06, 2019
 */

@NoArgsConstructor
@Controller
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
     *
     * @version 1.0.0
     * @author Plamedi L. Lusembo
     * @since August 06, 2019
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            longStart();
        });
    }

    /**
     * Load the ApplicationContext while splashing.
     * Check the database successful connection.
     * <p>
     * And launch the login screen.
     * </p>
     *
     * @return none
     *
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
                boolean serverListening = isServerListening("127.0.0.1", 9000);
                int max = MainApp.getApplicationContext().getBeanDefinitionCount();
                updateProgress(0, max);
                for (int k = 0; k < max; k++) {
                    Thread.sleep(10);
                    updateProgress(k + 1, max);
                }
                if (Boolean.TRUE.equals(serverListening)) {
                    return MainApp.getApplicationContext();
                } else {
                    //Here handel notification about unavailable to estabilish connection with the server
                    return null;
                }
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
                loader.setControllerFactory(MainApp.getApplicationContext()::getBean);
                loader.setResources(I18nFactory.getInstance().getResourceBundle());
                loader.setLocation(MainApp.class.getResource("/fxml/login.fxml"));
                BorderPane loginBorderPane = loader.load();
                Scene loginScene = new Scene(loginBorderPane);
                loginStage.setResizable(false);
                loginStage.setMaximized(false);
                loginStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("stage.title.login"));
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

    /**
     * Here is a simple method to check if a server is listening on a certain port.
     *
     * @param host the host of the server
     * @param port the port through which the server is listening
     *
     * @return true if the server is listening on the specify port,
     * otherwise return false.
     *
     * @version 1.0.0
     * @author Plamedi L. Lusembo
     * @since August 11, 2019
     */
    private boolean isServerListening(String host, int port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (Exception ex) {
                    Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }
    }

}
