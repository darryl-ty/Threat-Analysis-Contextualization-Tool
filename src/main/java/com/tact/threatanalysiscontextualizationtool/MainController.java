package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TitledPane uploadPopup;
    @FXML
    private AnchorPane greyOut;
    @FXML
    private Pane sidePane;
    @FXML
    private TabPane mainContent;
    @FXML
    private ChoiceBox<String> uploadChoiceBox;
    private String[] choiceBoxOptions = {"File", "URL/IP Address"};


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
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
