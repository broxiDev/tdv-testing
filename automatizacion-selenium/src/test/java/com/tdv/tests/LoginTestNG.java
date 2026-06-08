package com.tdv.tests;

import com.tdv.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTestNG {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void initPageFactory() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.get("https://saucedemo.com");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test(priority = 1)
    public void loginTest() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");
    }

    @Test(priority = 2)
    public void tituloTest() {
        String resultadoEsperado = "Products";
        Assert.assertEquals(loginPage.getTitulo(), resultadoEsperado);
    }

    @Test(priority = 3)
    public void finishTest() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
        System.out.println("Login Page terminó satisfactoriamente");
        Assert.assertNull(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
