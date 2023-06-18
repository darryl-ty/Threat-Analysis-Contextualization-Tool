package com.tact.threatanalysiscontextualizationtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class MainController{

    private final String[] FILE_TABS = new String[]{"VirusTotal", "MalwareBazaar", "Cuckoo(Open)"};
    private final String[] URL_TABS = new String[]{"VirusTotal", "Talos", "URLhaus", "URLVoid"};

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
    private Button redoButton;


    @FXML
    public void initialize(){
        uploadWindowDisable();
    }


    public void fileSelection(){
        uploadLabel.setVisible(true);
        uploadLabel.setText("Please click or drag a file to the box below.");
        menuDropdown.setVisible(false);
        fileURIBox.setVisible(true);
        fileURIButton.setVisible(true);
        redoButton.setVisible(true);
    }

    public void fileChooserPopup(ActionEvent event){
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        event.consume();
        fileURIBox.setText(file.getName());
    }

    public void uploadWindowEnable(){
        uploadPopup.setVisible(true);
        greyOut.setVisible(true);
        sidePane.setDisable(true);
        mainContent.setDisable(true);
        menuDropdown.setVisible(true);
        uploadLabel.setText("");
        fileURIBox.setText("");
    }

    public void uploadWindowDisable(){
        uploadPopup.setVisible(false);
        urlAddressBox.setVisible(false);
        fileURIBox.setVisible(false);
        fileURIButton.setVisible(false);
        redoButton.setVisible(false);
        greyOut.setVisible(false);
        sidePane.setDisable(false);
        mainContent.setDisable(false);
    }

    public void urlSelection(){
        uploadLabel.setVisible(true);
        uploadLabel.setText("Please input a URL/IP Address below.");
        menuDropdown.setVisible(false);
        urlAddressBox.setVisible(true);
        redoButton.setVisible(true);

    }

    public void redoUploadButton(){
        fileURIBox.setVisible(false);
        fileURIButton.setVisible(false);
        urlAddressBox.setVisible(false);
        redoButton.setVisible(false);
        uploadLabel.setVisible(false);
        menuDropdown.setVisible(true);

    }

    public void settingsPopupWindow(){

    }
}
