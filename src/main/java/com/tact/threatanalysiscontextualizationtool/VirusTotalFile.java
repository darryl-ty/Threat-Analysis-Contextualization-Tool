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
    private ArrayList<String> fileKnownAliases;
    private String threatCategories;
    private String[] familyLabels;
    private int threatRating;

    public VirusTotalFile(
            String fileURI,
            String fileName,
            String threatCategories,
            ArrayList<String> fileKnownAliases){
        this.fileURI = fileURI;
        this.fileName = fileName;
        this.threatCategories = threatCategories;
        this.fileKnownAliases = fileKnownAliases;
    }
    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateVirusTotalWebDriver(driver);
        virusTotalInfoCollection((JavascriptExecutor) driver);
    }

    private void virusTotalInfoCollection(JavascriptExecutor driverVirusTotal) {
        WebElement vtRating = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div.row.mb-4.d-none.d-lg-flex > div.col-auto > vt-ui-community-widget\")");
        threatRating = Integer.parseInt(vtRating.getAttribute("score"));



        WebElement alias = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-simple-expandable-list\").shadowRoot.querySelector(\"ul\")");
        for (WebElement name : alias.findElements(By.tagName("span"))){
            fileKnownAliases.add(name.getText().strip());
        }
    }

    private void initiateVirusTotalWebDriver(WebDriver driverVirusTotal){
        JavascriptExecutor js = jsNavigator(driverVirusTotal);
        sleepForASecond();

        WebElement detailsTab = (WebElement) js.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
        sleepForASecond();
    }

    private static void sleepForASecond() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private JavascriptExecutor jsNavigator(WebDriver driverVirusTotal) {
        driverVirusTotal.get("https://www.virustotal.com/gui/home/upload");
        JavascriptExecutor js = (JavascriptExecutor) driverVirusTotal;
        WebElement uploadFile = (WebElement) js.executeScript("return document.querySelector(\"#view-container > home-view\").shadowRoot.querySelector(\"#uploadForm\").shadowRoot.querySelector(\"#fileSelector\")");
        uploadFile.sendKeys(fileURI);
        return js;
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");

        return new ChromeDriver(options);
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
}
