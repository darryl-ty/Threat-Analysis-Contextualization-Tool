package com.tact.threatanalysiscontextualizationtool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class VirusTotalFile extends Thread {

    private final String fileURI;
    private final String fileName;
    private String[] fileKnownAliases;
    private String threatCategories;
    private String[] familyLabels;
    private int threatRating;

    public VirusTotalFile(String fileURI, String fileName){
        this.fileURI = fileURI;
        this.fileName = fileName;
    }
    public void run(){
        try {
            initiateVirusTotalWebDriver();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initiateVirusTotalWebDriver() throws InterruptedException {
        WebDriver driverVirusTotal = createWebDriver();
        driverVirusTotal.get("https://www.virustotal.com/gui/home/upload");
        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driverVirusTotal;
        WebElement uploadFile = (WebElement) js.executeScript("return document.querySelector(\"#view-container > home-view\").shadowRoot.querySelector(\"#uploadForm\").shadowRoot.querySelector(\"#fileSelector\")");
        uploadFile.sendKeys(fileURI);
    }

    private WebDriver createWebDriver(){
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
}
