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
    private String fileName;
    private String threatCategories;
    private int threatRating;
    private ArrayList<String> fileKnownAliases;
    private ArrayList<String> behaviorLabels;

    public VirusTotalFile(String fileURI){
        this.fileURI = fileURI;
        this.fileName = "";
        this.threatCategories = "";
        this.threatRating = 0;
        this.fileKnownAliases = new ArrayList<>();
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

    }

    private void fileRating(JavascriptExecutor driverVirusTotal) {
        WebElement vtRating = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div.row.mb-4.d-none.d-lg-flex > div.col-auto > vt-ui-community-widget\")");
        threatRating = Integer.parseInt(vtRating.getAttribute("score"));
    }

    private void otherFileNames(JavascriptExecutor driverVirusTotal) {
        WebElement alias = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement name : alias.findElements(By.tagName("span"))){
            if (name.getText().isBlank())
                continue;
            fileKnownAliases.add(name.getText().strip());
        }
    }


    private static void sleepForASecond() {
        try {
            Thread.sleep(1500);
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

    public String getThreatCategories(){
        return threatCategories;
    }

    public ArrayList<String> getFileKnownAliases(){
        return fileKnownAliases;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void setThreatCategories(String threatCategories) {
        this.threatCategories = threatCategories;
    }

    private void setThreatRating(int threatRating) {
        this.threatRating = threatRating;
    }
}
