package com.tact.threatanalysiscontextualizationtool.controllers;

import com.tact.threatanalysiscontextualizationtool.SeverityFile;
import com.tact.threatanalysiscontextualizationtool.VirusTotalFile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class VirusTotalFileController {

    private final VirusTotalFile vt;
    private SeverityFile fileSev;
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

    public VirusTotalFileController(VirusTotalFile vt){
        this.vt = vt;
    }

    @FXML
    public void initialize(){
        fileSeverity();
        fieldPopulation();
    }

    private void fileSeverity(){
        if (vt.getThreatRating() <= 3)
            fileSev = SeverityFile.SAFE;
        else if (vt.getThreatRating() <= 7)
            fileSev = SeverityFile.SUSPICIOUS;
        else
            fileSev = SeverityFile.MALICIOUS;
    }

    private void fieldPopulation() {
        fileBasicInfo();
        fileAdvancedInfo();
    }

    private void fileBasicInfo() {
        fileOverviewName.setText(vt.getFileName());
        fileType.setText(vt.getFileType());
        fileSize.setText(vt.getFileSize() + " Bytes");
        fileHash.setText(vt.getHashValue());
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

        for(String alias : vt.getFileKnownAliases()){
            aliasBuilder.append("• ").append(alias).append("\n");
        }
        fileAliases.setText(aliasBuilder.toString());
    }

    private void populateBehaviors() {
        StringBuilder behaviorBuilder = new StringBuilder();

        for(String behavior : vt.getBehaviorLabels()){
            behaviorBuilder.append("• ").append(behavior).append("\n");
        }
        fileBehaviors.setText(behaviorBuilder.toString());
    }

    private void populateIPS() {
        StringBuilder addressBuilder = new StringBuilder();

        for(String addresses : vt.getContactedAddresses()){
            addressBuilder.append("• ").append(addresses).append("\n");
        }
        fileIPS.setText(addressBuilder.toString());
    }

    private void populateDrops() {
        StringBuilder dropBuilder = new StringBuilder();

        for(String droppedFile : vt.getDroppedFiles()){
            dropBuilder.append("• ").append(droppedFile.split("\\d")[droppedFile.split("\\d").length - 1]).append("\n");
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
