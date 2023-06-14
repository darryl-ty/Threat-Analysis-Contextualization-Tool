package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController{

    @FXML
    private TitledPane uploadPopup;
    @FXML
    private AnchorPane greyOut;
    @FXML
    private Pane sidePane;
    @FXML
    private TabPane mainContent;


    @FXML
    public void initialize(){
        uploadWindowDisable();
    }

    public void uploadWindowEnable(){
        uploadPopup.setVisible(true);
        greyOut.setVisible(true);
        sidePane.setDisable(true);
        mainContent.setDisable(true);
    }

    public void uploadWindowDisable(){
        uploadPopup.setVisible(false);
        greyOut.setVisible(false);
        sidePane.setDisable(false);
        mainContent.setDisable(false);
    }

    public void settingsPopupWindow(){

    }
}
