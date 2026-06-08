package com.tdv.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EjemploTest extends BaseTest {

    @Test
    void abrirGoogle() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        assertTrue(title.contains("Google"));
    }
}
