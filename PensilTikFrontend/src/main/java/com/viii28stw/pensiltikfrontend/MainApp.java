package com.viii28stw.pensiltikfrontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viii28stw.pensiltikfrontend.controller.SplashScreenController;
import com.viii28stw.pensiltikfrontend.enumeration.LanguagesSetting;
import com.viii28stw.pensiltikfrontend.util.I18nFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.io.File;
import java.io.IOException;

/**
 * @author Plamedi L. Lusembo
 */
@ConditionalOnBean(ObjectMapper.class)
public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            LanguagesSetting languagesSetting = new ObjectMapper()
                    .readValue(new File("language-setting.i18n"), LanguagesSetting.class);
            I18nFactory.getInstance().setSystemLanguage(languagesSetting);
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
        splashScreenStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("title.loading"));
        splashScreenStage.setScene(splashScreenScene);
        SplashScreenController splashScreenController = loader.getController();
        splashScreenController.setSplashScreenStage(splashScreenStage);
        splashScreenStage.show();
    }
}
