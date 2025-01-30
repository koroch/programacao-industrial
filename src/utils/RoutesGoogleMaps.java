package utils;

import java.time.Duration;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoutesGoogleMaps {

    private double kms;

    public RoutesGoogleMaps(String origem, String destino) {
        JOptionPane.showMessageDialog(null, "Aguarde!... Estamos buscando a quilometragem no Google Maps");
        int retry = 0;
        // Configura o caminho do ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Configura o ChromeDriver em modo headless (opcional)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            executa(origem, destino, driver);
        } catch (Exception e) {
            System.out.println("Retry "+retry);
            if(retry <= 0) {
                executa(origem, destino.split(", ")[1], driver);
            }else if(retry == 1){
                executa(origem, destino, driver);
            }else{
                executa(origem, destino.split(", ")[1], driver);
                System.out.println("Destino com erro: "+destino);
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Deu erro ao salvar! Tente novamente");
                return;
            }
        } finally {
            driver.quit();
        }
    }
    
    private void executa(String origem, String destino, WebDriver driver){
    // Acessa o Google Maps no modo de rotas
        driver.get("https://www.google.com/maps/dir/");

        // Configura o WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Insere a origem
        WebElement inputOrigem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[aria-label='Escolher ponto de partida ou clicar no mapa...']")));
        inputOrigem.sendKeys(origem);

        // Insere o destino
        WebElement inputDestino = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@aria-label, 'Informe o destino')]")));
        inputDestino.sendKeys(destino);
        inputDestino.sendKeys(Keys.ENTER);

        // Aguarda o carregamento dos resultados
        WebElement distanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='section-directions-trip-0']//div[contains(text(), 'km')]")));

        // Extrai e converte a distância para double
        String infoText = distanceElement.getText();
        this.kms = parseDistance(infoText);
    }

    // Método para extrair a distância em quilômetros
    private double parseDistance(String text) {
        try {
            String distanceString = text.split(" ")[0].replace(",", ".");
            return Double.parseDouble(distanceString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro
        }
    }

    public double getKms() {
        return kms;
    }
}
