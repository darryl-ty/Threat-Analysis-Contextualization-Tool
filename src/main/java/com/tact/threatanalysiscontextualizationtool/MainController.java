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
import java.util.ArrayList;

public class MainController{

    private final String[] FILE_TABS = new String[]{"VirusTotal", "Cuckoo(Open)"};
    private final String[] URL_TABS = new String[]{"VirusTotal", "Talos", "URLVoid"};
    private File uploadedFile;
    private boolean fileSelect;
    private boolean urlSelect;

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
    private Button submitButton;


    @FXML
    public void initialize(){
        uploadWindowDisable();
        fileSelect = false;
        urlSelect = false;
    }


    public void fileSelection(){
        uploadLabel.setVisible(true);
        uploadLabel.setText("Please click or drag a file to the box below.");
        menuDropdown.setVisible(false);
        fileURIBox.setVisible(true);
        fileURIButton.setVisible(true);
        redoButton.setVisible(true);
        fileSelect = true;

        for (String entry : FILE_TABS){
            mainContent.getTabs().add(new Tab(entry));
        }
    }

    public void fileChooserPopup(ActionEvent event){
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(window);
        event.consume();
        fileURIBox.setText(file.getPath());

        uploadedFile = file;
    }

    public void uploadWindowEnable(){
        uploadPopup.setVisible(true);
        greyOut.setVisible(true);
        menuDropdown.setVisible(true);
        sidePane.setDisable(true);
        mainContent.setDisable(true);
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

        urlSelect = false;
        fileSelect = false;

        removeTabs();
    }

    public void urlSelection(){
        uploadLabel.setVisible(true);
        uploadLabel.setText("Please input a URL/IP Address below.");
        menuDropdown.setVisible(false);
        urlAddressBox.setVisible(true);
        redoButton.setVisible(true);
        urlSelect = true;

        for (String entry : URL_TABS){mainContent.getTabs().add(new Tab(entry));}

    }

    public void redoUploadButton(){
        fileURIBox.setVisible(false);
        fileURIButton.setVisible(false);
        urlAddressBox.setVisible(false);
        redoButton.setVisible(false);
        uploadLabel.setVisible(false);
        menuDropdown.setVisible(true);

        fileSelect = false;
        urlSelect = false;

        removeTabs();
    }

    public void submitSelection(ActionEvent event){
        if (fileSelect){
            fileUpload(uploadedFile);
        } else if (urlSelect) {
            urlUpload(urlAddressBox.getText());
        } else {
            System.out.println();
        }

    }

    public void settingsPopupWindow(){

    }

    private void fileUpload(File file){
        VirusTotalFile vt = new VirusTotalFile(file.getPath(), file.getName(), "", new ArrayList<>());
        vt.start();
    }

    private void urlUpload(String url){

    }

    private void removeTabs(){
        mainContent.getTabs().remove(1, mainContent.getTabs().size());
    }

}
