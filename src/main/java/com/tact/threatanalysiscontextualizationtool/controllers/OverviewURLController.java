package com.tact.threatanalysiscontextualizationtool.controllers;

import com.tact.threatanalysiscontextualizationtool.Severity;
import com.tact.threatanalysiscontextualizationtool.records.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OverviewURLController {

    private final URL overview;
    private Severity urlSev;
    @FXML
    private Label urlOverview;
    @FXML
    private Label dateRegistered;
    @FXML
    private Label serverLocation;
    @FXML
    private Label cityState;
    @FXML
    private Label ipAddresses;
    @FXML
    private Label referencedFiles;
    @FXML
    private Label categories;
    @FXML
    private TextField additionalInfo;

    public OverviewURLController(URL overview){
        this.overview = overview;
    }

    @FXML
    public void initialize(){
        urlSeverity();
        fieldPopulation();
    }

    private void urlSeverity(){
        // Change this to dynamically divide by the number of URL Record objects.
        int average = ((overview.vtURL().getWebRating() + overview.urlVoid().getWebRating())/2);

        if (average <= 3)
            urlSev = Severity.SAFE;
        else if (average <= 7)
            urlSev = Severity.SUSPICIOUS;
        else
            urlSev = Severity.MALICIOUS;
    }

    private void fieldPopulation(){
        urlBasicInfo();
        urlAdvancedInfo();
    }

    private void urlBasicInfo(){
        urlOverview.setText(overview.vtURL().getUrl());
        dateRegistered.setText(overview.urlVoid().getDateRegistered());
        serverLocation.setText(overview.urlVoid().getServerLocation());
        cityState.setText(overview.urlVoid().getCity() + ", " + overview.urlVoid().getRegion());
    }

    private void urlAdvancedInfo(){
        populateIPs();
        populateFiles();
        populateCategories();
        populateAdditionalInfo();
    }

    private void populateIPs(){
        StringBuilder ipBuilder = new StringBuilder();

        for (String ip : overview.vtURL().getReferencedIPs()){
            ipBuilder.append("• ").append(ip).append("\n");
        }
        ipAddresses.setText(ipBuilder.toString());
    }

    private void populateFiles(){
        StringBuilder filesBuilder = new StringBuilder();

        for (String file : overview.vtURL().getReferencedFiles()){
            filesBuilder.append("• ").append(file).append("\n");
        }
        referencedFiles.setText(filesBuilder.toString());
    }

    private void populateCategories(){
        StringBuilder categoryBuilder = new StringBuilder();

        for (String category : overview.vtURL().getUrlCategories()){
            categoryBuilder.append("• ").append(category).append("\n");
        }
        categories.setText(categoryBuilder.toString());
    }

    private void populateAdditionalInfo() {
        switch (urlSev) {
            case SAFE ->
                    additionalInfo.setText("This url/ip appears to be safe.");
            case SUSPICIOUS ->
                    additionalInfo.setText("This url/ip exhibits suspicious traffic. Exercise caution when connecting.");
            case MALICIOUS ->
                    additionalInfo.setText("This url/ip is known for having malicious traffic and/or distributing malicious payloads." +
                            " Be careful.");
            default ->
                    additionalInfo.setText("Could not assess url/ip severity. Use caution when connecting.");
        }
    }
}
