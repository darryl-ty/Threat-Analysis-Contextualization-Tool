package com.tact.threatanalysiscontextualizationtool.controllers;

import com.tact.threatanalysiscontextualizationtool.Severity;
import com.tact.threatanalysiscontextualizationtool.VirusTotalURL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VirusTotalURLController {

    private final VirusTotalURL vtUrl;
    private Severity urlSev;
    @FXML
    private Label urlSource;
    @FXML
    private Label finalURL;
    @FXML
    private Label ipAddresses;
    @FXML
    private Label referencedFiles;
    @FXML
    private Label categories;

    public VirusTotalURLController(VirusTotalURL vtUrl){
        this.vtUrl = vtUrl;
    }

    @FXML
    public void initialize(){
        urlSeverity();
        fieldPopulation();
    }

    public void gotoVirusTotalSource() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(vtUrl.getInfoLink()));
    }

    private void urlSeverity(){
        if (vtUrl.getWebRating() <= 3)
            urlSev = Severity.SAFE;
        else if (vtUrl.getWebRating() <= 7)
            urlSev = Severity.SUSPICIOUS;
        else
            urlSev = Severity.MALICIOUS;
    }

    private void fieldPopulation(){
        urlBasicInfo();
        urlAdvancedInfo();
    }

    private void urlBasicInfo(){
        urlSource.setText(vtUrl.getUrl());
        finalURL.setText(vtUrl.getFinalURL());
    }

    private void urlAdvancedInfo(){
        populateReferencedIPs();
        populateReferencedFiles();
        populateCategories();
    }

    private void populateReferencedIPs(){
        StringBuilder ipBuilder = new StringBuilder();

        for (String ip : vtUrl.getReferencedIPs()){
            ipBuilder.append("• ").append(ip).append("\n");
        }
        ipAddresses.setText(ipBuilder.toString());
    }

    private void populateReferencedFiles(){
        StringBuilder filesBuilder = new StringBuilder();

        for (String file : vtUrl.getReferencedFiles()){
            filesBuilder.append("• ").append(file).append("\n");
        }
        referencedFiles.setText(filesBuilder.toString());
    }

    private void populateCategories(){
        StringBuilder categoryBuilder = new StringBuilder();

        for (String category : vtUrl.getUrlCategories()){
            categoryBuilder.append("• ").append(category).append("\n");
        }
        categories.setText(categoryBuilder.toString());
    }
}
