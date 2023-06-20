package com.tact.threatanalysiscontextualizationtool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class VirusTotalFile extends Thread {

    private int threatRating;

    public VirusTotalFile(String sourceFile){

    }
    public void run(){
        Document doc = Jsoup.parse("https://www.virustotal.com/gui/file/43d54b8b86ae2493574243c900db4aef7fc5dd96ea5188ceaafecda01c02130b/details");
        System.out.println(doc.title());
    }
}
