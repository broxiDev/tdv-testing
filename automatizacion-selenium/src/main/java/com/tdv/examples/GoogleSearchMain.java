package com.tdv.examples;

public class GoogleSearchMain {

    public static void main(String[] args) {
        GoogleSearch buscador = new GoogleSearch();
        buscador.launchBrowser();
        buscador.launchTest();
        // Para observar lo sucedido, comentar la siguiente línea para que no se cierre
        // buscador.closeDriver();
    }
}
