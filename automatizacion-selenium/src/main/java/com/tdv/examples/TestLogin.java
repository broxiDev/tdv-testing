package com.tdv.examples;

import com.tdv.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestLogin {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://saucedemo.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.iniciarSesion("standard_user", "secret_sauce");

        String resultadoEsperado = "Products";
        WebElement tituloProductos = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title"))
        );

        String resultadoObtenido = tituloProductos.getText();
        if (resultadoObtenido.equals(resultadoEsperado)) {
            System.out.println("¡TEST EXITOSO! El título de la página es correcto: " + resultadoObtenido);
        } else {
            System.out.println("TEST FALLIDO. Se esperaba '" + resultadoEsperado + "' pero se obtuvo: " + resultadoObtenido);
        }
        //driver.quit();
    }
}
