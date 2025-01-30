/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import programacao.model.Carro;

/**
 *
 * @author Koroch
 */
public class FetchDataCarRastrek {
    private CookieStore cookieStore = new BasicCookieStore();
    private static final String QUERY_URL = "https://painel.rastrek.com.br/rastreamento/getHistorico";
    
    public FetchDataCarRastrek(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
    
    public JsonObject fetchData(String dataInicial, String dataFinal, String imeiCarro, List<Carro> carros) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(this.cookieStore)
                .build()) {

            // Cria a requisição de consulta
            HttpPost queryRequest = new HttpPost(QUERY_URL);
            queryRequest.setHeader("User-Agent", "Mozilla/5.0");

            // Configura o corpo da requisição
            HttpEntity queryEntity = MultipartEntityBuilder.create()
                    .addTextBody("dataInicial", dataInicial+" 00:00")
                    .addTextBody("dataFinal", dataFinal+" 00:00")
                    .addTextBody("imei", imeiCarro)
                    .build();
            queryRequest.setEntity(queryEntity);

            // Executa a requisição
            HttpResponse response = httpClient.execute(queryRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Código de resposta da consulta: " + statusCode);

            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                //System.out.println(jsonObject);

                if (jsonObject.has("data")) {
                    return jsonObject;
                } else {
                    System.err.println("O objeto 'data' não está presente.");
                    return null;
                }
            } else {
                System.err.println("Falha na consulta.");
                System.err.println(EntityUtils.toString(response.getEntity()));
            }
        }
        return null;
    }
}
