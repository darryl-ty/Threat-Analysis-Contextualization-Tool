package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class VirusTotalFile extends Thread {

    private final String fileURI;
    private final String fileName;
    private String fileType;
    private long fileSize;
    private String hashValue;
    private int threatRating;
    private ArrayList<String> fileKnownAliases;
    private ArrayList<String> droppedFiles;
    private ArrayList<String> contactedAddresses;
    private ArrayList<String> behaviorLabels;

    public VirusTotalFile(String fileURI){
        this.fileURI = fileURI;
        this.fileName = fileURI.substring(fileURI.lastIndexOf("\\") + 1);
        this.fileType = "";
        this.fileSize = 0;
        this.hashValue = "";
        this.threatRating = 0;
        this.fileKnownAliases = new ArrayList<>();
        this.droppedFiles = new ArrayList<>();
        this.contactedAddresses = new ArrayList<>();
        this.behaviorLabels = new ArrayList<>();
    }
    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateVirusTotalWebDriver(driver);
        virusTotalInfoCollection((JavascriptExecutor) driver);
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }

    private void initiateVirusTotalWebDriver(WebDriver driverVirusTotal){
        JavascriptExecutor js = jsNavigator(driverVirusTotal);
        sleepForASecond();

        WebElement detailsTab = (WebElement) js.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
        sleepForASecond();
    }

    private JavascriptExecutor jsNavigator(WebDriver driverVirusTotal) {
        driverVirusTotal.get("https://www.virustotal.com/gui/home/upload");
        JavascriptExecutor js = (JavascriptExecutor) driverVirusTotal;
        WebElement uploadFile = (WebElement) js.executeScript("return document.querySelector(\"#view-container > home-view\").shadowRoot.querySelector(\"#uploadForm\").shadowRoot.querySelector(\"#fileSelector\")");
        uploadFile.sendKeys(fileURI);
        return js;
    }

    private void virusTotalInfoCollection(JavascriptExecutor driverVirusTotal) {
        fileRating(driverVirusTotal);
        otherFileNames(driverVirusTotal);
        typeOfFile(driverVirusTotal);
        fileHash(driverVirusTotal);

        switchToBehaviorTab(driverVirusTotal);
        sleepForASecond();

        fileDrops(driverVirusTotal);
        fileContactedAddresses(driverVirusTotal);
        fileHeuristics(driverVirusTotal);
        
    }

    private void fileRating(JavascriptExecutor driverVirusTotal) {
        WebElement vtRating = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div.row.mb-4.d-none.d-lg-flex > div.col-auto > vt-ui-detections-widget\").shadowRoot.querySelector(\"div > div > div.positives\")");
        threatRating = Integer.parseInt(vtRating.getText().strip());
    }

    private void otherFileNames(JavascriptExecutor driverVirusTotal) {
        WebElement alias = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement name : alias.findElements(By.tagName("span"))){
            if (name.getText().isBlank())
                continue;
            fileKnownAliases.add(name.getText().strip());
        }
    }

    private void typeOfFile(JavascriptExecutor driverVirusTotal){
        WebElement type = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div > div:nth-child(10) > div > a:nth-child(3)\")");
        fileType = type.getText();
    }

    private void fileHash(JavascriptExecutor driverVirusTotal) {
        WebElement hash = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div > div:nth-child(3) > div > a:nth-child(3)\")");
        hashValue = hash.getText();
    }

    private void switchToBehaviorTab(JavascriptExecutor driverVirusTotal) {
        WebElement behaviorTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(5) > a\")");
        behaviorTab.click();
        }

    private void fileDrops(JavascriptExecutor driverVirusTotal) {
        try {
            WebElement drops = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#behaviourtab\").shadowRoot.querySelector(\"#sandbox-behaviour\").shadowRoot.querySelector(\"file-system-actions\").shadowRoot.querySelector(\"#files-dropped > span > div > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
            for (WebElement file : drops.findElements(By.tagName("span"))) {
                if (file.getText().isBlank())
                    continue;
                droppedFiles.add(file.getText().strip());
            }
        } catch (JavascriptException e){
            e.printStackTrace();
            droppedFiles.add("Could not obtain dropped files.");
        }
    }

    private void fileContactedAddresses(JavascriptExecutor driverVirusTotal) {
        try {
            WebElement addresses = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#behaviourtab\").shadowRoot.querySelector(\"#sandbox-behaviour\").shadowRoot.querySelector(\"network-communication\").shadowRoot.querySelector(\"#network-comms > span > div > vt-ui-expandable-entry > span > div > vt-ui-simple-multipivots-expandable-list\").shadowRoot.querySelector(\"ul\")");
            for (WebElement address : addresses.findElements(By.tagName("span"))) {
                if (address.getText().isBlank())
                    continue;
                contactedAddresses.add(address.getText().strip());
            }
        } catch (JavascriptException e){
            e.printStackTrace();
            contactedAddresses.add("Could not obtain contacted IPs.");
        }
    }
    
    private void fileHeuristics(JavascriptExecutor driverVirusTotal) {
        try {
            WebElement behaviors = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report > vt-ui-file-card\").shadowRoot.querySelector(\"div > div.card-body > div > div.hstack.gap-2.flex-wrap\")");
            for (int i = 1; i < behaviors.findElements(By.tagName("a")).size(); i++) {
                behaviorLabels.add(behaviors.findElements(By.tagName("a")).get(i).getText());
            }
        } catch (JavascriptException e){
            e.printStackTrace();
            behaviorLabels.add("Could not obtain File Heuristics.");
        }
    }


    private static void sleepForASecond() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileURI() {
        return fileURI;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getHashValue() {
        return hashValue;
    }

    public int getThreatRating() {
        return threatRating;
    }

    public ArrayList<String> getFileKnownAliases(){
        return fileKnownAliases;
    }

    public ArrayList<String> getDroppedFiles() {
        return droppedFiles;
    }

    public ArrayList<String> getContactedAddresses() {
        return contactedAddresses;
    }

    public ArrayList<String> getBehaviorLabels() {
        return behaviorLabels;
    }

    public void setFileSize(long size){
        this.fileSize = size;
    }

    public String toString(){
        return "File Name: "+ fileName +
                "\nFile Type: " + fileType +
                "\nFile Size:  " + fileSize +
                "\nFile Hash: " + hashValue +
                "\nFile Rating: " + threatRating +
                "\nFile Aliases: " + fileKnownAliases +
                "\nDropped Files: " + droppedFiles +
                "\nContacted Addresses: " + contactedAddresses +
                "\nBehaviors: " + behaviorLabels;
    }
}
