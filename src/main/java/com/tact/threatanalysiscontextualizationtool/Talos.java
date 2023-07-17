package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Talos extends Thread{

    private final String url;
    private String location;
    private int webRating;

    public Talos(String url){
        this.url = url;
        this.location = "";
        this.webRating = 0;
    }

    public void run(){
        WebDriver driver = createWebDriverWithOptions();
        initiateTalosWebDriver(driver);
        talosInfoCollection(driver);
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        options.addArguments("--disable-extensions");
        options.addArguments("--profile-directory=Default");
        options.addArguments("--incognito");
        options.addArguments("--disable-plugins-discovery");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private void initiateTalosWebDriver(WebDriver driverTalos){
        talosWebsiteNav(driverTalos);
    }

    private void talosWebsiteNav(WebDriver driverTalos) {
        driverTalos.get("https://talosintelligence.com/reputation_center/");
//        sleepForATime();

        inputUrlToSearch((JavascriptExecutor) driverTalos);
        searchButtonClick((JavascriptExecutor) driverTalos);
    }
    private void inputUrlToSearch(JavascriptExecutor driverTalos) {
        WebElement searchBar = (WebElement) driverTalos.executeScript("return document.querySelector(\"#rep-lookup\")");
        searchBar.sendKeys(this.getUrl());
    }

    private void searchButtonClick(JavascriptExecutor driverTalos) {
        WebElement searchButton = (WebElement) driverTalos.executeScript("return document.querySelector(\"#search-form > button\")");
        searchButton.click();
    }


    private void talosInfoCollection(WebDriver driverTalos){

    }

    private static void sleepForATime() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getLocation() {
        return location;
    }

    public int getWebRating() {
        return webRating;
    }

}
