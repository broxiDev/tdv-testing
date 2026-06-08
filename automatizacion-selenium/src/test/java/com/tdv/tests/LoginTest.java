package com.tdv.tests;

import com.tdv.pages.LoginPage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    private static LoginPage loginPage;

    @BeforeAll
    static void initLogin() {
        driver.get("https://saucedemo.com");
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    void loginTest() {
        loginPage.iniciarSesion("standard_user", "secret_sauce");
    }

    @Test
    @Order(2)
    void tituloTest() {
        String resultadoEsperado = "Products";
        assertEquals(resultadoEsperado, loginPage.getTitulo());
    }
}
