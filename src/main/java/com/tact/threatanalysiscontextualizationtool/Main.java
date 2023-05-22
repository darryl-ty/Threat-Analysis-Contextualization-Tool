package com.tact.threatanalysiscontextualizationtool;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXML/main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("TACT - Threat Analysis and Contextualization Tool");
        Scale scale = new Scale(1.2, 1.2);
        scale.setPivotX(0);
        scale.setPivotY(0);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(primScreenBounds.getWidth() - (primScreenBounds.getHeight()/ 2));
        stage.setHeight(primScreenBounds.getHeight() - (primScreenBounds.getHeight()/ 4));
        scene.getRoot().getTransforms().setAll(scale);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
