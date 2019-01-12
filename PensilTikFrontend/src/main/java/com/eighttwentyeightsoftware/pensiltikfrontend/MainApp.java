package com.eighttwentyeightsoftware.pensiltikfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Plamedi L. Lusembo
 */

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage splashScreenStage = new Stage();
        StackPane splashLayoutPane = FXMLLoader.load(getClass().getResource("/view/splash-screen.fxml"));
        Scene scene = new Scene(splashLayoutPane);
        splashScreenStage.setResizable(false);
        splashScreenStage.setMaximized(false);
        splashScreenStage.setScene(scene);
        splashScreenStage.show();
    }
}
