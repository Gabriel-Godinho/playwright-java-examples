package com.api;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.List;

public class WindyExample {
    public static void main(String[] args) {
        // Configura o Playwright
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

            // Cria um novo contexto de navegador com permissões para geolocalização
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setGeolocation(-26.3051, -48.8461) // Inicializa a localização de Joinville - SC
                    .setPermissions(List.of("geolocation"))); // Permite acesso à geolocalização

            // Cria uma nova página dentro do contexto
            Page page = context.newPage();

            // Acessa o Windy.com
            page.navigate("https://www.windy.com");

            // Espera o carregamento do mapa
            page.waitForTimeout(5000); // Espera 5 segundos para garantir que o mapa carregue

            // Aguarda o mapa se reposicionar com a nova localização
            page.waitForTimeout(5000); // Espera 5 segundos para garantir que o mapa se reposicione

            // Tira um print da tela
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("./src/main/resources/img/windy_screenshot.png"))
                    .setFullPage(true)); // Print da página inteira

            // Fecha o navegador
            browser.close();
        }  catch (Exception e) {
            System.err.println("Erro! (;-;)");
        }
    }
}
