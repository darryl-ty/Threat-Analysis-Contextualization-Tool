package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class VirusTotalURL extends Thread{

    private final String url;

    public VirusTotalURL(String url){
        this.url = url;
    }

    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateVirusTotalWebDriver(driver);
//        vtInfoCollection(driver);
    }

    private void initiateVirusTotalWebDriver(WebDriver driverVirusTotal) {
        vtWebsiteNav(driverVirusTotal);
    }

    private void vtWebsiteNav(WebDriver driverVirusTotal) {
        driverVirusTotal.get("https://www.virustotal.com/gui/home/url");
        sleepForASecond();

        searchForURL((JavascriptExecutor) driverVirusTotal);
        navigateToDetailsTab((JavascriptExecutor) driverVirusTotal);
    }

    private void searchForURL(JavascriptExecutor driverVirusTotal){
        WebElement searchBar = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > home-view\").shadowRoot.querySelector(\"#urlSearchInput\")");
        searchBar.sendKeys(url);
        searchBar.sendKeys(Keys.ENTER);
    }

    private void navigateToDetailsTab(JavascriptExecutor driverVirusTotal){
        WebElement detailsTab = (WebElement) driverVirusTotal.executeScript("return document.querySelector(\"#view-container > file-view\").shadowRoot.querySelector(\"#report\").shadowRoot.querySelector(\"div > div:nth-child(2) > div > ul > li:nth-child(3) > a\")");
        detailsTab.click();
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }

    private static void sleepForASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
