package com.tdv.examples;

import com.tdv.pages.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SearchPageMain {

    private static WebDriver driver;

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.get("https://google.com.ar");

        SearchPage searchPage = new SearchPage(driver);
        searchPage.buscar("The Simpsons");
        // driver.close();
    }
}
