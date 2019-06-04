package com.viii28stw.pensiltikfrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.controller.SplashScreenController;
import com.viii28stw.pensiltikfrontend.enumeration.NominatimCountryCodesEnum;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * @author Plamedi L. Lusembo
 */
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            NominatimCountryCodesEnum nominatimCountryCodesEnum = new ObjectMapper()
                    .readValue(new File("include/nominatim.i18n"), NominatimCountryCodesEnum.class);
            I18nFactory.getInstance().setSystemLanguage(nominatimCountryCodesEnum);
        } catch (IOException ex) {
        }

        Stage splashScreenStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(I18nFactory.getInstance().getResourceBundle());
        loader.setLocation(MainApp.class.getResource("/fxml/splash_screen.fxml"));
        StackPane splashScreenStackPane = loader.load();
        Scene splashScreenScene = new Scene(splashScreenStackPane);
        splashScreenStage.setResizable(false);
        splashScreenStage.setMaximized(false);
        splashScreenStage.setTitle("Loading...");
        splashScreenStage.setScene(splashScreenScene);
        SplashScreenController splashScreenController = loader.getController();
        splashScreenController.setSplashScreenStage(splashScreenStage);
        splashScreenStage.show();
    }
}
