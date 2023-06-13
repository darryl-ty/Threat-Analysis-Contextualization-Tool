package com.tact.threatanalysiscontextualizationtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private TitledPane uploadPopup;
    @FXML
    private AnchorPane greyOut;

    public void uploadPopupWindow(ActionEvent event){
        uploadPopup.setVisible(true);
        greyOut.setVisible(true);
    }

    public void settingsPopupWindow(ActionEvent event){

    }
}
