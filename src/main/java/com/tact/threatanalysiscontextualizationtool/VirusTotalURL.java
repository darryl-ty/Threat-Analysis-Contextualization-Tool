package com.tact.threatanalysiscontextualizationtool;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    }

    private WebDriver createWebDriverWithOptions(){
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");

        return new ChromeDriver(options);
    }
}
