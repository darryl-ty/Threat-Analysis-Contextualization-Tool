package com.tact.threatanalysiscontextualizationtool;

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
        WebDriver driver = createWebDriverWithOptions();
        initiateVirusTotalWebDriver(driver);
        virusTotalInfoCollection(driver);
    }

    private void virusTotalInfoCollection(WebDriver driverVirusTotal) {

    }

    private void initiateVirusTotalWebDriver(WebDriver driverVirusTotal){
        JavascriptExecutor js = getJavascriptExecutor(driverVirusTotal);
        sleepForASecond();

        WebElement detailsTab = (WebElement) js.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
    }

    private static void sleepForASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private JavascriptExecutor getJavascriptExecutor(WebDriver driverVirusTotal) {
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
}
