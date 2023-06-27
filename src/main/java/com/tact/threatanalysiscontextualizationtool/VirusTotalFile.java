package com.tact.threatanalysiscontextualizationtool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class VirusTotalFile extends Thread {

    private final String fileURI;
    private int threatRating;

    public VirusTotalFile(String sourceFile){
        fileURI = sourceFile;
    }
    public void run(){
        initiateVirusTotalWebDriver();
    }

    private void initiateVirusTotalWebDriver(){
        WebDriver driverVirusTotal = createWebDriver();
    }

    private WebDriver createWebDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }
}
