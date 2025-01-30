/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Koroch
 */
public class Authenticate {
    
    private static final String AUTH_URL = "https://painel.rastrek.com.br/auth";
    private static CookieStore cookieStore = new BasicCookieStore();
    
    public CookieStore authenticate() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            // Cria a requisição de autenticação
            HttpPost authRequest = new HttpPost(AUTH_URL);
            authRequest.setHeader("Authorization", "Bearer e3adde5a2a0e52c5bddcf7f7566a8eed6764529989f6e");
            authRequest.setHeader("User-Agent", "Mozilla/5.0");

            // Configura o corpo da requisição como multipart/form-data
            HttpEntity authEntity = MultipartEntityBuilder.create()
                    .addTextBody("usuario", "sgaautomacao")
                    .addTextBody("senha", "1698083401")
                    .build();
            authRequest.setEntity(authEntity);

            // Executa a requisição
            HttpResponse response = httpClient.execute(authRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Código de resposta da autenticação: " + statusCode);

            if (statusCode == 200 || statusCode == 303) {
                System.out.println("Sessão iniciada com sucesso. Cookies armazenados:");
                cookieStore.getCookies().forEach(cookie -> {
                    System.out.println(cookie.getName() + "=" + cookie.getValue());
                });
                
            } else {
                System.err.println("Falha na autenticação.");
                System.err.println(EntityUtils.toString(response.getEntity()));
            }
            return cookieStore;
        } 
    }
}
