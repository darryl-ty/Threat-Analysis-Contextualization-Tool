package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class URLVoid extends Thread{

    private final String url;
    private String dateRegistered;
    private String serverLocation;
    private String city;
    private String region;
    private int webRating;

    public URLVoid(String url){
        this.url = url;
        this.dateRegistered = "";
        this.serverLocation = "";
        this.city = "";
        this.region = "";
        this.webRating = 0;
    }

    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateURLVoidWebDriver(driver);
        urlVoidInfoCollection(driver);
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }

    private void initiateURLVoidWebDriver(WebDriver driverURLVoid){
        urlVoidWebsiteNav(driverURLVoid);
    }

    private void urlVoidWebsiteNav(WebDriver driverURLVoid){
        driverURLVoid.get("https://www.urlvoid.com/");
        sleepForASecond();

        inputUrlToSearch((JavascriptExecutor) driverURLVoid);
        searchButtonClick((JavascriptExecutor) driverURLVoid);
    }

    private void inputUrlToSearch(JavascriptExecutor driverURLVoid){
        WebElement searchBar = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"#hf-domain\")");
        searchBar.sendKeys(url);
    }

    private void searchButtonClick(JavascriptExecutor driverURLVoid){
        WebElement searchButton = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"#header-form > div > span > button\")");
        searchButton.click();
    }

    private void urlVoidInfoCollection(WebDriver driverURLVoid){
        urlDateRegistered((JavascriptExecutor) driverURLVoid);
        urlServerLocation((JavascriptExecutor) driverURLVoid);
        urlCityLocation((JavascriptExecutor) driverURLVoid);
        urlStateLocation((JavascriptExecutor) driverURLVoid);
        urlRating((JavascriptExecutor) driverURLVoid);
    }

    private void urlDateRegistered(JavascriptExecutor driverURLVoid){
        WebElement registered = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"body > div.container > div:nth-child(2) > div.panel-body > div > table > tbody > tr:nth-child(4) > td:nth-child(2)\")");
        dateRegistered = registered.getText().split("\\|")[0].strip();
    }

    private void urlServerLocation(JavascriptExecutor driverURLVoid){
        WebElement serverLocale = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"body > div.container > div:nth-child(2) > div.panel-body > div > table > tbody > tr:nth-child(9) > td:nth-child(2)\")");
        serverLocation = serverLocale.getText();
    }

    private void urlCityLocation(JavascriptExecutor driverURLVoid){
        WebElement cityLocale = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"body > div.container > div:nth-child(2) > div.panel-body > div > table > tbody > tr:nth-child(11) > td:nth-child(2)\")");
        city = cityLocale.getText();
    }

    private void urlStateLocation(JavascriptExecutor driverURLVoid){
        WebElement state = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"body > div.container > div:nth-child(2) > div.panel-body > div > table > tbody > tr:nth-child(12) > td:nth-child(2)\")");
        region = state.getText();
    }

    private void urlRating(JavascriptExecutor driverURLVoid){
        WebElement rating = (WebElement) driverURLVoid.executeScript("return document.querySelector(\"body > div.container > div:nth-child(2) > div.panel-body > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > span\")");
        webRating = Integer.parseInt(rating.getText().strip().split("/")[0]);
    }

    private static void sleepForASecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUrl() {
        return url;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public String getServerLocation() {
        return serverLocation;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public int getWebRating() {
        return webRating;
    }

    @Override
    public String toString() {
        return "URLVoid{" +
                "url='" + url + '\'' +
                ", dateRegistered='" + dateRegistered + '\'' +
                ", serverLocation='" + serverLocation + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", webRating=" + webRating +
                '}';
    }
}
