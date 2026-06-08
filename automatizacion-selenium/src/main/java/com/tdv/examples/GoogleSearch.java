package com.tdv.examples;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearch {

    private WebDriver driver;

    public void launchBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
    }

    public void launchTest() {
        driver.navigate().to("https://www.google.com.ar");
        System.out.println("Entró en " + driver.getTitle());

        Duration time = Duration.ofMillis(4000);
        WebDriverWait wait = new WebDriverWait(driver, time);
        By elementLocator = By.name("q");

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        element.sendKeys("The Simpsons");

        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnK")));
        element.click();
    }

    public void closeDriver() {
        if (driver != null) {
            driver.close();
            System.out.println("Google terminó satisfactoriamente");
        }
    }
}
