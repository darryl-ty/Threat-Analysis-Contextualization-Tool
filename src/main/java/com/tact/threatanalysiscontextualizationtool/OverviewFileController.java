package com.tact.threatanalysiscontextualizationtool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OverviewFileController {
    private FILE overview;
    @FXML
    private Label fileOverviewName;
    public OverviewFileController(FILE overview){
        this.overview = overview;
    }

    @FXML
    public void initialize(){
        fileOverviewName.setText(overview.vtFile().getFileName());
    }
}
