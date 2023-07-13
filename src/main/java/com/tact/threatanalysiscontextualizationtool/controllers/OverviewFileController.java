package com.tact.threatanalysiscontextualizationtool.controllers;

import com.tact.threatanalysiscontextualizationtool.records.FILE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OverviewFileController {

    enum Severity{
        SAFE,
        SUSPICIOUS,
        MALICIOUS
    }

    private final FILE overview;
    private Severity fileSev;
    @FXML
    private Label fileOverviewName;
    @FXML
    private Label fileType;
    @FXML
    private Label fileSize;
    @FXML
    private Label fileHash;
    @FXML
    private Label fileAliases;
    @FXML
    private Label fileBehaviors;
    @FXML
    private Label fileIPS;
    @FXML
    private Label fileDrops;
    @FXML
    private TextArea fileAdditionInfo;


    public OverviewFileController(FILE overview){
        this.overview = overview;
    }

    @FXML
    public void initialize(){
        fileSeverity();
        fieldPopulation();
    }

    private void fileSeverity(){ // Remember to average this rating across the # of OSINT sites.
        if (overview.vtFile().getThreatRating() <= 3)
            fileSev = Severity.SAFE;
        else if (overview.vtFile().getThreatRating() <= 7)
            fileSev = Severity.SUSPICIOUS;
        else
            fileSev = Severity.MALICIOUS;
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
        populateAliases();
        populateBehaviors();
        populateIPS();
        populateDrops();
        populateAdditionalInfo();
    }

    private void populateAliases() {
        StringBuilder aliasBuilder = new StringBuilder();

        for(String alias : overview.vtFile().getFileKnownAliases()){
            aliasBuilder.append("• " + alias + "\n");
        }
        fileAliases.setText(aliasBuilder.toString());
    }

    private void populateBehaviors() {
        StringBuilder behaviorBuilder = new StringBuilder();

        for(String behavior : overview.vtFile().getBehaviorLabels()){
            behaviorBuilder.append("• " + behavior + "\n");
        }
        fileBehaviors.setText(behaviorBuilder.toString());
    }

    private void populateIPS() {
        StringBuilder addressBuilder = new StringBuilder();

        for(String addresses : overview.vtFile().getContactedAddresses()){
            addressBuilder.append("• " + addresses + "\n");
        }
        fileIPS.setText(addressBuilder.toString());
    }

    private void populateDrops() {
        StringBuilder dropBuilder = new StringBuilder();

        for(String droppedFile : overview.vtFile().getDroppedFiles()){
            dropBuilder.append("• " + droppedFile.split("\\d")[droppedFile.split("\\d").length-1] + "\n");
        }
        fileDrops.setText(dropBuilder.toString());
    }

    private void populateAdditionalInfo() {
        switch (fileSev) {
            case SAFE ->
                    fileAdditionInfo.setText("This file appears to be safe.");
            case SUSPICIOUS ->
                    fileAdditionInfo.setText("This file exhibits suspicious behavior or potentially contains harmful code. " +
                            "Exercise caution when running it.");
            case MALICIOUS ->
                    fileAdditionInfo.setText("This file exhibits malicious behavior and contains harmful code. " +
                            "If run, it could damage your computer. Be careful.");
            default ->
                    fileAdditionInfo.setText("Could not assess file severity. Use caution when analyzing.");
        }
    }
}
