package utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import programacao.model.DadosProjeto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Koroch
 */
public class SheetDBProvider {
    private static final String sheetDbApiId = "sthkew9zogk62"; //criar lançamentos
    
    public SheetDBProvider() {}
    
    public String saveData(DadosProjeto projeto) throws MalformedURLException, IOException {
        String apiUrl = "https://sheetdb.io/api/v1/" + sheetDbApiId;

            // Gera o JSON usando Gson
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
            
            String jsonData = "{ \"data\": [" + gson.toJson(projeto) + "] }";
            System.out.println("JSON Enviado: " + jsonData);

            // Configura a conexão HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            // Envia os dados JSON
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Lê a resposta
            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
                return "Dados do projeto salvos com sucesso!";
            } else {
                return "Falha ao salvar os dados do projeto. Código de resposta: " + responseCode;
            }
        
    }
}

