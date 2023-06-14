package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
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
    private Label uploadLabel;
    @FXML
    private SplitMenuButton menuDropdown;


    @FXML
    public void initialize(){
        uploadWindowDisable();
    }


    public void fileSelection(){
        uploadLabel.setText("Please click or drag a file to the box below.");
        menuDropdown.setVisible(false);
    }

    public void uploadWindowEnable(){
        uploadPopup.setVisible(true);
        greyOut.setVisible(true);
        sidePane.setDisable(true);
        mainContent.setDisable(true);
        menuDropdown.setVisible(true);
        uploadLabel.setText("");
    }

    public void uploadWindowDisable(){
        uploadPopup.setVisible(false);
        greyOut.setVisible(false);
        sidePane.setDisable(false);
        mainContent.setDisable(false);
    }

    public void urlSelection(){
        uploadLabel.setText("Please input a URL/IP Address below.");
        menuDropdown.setVisible(false);

    }

    public void settingsPopupWindow(){

    }
}
