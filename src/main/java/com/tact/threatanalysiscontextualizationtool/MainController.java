package com.tact.threatanalysiscontextualizationtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;

public class MainController {

    @FXML
    private TitledPane uploadPopup;

    public void uploadPopupWindow(ActionEvent event){
        uploadPopup.setVisible(true);
    }

    public void settingsPopupWindow(ActionEvent event){

    }
}
