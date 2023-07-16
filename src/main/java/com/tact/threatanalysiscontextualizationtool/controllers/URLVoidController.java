package com.tact.threatanalysiscontextualizationtool.controllers;

import com.tact.threatanalysiscontextualizationtool.Severity;
import com.tact.threatanalysiscontextualizationtool.URLVoid;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLVoidController {

    private final URLVoid urlVoid;
    private Severity urlSev;
    @FXML
    private Label urlSource;
    @FXML
    private Label dateRegistered;
    @FXML
    private Label serverLocation;
    @FXML
    private Label cityState;

    public URLVoidController(URLVoid urlVoid){
        this.urlVoid = urlVoid;
    }

    @FXML
    public void initialize(){
        urlSeverity();
        fieldPopulation();
    }

    public void gotoURLVoidLink() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(urlVoid.getInfoLink()));
    }

    private void urlSeverity(){
        if (urlVoid.getWebRating() <= 3)
            urlSev = Severity.SAFE;
        else if (urlVoid.getWebRating() <= 7)
            urlSev = Severity.SUSPICIOUS;
        else
            urlSev = Severity.MALICIOUS;
    }

    private void fieldPopulation(){
        urlInfo();
    }

    private void urlInfo(){
        urlSource.setText(urlVoid.getUrl());
        dateRegistered.setText(urlVoid.getDateRegistered());
        serverLocation.setText(urlVoid.getServerLocation());
        cityState.setText(urlVoid.getCity() + ", " + urlVoid.getRegion());
    }
}
