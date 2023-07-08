package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class VirusTotalURL extends Thread{

    private final String url;
    private String finalURL;
    private ArrayList<String> urlCategories;
    private int webRating;

    public VirusTotalURL(String url){
        this.url = url;
        this.finalURL = "";
        this.urlCategories = new ArrayList<>();
        this.webRating = 0;
    }

    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateVirusTotalWebDriver(driver);
        vtInfoCollection(driver);
    }

    private void initiateVirusTotalWebDriver(WebDriver driverVirusTotal) {
        vtWebsiteNav(driverVirusTotal);
    }

    private void vtWebsiteNav(WebDriver driverVirusTotal) {
        driverVirusTotal.get("https://www.virustotal.com/gui/home/url");
        sleepForASecond();

        searchForURL((JavascriptExecutor) driverVirusTotal);
        sleepForASecond();

        navigateToDetailsTab((JavascriptExecutor) driverVirusTotal);
    }

    private void searchForURL(JavascriptExecutor driverVirusTotal){
        WebElement searchBar = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > home-view\").shadowRoot.querySelector(\"#urlSearchInput\")");
        searchBar.sendKeys(url);
        searchBar.sendKeys(Keys.ENTER);
    }

    private void navigateToDetailsTab(JavascriptExecutor driverVirusTotal){ // TODO - figure out why details tab wont get clicked.
        WebElement detailsTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
    }

    private void vtInfoCollection(WebDriver driverVirusTotal){
        finalWebAddress((JavascriptExecutor) driverVirusTotal);
        urlCharacteristics((JavascriptExecutor) driverVirusTotal);
        urlRating((JavascriptExecutor) driverVirusTotal);
    }

    private void finalWebAddress(JavascriptExecutor driverVirusTotal) {
        WebElement webAddress = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-expandable-entry:nth-child(1) > span > div\")");
        finalURL = webAddress.getText();
    }

    private void urlCharacteristics(JavascriptExecutor driverVirusTotal) {
        WebElement characteristics = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div\")");
        for(WebElement characteristic : characteristics.findElements(By.tagName("div"))){
            System.out.println(characteristic.getText());
        }
    }

    private void urlRating(JavascriptExecutor driverVirusTotal) {
        WebElement rating = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div.row.mb-4.d-none.d-lg-flex > div.col-auto > vt-ui-detections-widget\").shadowRoot.querySelector(\"div > div > div.positives\")");
        webRating = Integer.parseInt(rating.getText());
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }

    private static void sleepForASecond() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
