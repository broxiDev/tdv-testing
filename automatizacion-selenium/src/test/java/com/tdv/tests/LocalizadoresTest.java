package com.tdv.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizadoresTest extends BaseTest {

    @Test
    void pruebaLocalizadoresReales() {
        driver.get("https://www.saucedemo.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement txtUsuario = driver.findElement(By.id("user-name"));
        txtUsuario.sendKeys("standard_user");

        WebElement btnLogin = driver.findElement(By.name("login-button"));

        WebElement txtPassword = driver.findElement(By.cssSelector("input[data-test='password']"));
        txtPassword.sendKeys("secret_sauce");

        btnLogin.click();

        WebElement tituloProductos = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title"))
        );
        System.out.println("¡Inicio de sesión exitoso! Pantalla actual: " + tituloProductos.getText());

        WebElement txtFooterCopy = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".footer_copy"))
        );
        System.out.println("Contenido del Footer encontrado: " + txtFooterCopy.getText());

        assertEquals("Products", tituloProductos.getText());
    }
}
