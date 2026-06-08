package com.tdv.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    @FindBy(name = "q")
    private WebElement q;

    @FindBy(name = "btnK")
    private WebElement btnK;

    private WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void buscar(String busqueda) {
        q.sendKeys(busqueda);

        Duration time = Duration.ofMillis(8000);
        WebDriverWait wait = new WebDriverWait(driver, time);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnK")));
        element.click();
    }
}
