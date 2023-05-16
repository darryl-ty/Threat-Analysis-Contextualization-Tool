package com.tact.threatanalysiscontextualizationtool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("TACT - Threat Analysis and Contextualization Tool");
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(primScreenBounds.getWidth() - (primScreenBounds.getHeight()/ 2));
        stage.setHeight(primScreenBounds.getHeight() - (primScreenBounds.getHeight()/ 4));


        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
