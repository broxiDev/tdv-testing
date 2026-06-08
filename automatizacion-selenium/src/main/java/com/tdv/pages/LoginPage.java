package com.tdv.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Repositorio de elementos encapsulado (oculto al test)
    private By txtUsuario = By.id("user-name");
    private By txtPassword = By.cssSelector("input[data-test='password']");
    private By btnLogin = By.name("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }

    // 2. Acciones expuestas de forma semántica
    private void escribirUsuario(String usuario) {
        driver.findElement(txtUsuario).sendKeys(usuario);
    }

    private void escribirContrasena(String password) {
        driver.findElement(txtPassword).sendKeys(password);
    }

    private void hacerClicEnIngresar() {
        driver.findElement(btnLogin).click();
    }

    public String getTitulo() {
        WebElement tituloProductos = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title"))
        );
        return tituloProductos.getText();
    }

    public void iniciarSesion(String usuario, String password) {
        this.escribirUsuario(usuario);
        this.escribirContrasena(password);
        this.hacerClicEnIngresar();
    }
}
