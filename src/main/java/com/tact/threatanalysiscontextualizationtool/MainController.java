package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField urlAddressBox;
    @FXML
    private TextField fileURIBox;
    @FXML
    private Button fileURIButton;


    @FXML
    public void initialize(){
        uploadWindowDisable();
    }


    public void fileSelection(){
        uploadLabel.setText("Please click or drag a file to the box below.");
        menuDropdown.setVisible(false);
        fileURIBox.setVisible(true);
        fileURIButton.setVisible(true);
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
        urlAddressBox.setVisible(false);
        fileURIBox.setVisible(false);
        fileURIButton.setVisible(false);
        greyOut.setVisible(false);
        sidePane.setDisable(false);
        mainContent.setDisable(false);
    }

    public void urlSelection(){
        uploadLabel.setText("Please input a URL/IP Address below.");
        menuDropdown.setVisible(false);
        urlAddressBox.setVisible(true);

    }

    public void settingsPopupWindow(){

    }
}
