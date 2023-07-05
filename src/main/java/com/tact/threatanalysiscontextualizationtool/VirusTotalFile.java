package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
//        options.addArguments("--headless=new");

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
        this.threatRating = Integer.parseInt(vtRating.getText().strip());
    }

    private void otherFileNames(JavascriptExecutor driverVirusTotal) {
        WebElement alias = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement name : alias.findElements(By.tagName("span"))){
            if (name.getText().isBlank())
                continue;
            this.fileKnownAliases.add(name.getText().strip());
        }
    }

    private void typeOfFile(JavascriptExecutor driverVirusTotal){
        WebElement type = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div > div:nth-child(10) > div > a:nth-child(3)\")");
        this.fileType = type.getText();
    }

    private void fileHash(JavascriptExecutor driverVirusTotal) {
        WebElement hash = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div > div:nth-child(3) > div > a:nth-child(3)\")");
        this.hashValue = hash.getText();
    }

    private void switchToBehaviorTab(JavascriptExecutor driverVirusTotal) {
        WebElement behaviorTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(5) > a\")");
        behaviorTab.click();
        }

    private void fileDrops(JavascriptExecutor driverVirusTotal) {
        WebElement drops = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#behaviourtab\").shadowRoot.querySelector(\"#sandbox-behaviour\").shadowRoot.querySelector(\"file-system-actions\").shadowRoot.querySelector(\"#files-dropped > span > div > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement file : drops.findElements(By.tagName("span"))){
            if (file.getText().isBlank())
                continue;
            this.droppedFiles.add(file.getText().strip());
        }
    }

    private void fileContactedAddresses(JavascriptExecutor driverVirusTotal) {
        WebElement addresses = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#behaviourtab\").shadowRoot.querySelector(\"#sandbox-behaviour\").shadowRoot.querySelector(\"network-communication\").shadowRoot.querySelector(\"#network-comms > span > div > vt-ui-expandable-entry:nth-child(2) > span > div > vt-ui-simple-multipivots-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement address : addresses.findElements(By.tagName("span"))){
            if (address.getText().isBlank())
                continue;
            this.contactedAddresses.add(address.getText().strip());
        }
    }
    
    private void fileHeuristics(JavascriptExecutor driverVirusTotal) {
        WebElement behaviors = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report > vt-ui-file-card\").shadowRoot.querySelector(\"div > div.card-body > div > div.hstack.gap-2.flex-wrap\")");
        for (int i = 1; i <= behaviors.findElements(By.tagName("a")).size(); i++){
            this.behaviorLabels.add(behaviors.findElements(By.tagName("a")).get(i).getText());
        }
    }


    private static void sleepForASecond() {
        try {
            Thread.sleep(3500);
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
        return "File Name: "+ this.fileName +
                "\nFile Type: " + this.fileType +
                "\nFile Size:  " + this.fileSize +
                "\nFile Hash: " + this.hashValue +
                "\nFile Rating: " + this.threatRating +
                "\nFile Aliases: " + this.fileKnownAliases +
                "\nDropped Files: " + this.droppedFiles +
                "\nContacted Addresses: " + this.contactedAddresses +
                "\nBehaviors: " + this.behaviorLabels;
    }
}
