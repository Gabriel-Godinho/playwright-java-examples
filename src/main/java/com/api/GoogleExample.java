package com.api;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class GoogleExample {
    public static void main(String[] args) {
        // Configura o Playwright
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

            // Cria um novo contexto com a opção de gravação de vídeo
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("./src/main/resources/videos/")) // Define o diretório para salvar os vídeos
                    .setRecordVideoSize(1280, 720)); // Define a resolução do vídeo

            // Cria uma nova página dentro do contexto
            Page page = context.newPage();

            // Acessa o Google
            page.navigate("https://www.google.com");

            // Realiza uma busca no Google
            page.locator("[name='q']").fill("Playwright Java tutorial");
            page.locator("[name='q']").press("Enter");

            // Espera alguns segundos e clica no primeiro resultado
            page.waitForSelector("h3");
            page.locator("h3").first().click();

            // Espera mais alguns segundos para gravação
            page.waitForTimeout(5000);

            // Fecha o navegador
            browser.close();
        }  catch (Exception e) {
            System.err.println("Erro! (;-;)");
        }
    }
}