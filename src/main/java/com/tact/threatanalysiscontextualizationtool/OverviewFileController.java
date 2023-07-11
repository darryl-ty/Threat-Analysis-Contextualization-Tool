package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverviewFileController {
    private FILE overview;
    @FXML
    private Label fileOverviewName;
    @FXML
    private Label fileType;
    @FXML
    private Label fileSize;
    @FXML
    private Label fileHash;


    public OverviewFileController(FILE overview){
        this.overview = overview;
    }

    @FXML
    public void initialize(){
        fieldPopulation();
    }

    private void fieldPopulation() {
        fileBasicInfo();
        fileAdvancedInfo();
    }

    private void fileBasicInfo() {
        fileOverviewName.setText(overview.vtFile().getFileName());
        fileType.setText(overview.vtFile().getFileType());
        fileSize.setText(String.valueOf(overview.vtFile().getFileSize()) + " Bytes");
        fileHash.setText(overview.vtFile().getHashValue());
    }

    private void fileAdvancedInfo(){

    }
}
