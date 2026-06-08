package com.tdv.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class Selenium4Test extends BaseTest {

    @Test
    void loginConLocalizadoresRelativos() {
        driver.get("https://saucedemo.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Ancla principal: campo usuario por ID
        WebElement txtUsuario = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))
        );
        txtUsuario.sendKeys("standard_user");

        // 2. Selenium 4: .below() — campo password debajo del usuario
        WebElement txtPasswordRelativo = driver.findElement(
            with(By.tagName("input")).below(txtUsuario)
        );
        txtPasswordRelativo.sendKeys("secret_sauce");

        // 3. Selenium 4 combinado: botón submit debajo del campo password
        WebElement btnLoginRelativo = driver.findElement(
            with(By.cssSelector("input[type='submit']")).below(txtPasswordRelativo)
        );
        btnLoginRelativo.click();

        WebElement tituloProductos = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title"))
        );
        System.out.println("¡Inicio de sesión exitoso usando Selenium 4! Pantalla: " + tituloProductos.getText());
        assertEquals("Products", tituloProductos.getText());
    }
}
