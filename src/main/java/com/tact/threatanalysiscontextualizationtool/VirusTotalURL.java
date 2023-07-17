package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class VirusTotalURL extends Thread{

    private final String url;
    private String finalURL;
    private String infoLink;
    private ArrayList<String> urlCategories;
    private ArrayList<String> referencedIPs;
    private ArrayList<String> referencedFiles;
    private int webRating;

    public VirusTotalURL(String url){
        this.url = url;
        this.finalURL = "";
        this.infoLink = "";
        this.urlCategories = new ArrayList<>();
        this.referencedIPs = new ArrayList<>();
        this.referencedFiles = new ArrayList<>();
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

    private void navigateToDetailsTab(JavascriptExecutor driverVirusTotal){
        WebElement detailsTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
    }

    private void vtInfoCollection(WebDriver driverVirusTotal){
        vtLink(driverVirusTotal);
        finalWebAddress((JavascriptExecutor) driverVirusTotal);
        urlCharacteristics((JavascriptExecutor) driverVirusTotal);
        urlRating((JavascriptExecutor) driverVirusTotal);

        switchToRelationsTab((JavascriptExecutor) driverVirusTotal);

        urlIPs((JavascriptExecutor) driverVirusTotal);
        urlFiles((JavascriptExecutor) driverVirusTotal);
    }

    private void vtLink(WebDriver driverVirusTotal){
        infoLink = driverVirusTotal.getCurrentUrl();
    }

    private void finalWebAddress(JavascriptExecutor driverVirusTotal) {
        try {
            WebElement webAddress = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(3) > span > vt-ui-expandable-entry:nth-child(1) > span > div\")");
            finalURL = webAddress.getText();
        } catch (JavascriptException e){
            e.printStackTrace();
            finalURL = url;
        }
    }

    private void urlCharacteristics(JavascriptExecutor driverVirusTotal) {
        WebElement characteristics = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#details\").shadowRoot.querySelector(\"div > vt-ui-expandable:nth-child(1) > span > vt-ui-key-val-table\").shadowRoot.querySelector(\"div > div\")");
        for(WebElement characteristic : characteristics.findElements(By.tagName("div"))){
            if (characteristic.getText().isBlank())
                continue;
            if (urlCategories.contains(characteristic.getText().strip()))
                continue;
            urlCategories.add(characteristic.getText().strip());
        }
    }

    private void urlRating(JavascriptExecutor driverVirusTotal) {
        WebElement rating = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > url-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div.row.mb-4.d-none.d-lg-flex > div.col-auto > vt-ui-detections-widget\").shadowRoot.querySelector(\"div > div > div.positives\")");
        webRating = Integer.parseInt(rating.getText());
    }

    private void switchToRelationsTab(JavascriptExecutor driverVirusTotal){
        try {
            WebElement relationsTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > domain-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(4) > a\")");
            relationsTab.click();
        } catch (JavascriptException e){
            e.printStackTrace();
            referencedIPs.add("Could not obtain referenced IPs");
            referencedFiles.add("Could not obtain referenced Files");
        }
    }

    private void urlIPs(JavascriptExecutor driverVirusTotal){
        try {
            WebElement ips = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > domain-view\").shadowRoot.querySelector(\"#relations\").shadowRoot.querySelector(\"div > vt-ui-expandable.mb-3.resolutions > span > vt-ui-resolution-list\").shadowRoot.querySelector(\"div > div.content\")");
            for (WebElement ip : ips.findElements(By.tagName("a"))) {
                if (ip.getText().isBlank())
                    continue;
                referencedIPs.add(ip.getText().strip());
            }
        } catch (JavascriptException e){
            e.printStackTrace();
            referencedIPs.add("No referenced IPs");
        }
    }

    private void urlFiles(JavascriptExecutor driverVirusTotal){
        try {
            WebElement files = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > domain-view\").shadowRoot.querySelector(\"#relations\").shadowRoot.querySelector(\"#communicating\").shadowRoot.querySelector(\"div > table > tbody\")");
            for (WebElement file : files.findElements(By.tagName("a"))) {
                if (file.getText().isBlank())
                    continue;
                referencedFiles.add(file.getText().strip());
            }
        } catch (JavascriptException e){
            e.printStackTrace();
            referencedFiles.add("No referenced Files");
        }
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


    public String getUrl() {
        return url;
    }

    public String getFinalURL() {
        return finalURL;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public ArrayList<String> getUrlCategories() {
        return urlCategories;
    }

    public ArrayList<String> getReferencedIPs() {
        return referencedIPs;
    }

    public ArrayList<String> getReferencedFiles() {
        return referencedFiles;
    }

    public int getWebRating() {
        return webRating;
    }

    @Override
    public String toString() {
        return "VirusTotalURL{" +
                "url='" + url + '\'' +
                ", finalURL='" + finalURL + '\'' +
                ", urlCategories=" + urlCategories +
                ", webRating=" + webRating +
                '}';
    }
}
