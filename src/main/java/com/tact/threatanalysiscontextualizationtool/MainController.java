package com.tact.threatanalysiscontextualizationtool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class MainController{

    private final String[] FILE_TABS = new String[]{"VirusTotal"};
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
    private Label fileOverviewName;


    @FXML
    public void initialize() {
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
        urlSelect = false;
        fileSelect = true;

        for (String fileTab : FILE_TABS) {
            mainContent.getTabs().add(new Tab(fileTab));
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
        disables();

        removeTabs();
    }

    public void urlSelection(){
        uploadLabel.setVisible(true);
        uploadLabel.setText("Please input a URL/IP Address below.");
        menuDropdown.setVisible(false);
        urlAddressBox.setVisible(true);
        redoButton.setVisible(true);
        fileSelect = false;
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
            VirusTotalFile fileInfo = fileUpload(uploadedFile);
            loadFileWindowTabs(fileInfo);
        } else if (urlSelect) {
            URL urls = urlUpload(urlAddressBox.getText());
            loadURLWindowTabs();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Please select either File or URL/IP Address upload type.", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void settingsPopupWindow(){

    }

    private VirusTotalFile fileUpload(File file){
        disables();

        VirusTotalFile vt = new VirusTotalFile(file.getPath());
        vt.start();
        vt.setFileSize(file.length() / 1024);

        return vt;
    }

    private void loadFileWindowTabs(VirusTotalFile fileInfo){
        try { //TODO - Figure out a better way to load the FXML panes instead of using redundant code.
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/tact/threatanalysiscontextualizationtool/FXML/overview-file.fxml"));
            FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("/com/tact/threatanalysiscontextualizationtool/FXML/virus-total-file.fxml"));
            AnchorPane overviewPane = loader.load();
            AnchorPane vtPane = loader2.load();

            for (int i = 0; i <= FILE_TABS.length; i++){
                if (mainContent.getTabs().get(i).getContent() == null){
                    mainContent.getTabs().get(i).setContent(new AnchorPane());
                    ((AnchorPane) mainContent.getTabs().get(i).getContent()).getChildren().addAll(vtPane);
                }
            }
            ((AnchorPane) mainContent.getTabs().get(0).getContent()).getChildren().addAll(overviewPane);

//            AnchorPane.setBottomAnchor(newPane, 0.0);
//            AnchorPane.setLeftAnchor(newPane, 0.0);
//            AnchorPane.setRightAnchor(newPane, 0.0);
//            AnchorPane.setTopAnchor(newPane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void loadFileInfo(VirusTotalFile inputFile){ //TODO - Figure out how to load info into FXML file that is loaded after the fact.
//        System.out.println(fileOverviewName.getText());
//    }

    private URL urlUpload(String url){
        disables();

        Talos talos = new Talos();
        URLVoid urlVoid = new URLVoid();
        VirusTotalURL vtUrl = new VirusTotalURL();

        startURLThreads(talos, urlVoid, vtUrl);

        return new URL(talos, urlVoid, vtUrl);
    }

    private void startURLThreads(Talos talos, URLVoid urlVoid, VirusTotalURL vtUrl) {
        talos.start();
        urlVoid.start();
        vtUrl.start();
    }

    private void loadURLWindowTabs() {
    }

    private void disables(){
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
    }

    private void removeTabs(){
        mainContent.getTabs().remove(1, mainContent.getTabs().size());
    }

}
