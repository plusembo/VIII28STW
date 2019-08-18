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
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class MainApp extends Application {
    @Getter
    @Setter
    private static ConfigurableApplicationContext applicationContext;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        setApplicationContext(SpringApplication.run(MainApp.class));
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(getApplicationContext()::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            LanguagesSetting languagesSetting = new ObjectMapper()
                    .readValue(new File("language-setting.i18n"), LanguagesSetting.class);
            I18nFactory.getInstance().setSystemLanguage(languagesSetting);
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        fxmlLoader.setResources(I18nFactory.getInstance().getResourceBundle());
        fxmlLoader.setLocation(MainApp.class.getResource("/fxml/splash_screen.fxml"));
        StackPane splashScreenStackPane = fxmlLoader.load();
        Scene splashScreenScene = new Scene(splashScreenStackPane);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.setTitle(I18nFactory.getInstance().getResourceBundle().getString("title.loading"));
        primaryStage.setScene(splashScreenScene);
        SplashScreenController splashScreenController = fxmlLoader.getController();
        splashScreenController.setSplashScreenStage(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop() {
        getApplicationContext().stop();
    }

}
