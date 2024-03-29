package com.tact.threatanalysiscontextualizationtool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
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
        stage.setWidth(1280);
        stage.setHeight(720);
        scene.getRoot().getTransforms().setAll(scale);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
