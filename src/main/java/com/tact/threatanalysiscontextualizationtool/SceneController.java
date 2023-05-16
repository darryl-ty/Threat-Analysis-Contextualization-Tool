package com.tact.threatanalysiscontextualizationtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneController {

    public void switchToMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("main-page.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("TACT - Threat Analysis and Contextualization Tool");
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(primScreenBounds.getWidth() - (primScreenBounds.getHeight()/ 2));
        stage.setHeight(primScreenBounds.getHeight() - (primScreenBounds.getHeight()/ 4));

        stage.setScene(scene);
        stage.show();
    }
}
