package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GeocodingGoogleMaps {

    private double latitude;
    private double longitude;

    public GeocodingGoogleMaps(String search) {
        // Defina o caminho do seu ChromeDriver (ajuste conforme necessário)
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Inicializar o ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        try {
            // Criar um WebDriverWait para esperar elementos carregarem

            // Abrir o Google Maps
            driver.get("https://www.google.com/maps");

            // Esperar o campo de busca estar visível
            WebElement searchBoxInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchboxinput")));
            searchBoxInput.sendKeys(search);

            // Esperar o botão de pesquisa estar clicável
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchbox-searchbutton")));
            searchButton.click();

            // Esperar que a URL mude (indicando que a busca foi concluída)
            wait.until(ExpectedConditions.urlContains("@"));

            // Capturar a URL da página após a pesquisa (isso pode incluir as coordenadas)
            String currentUrl = driver.getCurrentUrl();
            String sub1 = currentUrl.split("@")[1];
            String sub2 = sub1.split("/data=")[0];
            String[] coordenadas = sub2.split(",");
            System.out.println("URL da pesquisa: " + currentUrl);

            this.latitude = Double.parseDouble(coordenadas[0]);
            this.longitude = Double.parseDouble(coordenadas[1]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
